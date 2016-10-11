package org.alexburchak.tyra.ws;

/**
 * @author alexburchak
 */
public enum OutcomingMessageType {
    /**
     * Success, as reply to {@link IncomingMessageType#TYRA}
     */
    TYRA,
    /**
     * SID is expired or does not exist, as reply to {@link IncomingMessageType#TYRA}
     */
    EXPIRED,
    /**
     * HTTP Request dump message
     */
    REQUEST,
    /**
     * Say bye
     */
    ARYT;

    public String format(String message) {
        return name() + (message != null ? " " + message : "");
    }
}
