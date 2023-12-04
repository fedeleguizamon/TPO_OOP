package com.tpo_oop.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Error {
    private String error;

    public Error() {
        this.error = "{\"error\":\"Error. Por favor intente nuevamente\"}";
    }
}
