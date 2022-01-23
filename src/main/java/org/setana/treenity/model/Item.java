package org.setana.treenity.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String itemName;

    private Integer cost;

    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    @OneToMany(mappedBy = "item")
    List<Tree> trees = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    List<UserItem> userItems = new ArrayList<>();
}
