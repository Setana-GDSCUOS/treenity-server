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

    public Item(String name, ItemType itemType, Integer cost) {
        this(name, itemType, cost, null);
    }

    public Item(String name, ItemType itemType, Integer cost, String imagePath) {
        this(name, itemType, cost, imagePath, null);
    }

    public Item(String name, ItemType itemType, Integer cost, String imagePath,
        Integer purchaseLimit) {
        this.name = name;
        this.imagePath = imagePath;
        this.cost = cost;
        this.itemType = itemType;
        this.purchaseLimit = purchaseLimit;
    }

    public void apply(Tree tree) {
        if (itemType == ItemType.WATER) {
            tree.waterPlant();
        }
    }

}
