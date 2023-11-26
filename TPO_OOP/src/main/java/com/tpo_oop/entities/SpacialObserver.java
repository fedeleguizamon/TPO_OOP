package com.tpo_oop.entities;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
public abstract class SpacialObserver {
    @Column(name = "Name")
    @Setter
    private String name;
    @Column(name = "Distance")
    @Setter
    private double distance;
    @Column(name = "Message")
    @Setter
    private List<String> message;
    @Column(name = "CoordinateX")
    @Setter
    private double coordinateX;
    @Column(name = "CoordinateY")
    @Setter
    private double coordinateY;

    public SpacialObserver(String name, double distance, List<String> message, double coordinateX, double coordinateY) {
        this.name = name;
        this.distance = distance;
        this.message = message;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }
}
