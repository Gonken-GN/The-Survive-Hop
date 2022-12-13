/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.awt.Color;

import java.awt.Graphics;
/**
 *
 * @author dell
 */
public class Obstacle extends GameObject{
    private int width, height;
    private Color random;
    public Obstacle(int x, int y, Id id, int width, int height, Color random){
        // mmemangil konstruktor parent (GameObject)
        super(x, y, id, width, height);
        this.width = width;
        this.height = height;
        this.random = random;
    }
    @Override
    public void tick() {
       
    }
    // memunculkan obstacle
    @Override
    public void render(Graphics g) {
        g.setColor(random);
        g.fillRect(x, y, width, height);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
