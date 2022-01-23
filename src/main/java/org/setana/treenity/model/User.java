package org.setana.treenity.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private Long googleId;

    private String username;

    private Integer point = 0;

    @OneToMany(mappedBy = "user")
    List<WalkLog> walkLogs = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    List<UserItem> userItems = new ArrayList<>();

    public User(Long googleId, String username) {
        this(googleId, username, 0);
    }

    public User(Long googleId, String username, Integer point) {
        this.googleId = googleId;
        this.username = username;
        this.point = point;
    }

}
