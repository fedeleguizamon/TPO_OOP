package com.tpo_oop.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Satellites")
@NoArgsConstructor
@Getter
public class Satellite extends SpacialObserver{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public Satellite(String name, double distance, List<String> message, double coordinateX, double coordinateY) {
        super(name, distance, message, coordinateX, coordinateY);
    }
}
