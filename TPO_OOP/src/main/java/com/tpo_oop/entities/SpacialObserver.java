package com.tpo_oop.entities;

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
    @Setter
    private double coordinateX;
    @Setter
    private double coordinateY;
}
