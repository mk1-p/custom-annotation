package com.example.customannotation.custom.slack;

import lombok.Getter;

import java.awt.*;

@Getter
public enum ColorType {
    GREEN(Color.GREEN),
    BLUE(Color.BLUE),
    RED(Color.RED);

    private Color color;

    ColorType(Color color) {
        this.color = color;
    }

    public String getHexCode() {
        return "#"+Integer.toHexString(this.getColor().getRGB()).substring(2);

    }


}
