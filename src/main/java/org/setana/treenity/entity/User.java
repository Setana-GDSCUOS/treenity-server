package org.setana.treenity.entity;

import static org.setana.treenity.entity.QUser.user;

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

    private Integer dailyWalks = 0;

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

    public UserItem createUserItem(Item item) {
        validatePoint(item);
        point -= item.getCost();

        return new UserItem(this, item);
    }

    private void validatePoint(Item item) {
        if (point < item.getCost())
            throw new IllegalStateException();
    }

    public void addPoint(Integer walks) {
        // TODO : 걸음 수를 포인트로 전환 시 비율 논의 필요
        point += walks / 100;
    }

    public void changeDailyWalks(WalkLog walkLog) {
        this.dailyWalks = walkLog.getWalks();
    }

}
