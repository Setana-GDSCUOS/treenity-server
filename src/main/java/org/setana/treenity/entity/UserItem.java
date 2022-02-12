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

    private Integer totalCount = 1;

    private Integer purchaseCount = 1;

    private LocalDateTime purchaseDate = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public UserItem(User user, Item item) {
        this.user = user;
        this.item = item;
    }

    public void consume() {
        validateCount();
        totalCount -= 1;
    }

    private void validateCount() {
        if (totalCount <= 0)
            throw new IllegalStateException();
    }

    public void consume(Tree tree) {
        consume();
        item.apply(tree);
    }

}
