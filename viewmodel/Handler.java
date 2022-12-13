/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewmodel;

import model.GameObject;
import java.awt.Graphics;
import java.util.LinkedList;
/**
 *
 * @author dell
 */
public class Handler {
    private double cameraX = 0, cameraY = 0;
    LinkedList<GameObject> object = new LinkedList<GameObject>();
    public void tick(){
        cameraX-=0.3; // kecepatan pergerakan kamera dari kiri ke kanan
        for(int i=0;i<object.size();i++){
            GameObject tempGameObject = object.get(i);
            tempGameObject.tick();
        }
    }
    public void render(Graphics g){
        // pergerakan camera
        g.translate(-(int)cameraX, -(int)cameraY);
        for(int i=0;i<object.size();i++){
            GameObject temGameObject = object.get(i);
            temGameObject.render(g);
        }
        g.translate((int)cameraX, (int)cameraY);
    }
    public void addObject(GameObject object){
        this.object.add(object);
    }
    public void removeObject(GameObject object){
        this.object.remove(object);
    }
    public int countObject(){
        return this.object.size();
    }
}
