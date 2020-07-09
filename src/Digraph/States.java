package Digraph;

import Digraph.*;
import java.lang.*;
import java.util.*;

public class States {
    private ArrayList<Map<String, Vertex>> states;
    private int curIndex = 0;

    public States(){
        this.states = new ArrayList<Map<String, Vertex>>();
    }



    public void addState(Map<String, Vertex> state){
        states.add(state);
    }

    public Map<String, Vertex> getState(int index){
        return states.get(index);
    }

    public ArrayList<Map<String, Vertex>> getSt(){
        return states;
    }

    public Map<String, Vertex> nextState(){
        if((curIndex+1) < states.size())
            return getState(++curIndex);
        else
            return getState(curIndex);
    }

    public Map<String, Vertex> prevState(){
        if(curIndex > 0)
            return getState(--curIndex);
        else
            return getState(curIndex);
    }

    public Map<String, Vertex> setStartState(){
        return getState(0);
    }

    public Map<String, Vertex> setFinishState(){
        return getState(states.size() -1 );
    }

}
