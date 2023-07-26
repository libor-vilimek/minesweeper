/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gamelogic;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author libik
 */
public class TwoKeysList {
    List<TwoKeys> list;

    public TwoKeysList(){
        list = new LinkedList<TwoKeys>();
    }

    public boolean addValues(int key, int value){
        if (canJoin(key,value)){
            list.add(new TwoKeys(key,value));
            return true;
        }
        return false;
    }

    public boolean canJoin(int key, int value){
        for (TwoKeys tempList : list){
            if ((tempList.getKey() == key) || (tempList.getValue() == value)){
                return false;
            }
        }
        return true;
    }

    public int getKey(int value){
        for (TwoKeys tempList : list){
            if (tempList.getValue() == value){
                return tempList.getKey();
            }
        }        
        return -1;
    }

    public int getValue(int key){
        for (TwoKeys tempList : list){
            if (tempList.getKey() == key){
                return tempList.getValue();
            }
        }
        return -1;
    }

    public int size(){
        return list.size();
    }
}
