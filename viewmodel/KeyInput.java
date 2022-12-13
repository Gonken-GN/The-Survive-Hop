/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewmodel;
import viewmodel.Game.STATE;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import model.Id;
import viewmodel.Game.STATE;
import model.GameObject;
import view.Menu;
/**
 *
 * @author dell
 */
public class KeyInput extends KeyAdapter{
    private Handler handler;
    Game main;
    
    public KeyInput(Handler handler, Game main){
        this.main = main;
        this.handler = handler;
    }
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        
        if(main.gameState == STATE.Game){
            for(int i = 0;i<handler.object.size();i++){
                if(handler.object.get(i).getId() == Id.Player){                 
                    GameObject tempObject = handler.object.get(i);
                    if((key == KeyEvent.VK_W) || (key == KeyEvent.VK_UP)){
                        tempObject.setVel_y(-4);
                        if(tempObject.getVel_y() == 0){
                            tempObject.setVel_y(-4);
                        }
                    }
                    if((key == KeyEvent.VK_A) || (key == KeyEvent.VK_LEFT)){
                        tempObject.setVel_x(-5);
                    }

                    if((key == KeyEvent.VK_D) || (key == KeyEvent.VK_RIGHT)){
                        tempObject.setVel_x(+5);
                    }
                    // Jika user ingin menghentinkan permainan
                    if(key == KeyEvent.VK_SPACE){
                        main.stopSound();
                        main.gameState = STATE.Gameover;
                        main.setScore();
                        new Menu().setVisible(true);
                        main.close();
                    }
                    break;
                }
            }
            
        }
           
    }
            
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        
        for(int i = 0;i<handler.object.size();i++){
            if(handler.object.get(i).getId() == Id.Player){
                GameObject tempObject = handler.object.get(i);
                if((key == KeyEvent.VK_W) || (key == KeyEvent.VK_UP)){
                    tempObject.setVel_y(0);
                }
                if((key == KeyEvent.VK_A) || (key == KeyEvent.VK_LEFT)){
                    tempObject.setVel_x(0);
                }

                if((key == KeyEvent.VK_D) || (key == KeyEvent.VK_RIGHT)){
                    tempObject.setVel_x(0);
                }
                break;
            }
        }
    }
}
