package com.tpo_oop.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Messages {
    private List<String> message1;
    private List<String> message2;
    private List<String> message3;
}
