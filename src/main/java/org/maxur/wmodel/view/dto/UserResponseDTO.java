package org.maxur.wmodel.view.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.maxur.wmodel.domain.User;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
@SuppressWarnings("unused")
public class UserResponseDTO {

    @JsonProperty
    public String id;

    @JsonProperty
    public String name;

    @JsonProperty
    public String groupId;

    @JsonProperty
    public String groupName;

    public UserResponseDTO() {
    }

    public static UserResponseDTO from(final User user) {
        final UserResponseDTO dto = new UserResponseDTO();
        dto.id = user.getId();
        dto.name = user.getName();
        dto.groupId = user.getGroup().getId();
        dto.groupName = user.getGroup().getName();
        return dto;
    }

}