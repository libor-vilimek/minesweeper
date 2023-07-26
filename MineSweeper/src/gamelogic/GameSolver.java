/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamelogic;

import java.awt.Point;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author libik
 */
public class GameSolver {

    private MineOrNot solveResult[][];
    private GameTable gameTable;

    public GameSolver(int width, int height, GameTable gameTable) {
        clear(width, height);
        this.gameTable = gameTable;
    }

    public MineOrNot[][] solveGame() {
        //clear(gameTable.getGameLogic().getWidth(), gameTable.getGameLogic().getHeight());
        this.sureMines();
        if (isThereEmptySpace() == false) {
            this.makeAllSolutions();
        }
        return solveResult;
    }

    private void makeAllSolutions() {
        List<Point> allPoints = new LinkedList<Point>();
        List<List<MinesAround>> listOfMinesAround = new LinkedList<List<MinesAround>>();
        List<MinesAround> minesAround = new LinkedList<MinesAround>();
        boolean allPosibilities[];
        List<boolean[]> rightDisposition = new LinkedList<boolean[]>();

        for (int i = 0; i < solveResult.length; i++) {
            for (int j = 0; j < solveResult[i].length; j++) {
                List<Point> mergePoints = Functions.mergePoints(getAllAround(i, j, MineOrNot.Nothing), gameTable.getAllAround(i, j, ShowOrNot.DontShow));
                if ((mergePoints.size() > 0) && (gameTable.isShow(i, j))) {
                    Functions.togetherPoints(allPoints, mergePoints);
                }
            }
        }
        for (int i = 0; i < solveResult.length; i++) {
            for (int j = 0; j < solveResult[i].length; j++) {
                List<Point> mergePoints = Functions.mergePoints(getAllAround(i, j, MineOrNot.Nothing), gameTable.getAllAround(i, j, ShowOrNot.DontShow));
                if ((mergePoints.size() > 0) && (gameTable.isShow(i, j))) {
                    int index[] = new int[mergePoints.size()];
                    for (int k = 0; k < mergePoints.size(); k++) {
                        index[k] = Functions.whereIsPoint(allPoints, mergePoints.get(k));
                    }
                    minesAround.add(new MinesAround(index, gameTable.getGameLogic().getValue(i, j)
                            - Functions.mergePoints(getAllAround(i, j, MineOrNot.Mine), gameTable.getAllAround(i, j, ShowOrNot.DontShow)).size()));
                }
            }
        }

        listOfMinesAround = divideToZones(minesAround);
        for (List<MinesAround> tempMinesAround : listOfMinesAround) {
            rightDisposition = new LinkedList<boolean[]>();
            TwoKeysList map = makeBridge(tempMinesAround);
            allPosibilities = new boolean[map.size()];
            clearBoolean(allPosibilities, false);
            long value = (long) Math.pow(2, map.size());
            if (map.size() > 18) {
                return;
            }
            for (int i = 0; i < value; i++) {
                if (isItPossible(allPosibilities, tempMinesAround, map)) {
                    rightDisposition.add(Arrays.copyOf(allPosibilities, allPosibilities.length));
                }
                addToBoolean(allPosibilities);
            }
            collectResults(rightDisposition, allPoints, map);
        }

//        allPosibilities = new boolean[allPoints.size()];
//        clearBoolean(allPosibilities, false);
//        long value = (long) Math.pow(2, allPoints.size());
//        if (allPoints.size() > 18) {
//            return;
//        }
//        for (int i = 0; i < value; i++) {
//            if (isItPossible(allPosibilities, minesAround)) {
//                rightDisposition.add(Arrays.copyOf(allPosibilities, allPosibilities.length));
//            }
//            addToBoolean(allPosibilities);
//        }
//        collectResults(rightDisposition, allPoints);
    }

    private TwoKeysList makeBridge(List<MinesAround> list) {
        TwoKeysList map = new TwoKeysList();
        int count = 0;
        for (MinesAround mines : list) {
            for (int i = 0; i < mines.getIndex().length; i++) {
                if (map.addValues(mines.getIndex()[i],count)){
                    count++;
                }
            }
        }
        return map;
    }

    private List<List<MinesAround>> divideToZones(List<MinesAround> all) {
        List<List<MinesAround>> listOfMinesAround = new LinkedList<List<MinesAround>>();
        boolean isAdded[] = new boolean[all.size()];
        clearBoolean(isAdded, false);

        while (isAnyBoolean(isAdded, false)) {
            List<MinesAround> zone = new LinkedList<MinesAround>();
            for (int i = 0; i < all.size(); i++) {
                if (isAdded[i] == false) {
                    if (zone.isEmpty()) {
                        zone.add(all.get(i));
                        isAdded[i] = true;
                    } else if (isConnectedToZone(zone, all.get(i))) {
                        zone.add(all.get(i));
                        isAdded[i] = true;
                    }
                }
            }
            listOfMinesAround.add(zone);
        }
        return listOfMinesAround;
    }

    private boolean isAnyBoolean(boolean source[], boolean whatFind) {
        for (int i = 0; i < source.length; i++) {
            if (source[i] == whatFind) {
                return true;
            }
        }
        return false;
    }

    private boolean isConnectedToZone(List<MinesAround> zone, MinesAround mineToCheck) {
        for (MinesAround mine : zone) {
            if (mine.isConnected(mineToCheck)) {
                return true;
            }
        }
        return false;
    }

    private void collectResults(List<boolean[]> results, List<Point> allPoints, TwoKeysList map) {
        if (results.size() <= 0) {
            return;
        }
        for (int i = 0; i < results.get(0).length; i++) {
            boolean start = true;
            boolean temp = false;
            boolean definitivni = true;
            for (boolean[] result : results) {
                if (start == true) {
                    temp = result[i];
                    start = false;
                } else {
                    if (temp != result[i]) {
                        definitivni = false;
                    }
                }
            }
            if (definitivni) {
                if (temp == true) {
                    this.addMine(allPoints.get(map.getKey(i)).x, allPoints.get(map.getKey(i)).y);
                }
                if (temp == false) {
                    this.addEmpty(allPoints.get(map.getKey(i)).x, allPoints.get(map.getKey(i)).y);
                }
            }
        }
    }

    private int findFirstValue(Map<Integer, Integer> map, int whatFind) {
        if (map.containsValue(whatFind)) {
            int i = 0;
            while (1 < 2) {
                if (map.containsKey(i) == true) {
                    if (map.get(i) == whatFind) {
                        return i;
                    }
                    i++;
                }
            }
        }

        return -1;
    }

    private boolean isThereEmptySpace() {
        for (int i = 0; i < solveResult.length; i++) {
            for (int j = 0; j < solveResult[i].length; j++) {
                if (this.whatIsThere(i, j) == MineOrNot.Empty) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isItPossible(boolean allPosibilities[], List<MinesAround> minesAround, TwoKeysList map) {
        boolean result = true;
        for (MinesAround mines : minesAround) {
            if (mines.isThisRight(allPosibilities, map) == false) {
                return false;
            }
        }
        return result;
    }

    private void addToBoolean(boolean source[]) {
        for (int i = 0; i < source.length; i++) {
            if (source[i] == false) {
                source[i] = true;
                return;
            } else {
                source[i] = false;
            }
        }
    }

    private void clearBoolean(boolean toClear[], boolean toWhat) {
        for (int i = 0; i < toClear.length; i++) {
            toClear[i] = toWhat;
        }
    }

    private void sureMines() {
        boolean end = false;
        while (end == false) {
            end = true;
            for (int i = 0; i < solveResult.length; i++) {
                for (int j = 0; j < solveResult[i].length; j++) {
                    int value = gameTable.getGameLogic().getValue(i, j);
                    if ((value > 0) && (gameTable.isShow(i, j))) {
                        if ((gameTable.shownAround(i, j, ShowOrNot.DontShow) == value)) {
                            if (Functions.mergePoints(getAllAround(i, j, MineOrNot.Nothing), gameTable.getAllAround(i, j, ShowOrNot.DontShow)).size() > 0) {
                                makeMinesAround(i, j);
                                end = false;
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < solveResult.length; i++) {
                for (int j = 0; j < solveResult[i].length; j++) {
                    int value = gameTable.getGameLogic().getValue(i, j);
                    if ((value > 0) && (gameTable.isShow(i, j))) {
                        if (this.shownAround(i, j, MineOrNot.Mine) == value) {
                            if (Functions.mergePoints(getAllAround(i, j, MineOrNot.Nothing), gameTable.getAllAround(i, j, ShowOrNot.DontShow)).size() > 0) {
                                this.makeEmptyAround(i, j);
                                end = false;
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < solveResult.length; i++) {
                for (int j = 0; j < solveResult[i].length; j++) {
                    int value = gameTable.getGameLogic().getValue(i, j);
                    if ((value > 0) && (gameTable.isShow(i, j))) {
                        if ((gameTable.shownAround(i, j, ShowOrNot.DontShow)
                                - Functions.mergePoints(getAllAround(i, j, MineOrNot.Empty), gameTable.getAllAround(i, j, ShowOrNot.DontShow)).size()
                                == value)) {
                            if (Functions.mergePoints(getAllAround(i, j, MineOrNot.Nothing), gameTable.getAllAround(i, j, ShowOrNot.DontShow)).size() > 0) {
                                makeMinesAround(i, j);
                                end = false;
                            }
                        }
                    }
                }
            }
        }
    }

    private void makeEmptyAround(int x, int y) {
        for (Point point : gameTable.getAllAround(x, y, ShowOrNot.DontShow)) {
            if (whatIsThere(point.x, point.y) == MineOrNot.Nothing) {
                addEmpty(point.x, point.y);
            }
        }
    }

    private void makeMinesAround(int x, int y) {
        for (Point point : gameTable.getAllAround(x, y, ShowOrNot.DontShow)) {
            if (whatIsThere(point.x, point.y) == MineOrNot.Nothing) {
                addMine(point.x, point.y);
            }
        }
    }

    private void addEmpty(int x, int y) {
        this.solveResult[x][y] = MineOrNot.Empty;
    }

    private void addMine(int x, int y) {
        this.solveResult[x][y] = MineOrNot.Mine;
    }

    private void clear(int width, int height) {
        this.solveResult = new MineOrNot[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                this.solveResult[i][j] = MineOrNot.Nothing;
            }
        }
    }

    private MineOrNot whatIsThere(int x, int y) {
        if ((x < 0) || (y < 0) || (x > gameTable.getGameLogic().getWidth() - 1) || (y > gameTable.getGameLogic().getHeight() - 1)) {
            return MineOrNot.Wall;
        } else {
            return this.solveResult[x][y];
        }
    }

    public int shownAround(int x, int y, MineOrNot lookingFor) {
        return getAllAround(x, y, lookingFor).size();
    }

    public List<Point> getAllAround(int x, int y, MineOrNot lookingFor) {
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
}
