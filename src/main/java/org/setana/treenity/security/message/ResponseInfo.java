package org.setana.treenity.security.message;

import lombok.Data;
import org.setana.treenity.security.model.CustomUser;

@Data
public class ResponseInfo {

    private Long userId;
    private String uid;
    private String email;
    private String username;

    public ResponseInfo(CustomUser user) {
        this.userId = user.getUserId();
        this.uid = user.getUid();
        this.email = user.getEmail();
        this.username = user.getUsername();
    }

}
