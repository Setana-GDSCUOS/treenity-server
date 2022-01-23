package org.setana.treenity.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String itemName;

    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    private Integer cost;

    @OneToMany(mappedBy = "item")
    List<Tree> trees = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    List<UserItem> userItems = new ArrayList<>();

    public Item(String itemName, ItemType itemType, Integer cost) {
        this.itemName = itemName;
        this.cost = cost;
        this.itemType = itemType;
    }

}
