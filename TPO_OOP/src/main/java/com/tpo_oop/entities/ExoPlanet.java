package com.tpo_oop.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Random;

@NoArgsConstructor
@Getter
public class ExoPlanet {
    @Setter
    private double coordinateX;
    @Setter
    private double coordinateY;
    @Setter
    private String message;
    @Setter
    private int radio;
    @Setter
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
