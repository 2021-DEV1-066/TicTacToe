package com.example.tictactoe.rest.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class ElementInformation {

    @Min(0)
    private int x;

    @Min(0)
    private int y;

    @NotBlank
    private String value;

    @JsonCreator
    public ElementInformation(@JsonProperty("location_x") int x,
                              @JsonProperty("location_y") int y,
                              @JsonProperty("element") String value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
