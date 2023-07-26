/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import gamelogic.Flag;
import gamelogic.Functions;
import gamelogic.GameTable;
import gamelogic.MineOrNot;
import gamelogic.ShowOrNot;
import java.awt.Color;
import java.awt.Point;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.border.EtchedBorder;

/**
 *
 * @author libik
 */
public class MineSweepController {

    private GameTable gameTable;
    private int width;
    private int height;
    private int mines;
    private boolean help;

    public MineSweepController(int width, int height, int mines) {
        this.width = width;
        this.height = height;
        this.mines = mines;
    }

    public boolean isHelp() {
        return help;
    }

    public void middleClick(int x, int y, JButton gameButtons[][]) {
        gameTable.middleClick(x, y);
        refresh(gameButtons, false);
    }

    public int minesLeft() {
        return (mines - gameTable.getFlagCount());
    }

    public void rightClick(int x, int y, JButton gameButtons[][]) {
        if (gameTable != null) {
            this.gameTable.rightClick(x, y);
        }
        while (checkFlags() == false) {
        }
        refresh(gameButtons, false);
    }

    public void pressedButton(int x, int y, JButton gameButtons[][]) {
        if (getGameTable() == null) {
            gameTable = new GameTable(getWidth(), getHeight(), getMines(), x, y);
        }
        if (gameTable.getFlag()[x][y] != Flag.Flag) {
            gameTable.clickAt(x, y);
        }
        while (checkFlags() == false) {
        }
        refresh(gameButtons, false);
    }

    public void refresh(JButton gameButtons[][], boolean help) {
        this.help = help;
        boolean repeat = false;
        MineOrNot inteligence[][];
        inteligence = gameTable.solveGame();
        while (canPlay(inteligence) == false) {
            Point point = randomPlayable();
            if (point != null) {
                repeat = true;
                gameTable.clickAt(point.x, point.y);
            }
            inteligence = gameTable.solveGame();
        }
        for (int i = 0; i < gameTable.getGameLogic().getWidth(); i++) {
            for (int j = 0; j < gameTable.getGameLogic().getHeight(); j++) {
                int value = gameTable.getGameLogic().getValue(i, j);
                if (gameTable.isShow(i, j)) {
                    if (gameButtons[i][j].getBackground().getRGB() != (new Color(100,100,255)).getRGB()) {

                        if (value > 0) {
                            gameButtons[i][j].setText(Integer.toString(value));
                        }
                        if (value == -1) {
                            gameButtons[i][j].setIcon(new ImageIcon("icon/mine.gif"));
                        }
                        gameButtons[i][j].setBorder(new EtchedBorder());
                        gameButtons[i][j].setBackground(new Color(100, 100, 255));
                        gameButtons[i][j].setForeground(whichColor(value));
                    }
                } else {
                    if (help) {
                        if (inteligence[i][j] == MineOrNot.Mine) {
                            gameButtons[i][j].setBackground(Color.red);
                        } else if (inteligence[i][j] == MineOrNot.Empty) {
                            gameButtons[i][j].setBackground(Color.white);
                        } else {
                            gameButtons[i][j].setBackground(new Color(0, 0, 150));
                        }
                    } else {
                        if (gameButtons[i][j].getBackground().getRGB() != (new Color(0, 0, 150)).getRGB()) {
                            gameButtons[i][j].setBackground(new Color(0, 0, 150));
                        }
                    }

                    if (gameTable.getFlag()[i][j] == Flag.Flag) {
                        gameButtons[i][j].setIcon(new ImageIcon("icon/flag.png"));
                    } else if (gameTable.getFlag()[i][j] == Flag.Question) {
                        gameButtons[i][j].setText("?");
                        gameButtons[i][j].setForeground(new Color(255, 255, 255));
                    } else {
                        if ((gameButtons[i][j].getIcon() != null) || (gameButtons[i][j].getText() != null)) {
                            gameButtons[i][j].setIcon(null);
                            gameButtons[i][j].setText(null);
                        }
                    }
                }
            }
        }

        if (repeat) {
            refresh(gameButtons, help);
        }
    }

    public boolean isDead() {
        for (int i = 0; i < gameTable.getGameLogic().getWidth(); i++) {
            for (int j = 0; j < gameTable.getGameLogic().getHeight(); j++) {
                if ((gameTable.getGameLogic().whatIsThere(i, j) == MineOrNot.Mine) && (gameTable.isShow(i, j))) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkFlags() {
        boolean noChange = true;
        for (int i = 0; i < gameTable.getGameLogic().getWidth(); i++) {
            for (int j = 0; j < gameTable.getGameLogic().getHeight(); j++) {
                if (gameTable.isShow(i, j)) {
                    if (Functions.mergePoints(gameTable.getAllAround(i, j, Flag.Flag), gameTable.getAllAround(i, j, ShowOrNot.DontShow)).size()
                            == gameTable.getGameLogic().getValue(i, j)) {
                        List<Point> listOfPoint = Functions.mergePoints(gameTable.getAllAround(i, j, ShowOrNot.DontShow), gameTable.getAllAround(i, j, Flag.Nothing));
                        for (Point point : listOfPoint) {
                            gameTable.clickAt(point.x, point.y);
                            noChange = false;
                        }
                    }
                }
            }
        }
        return noChange;
    }

    private boolean canPlay(MineOrNot inteligence[][]) {
        for (int i = 0; i < gameTable.getGameLogic().getWidth(); i++) {
            for (int j = 0; j < gameTable.getGameLogic().getHeight(); j++) {
                if (inteligence[i][j] == MineOrNot.Empty) {
                    return true;
                }
            }
        }

        if (findAllEmpty().isEmpty()) {
            return true;
        }
        return false;
    }

    private Point randomPlayable() {
        List<Point> list = new LinkedList<Point>();
        Random random = new Random();

        list = findPlayable();
        if (list.size() > 0) {
            return list.get(random.nextInt(list.size()));
        }

        list = findAllEmpty();
        if (list.size() > 0) {
            return list.get(random.nextInt(list.size()));
        }

        return null;
    }

    public List<Point> findAllEmpty() {
        List<Point> list = new LinkedList<Point>();
        for (int i = 0; i < gameTable.getGameLogic().getWidth(); i++) {
            for (int j = 0; j < gameTable.getGameLogic().getHeight(); j++) {
                if ((gameTable.getGameLogic().whatIsThere(i, j) == MineOrNot.Empty) && (gameTable.isShow(i, j) == false)) {
                    list.add(new Point(i, j));
                }
            }
        }
        return list;
    }

    private List<Point> findPlayable() {
        List<Point> list = new LinkedList<Point>();
        for (int i = 0; i < gameTable.getGameLogic().getWidth(); i++) {
            for (int j = 0; j < gameTable.getGameLogic().getHeight(); j++) {
                if (gameTable.getShowOrNot()[i][j] == ShowOrNot.Show) {
                    Functions.togetherPoints(list, Functions.mergePoints(gameTable.getAllAround(i, j, ShowOrNot.DontShow), gameTable.getGameLogic().getAllAround(i, j, MineOrNot.Empty)));
                }
            }
        }
        return list;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return the mines
     */
    public int getMines() {
        return mines;
    }

    /**
     * @return the gameTable
     */
    public GameTable getGameTable() {
        return gameTable;
    }

    public Color whichColor(int value) {
        switch (value) {
            case 1:
                return new Color(0, 0, 100);
            case 2:
                return new Color(0, 113, 20);
            case 3:
                return new Color(200, 0, 0);
            case 4:
                return new Color(0, 0, 70);
            case 5:
                return new Color(170, 0, 0);
            case 6:
                return new Color(150, 0, 0);
            case 7:
                return new Color(100, 0, 0);
            case 8:
                return new Color(50, 0, 0);
        }
        return new Color(0, 0, 0);
    }

    public boolean isInitialized() {
        if (gameTable == null) {
            return false;
        }
        return true;
    }
}
