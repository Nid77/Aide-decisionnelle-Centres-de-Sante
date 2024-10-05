/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sae;

import java.awt.Color;
import java.awt.Point;

/**
 *
 * @author Rayan Tail et Lucas laiguillon
 */
public class Cercle {
    private int x;
    private int y;
    private final int radius;
    private final Color color;
    private boolean dragging;
    private int offsetX;
    private int offsetY;
    private String text;


    public Cercle(int x, int y, int radius, Color color, String text) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
        this.dragging = false;
        this.text = text;
}

    public boolean contains(int mouseX, int mouseY, int cX, int cY, int radius) {
    double distance = Math.sqrt(Math.pow(mouseX - cX, 2) + Math.pow(mouseY - cY, 2));
    return distance <= radius;
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRadius() {
        return radius;
    }

    public Color getColor() {
        return color;
    }


    public boolean isDragging() {
        return dragging;
    }

    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffset(int offsetX, int offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }
    
    public String getText(){
        return text;
    }
}
