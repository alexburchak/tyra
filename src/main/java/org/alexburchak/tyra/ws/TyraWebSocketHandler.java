package org.alexburchak.tyra.ws;

import lombok.extern.slf4j.Slf4j;
import org.alexburchak.tyra.data.DistributedWebSocketSessionManager;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

/**
 * @author alexburchak
 */
@Component
@Slf4j
public class TyraWebSocketHandler extends TextWebSocketHandler {
    @Autowired
    private DistributedWebSocketSessionManager distributedWebSocketSessionManager;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);

        log.debug("Established WebSocket connection for session {}", session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);

        log.debug("Closing WebSocket session {} on connection close", session.getId());

        distributedWebSocketSessionManager.remove(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String payload = message.getPayload();

        IncomingMessageType.handle(payload, new IncomingMessageTypeVisitor() {
            @Override
            public void visitTyra(String sid) throws IOException {
                onTyra(session, sid);
            }

            @Override
            public void visitAryt(String unused) {
                onEcirt(session);
            }

            @Override
            public void visitUnknown(String payload) {
                onUnknown(payload);
            }
        });
    }

    private void onTyra(WebSocketSession session, String sid) throws IOException {
        log.debug("Initializing WebSocket session {} for sid {}", session.getId(), sid);

        distributedWebSocketSessionManager.register(sid, session);

        distributedWebSocketSessionManager.send(sid, OutcomingMessageType.TYRA.format(sid));
    }

    private void onEcirt(WebSocketSession session) {
        log.debug("Closing WebSocket session {}", session.getId());

        IOUtils.closeQuietly(session);
    }

    private void onUnknown(String payload) {
        log.error("Unknown message {}", payload);
    }
}
