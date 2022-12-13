/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewmodel;

import model.GameObject;

/**
 *
 * @author dell
 */
public class Camera {
    private float x, y;
    
    public Camera(float x, float y){
        this.x = x;
        this.y = y;
    }
    public void tick(GameObject Player){
//        x += 0.5;
    }
    public float getX() {
        return x;
    }
    public void setX(float x) {
        this.x = x;
    }
    public float getY() {
        return y;
    }
    public void setY(float y) {
        this.y = y;
    }
}
