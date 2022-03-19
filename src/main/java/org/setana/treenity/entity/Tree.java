package org.setana.treenity.entity;

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
import org.locationtech.jts.geom.Point;
import org.setana.treenity.model.Location;
import org.setana.treenity.util.Calculate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Tree extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tree_id")
    private Long id;

    private String cloudAnchorId;

    @Column(name = "tree_name")
    private String name;

    private Point point;

    @Column(name = "tree_description")
    private String description;

    private Integer level = 1;

    private Integer bucket = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public Tree(Location location, User user, Item item) {
        this(location, null, user, item);
    }

    public Tree(Location location, String cloudAnchorId, User user, Item item) {
        this(location, cloudAnchorId, null, user, item);
    }

    public Tree(Location location, String cloudAnchorId, String name, User user, Item item) {
        this.point = location.toPoint();
        this.cloudAnchorId = cloudAnchorId;
        this.name = name;
        this.user = user;
        this.item = item;
    }

    public Location getLocation() {
        return new Location(point.getX(), point.getY());
    }

    public void waterPlant() {
        int multiple = Calculate.getMultiple(item.getCost());
        int perLevel = multiple * level;

        level += (bucket + 1) / perLevel;
        bucket += (bucket + 1) % perLevel;
    }

    public void updateCloudAnchorId(String cloudAnchorId) {
        this.cloudAnchorId = cloudAnchorId;
    }
}
