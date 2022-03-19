package org.setana.treenity.entity;

import java.time.LocalDate;
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
import org.setana.treenity.exception.ErrorCode;
import org.setana.treenity.exception.NotAcceptableException;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class UserItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_item_Id")
    private Long id;

    private Integer totalCount;

    private Integer purchaseCount;

    private LocalDate purchaseDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public UserItem(User user, Item item) {
        this(user, item, 0, 0);
    }

    public UserItem(User user, Item item, int totalCount, int purchaseCount) {
        this.user = user;
        this.item = item;
        this.totalCount = totalCount;
        this.purchaseCount = purchaseCount;
    }

    // tree 가 이미 존재하는 경우, 해당 아이템을 차감하고 tree 에 적용
    public void apply(Tree tree) {
        consume();
        item.apply(tree);
    }

    // tree 가 존재하지 않는 경우, 해당 아이템의 차감만 적용
    public void consume() {
        validateCount();
        totalCount -= 1;
    }

    private void validateCount() {
        if (totalCount <= 0) {
            throw new NotAcceptableException(ErrorCode.USER_ITEM_NOT_ENOUGH);
        }
    }

    public void purchase() {
        validateLimit();
        user.purchaseItem(item);

        totalCount += 1;
        purchaseCount = Objects.isNull(item.getPurchaseLimit())
            ? (purchaseCount + 1)
            : (purchaseCount + 1) % (item.getPurchaseLimit() + 1);
        purchaseDate = LocalDate.now();
    }

    private void validateLimit() {
        Integer purchaseLimit = item.getPurchaseLimit();

        if (!Objects.isNull(purchaseLimit)
            && purchaseCount >= purchaseLimit
            && purchaseDate.isEqual(LocalDate.now())) {
            throw new NotAcceptableException(ErrorCode.USER_ITEM_LIMIT);
        }
    }

    public void provide() {
        LocalDateTime lastLogin = user.getLastLogin();
        LocalDate today = LocalDate.now();

        if (lastLogin == null || !lastLogin.toLocalDate().equals(today)) {
            totalCount += 1;
        }
    }
}
