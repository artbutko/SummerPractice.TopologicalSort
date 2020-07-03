package graph;

/**
 * @Toshi:
 * Надо бы сделать поле g, сделать приватным и придумать методы получения значений, как думаете?
 */

import java.lang.*;
import java.util.*;

enum typeOfError{
    LOOP,
    FOREST;
}

public class Graph {


    Map<Integer, Vertex> g;                     // Надо бы сделать поле приватным и реализовать методы, как думаете?

    public Graph(){}

    public boolean addVertex(int v){return true;}
    public boolean removeVertex(int v){return true;}
    public boolean addEdge(int vFrom, int vTo){return true;}
    public boolean removeEdge(int vFrom, int vTo){return true;}
    public boolean replace(){return true;} // Метод для "Песочницы"
    public boolean isEmpty(){return true;}

    public void gHasError(typeOfError e){               //Псевдо обработчик ошибок
        if(e == typeOfError.FOREST){
            System.out.println("TIPO GRAPH IS A FOREST");
        }

        else if(e == typeOfError.LOOP){
            System.out.println("TIPO GRAPH ETO LOOP");
        }
    }


}
