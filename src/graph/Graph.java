package graph;

import java.lang.*;
import java.util.*;


public class Graph {
    Map<Integer, Vertex> g; // Edge - гипотетически

    public Graph(){}

    public boolean addVertex(int v){return true;}
    public boolean removeVertex(int v){return true;}
    public boolean addEdge(int vFrom, int vTo){return true;}
    public boolean removeEdge(int vFrom, int vTo){return true;}
    public boolean replace(){return true;} // Метод для "Песочницы"
    public boolean isEmpty(){return true;}
    public boolean isForest(){return true;} // Возможно не понадобится
    public boolean isLoop(){return true;} // Возомжно не понадобится


}
