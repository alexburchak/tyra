package org.alexburchak.tyra.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author alexburchak
 */
@AllArgsConstructor
@Getter
@ToString
public class NameValuePair implements Serializable {
    private String name;
    private String value;
}
