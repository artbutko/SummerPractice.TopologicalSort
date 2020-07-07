package UserInteractions.GUI;

import Digraph.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VertexApp {
    private ArrayList<Vertex> vertexesList = new ArrayList<Vertex>();
    private ArrayList<Vertex> selected = new ArrayList<Vertex>();
    private ArrayList<Edge> edgesList = new ArrayList<Edge>();


    public ArrayList<Edge> getEdgesList() {
        return edgesList;
    }

    public ArrayList<Vertex> getVertexesList() {
        return vertexesList;
    }

    public ArrayList<Vertex> getSelected() {
        return selected;
    }
}
