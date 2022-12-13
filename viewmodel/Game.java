/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*Saya Raden Fadhil A.A [2004305] mengerjakan evaluasi Tugas Masa Depan dalam mata kuliah 
Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya 
tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.*/
package viewmodel;

import view.*;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.lang.Object;
import java.awt.geom.RectangularShape;
import java.awt.geom.Rectangle2D;
import java.awt.Rectangle;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.Clip;
import java.io.File;
import javax.swing.JOptionPane;
import java.io.IOException;
import java.net.URL;
import model.Player;
import model.Obstacle;
import model.GameObject;
import model.Id;
import model.TabelUser;
/**
 *
 * @author dell
 */
public class Game extends Canvas implements Runnable{
    // setting window
    Window window;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    //buat atribut clip background music
    private Clip clip = null;
    // Thread
    private Thread thread;
    public double gravity = 2;
    private boolean running = false;
    private Handler handler;
    private Camera cam;
    // Thread Start
    public enum STATE{
        Game,
        Gameover,
    };
     // Data Game
    private String username;
    private int adaptScore = 0;
    private int fallScore = 0; 
    private int maxHeightPlayer = 559;
     // Dimulai dengan state 'game'
    public STATE gameState = STATE.Game;
    public Game(){
        // membuat window
        window = new Window(WIDTH, HEIGHT, "Balok Rush", this);
        handler = new Handler(); // handler
        cam = new Camera(0, 0);
        // KeyListener -> menerima input keyboard
        this.addKeyListener(new KeyInput(handler, this));
        // Jika user telah mengklick play maka status akan berubah menjadi Game
        if(gameState == STATE.Game){
            // Jika game dimulai maka player dan obstacle akan respwan
            playMusic("bgm.wav"); // mulai bgm
            handler.addObject(new Player(window,340, 20, Id.Player)); // memunculkan player
            handler.addObject(new Obstacle(-3000, 559, Id.Obstacle, 6000, 2, Color.black)); // floor (obstacle paling bawah)
           loopObstacle(-600, 6000); // memanggil fungsi looping obstacle
        }
        if(gameState == STATE.Gameover){
            stopSound();
            stop();
        }
    }
    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }
    public void setUsername(String username){
        this.username = username;
    }
    
    // Thread Stop
    public synchronized void stop() {
        try{
            thread.join();
            running = false;
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    // Main Gameloop
    @Override
    public void run() {
         //atur variabel penghitung waktu, delta dan timer permainan, serta frame per second (FPS) nya
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        //selama game masih berjalan
         while(running){
            //set waktu sekarang
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            //jika deltanya masih lebih dari 1, gambar objectnya
            while(delta >= 1){
                tick();
                delta--;
            }
            //cetak object awal ketika game masih berjalan
            if(running){
                render();
                frames++;
            }
            collision();
            //cek timernya dan jumlahkan setiap detiknya
            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("FPS " + frames);
                frames = 0;
            }
        }
        //stop threadnya
        stop();
    }
    public void tick(){
        handler.tick();
        if(gameState == STATE.Game){
            GameObject playerObject = null;
            // Mengambil object player
            for(int i=0;i< handler.object.size(); i++){
                if(handler.object.get(i).getId() == Id.Player){
                    playerObject = handler.object.get(i);
                    cam.tick(playerObject);
                }
            }
        }
    }
    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
         if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
         //gambar background canvas
        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D)g;
        // set warna background swing
       g.setColor(Color.decode("#b2e4eb"));
       g.fillRect(0, 0, WIDTH, HEIGHT); 
        // Jika game masih berjalan
         if(gameState == STATE.Game){
             g2d.translate(cam.getX(), cam.getY());
            // menampilkan gambar dan komponen swing
            handler.render(g);
            g2d.translate(-cam.getX(), -cam.getY());
            g.setFont(new java.awt.Font("Ubuntu", 1, 14));
            g.setColor(Color.decode("#3A1070"));
            g.drawString("Adapt: " +Integer.toString(adaptScore) , 20, 20);
            g.drawString("Fall: " +Integer.toString(fallScore) , 20, 50);
         }
         //buang canvas sebelumnya
        g.dispose();
        bs.show();
    }
     //method menutup game
    public void close(){
        window.CloseWindow();
    }
    public void collision(){
        // inisialisasi gameobject
        GameObject player = null;
        GameObject obstacle = null;
        // Proses pencarian object berdasrkan id
        for(int i=0;i<handler.object.size();i++){
            // Jika id player
            if(handler.object.get(i).getId() == Id.Player){
                player = handler.object.get(i);
                player.setFall(true); // pada saat player respawn maka player akan terjatuh
            }
            // jika id obstacle
            if(handler.object.get(i).getId() == Id.Obstacle){
                // Proses membuat objek baru sebagai representasi dari player
                Rectangle playerrect = new Rectangle((int)(player.getX()+player.getVel_x()), (int)(player.getY()+player.getVel_y()), 50, 50);
                obstacle = handler.object.get(i);
                // Jika koordinat player saling bersentuhan dengan obstacle
                if(playerrect.intersects(obstacle.getX(), obstacle.getY(), obstacle.getWidth(), obstacle.getHeight())){
                    // Agar player dapat berdiri diam di atas objek
                    if(player.getY()<= obstacle.getY()+1){  
                        player.setFall(false);
                        // player berdiri diam (koordinat y tidak berubah) namun dapat bergerak secara horizontal
                        if(player.getVel_y() > 0){
                            // programmer berasumsi bahwa nilai adapt akan bertambah jika balok yang dilewati tingginya 
                            // lebih besar daripada sebelumnya dimana maksimum tinggi balok yang dilewati akan ter refresh
                            // setiap player menginjak obstacle
                            if(maxHeightPlayer > player.getY()){
                                adaptScore++;
                            }else{
                                fallScore++;
                            }
                            maxHeightPlayer = player.getY();
                            player.setVel_y(0);
                            player.setY(obstacle.getY() - 49);
                        }
                    }else if(player.getY() < obstacle.getY()){
                        player.setVel_x(0);
                    }
                    // jika player di atas obstacle atau terjatuh dari obstacle
                    if(player.getVel_y() < 0 && player.getY() > obstacle.getY()){
                        player.setFall(true);
                        float temp = player.getY() - player.getVel_y() - 1;
                        player.setY((int)temp);
                        player.setVel_y(-1*player.getVel_y());
                    }
                    // jika player menyentuh obstacle secara horizontal
                    if(obstacle.getX()+obstacle.getWidth() <= player.getX()+4){
                        // maka player akan dipaksa untuk berhenti
                        player.setVel_x(0);
                    }
                    if(obstacle.getX()+4 >= player.getX()+50){
                        player.setVel_x(0);
                    }

                }
            }
        }
    }
    // Fungsi untuk membuat balok/rintangan sepanjang game berjalan
    public void loopObstacle(int sectorX, int sectorWidth){
        Random random = new Random();
        int y=0;
        boolean first = false;
        int iterator = 1;
        for(int x=sectorX;x<sectorWidth; x+=iterator){
            // Proses random warna
            float R = random.nextFloat(), G = random.nextFloat(), B = random.nextFloat();
            Color randomWarna = new Color(R, G, B);
            // Proses pengecakan untuk membuat kotak pertama yang akan diinjak oleh player
            if(first == false){
                y = 530 - random.nextInt(270);
                first = true;
            }else{
                y = 530 - random.nextInt(450);
            }
            // minimum dan maximum lebar balok
            // minimum selebar player
            int minw = 50, maxw = 75;
            int lebar = random.nextInt(maxw - minw) + minw;
            int z= 500-y; // menyesuaikan height object dengan koordinat Y
            // jika koordinat y terlalu tinggi maka tidak akan memunculkan obstacle
            // dan akan membuat celah
            if(y>410){
                iterator = lebar;
                // Jika koordinat y rendah maka akan memunculkan obstacle
            }else{
                // koordinat x bernilai minus dengan tujuan agar memunculkan obstacle di luar frame window
                // atau pada saat camera bergerak maka akan terus memunculkan obstacle hingga batas tertentu
                handler.addObject(new Obstacle(-x-lebar, y, Id.Obstacle, lebar, 60+z, randomWarna));
                iterator = lebar;
            }
            
        }
    }
    public void setScore(){
        try{
            // proses pencarian username
            TabelUser tuser = new TabelUser();
            Object[] cariUser = tuser.searchName(username);
            // jika ditemukan username
            if(cariUser[0] != null){
                // programmer berasumsi bahwa nilai yg perlu diupdate adalah jika nilai adapt dan fall pada game yg dimainkan
                // lebih tinggi daripada yg ada di database
                if(this.fallScore > (int)cariUser[1] || this.adaptScore > (int)cariUser[2]){
                     tuser.updateData(this.username, fallScore, adaptScore); 
                }
            // Jika tidak ditemukan username
            }else{
                 tuser.insertData(this.username, fallScore, adaptScore);
            }
        }catch(Exception e){
         e.printStackTrace();
        }
    }
      //method menyalakan background music
    public void playMusic(String filename){
        try {
            // Open an audio input stream.
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("resources/" + filename).getAbsoluteFile());
            // Get a sound clip resource.
            clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            //loop berulang-ulang
            clip.loop(clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
           e.printStackTrace();
        } catch (IOException e) {
           e.printStackTrace();
        } catch (LineUnavailableException e) {
           e.printStackTrace();
        }
    }
    //method ketika background music dimatikan
    public void stopSound(){
       
        //stop dan tutup musicnya
        clip.stop();
        clip.close();
    }
}
