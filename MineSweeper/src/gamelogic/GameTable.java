/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamelogic;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author libik
 */
public class GameTable {

    private MineField gameLogic;
    private ShowOrNot showOrNot[][];
    private Flag flag[][];
    private GameSolver gameSolver;
    private long startTime;

    public GameTable(int width, int height, int mines, int x, int y) {
        gameLogic = new MineField(width, height, mines, x, y);
        this.startTime = System.currentTimeMillis();
        this.clear(width, height);
    }

    public void middleClick(int x, int y) {
        if (this.flag[x][y] == Flag.Question) {
            this.flag[x][y] = Flag.Nothing;
        } else {
            this.flag[x][y] = Flag.Question;
        }
    }

    public int getFlagCount() {
        int count = 0;
        for (int i = 0; i < gameLogic.getWidth(); i++) {
            for (int j = 0; j < gameLogic.getHeight(); j++) {
                if ((flag[i][j] == Flag.Flag) && (isShow(i, j) == false)) {
                    count++;
                }
            }
        }
        return count;
    }

    public long getMiliSecondsInGame() {
        return ((System.currentTimeMillis() - this.startTime));
    }

    private void clear(int width, int height) {
        this.showOrNot = new ShowOrNot[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                this.showOrNot[i][j] = ShowOrNot.DontShow;
            }
        }

        this.flag = new Flag[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                this.flag[i][j] = Flag.Nothing;
            }
        }
    }

    public void rightClick(int x, int y) {
        if (whatIsThere(x, y) == ShowOrNot.DontShow) {
            if (this.flag[x][y] == Flag.Nothing) {
                this.flag[x][y] = Flag.Flag;
            } else {
                this.flag[x][y] = Flag.Nothing;
            }
        }
    }

    public final int clickAt(int x, int y) {
        int value = gameLogic.getValue(x, y);
        if (value == -2) {
            return value;
        }
        ShowOrNot previous = this.showOrNot[x][y];
        this.showOrNot[x][y] = ShowOrNot.Show;
        if ((previous == ShowOrNot.DontShow) && (value == 0)) {
            clickAt(x + 1, y);
            clickAt(x + 1, y + 1);
            clickAt(x + 1, y - 1);
            clickAt(x - 1, y);
            clickAt(x - 1, y + 1);
            clickAt(x - 1, y - 1);
            clickAt(x, y + 1);
            clickAt(x, y - 1);
        }
        return value;
    }

    public boolean isShow(int x, int y) {
        if (whatIsThere(x, y) == ShowOrNot.Show) {
            return true;
        } else {
            return false;
        }
    }

    public MineOrNot[][] solveGame() {
        gameSolver = new GameSolver(getGameLogic().getWidth(), getGameLogic().getHeight(), this);
        return gameSolver.solveGame();
    }

    public ShowOrNot whatIsThere(int x, int y) {
        if ((x < 0) || (y < 0) || (x > getGameLogic().getWidth() - 1) || (y > getGameLogic().getHeight() - 1)) {
            return ShowOrNot.Wall;
        } else {
            return this.showOrNot[x][y];
        }
    }

    public int shownAround(int x, int y, ShowOrNot lookingFor) {
        return getAllAround(x, y, lookingFor).size();
    }

    /**
     * @return the gameLogic
     */
    public MineField getGameLogic() {
        return gameLogic;
    }

    /**
     * @return the showOrNot
     */
    public ShowOrNot[][] getShowOrNot() {
        return showOrNot;
    }

    public List<Point> getAllAround(int x, int y, ShowOrNot lookingFor) {
        List<Point> list = new LinkedList<Point>();
        if (whatIsThere(x + 1, y + 1) == lookingFor) {
            list.add(new Point(x + 1, y + 1));
        }
        if (whatIsThere(x + 1, y - 1) == lookingFor) {
            list.add(new Point(x + 1, y - 1));
        }
        if (whatIsThere(x + 1, y) == lookingFor) {
            list.add(new Point(x + 1, y));
        }
        if (whatIsThere(x - 1, y + 1) == lookingFor) {
            list.add(new Point(x - 1, y + 1));
        }
        if (whatIsThere(x - 1, y - 1) == lookingFor) {
            list.add(new Point(x - 1, y - 1));
        }
        if (whatIsThere(x - 1, y) == lookingFor) {
            list.add(new Point(x - 1, y));
        }
        if (whatIsThere(x, y + 1) == lookingFor) {
            list.add(new Point(x, y + 1));
        }
        if (whatIsThere(x, y - 1) == lookingFor) {
            list.add(new Point(x, y - 1));
        }

        return list;
    }

    public List<Point> getAllAround(int x, int y, Flag lookingFor) {
        List<Point> list = new LinkedList<Point>();
        if ((whatIsThere(x + 1, y + 1) != ShowOrNot.Wall) && (this.flag[x + 1][y + 1] == lookingFor)) {
            list.add(new Point(x + 1, y + 1));
        }
        if ((whatIsThere(x + 1, y - 1) != ShowOrNot.Wall) && (this.flag[x + 1][y - 1] == lookingFor)) {
            list.add(new Point(x + 1, y - 1));
        }
        if ((whatIsThere(x + 1, y) != ShowOrNot.Wall) && (this.flag[x + 1][y] == lookingFor)) {
            list.add(new Point(x + 1, y));
        }
        if ((whatIsThere(x - 1, y + 1) != ShowOrNot.Wall) && (this.flag[x - 1][y + 1] == lookingFor)) {
            list.add(new Point(x - 1, y + 1));
        }
        if ((whatIsThere(x - 1, y - 1) != ShowOrNot.Wall) && (this.flag[x - 1][y - 1] == lookingFor)) {
            list.add(new Point(x - 1, y - 1));
        }
        if ((whatIsThere(x - 1, y) != ShowOrNot.Wall) && (this.flag[x - 1][y] == lookingFor)) {
            list.add(new Point(x - 1, y));
        }
        if ((whatIsThere(x, y + 1) != ShowOrNot.Wall) && (this.flag[x][y + 1] == lookingFor)) {
            list.add(new Point(x, y + 1));
        }
        if ((whatIsThere(x, y - 1) != ShowOrNot.Wall) && (this.flag[x][y - 1] == lookingFor)) {
            list.add(new Point(x, y - 1));
        }

        return list;
    }

    /**
     * @return the flag
     */
    public Flag[][] getFlag() {
        return flag;
    }

    /**
     * @return the startTime
     */
    public long getStartTime() {
        return startTime;
    }
}
