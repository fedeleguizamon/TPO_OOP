package com.tpo_oop.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class RequestHandler {
    @JsonProperty("telescopes")
    private List<SpacialObserver> telescopes;
    @JsonProperty("satellites")
    private List<SpacialObserver> satellites;
}