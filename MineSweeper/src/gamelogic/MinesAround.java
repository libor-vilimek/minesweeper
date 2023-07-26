/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamelogic;

import java.util.Map;

/**
 *
 * @author libik
 */
public class MinesAround {

    private int index[];
    private int mines;

    public MinesAround(int index[], int mines) {
        this.mines = mines;
        this.index = index;

    }

    public boolean isConnected(MinesAround mine) {
        for (int i = 0; i < index.length; i++) {
            for (int j = 0; j < mine.getIndex().length; j++) {
                if (index[i] == mine.getIndex()[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isThisRight(boolean solution[], TwoKeysList map) {
        if (getValue(solution,map) == mines) {
            return true;
        } else {
            return false;
        }
    }

    public int getValue(boolean solution[],TwoKeysList map) {
        int count = 0;
        for (int i = 0; i < index.length; i++) {
            if (solution[map.getValue(index[i])]) {
                count++;
            }
        }
        return count;
    }

    /**
     * @return the mines
     */
    public int getMines() {
        return mines;
    }

    /**
     * @return the index
     */
    public int[] getIndex() {
        return index;
    }
}
