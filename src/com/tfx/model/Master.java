package com.tfx.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    

    volatile int x;

    volatile int y;

    public Master() {
        super(null);
    }

    public Master(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    public Master(LayoutManager layout) {
        super(layout);
    }
    
    public Master(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public static Master getInstance(int x,int y){
        Master master = new Master();
        master.x = x;
        master.y = y;
        master.setLocation(x,y);
        return master;
    }

    
    @Override
    public int getX() {
        return x;
    }


    @Override
    public int getY() {
        return y;
    }

    public void setLocationX(int x) {
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

    public KeyListener keyAction(){
        return new KeyListener() {
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
    }

    public volatile boolean jumpFlag = false;
    public void jumpAction(){
        if (jumpFlag){
            return;
        }
        jumpFlag = true;
        resetJumpFlag();
        jumpTransfer();
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
                    this.setLocationY(this.getY() - getDistance((step - i)));
                    this.repaint();
                    Thread.sleep(jumpStep/2/step);
                }
                for (int i = 0; i < step; i++) {
                    this.setLocationY(this.getY() + getDistance((i + 1)));
                    this.repaint();
                    Thread.sleep(jumpStep/2/step);
                }
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
        resetMoveFlag(ngative);
        moveTransfer(ngative);
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
                int target = ngative ? -30 : 30;
                int step = 10;
                for (int i = 0; i < step; i++) {
                    if (!(ngative?keepLeftMoveFlag:keepRightMoveFlag)){
                        break;
                    }
                    this.setLocationX(Math.max((this.getX()+(target/step)),0));
                    this.repaint();
                    Thread.sleep(moveStep/step);
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
}
