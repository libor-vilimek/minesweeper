/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamelogic;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author libik
 */
public class MineField {

    private MineOrNot gameArea[][];

    public MineField(int width, int height, int mines, int x, int y) {
        Random random = new Random();
        if ((mines - 10 > width * height) || (width < 5) || (height < 5)) {
            width = 10;
            height = 10;
            mines = 10;
        }

        this.clear(width, height);

        int count = 0;
        
        while (count < mines) {
            int randX = random.nextInt(width);
            int randY = random.nextInt(height);
            if (!(( randX == x ) && (randY == y)) && (addMine(randX, randY))) {
                count++;
            }
        }
        
//        addMine(0,2);
//        addMine(2,1);
//        addMine(2,2);
//        addMine(2,3);
//
//        addMine(0,0);
//        addMine(2,0);
//        addMine(3,0);
//        addMine(3,1);

//        addMine(0,0);
//        addMine(2,0);
//        addMine(3,0);
//        addMine(3,1);
//
//        addMine(width-1,0);
//        addMine(width-3,0);
//        addMine(width-4,0);
//        addMine(width-4,1);
         

    }

    private boolean addMine(int x, int y) {
        if (whatIsThere(x, y) == MineOrNot.Empty) {
            this.gameArea[x][y] = MineOrNot.Mine;
            return true;
        } else {
            return false;
        }
    }

    public MineOrNot whatIsThere(int x, int y) {
        if (this.isOutOfGame(x, y)) {
            return MineOrNot.Wall;
        }
        return this.gameArea[x][y];
    }

    public int getWidth() {
        return this.gameArea.length;
    }

    public int getHeight() {
        return gameArea[0].length;
    }

    public int getValue(int x, int y) {
        if (whatIsThere(x, y) == MineOrNot.Empty) {
            return minesAround(x, y);
        } else if (whatIsThere(x, y) == MineOrNot.Mine) {
            return -1;
        } else {
            return -2;
        }
    }

    private int minesAround(int x, int y) {
        int count = 0;

        if (whatIsThere(x+1, y) == MineOrNot.Mine) {
            count++;
        }
        if (whatIsThere(x-1, y) == MineOrNot.Mine) {
            count++;
        }
        if (whatIsThere(x, y+1) == MineOrNot.Mine) {
            count++;
        }
        if (whatIsThere(x-1, y+1) == MineOrNot.Mine) {
            count++;
        }
        if (whatIsThere(x+1, y+1) == MineOrNot.Mine) {
            count++;
        }
        if (whatIsThere(x, y-1) == MineOrNot.Mine) {
            count++;
        }
        if (whatIsThere(x+1, y-1) == MineOrNot.Mine) {
            count++;
        }
        if (whatIsThere(x-1, y-1) == MineOrNot.Mine) {
            count++;
        }

        return count;
    }

    private boolean isOutOfGame(int x, int y) {
        if ((x < 0) || (y < 0) || (x > getWidth() - 1) || (y > getHeight() - 1)) {
            return true;
        } else {
            return false;
        }
    }

    private void clear(int width, int height) {
        this.gameArea = new MineOrNot[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                this.gameArea[i][j] = MineOrNot.Empty;
            }
        }
    }

    public List<Point> getAllAround(int x, int y, MineOrNot lookingFor){
        List<Point> list = new LinkedList<Point>();
        if (whatIsThere(x+1, y+1) == lookingFor){
            list.add(new Point(x+1,y+1));
        }
        if (whatIsThere(x+1, y-1) == lookingFor){
            list.add(new Point(x+1,y-1));
        }
        if (whatIsThere(x+1, y) == lookingFor){
            list.add(new Point(x+1,y));
        }
        if (whatIsThere(x-1, y+1) == lookingFor){
            list.add(new Point(x-1,y+1));
        }
        if (whatIsThere(x-1, y-1) == lookingFor){
            list.add(new Point(x-1,y-1));
        }
        if (whatIsThere(x-1, y) == lookingFor){
            list.add(new Point(x-1,y));
        }
        if (whatIsThere(x, y+1) == lookingFor){
            list.add(new Point(x,y+1));
        }
        if (whatIsThere(x, y-1) == lookingFor){
            list.add(new Point(x,y-1));
        }

        return list;
    }
}
