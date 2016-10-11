package org.alexburchak.tyra.data;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author alexburchak
 */
@Getter
@ToString
public class Cookie implements Serializable {
    private String name;
    private String value;
    private String comment;
    private String domain;
    private int maxAge;
    private String path;
    private boolean secure;
    private int version;

    public Cookie(javax.servlet.http.Cookie cookie) {
        name = cookie.getName();
        value = cookie.getValue();
        comment = cookie.getComment();
        domain = cookie.getDomain();
        maxAge = cookie.getMaxAge();
        path = cookie.getPath();
        secure = cookie.getSecure();
        version = cookie.getVersion();
    }
}
