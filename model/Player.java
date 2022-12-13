/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author dell
 */
import java.awt.Color;
import java.awt.Graphics;
import viewmodel.Game;
import view.*;
import viewmodel.*;
// player merupakan bagian dari gameobject
public class Player extends GameObject{
    private Window w;
    private Handler handler;
    private double gravity = 2;
    public boolean falling = false;
    public Player(Window w, int x, int y, Id id){
        // mmemangil konstruktor parent (GameObject)
        super(x, y, id);
        this.w = w;
    }
    // Gambar character agar dapat bergerak
    @Override
    public void tick() {
        
        x += vel_x; 
        // agar tidak melebihi window
        if(y >= Game.HEIGHT-80){
            y = Game.HEIGHT-80;
            if(y+vel_y < Game.HEIGHT-80){
                y+= vel_y;
                // agar player dapat berdiri diam di atas objek
            }else if(!falling && vel_y > 0){
                vel_y = 0;
            }
        }
        
        y += vel_y;
        
        if(y >= Game.HEIGHT-80) {
            y = Game.HEIGHT-80;
        }
        
        if(x >= Game.WIDTH-60) {
            x = Game.WIDTH-60;
        }
    //    Jika player tidak di atas objek maka akan terjatuh
        if(vel_y < gravity && fall){
            vel_y += 0.1;
        }
    }
    // fungsi untuk membuat/menampilkan player
    @Override
    public void render(Graphics g) {
        g.setColor(Color.decode("#a2a9b3"));
        g.fillRect(x, y, 50, 50);
    }
     
}
