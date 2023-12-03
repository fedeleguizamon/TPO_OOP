package com.tpo_oop.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ObserversRequest {
    private List<Telescope> telescopes;
    private List<Satellite> satellites;

    @NoArgsConstructor
    @Getter
    @Setter
    public static class Telescope {
        private String name;
        private double distance;
        private List<String> message;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class Satellite {
        private String name;
        private double distance;
        private List<String> message;
    }
}
