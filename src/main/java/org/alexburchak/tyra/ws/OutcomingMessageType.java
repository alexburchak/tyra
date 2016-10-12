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
     * SID is already used, as reply to {@link IncomingMessageType#TYRA}
     */
    USED,
    /**
     * There was attempt to open page with the same SID
     */
    CONCURRENT,
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
