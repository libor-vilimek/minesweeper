/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import gamelogic.Functions;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author libik
 */
public class HighScore implements Serializable {
    private int width;
    private int height;
    private int mines;
    private long time;
    private String name;

    public HighScore(int width, int height, int mines, long time, String name) {
        this.width = width;
        this.height = height;
        this.mines = mines;
        this.time = time;
        this.name = name;
    }

    public void writeObject() {
        List<HighScore> highScore = Functions.loadHighScores();
        highScore.add(this);

        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream("highscore.dat");
            out = new ObjectOutputStream(fos);
            for (HighScore listHighScore : highScore){
                out.writeObject(listHighScore);
            }
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public boolean isItSameType(HighScore highScore){
        if ( (highScore.width == width) && (highScore.height == height) && (highScore.mines == mines) ){
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return the mines
     */
    public int getMines() {
        return mines;
    }

    /**
     * @param mines the mines to set
     */
    public void setMines(int mines) {
        this.mines = mines;
    }

    /**
     * @return the time
     */
    public long getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(long time) {
        this.time = time;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
