package UserInteractions.GUI;

import Digraph.Edge;
import Digraph.Vertex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class GraphPanel extends JComponent{


    private VertexApp vertexApp = new VertexApp();
    private int radius = 20;

    public GraphPanel() {
        this.setOpaque(true);
        this.addMouseListener(new MouseHandler());
        this.addMouseMotionListener(new MouseMotionHandler());
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(0, 0, getWidth(), getHeight());
        for (Edge e : vertexApp.getEdgesList()) {
            e.draw(g);
        }
        for (Vertex n : vertexApp.getVertexesList()) {
            n.draw(g);
        }
        if (selecting) {
            g.setColor(Color.darkGray);
            g.drawRect(mouseRect.x, mouseRect.y,
                    mouseRect.width, mouseRect.height);
        }
    }

    private Rectangle mouseRect = new Rectangle();
    private Point mousePt = new Point(720 / 2, 40 / 2);
    private boolean selecting = false;
    private class MouseHandler extends MouseAdapter {
        @Override
        public void mouseReleased(MouseEvent e) {
            selecting = false;
            mouseRect.setBounds(0, 0, 0, 0);
            e.getComponent().repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            mousePt = e.getPoint();
            if (e.isShiftDown()) {
                Vertex.selectToggle(vertexApp.getVertexesList(), mousePt);
            } else if (e.isPopupTrigger()) {
                Vertex.selectOne(vertexApp.getVertexesList(), mousePt);
            } else if (Vertex.selectOne(vertexApp.getVertexesList(), mousePt)) {
                selecting = false;
            } else {
                Vertex.selectNone(vertexApp.getVertexesList());
                selecting = true;
            }
            e.getComponent().repaint();
        }

    }

    private class MouseMotionHandler extends MouseMotionAdapter {

        Point delta = new Point();

        @Override
        public void mouseDragged(MouseEvent e) {
            if (selecting) {
                mouseRect.setBounds(
                        Math.min(mousePt.x, e.getX()),
                        Math.min(mousePt.y, e.getY()),
                        Math.abs(mousePt.x - e.getX()),
                        Math.abs(mousePt.y - e.getY()));
                Vertex.selectRect(vertexApp.getVertexesList(), mouseRect);
            } else {
                delta.setLocation(
                        e.getX() - mousePt.x,
                        e.getY() - mousePt.y);
                Vertex.updatePosition(vertexApp.getVertexesList(), delta);
                mousePt = e.getPoint();
            }
            e.getComponent().repaint();
        }
    }

}
