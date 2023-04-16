package com.dietrich.fiducial.model;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class NameCreationResponse {
    private final Set<String> names = new HashSet<>();
    private int total = 0;

    public void addName(String name) {
        names.add(name);
        total++;
    }
}
