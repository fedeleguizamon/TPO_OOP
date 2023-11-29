package com.tpo_oop.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "SpacialObservers")
@NoArgsConstructor
@Getter
public class SpacialObserver {
    @Id
    private int id;
    @Column(name = "Type")
    @Setter
    private Type type;
    @Column(name = "Name")
    @Setter
    private String name;
    @Column(name = "Distance")
    @Setter
    private double distance;
    @ElementCollection
    @CollectionTable(name = "ObserverMessages", joinColumns = @JoinColumn(name = "observer_id"))
    @Column(name = "Message")
    @Setter
    private List<String> message;
    @Column(name = "CoordinateX")
    @Setter
    private double coordinateX;
    @Column(name = "CoordinateY")
    @Setter
    private double coordinateY;

    public SpacialObserver(int id, Type type, String name, double distance, List<String> message, double coordinateX, double coordinateY) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.distance = distance;
        this.message = message;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }
}
