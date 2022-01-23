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

    private Double longitude;

    private Double latitude;

    private String description;

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
        this(location.getLongitude(), location.getLatitude(), user, item);
    }

    public Tree(Double longitude, Double latitude, User user, Item item) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.user = user;
        this.item = item;
    }

    public void validatePlant(Location location) {
        Location here = new Location(longitude, latitude);
        double distance = Haversine.distance(here, location);

        if (distance > 0.001)
            throw new IllegalStateException();
    }
}
