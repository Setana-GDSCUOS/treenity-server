package org.setana.treenity.entity;

import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 값 타입의 불변성을 위해 Setter 설정 X

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location {

    private double longitude;
    private double latitude;

    public Location(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public boolean equals(Object object) {
        if (object == null || object.getClass() != this.getClass())
            return false;
        return ((Location)object).longitude == this.longitude &&
            ((Location)object).latitude == this.latitude;

    }

}
