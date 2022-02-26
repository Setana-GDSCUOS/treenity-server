package org.setana.treenity.security.message.response;

import lombok.Data;
import org.setana.treenity.security.model.CustomUser;

@Data
public class UserInfo {

    private String uid;
    private String email;
    private String username;

    public UserInfo(CustomUser customUser) {
        this.uid = customUser.getUid();
        this.email = customUser.getEmail();
        this.username = customUser.getUsername();
    }
}
