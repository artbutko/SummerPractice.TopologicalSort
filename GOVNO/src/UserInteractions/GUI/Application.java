package UserInteractions.GUI;

import Digraph.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ListIterator;

/**
 * Костяк GUI приложения визуализации топологической сортировки
 * @author artbutko
 * @version 0.1
 * #TODO привязать кнопки к методам, рисование на поле, удаление. P.S Перегружать методы взаимодействия по кнопке.
 * #TODO массив "кругов" (вершин) с координатами и радиусами вершин, аналогично с ребрами графа.
 */

public class Application extends JFrame
{

    private JMenuBar menu;
    private JPanel toolBar;
    private JPanel canvas;

    //--------------------------------------------------------------------------------------------------
    private int radius = 20;
    private VertexApp vertexApp = new VertexApp();
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

    //-------------------------------------------------------------------------------------------------
    private void createMenuBar() {
        /* header menu */
        menu = new JMenuBar();

        /* "Файл" и его подвкладки */
        JMenu mFile = new JMenu("Файл");

        JMenu mFileNew = new JMenu("Создать");
        mFile.add(mFileNew);

        JMenuItem mFileNewByString = new JMenuItem("Ввести строку");
        mFileNew.add(mFileNewByString);

        JMenu mFileOpen = new JMenu("Открыть");
        mFile.add(mFileOpen);

        JMenu mFileSave = new JMenu("Сохранить");
        mFile.add(mFileSave);

        mFile.addSeparator();

        JMenuItem mFileClose = new JMenu("Закрыть");
        mFile.add(mFileClose);

        mFileClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menu.add(mFile);

        /* "Помощь" и его подвкладки */
        JMenu mHelp = new JMenu("Помощь");

        JMenu mHelpAuthors = new JMenu("Авторы");
        mHelp.add(mHelpAuthors);

        JMenu mHelpAlgorithm = new JMenu("Алгоритм");
        mHelp.add(mHelpAlgorithm);

        menu.add(mHelp);
    }

    /** метод создания панели инструментов */
    private void createToolBar()
    {
        toolBar = new JPanel();
        toolBar.setPreferredSize(new Dimension(720, 40));



        JButton buttonAddVert = new JButton("[+] вершина");
        toolBar.add(buttonAddVert);
        buttonAddVert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vertex.selectNone(vertexApp.getVertexesList());
                Point coordinate = mousePt.getLocation();
                Vertex v = new Vertex(coordinate,radius, Color.WHITE, "A");
                v.setSelected(true);
                vertexApp.getVertexesList().add(v);
                System.out.println("Q");
                repaint();
            }
        });



        JButton buttonRemVert = new JButton("[-] вершина");
        toolBar.add(buttonRemVert);
        buttonRemVert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListIterator<Vertex> iter = vertexApp.getVertexesList().listIterator();
                while (iter.hasNext()) {
                    Vertex n = iter.next();
                    if (n.isSelected()) {
                        deleteEdges(n);
                        iter.remove();
                    }
                }
                repaint();
            }

            private void deleteEdges(Vertex v) {
                ListIterator<Edge> iter = vertexApp.getEdgesList().listIterator();
                while (iter.hasNext()) {
                    Edge e = iter.next();
                    if (e.getVFrom() == v || e.getVTo() == v) {
                        iter.remove();
                    }
                }
            }
        });




        JButton buttonAddEdge = new JButton("[+] ребро");
        toolBar.add(buttonAddEdge);
        buttonAddEdge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vertex.getSelected(vertexApp.getVertexesList(), vertexApp.getSelected());
                if (vertexApp.getSelected().size() > 1) {
                    for (int i = 0; i < vertexApp.getSelected().size() - 1; ++i) {
                        Vertex vFrom = vertexApp.getSelected().get(i);
                        Vertex vTo = vertexApp.getSelected().get(i + 1);
                        vertexApp.getEdgesList().add(new Edge(vFrom, vTo));
                    }
                }
                repaint();
            }
        });



        JButton buttonRemEdge = new JButton("[-] ребро");
        toolBar.add(buttonRemEdge);
        buttonRemEdge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListIterator<Vertex> iter = vertexApp.getVertexesList().listIterator();
                while (iter.hasNext()) {
                    Vertex v1 = iter.next();
                    if (v1.isSelected()) {
                        while (iter.hasNext()) {
                            Vertex v2 = iter.next();
                            if (v2.isSelected())
                                deleteEdges(v1, v2);
                        }
                    }
                }
                repaint();
            }

            private void deleteEdges(Vertex v1, Vertex v2) {
                ListIterator<Edge> iter = vertexApp.getEdgesList().listIterator();
                while (iter.hasNext()) {
                    Edge e = iter.next();
                    if (e.getVFrom() == v1 || e.getVTo() == v2) {
                        iter.remove();
                    }
                }
            }

        });



        JButton buttonSort = new JButton("сортировка");
        toolBar.add(buttonSort);
    }

    /** метод создания панели для рисования */
    private void createCanvas()
    {
        canvas = new JPanel();
        canvas.setLayout(new FlowLayout());
        canvas.setBackground(new java.awt.Color(169, 227, 219));
    }


    public void createGUI()
    {
        JFrame frame = new JFrame("Топологическая сортировка");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        /* Создание меню и добавление в окно приложения */
        createMenuBar();
        frame.setJMenuBar(menu);

        /* Создание панели с инструментами */
        createToolBar();
        /* Создание панели с холстом */
        createCanvas();

        frame.getContentPane().add(toolBar, BorderLayout.PAGE_START);
        frame.getContentPane().add(canvas, BorderLayout.CENTER);

        /* window settings */
        frame.setPreferredSize(new Dimension(720, 480));
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}