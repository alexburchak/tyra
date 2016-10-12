package org.alexburchak.tyra.data;

import com.hazelcast.core.IExecutorService;
import com.hazelcast.core.IMap;
import com.hazelcast.core.Member;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author alexburchak
 */
@Component
@Slf4j
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DistributedWebSocketSessionManager {
    @AllArgsConstructor
    private static class CallableTask implements Callable, Serializable {
        private String sid;
        private String value;

        @Override
        public Object call() {
            log.debug("Executing callback for sid {}", sid);

            WebSocketSession session = WEB_SOCKET_SESSION_MAP.get(sid);
            try {
                session.sendMessage(new TextMessage(value));
            } catch (IOException e) {
                log.error("Failed to send message with text body {}", value);
            }
            return null;
        }
    }

    private static final String TYRA_SID_ATTRIBUTE = "Tyra-sid";

    @Autowired
    private IExecutorService executorService;
    @Autowired
    @Qualifier("membersMap")
    private IMap<String, Member> membersMap;
    @Autowired
    private Member member;

    private static final Map<String, WebSocketSession> WEB_SOCKET_SESSION_MAP = new HashMap<>();

    public boolean register(String sid, WebSocketSession session) {
        synchronized (WEB_SOCKET_SESSION_MAP) {
            Member other = membersMap.putIfAbsent(sid, member);
            if (other != null) {
                log.debug("There is already sid {} registered for {}", sid, other);
                return false;
            }

            WEB_SOCKET_SESSION_MAP.put(sid, session);
            session.getAttributes().put(TYRA_SID_ATTRIBUTE, sid);

            log.debug("Registered new WebSocket session for sid {}", sid);
            return true;
        }
    }

    public void send(String sid, String value) {
        Member member = membersMap.get(sid);
        if (member != null) {
            log.debug("Found member {} for sid {}", member.getAddress(), sid);

            executorService.submitToMember(new CallableTask(sid, value), member);
        } else {
            log.debug("No member found for sid {}", sid);
        }
    }

    public void remove(WebSocketSession session) {
        synchronized (WEB_SOCKET_SESSION_MAP) {
            String sid = (String) session.getAttributes().get(TYRA_SID_ATTRIBUTE);
            if (sid != null) {
                if (WEB_SOCKET_SESSION_MAP.remove(sid, session)) {
                    membersMap.remove(sid);

                    log.debug("Removed WebSocket session for sid {}", sid);
                }
            }
        }
    }
}
