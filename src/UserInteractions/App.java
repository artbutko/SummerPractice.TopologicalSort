package UserInteractions;

import Digraph.*;
import Algorithm.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 * Костяк GUI приложения визуализации топологической сортировки
 * @author artbutko
 * @version 0.4
 * #TODO удаление ребер, привязка к структуре (Digraph), вывод ответа окном.
 */

public class App extends JFrame
{
    private JMenuBar menu;
    private JPanel toolBar;
    private DrawPanel canvas;
    private final Listener listener = new Listener();

    /** кнопки нужны во всем суперклассе UserInteractions.App */
    JButton buttonAddVert;
    JButton buttonRemVert;
    JButton buttonAddEdge;
    JButton buttonRemEdge;
    JButton buttonSort;

    /** метод создания меню */
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

        /* Кнопка добавления вершины */
        buttonAddVert = new JButton("[+] вершина");
        toolBar.add(buttonAddVert);
        buttonAddVert.setActionCommand("Add Vertex");
        buttonAddVert.addActionListener(listener);

        /* Кнопка удаления вершины */
        buttonRemVert = new JButton("[-] вершина");
        toolBar.add(buttonRemVert);
        buttonRemVert.setActionCommand("Remove Vertex");
        buttonRemVert.addActionListener(listener);

        /* Кнопка добавления ребра */
        buttonAddEdge = new JButton("[+] ребро");
        toolBar.add(buttonAddEdge);
        buttonAddEdge.setActionCommand("Add Edge");
        buttonAddEdge.addActionListener(listener);

        /* Кнопка удаления ребра */
        buttonRemEdge = new JButton("[-] ребро");
        toolBar.add(buttonRemEdge);
        buttonRemEdge.setActionCommand("Remove Edge");
        buttonRemEdge.addActionListener(listener);

        /* Кнопка сортировки */
        buttonSort = new JButton("сортировка");
        toolBar.add(buttonSort);
        buttonSort.setActionCommand("Sort");
        buttonSort.addActionListener(listener);
    }

    /** класс для отслеживания нажатий кнопок */
    private class Listener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == buttonAddVert)
            {
                System.out.println(e.getActionCommand());
                canvas.setPressedButton(buttonAddVert);
            }
            else if(e.getSource() == buttonRemVert)
            {
                System.out.println(e.getActionCommand());
                canvas.setPressedButton(buttonRemVert);
            }
            else if(e.getSource() == buttonAddEdge)
            {
                System.out.println(e.getActionCommand());
                canvas.setPressedButton(buttonAddEdge);
            }
            else if(e.getSource() == buttonRemEdge)
            {
                System.out.println(e.getActionCommand());
                canvas.setPressedButton(buttonRemEdge);
            }
            else if(e.getSource() == buttonSort) {
                System.out.println(e.getActionCommand());
                canvas.setPressedButton(buttonSort);
            }
        }
    }

    /** метод создания панели для рисования */
    private void createCanvas()
    {
        canvas = new DrawPanel();
        canvas.setLayout(new FlowLayout());
        canvas.setBackground(new java.awt.Color(255, 255, 255));
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
        canvas.drawProcess();

        /* window settings */
        frame.setPreferredSize(new Dimension(720, 480));
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

class DrawPanel extends JPanel
{
    /** Ориентированный граф */
    public Digraph digraph;

    /** Поле активных (заблокированных) кнопок */
    private boolean  isAddVertex, isRemVertex, isAddEdge, isRemEdge, isSort;

    /** Поле для MouseEvent, для фиксации двух кликов мыши */
    private boolean isTwoVertices = false;

    /** Поля для двух вершин, у которых будет добавлено (удалено) ребро */
    Vertex vertex = new Vertex("");

    /** Поля с вершинами, ребрами, именами вершин */
    public ArrayList<Vertex> vertices;
    public ArrayList<Edge> edges;
    public ArrayList<String> labels;

    /** Конструктор класса */
    DrawPanel()
    {
        digraph = new Digraph();
        vertices = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();
        labels = new ArrayList<String>();
    }



    int k = 0;

    /*private void structBuilder()
    {
        for(Vertex p : vertices)
        {
            digraph.addVertex(labels.get(k));
            digraph.getMap().get(labels.get(k)).point = p;
            k += 1;
        }

        for(String key: digraph.getMap().keySet())
        {
            for(Line line : edges)
            {
                if (digraph.getMap().get(key).point.x == line.point1.x)
                {
                    for(Vertex p : vertices) {
                        if (p.x == line.point2.x) {
                            for (String key2 : digraph.getMap().keySet()) {
                                if (digraph.getMap().get(key2).point.x == p.x) {
                                    digraph.getMap().get(key).addVNext(digraph.getMap().get(key2));
                                    digraph.addEdge(key2, key);
                                    System.out.println(key + " " + key2);
                                }
                            }

                        }
                    }
                }
            }
        }
        System.out.println("sorted!");

        System.out.println(digraph.getEdges());
    }*/

    private void setButtonsLocked()
    {
        isAddVertex = false;
        isRemVertex = false;
        isAddEdge = false;
        isRemEdge = false;
        isSort = false;
    }

    public void setPressedButton(JButton button)
    {
        setButtonsLocked();
        switch (button.getActionCommand())
        {
            case "Add Vertex" -> {
                isAddVertex = true;
                System.out.println("OK_ADD_VERT");
            }
            case "Remove Vertex" -> {
                isRemVertex = true;
                System.out.println("OK_REM_VERT");
            }
            case "Add Edge" -> {
                isAddEdge = true;
                System.out.println("OK_ADD_EDGE");
            }
            case "Remove Edge" -> {
                isRemEdge = true;
                System.out.println("OK_REM_EDGE");
            }
            case "Sort" -> {
                isSort = true;
                System.out.println("OK_SORT");
            }
        }
    }


    private void lineCreator(MouseEvent event)
    {
        int x = event.getX();
        int y = event.getY();

        if (!isTwoVertices)
        {
            for (Vertex point : vertices)
                if (x < point.x + 25 & x > point.x - 25 & y < point.y + 25 & y > point.y - 25)
                {
                    vertex.x = (int)point.x;
                    vertex.y = (int)point.y;
                    isTwoVertices = true;
                    break;
                }
            System.out.println("Vertex 1");
        }
        else
        {
            for (Vertex point : vertices)
                if (x < point.getX() + 25 & x > point.getX() - 25 & y < point.getY() + 25 & y > point.getY() - 25)
                {
                    // Опять же не очень история
                    System.out.println((int)vertex.x + " " + (int)vertex.y);
                    Vertex v = new Vertex("");
                    v.x = point.x;
                    v.y = point.y;
                    edges.add(new Edge(vertex, v));
                    //digraph.addEdge(vSource.getName(), vStock.getName());

                    System.out.println("Vertex 2");
                    isTwoVertices = false;
                    break;
                }
        }
    }

    /*
    private void lineRemover(MouseEvent event)
    {
        int x = event.getX();
        int y = event.getY();
        if (!isTwovertices)
        {
            for (Vertex point : vertices)
                if (x < point.getX() + 25 & x > point.getX() - 25 & y < point.getY() + 25 & y > point.getY() - 25)
                {
                    point1 = new Vertex((int) point.getX(), (int) point.getY());
                    isTwovertices = true;
                    break;
                }
            System.out.println("Vertex 1");
        }
        else
        {
            point2 = point1;
            for (Vertex point : vertices)
                if (x < point.getX() + 25 & x > point.getX() - 25 & y < point.getY() + 25 & y > point.getY() - 25)
                {
                    point1 = new Vertex((int) point.getX(), (int) point.getY());
                    for (int i = 0; i < edges.size(); ++i)
                    {
                        System.out.println("Vertex1 = " + edges.get(i).point1.getX() + " " + edges.get(i).point1.getY());
                        System.out.println("Vertex2 = " + edges.get(i).point2.getX() + " " + edges.get(i).point2.getY());
                        System.out.println("New p1 = " + point1.getX() + " " + point1.getY());
                        System.out.println("New p2 = " + point2.getX() + " " + point2.getY());

                        //#TODO нормальный поиск двух точек
                        if ((edges.get(i).point1.getX() == point1.getX() && edges.get(i).point2.getX() == point2.getX() &&
                                edges.get(i).point1.getY() == point1.getY() && edges.get(i).point2.getY() == point2.getY()) ||
                                edges.get(i).point1.getX() == point2.getX() && edges.get(i).point2.getX() == point1.getX() &&
                                        edges.get(i).point1.getY() == point2.getY() && edges.get(i).point2.getY() == point1.getY()) {
                            System.out.println("Vertex found!");
                            edges.remove(edges.get(i));

                            break;
                        }
                    }
                    System.out.println("Vertex 2");
                    isTwovertices = false;
                    break;
                }
        }
    }*/

    /*
    //KOSTYL'
    private void incLineRemover(Vertex point)
    {
        int i = -1;
        for(Line stroke : edges) {
            if ((stroke.point1.getX() == point.getX() && stroke.point1.getY() == point.getY()) ||
                    (stroke.point2.getX() == point.getX() && stroke.point2.getY() == point.getY())) {

                edges.remove(stroke);
                break;
            }
            i++;
        }
    } */

    //#TODO глобальный баг с кнопками
    int index = 0;
    public void drawProcess()
    {
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent event)
            {
                if(isAddVertex)
                {
                    isTwoVertices = false;

                    String definition = JOptionPane.showInputDialog("Введите имя вершины");

                    Vertex vertex = new Vertex(definition);
                    vertex.setPoint(new Point(event.getX(), event.getY()));
                    // Разлад тут ---------------
                    vertices.add(vertex);
                    digraph.addVertex(definition);
                    // ---------------------------
                    labels.add(definition);
                    index++;

                    repaint();
                }
                else if (isRemVertex)
                {
                    isTwoVertices = false;

                    int x = event.getX() - 10;
                    int y = event.getY() - 10;

                    for (int i = 0; i < vertices.size(); ++i)
                        if (x < vertices.get(i).getX() + 25 & x > vertices.get(i).getX() - 25 & y < vertices.get(i).getY() + 25 & y > vertices.get(i).getY() - 25)
                        {
                            for(int j = 0; j < vertices.size(); j++)
                                //incLineRemover(vertices.get(i));
                            // #TODO подумать над созданием класса где будут имена + точки
                            digraph.removeVertex(vertices.get(i));
                            labels.remove(i);
                            vertices.remove(i);
                            break;
                        }
                    repaint();
                }
                else if(isAddEdge)
                {
                    lineCreator(event);
                    repaint();
                }
                else if(isRemEdge)
                {
                    //#TODO баг с точками
                    //lineRemover(event);
                    repaint();
                }
                else if(isSort)
                {
                    //structBuilder();
                    Algorithm algorithm = new Algorithm(digraph);
                    JOptionPane.showConfirmDialog(null, algorithm.sort(), "Результат сортировки", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                }
            }
        });
    }

    private void drawvertices(Graphics g)
    {
        Graphics2D gvertices = (Graphics2D) g;
        gvertices.setColor(Color.blue);
        IntStream.range(0, vertices.size()).forEach(i -> {
            gvertices.fillOval((int) vertices.get(i).x, (int) vertices.get(i).y, 20, 20);
            gvertices.drawString(labels.get(i), (int) vertices.get(i).x, (int) vertices.get(i).y);
        });
    }

     private void drawLines(Graphics g)
    {
        Graphics2D gEdges = (Graphics2D) g;
        gEdges.setColor(Color.gray);

        for (Edge edge : edges)
        {
            gEdges.drawLine((int)edge.vGetSource().x + 10, (int)edge.vGetSource().y + 10, (int)edge.vGetStock().x + 10, (int)edge.vGetStock().y + 10);
            System.out.println((int)edge.vGetSource().x + 10 + " " + (int)edge.vGetSource().y + 10 + " " + (int)edge.vGetStock().x + 10 + " " + (int)edge.vGetStock().y + 10);
        }

    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        drawLines(g);
        drawvertices(g);
    }
}
