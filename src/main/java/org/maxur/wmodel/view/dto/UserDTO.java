package org.maxur.wmodel.view.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.maxur.wmodel.domain.NotFoundException;
import org.maxur.wmodel.domain.User;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
@SuppressWarnings("unused")
public class UserDTO {

    @JsonProperty
    public int id;

    @JsonProperty
    public String name;

    @JsonProperty
    public int groupId;

    @JsonProperty
    public String groupName;

    public UserDTO() {
    }

    public static UserDTO dto(final User user) {
        final UserDTO dto = new UserDTO();
        dto.id = user.getId();
        dto.name = user.getName();
        try {
            dto.groupId = user.getGroup().getId();
            dto.groupName = user.getGroup().getName();
        } catch (NotFoundException e) {
            // XXX
            assert false;
        }
        return dto;
    }

    public User assemble() {
        return User.make(id, name, groupId);
    }
}