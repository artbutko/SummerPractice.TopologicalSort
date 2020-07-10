package Digraph;

import Digraph.*;

import java.awt.*;
import java.lang.*;
import java.util.*;

public class States {
    private ArrayList<ArrayList<Color>> colors;
    private int curIndex = 0;

    public States(){
        this.colors = new ArrayList<ArrayList<Color>>();
    }



    public void addState(ArrayList<Color> colorState){
        colors.add(colorState);
    }

    public ArrayList<Color> getState(){
        return colors.get(curIndex);
    }

    public ArrayList<ArrayList<Color>> getSt(){
        return colors;
    }

    public ArrayList<Color> nextState(){
        if((curIndex+1) < colors.size()) {
            curIndex++;
            return getState();
        }
        else
            return getState();
    }

    public ArrayList<Color> prevState(){
        if(curIndex > 0) {
            curIndex--;
            return getState();
        }
        else
            return getState();
    }

    public ArrayList<Color> setStartState(){
        return colors.get(0);
    }

    public ArrayList<Color> setFinishState(){
        return colors.get(colors.size()-1);
    }

}
