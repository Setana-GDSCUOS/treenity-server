package org.setana.treenity.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
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
import org.setana.treenity.util.Calculate;
import org.setana.treenity.util.Haversine;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Tree extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tree_id")
    private Long id;

    @Column(name = "tree_name")
    private String name;

    @Column(name = "tree_description")
    private String description;

    @Column(name = "tree_image_path")
    private String imagePath;

    @Embedded
    private Location location;

    private Integer level = 1;

    private Integer bucket = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public Tree(Location location, User user, Item item) {
        this.location = location;
        this.user = user;
        this.item = item;
    }

    public void validatePlant(Location other) {
        double distance = Haversine.distance(location, other);

        if (distance <= 0.001)
            throw new IllegalStateException();
    }

    public void waterPlant() {
        int multiple = Calculate.getMultiple(item.getCost());
        int perLevel = multiple * level;

        level += (bucket + 1) / perLevel;
        bucket += (bucket + 1) % perLevel;
    }

}
