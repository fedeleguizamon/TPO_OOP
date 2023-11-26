package com.tpo_oop.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Random;

@Entity
@Table(name = "ExoPlanet")
@NoArgsConstructor
@Getter
public class ExoPlanet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "CoordinateX")
    @Setter
    private double coordinateX;
    @Column(name = "CoordinateY")
    @Setter
    private double coordinateY;
    @Setter
    @Column(name = "Messages")
    private String message;
    @Setter
    @Column(name = "Radio")
    private int radio;
    @Setter
    @Column(name = "Mass")
    private int mass;

    public ExoPlanet(double coordinateX, double coordinateY, String message) {
        Random random = new Random();
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.message = message;
        this.radio = random.nextInt(9001) + 1000;
        this.mass = random.nextInt(901) + 100;
    }
}
