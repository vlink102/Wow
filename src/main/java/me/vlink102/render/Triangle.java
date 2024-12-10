package me.vlink102.render;

import java.awt.*;

public class Triangle {
    Vertex v1;
    Vertex v2;
    Vertex v3;
    Color color;

    public Triangle(Vertex v1, Vertex v2, Vertex v3, Color color) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.color = color;
    }

    public Vertex v1() {
        return v1;
    }

    public Vertex v2() {
        return v2;
    }
    public Vertex v3() {
        return v3;
    }

    public Color color() {
        return color;
    }
}
