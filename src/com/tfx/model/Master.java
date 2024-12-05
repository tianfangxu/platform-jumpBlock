package com.tfx.model;

import com.tfx.interfaces.DieNoticeHandle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author tianfx
 * @date 2024/12/4 09:56
 */
public class Master extends JPanel {
    
    public static int LEFT_KEY = 37;
    public static int RIGHT_KEY = 39;
    public static int JUMP_KEY = 38;
    
    public static int WIDTH = 30;
    public static int HEIGHT = 30;
    

    volatile int x;
    volatile int y;
    KeyListener keyListener;
    DieNoticeHandle dieNoticeHandle;
    List<Region> regions;

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    public Master() {
        super(null);
    }

    public static Master getInstance(int x, int y, DieNoticeHandle handle){
        Master master = new Master();
        master.x = x;
        master.y = y;
        master.dieNoticeHandle = handle;
        master.setLocation(x,y);
        master.setSize(WIDTH,HEIGHT);
        return master;
    }

    public void setLocationX(int x) {
        x = Math.max(x, 0);
        this.x = x;
        super.setLocation(x, y);
        
    }
    
    public void setLocationY(int y) {
        this.y = y;
        super.setLocation(x, y);
    }

    public volatile boolean keepLeftMoveFlag = false;
    public volatile boolean keepRightMoveFlag = false;
    public volatile boolean keepjumpFlag = false;
    
    
    public static long QUICKTIMEGAP = 350;
    public volatile boolean quickFlag = false;
    

    public KeyListener keyAction(){
        if (keyListener != null){
            return keyListener;
        }
        keyListener =  new KeyListener() {
            public long lsatClickTime = 0L;
            
            @Override// 当按下并释放键时调用
            public void keyTyped(KeyEvent e) {}
            @Override// 当释放键时调用
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == RIGHT_KEY){
                    keepRightMoveFlag = false;
                }else if (keyCode == LEFT_KEY){
                    keepLeftMoveFlag = false;
                }else if (keyCode == JUMP_KEY){
                    keepjumpFlag = false;
                }
            }
            @Override// 当按下键时调用
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == RIGHT_KEY || keyCode == LEFT_KEY){
                    if (System.currentTimeMillis() - lsatClickTime <= QUICKTIMEGAP){
                        quickFlag = true;
                    }else{
                        quickFlag = false;
                    }
                    lsatClickTime = System.currentTimeMillis();
                }
                if (keyCode == RIGHT_KEY){
                    keepRightMoveFlag = true;
                    keepLeftMoveFlag = false;
                    moveAction(false);
                }else if (keyCode == LEFT_KEY){
                    keepLeftMoveFlag = true;
                    keepRightMoveFlag = false;
                    moveAction(true);
                }else if (keyCode == JUMP_KEY){
                    keepjumpFlag = true;
                    jumpAction();
                }
            }
        };
        return keyListener;
    }

    public volatile boolean jumpFlag = false;
    public void jumpAction(){
        if (jumpFlag){
            return;
        }
        jumpFlag = true;
        jumpTransfer();
        resetJumpFlag();
    }

    public ExecutorService resetJumpExecutor = Executors.newSingleThreadExecutor();
    int jumpStep = 600;
    public void resetJumpFlag(){
        resetJumpExecutor.submit(()->{
            try {
                Thread.sleep(jumpStep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            jumpFlag = false;
            if (keepjumpFlag){
                jumpAction();
            }
        });
    }

    public ExecutorService jumpTransferExecutor = Executors.newSingleThreadExecutor();
    private void jumpTransfer() {
        jumpTransferExecutor.submit(()->{
            try {
                int step = 5;
                for (int i = 0; i < step; i++) {
                    this.setLocationY(y - getDistance((step - i)));
                    this.repaint();
                    Thread.sleep(jumpStep/2/step);
                }
                for (int i = 0; i < step; i++) {
                    this.setLocationY(y + getDistance((i + 1)));
                    this.repaint();
                    Thread.sleep(jumpStep/2/step);
                }
                jumpMoveEnd();
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public volatile boolean moveFlag = false;
    public void moveAction(boolean ngative){
        if (moveFlag){
            return;
        }
        moveFlag = true;
        moveTransfer(ngative);
        resetMoveFlag(ngative);
    }


    public ExecutorService resetMoveExecutor = Executors.newSingleThreadExecutor();
    int moveStep = 350;
    public void resetMoveFlag(boolean ngative){
        resetMoveExecutor.submit(()->{
            try {
                Thread.sleep(moveStep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            moveFlag = false;
            if (ngative?keepLeftMoveFlag:keepRightMoveFlag){
                moveAction(ngative);
            }
        });
    }

    public ExecutorService moveTransferExecutor = Executors.newSingleThreadExecutor();
    public void moveTransfer(boolean ngative){
        moveTransferExecutor.submit(()->{
            try {
                int target = (ngative?-1:1) *(quickFlag?60:30);
                int step = 10;
                for (int i = 0; i < step; i++) {
                    if (!(ngative?keepLeftMoveFlag:keepRightMoveFlag)){
                        break;
                    }
                    this.setLocationX(x+(target/step));
                    this.repaint();
                    Thread.sleep(moveStep/step);
                }
                if (!jumpFlag) {
                    jumpMoveEnd();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
    
    public void die(){
        keepLeftMoveFlag = false;
        keepRightMoveFlag = false;
        keepjumpFlag = false;
        jumpTransferExecutor.submit(()->{
            try {
                for (int i = 0; i < 4; i++) {
                    this.setLocationY(y - getDistance((3 - i)));
                    this.repaint();
                    Thread.sleep(100);
                }
                for (int i = 0; i < 10; i++) {
                    this.setLocationY(y + getDistance((i + 1)));
                    this.repaint();
                    Thread.sleep(40);
                }
                Container parent = this.getParent();
                parent.remove(this);
                parent.repaint();
                if (dieNoticeHandle != null){
                    dieNoticeHandle.notice(this);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * jump Distance
     */
    public static int getDistance(int time){
        return time*time*2;
    }
    
    void jumpMoveEnd(){
        if (regions==null||regions.size()==0){
            return;
        }
        boolean aliveFlag = false;
        for (int i = 0; i < regions.size(); i++) {
            Region region = regions.get(i);
            if (x > region.getStartX()-WIDTH && x < region.getEndX()){
                aliveFlag = true;
                break;
            }
        }
        if (!aliveFlag){
            die();
        }
    }
    
}
