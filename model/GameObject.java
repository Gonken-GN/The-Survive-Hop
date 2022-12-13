/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.awt.Graphics;
/**
 *
 * @author dell
 */
public abstract class GameObject {
    //buat variabel x, y, enumerasi id, x dan y velocity, serta speed enemy atau nomor pemain
    protected int x, y;
    protected Id id;
    protected float vel_x;
    protected float vel_y;
    protected boolean onAir = false;
    protected boolean fall = false;
    protected int width, height;
     //inisialisasi nilai semua atribut, kecuali x dan y velocity
    public GameObject(int x, int y, Id id){
        this.x = x;
        this.y = y;
        this.id = id;
    }
    public GameObject(int x, int y, Id id, int width, int height){
        this.x = x;
        this.y = y;
        this.id = id;
        this.width = width;
        this.height = height;
    }
    //buat method abstract tick dan render untuk diimplementasikan di childnya
    public abstract void tick();
    public abstract void render(Graphics g);
    //getter dan setter setiap atribut
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

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public float getVel_x() {
        return vel_x;
    }

    public void setVel_x(float vel_x) {
        this.vel_x = vel_x;
    }

    public float getVel_y() {
        return vel_y;
    }

    public void setVel_y(float vel_y) {
        this.vel_y = vel_y;
    }
    public boolean isOnAir() {
        return onAir;
    }
    public void setOnAir(boolean onAir) {
        this.onAir = onAir;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public boolean isFall() {
        return fall;
    }
    public void setFall(boolean fall) {
        this.fall = fall;
    }
}
