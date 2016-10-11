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

    @Autowired
    private IExecutorService executorService;
    @Autowired
    @Qualifier("membersMap")
    private IMap<String, Member> membersMap;
    @Autowired
    private Member member;

    private static final Map<String, WebSocketSession> WEB_SOCKET_SESSION_MAP = new HashMap<>();

    public void register(String sid, WebSocketSession session) {
        synchronized (WEB_SOCKET_SESSION_MAP) {
            WEB_SOCKET_SESSION_MAP.putIfAbsent(sid, session);
            membersMap.putIfAbsent(sid, member);

            log.debug("Registered new WebSocket session for sid {}", sid);
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
            WEB_SOCKET_SESSION_MAP.entrySet().stream()
                    .filter(e -> e.getValue() == session)
                    .findAny()
                    .ifPresent(e -> {
                        if (WEB_SOCKET_SESSION_MAP.remove(e.getKey()) != null) {
                            membersMap.remove(e.getKey());

                            log.debug("Removed WebSocket session for sid {}", e.getKey());
                        }
                    });
        }
    }
}
