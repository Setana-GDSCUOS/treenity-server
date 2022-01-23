package org.setana.treenity.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class UserItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_item_Id")
    private Long id;

    private LocalDateTime expDate;

    private Boolean isUsed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public UserItem(User user, Item item) {
        this(user, item, null);
    }

    public UserItem(User user, Item item, LocalDateTime expDate) {
        this.user = user;
        this.item = item;
        this.expDate = expDate;
    }

    public void consume() {
        validateExpDate();
        isUsed = true;
    }

    public void validateExpDate() {
        LocalDateTime now = LocalDateTime.now();

        if (!Objects.isNull(expDate) && expDate.isAfter(now))
            throw new IllegalStateException();
    }
}
