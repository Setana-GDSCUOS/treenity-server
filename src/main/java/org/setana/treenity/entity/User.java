package org.setana.treenity.entity;

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

    private Integer totalWalks = 0;

    @OneToMany(mappedBy = "user")
    List<WalkLog> walkLogs = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    List<UserItem> userItems = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    List<Tree> trees = new ArrayList<>();

    public User(Long googleId, String username) {
        this(googleId, username, 0);
    }

    public User(Long googleId, String username, Integer point) {
        this.googleId = googleId;
        this.username = username;
        this.point = point;
    }

    public UserItem createUserItem(Item item) {
        purchaseItem(item);
        return new UserItem(this, item);
    }

    public void purchaseItem(Item item) {
        validatePoint(item);
        point -= item.getCost();
    }

    private void validatePoint(Item item) {
        if (point < item.getCost()) {
            throw new IllegalStateException();
        }
    }

    public void updateTotalWalksAndPoint(Integer walks) {
        // 걸음수 대 포인트 전환 비율 = 100 : 1
        point += walks / 100;
        totalWalks += walks;
    }

    public void changeDailyWalks(WalkLog walkLog) {
        this.dailyWalks = walkLog.getWalks();
    }

}
