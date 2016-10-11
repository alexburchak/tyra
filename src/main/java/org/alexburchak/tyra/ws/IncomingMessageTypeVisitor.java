package org.alexburchak.tyra.ws;

import java.io.IOException;

/**
 * @author alexburchak
 */
public interface IncomingMessageTypeVisitor {
    void visitTyra(String payload) throws IOException;

    void visitAryt(String payload) throws IOException;

    void visitUnknown(String payload) throws IOException;
}
