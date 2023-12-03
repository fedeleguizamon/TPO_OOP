package com.tpo_oop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SpacialObserver {
    @Setter
    private Type type;
    @Setter
    private String name;
    @Setter
    private double distance;
    @Setter
    private List<String> message;
    @Column(name = "CoordinateX")
    @Setter
    private double coordinateX;
    @Column(name = "CoordinateY")
    @Setter
    private double coordinateY;
}
