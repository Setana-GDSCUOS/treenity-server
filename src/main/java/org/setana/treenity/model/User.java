package org.setana.treenity.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private Long googleId;

    private String username;

    private Integer point;

    @OneToMany(mappedBy = "user")
    List<WalkLog> walkLogs = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    List<UserItem> userItems = new ArrayList<>();


}
