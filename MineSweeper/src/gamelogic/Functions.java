/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gamelogic;

import java.awt.Point;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.List;
import minesweeper.HighScore;

/**
 *
 * @author libik
 */
public class Functions {
    public static List<Point> mergePoints(List<Point> one, List<Point> two){
        List<Point> merge = new LinkedList<Point>();
        for (Point onePoint : one){
            for (Point twoPoint : two){
                if ((onePoint.x == twoPoint.x) && (onePoint.y == twoPoint.y)){
                    merge.add(new Point(onePoint.x,onePoint.y));
                }
            }
        }
        return merge;
    }

    public static void togetherPoints(List<Point> source, List<Point> add){
        for (Point point : add){
            add(source,point);
        }
    }

    private static void add(List<Point> source, Point add){
        boolean unique = true;
        for (Point point : source){
            if ( (point.x == add.x) && (point.y == add.y) ){
                unique = false;
            }
        }
        if (unique){
            source.add(new Point(add.x,add.y));
        }
    }

    public static int whereIsPoint(List<Point> source, Point asking){
        for (int i=0;i<source.size();i++){
            if ((source.get(i).x == asking.x) && (source.get(i).y == asking.y)){
                return i;
            }
        }
        return -1;
    }

    public static List<HighScore> loadHighScores() {
        HighScore singleHighScore;
        List<HighScore> highScore = new LinkedList<HighScore>();
        FileInputStream fis = null;
        ObjectInputStream in = null;
        try {
            fis = new FileInputStream("highscore.dat");
            in = new ObjectInputStream(fis);
            while((singleHighScore = (HighScore) in.readObject()) != null) {
                highScore.add(singleHighScore);
            }
            in.close();
        } catch (EOFException ex) { //This exception will be caught when EOF is reached
            System.out.println("End of file reached.");
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return highScore;
    }
}
