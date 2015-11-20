package org.maxur.wmodel.view.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
@SuppressWarnings("unused")
public class UserRequestDTO {

    @JsonProperty
    public String name;

    @JsonProperty
    public String groupId;

    public UserRequestDTO() {
    }

}