package org.setana.treenity.entity;

import java.time.LocalDateTime;
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

    @Column(unique = true)
    private String uid;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;

    private Integer point = 0;

    private Integer dailyWalks = 0;

    private Integer totalWalks = 0;

    private LocalDateTime lastLogin;

    @OneToMany(mappedBy = "user")
    List<WalkLog> walkLogs = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    List<UserItem> userItems = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    List<Tree> trees = new ArrayList<>();

    public User(String uid, String email, String username) {
        this(uid, email, username, 0);
    }

    public User(String uid, String email, String username, Integer point) {
        this.uid = uid;
        this.email = email;
        this.username = username;
        this.point = point;
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

    public void updateLastLogin() {
        lastLogin = LocalDateTime.now();
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
