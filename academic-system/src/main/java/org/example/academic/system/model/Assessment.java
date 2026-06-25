package org.example.academic.system.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public abstract class Assessment {
    private double value;
    private double weight;
}