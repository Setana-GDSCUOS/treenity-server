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
import org.setana.treenity.model.Location;
import org.setana.treenity.util.GeometryUtil;
import org.springframework.data.geo.Point;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Tree extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tree_id")
    private Long id;

    private Point point;

    private String description;

    @Column(name = "tree_image_path")
    private String imagePath;

    private Integer level = 0;

    private Integer exp = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public Tree(Location location, User user, Item item) {
        this.point = location.toPoint();
        this.user = user;
        this.item = item;
    }

    public Location getLocation() {
        return new Location(point.getX(), point.getY());
    }

    public void validatePlant(Location other) {
        Location location = new Location(point.getX(), point.getY());
        double distance = GeometryUtil.calculateDistance(location, other);

        if (distance <= 0.001)
            throw new IllegalStateException();
    }

    public void waterPlant() {
        // TODO : 나무 성장 시 exp 와 level 상승 고려 필요
        level += 1;
    }
}
