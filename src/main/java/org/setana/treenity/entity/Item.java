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

    @Column(name = "item_name")
    private String name;

    @Column(name = "item_description")
    private String description;

    @Column(name = "item_image_path")
    private String imagePath;

    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    private Integer cost;

    private Integer purchaseLimit;

    @OneToMany(mappedBy = "item")
    List<Tree> trees = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    List<UserItem> userItems = new ArrayList<>();

    public Item(String itemName, ItemType itemType, Integer cost) {
        this(itemName, itemType, cost, null);
    }

    public Item(String itemName, ItemType itemType, Integer cost, String imagePath) {
        this.itemName = itemName;
        this.cost = cost;
        this.itemType = itemType;
        this.imagePath = imagePath;
    }

    public void apply(Tree tree) {
        if (itemType == ItemType.WATER) {
            tree.waterPlant();
        }
        // TODO : 물 외에 다른 아이템 사용 시 나무의 성장에 어떤 영향을 줄지 고려
    }

}
