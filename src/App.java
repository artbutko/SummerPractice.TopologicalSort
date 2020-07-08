import Digraph.*;
import Algorithm.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

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
    private JFrame frame;

    /** кнопки нужны во всем суперклассе App */
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
        frame = new JFrame("Топологическая сортировка");
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
    private boolean  isAddVertex, isRemVertex, isAddEdge, isRemEdge, isSort;

    private boolean isTwoPoints = false;
    Point point1 = new Point();
    Point point2 = new Point();

    private class Line
    {
        private final Point point1;
        private final Point point2;

        public Line(Point point1, Point point2)
        {
            this.point1 = point1;
            this.point2 = point2;
        }

        public Point getPoint1()
        {
            return point1;
        }

        public Point getPoint2()
        {
            return point2;
        }
    }

    int vDeleteIndex = -1;
    int eDeleteIndex = -1;

    public ArrayList<Point> points;
    public ArrayList<Line> strokes;
    public ArrayList<String> names;


    public Digraph digraph;

    int k = 0;

    private void qwer()
    {
        System.out.println(names);

        for(Point p : points)
        {
            digraph.addVertex(names.get(k));
            digraph.getMap().get(names.get(k)).point = p;
            k += 1;
        }

        for(String key: digraph.getMap().keySet())
        {
            for(Line line : strokes)
            {
                if (digraph.getMap().get(key).point.x == line.point1.x)
                {
                    for(Point p : points) {
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
    }

    DrawPanel()
    {
        digraph = new Digraph();
        points = new ArrayList<Point>();
        strokes = new ArrayList<Line>();
        names = new ArrayList<String>();
    }

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
        if (!isTwoPoints)
        {
            for (Point point : points)
                if (x < point.getX() + 25 & x > point.getX() - 25 & y < point.getY() + 25 & y > point.getY() - 25)
                {
                    point1 = new Point((int) point.getX(), (int) point.getY());
                    isTwoPoints = true;
                    break;
                }
            System.out.println("Point 1");
        }
        else
        {
            point2 = point1;
            for (Point point : points)
                if (x < point.getX() + 25 & x > point.getX() - 25 & y < point.getY() + 25 & y > point.getY() - 25)
                {
                    point1 = new Point((int) point.getX(), (int) point.getY());
                    strokes.add(new Line(point1, point2));
                    System.out.println("Point 2");
                    isTwoPoints = false;
                    break;
                }
        }
    }

    private void lineRemover(MouseEvent event)
    {
        int x = event.getX();
        int y = event.getY();
        if (!isTwoPoints)
        {
            for (Point point : points)
                if (x < point.getX() + 25 & x > point.getX() - 25 & y < point.getY() + 25 & y > point.getY() - 25)
                {
                    point1 = new Point((int) point.getX(), (int) point.getY());
                    isTwoPoints = true;
                    break;
                }
            System.out.println("Point 1");
        }
        else
        {
            point2 = point1;
            for (Point point : points)
                if (x < point.getX() + 25 & x > point.getX() - 25 & y < point.getY() + 25 & y > point.getY() - 25)
                {
                    point1 = new Point((int) point.getX(), (int) point.getY());
                    for (int i = 0; i < strokes.size(); ++i)
                    {
                        System.out.println("Point1 = " + strokes.get(i).point1.getX() + " " + strokes.get(i).point1.getY());
                        System.out.println("Point2 = " + strokes.get(i).point2.getX() + " " + strokes.get(i).point2.getY());
                        System.out.println("New p1 = " + point1.getX() + " " + point1.getY());
                        System.out.println("New p2 = " + point2.getX() + " " + point2.getY());

                        //#TODO нормальный поиск двух точек
                        if ((strokes.get(i).point1.getX() == point1.getX() && strokes.get(i).point2.getX() == point2.getX() &&
                                strokes.get(i).point1.getY() == point1.getY() && strokes.get(i).point2.getY() == point2.getY()) ||
                                strokes.get(i).point1.getX() == point2.getX() && strokes.get(i).point2.getX() == point1.getX() &&
                                        strokes.get(i).point1.getY() == point2.getY() && strokes.get(i).point2.getY() == point1.getY()) {
                            System.out.println("Point found!");
                            strokes.remove(strokes.get(i));

                            break;
                        }
                    }
                    System.out.println("Point 2");
                    isTwoPoints = false;
                    break;
                }
        }
    }

    //KOSTYL'
    private void incLineRemover(Point point)
    {
        int i = -1;
        for(Line stroke : strokes) {
            if ((stroke.point1.getX() == point.getX() && stroke.point1.getY() == point.getY()) ||
                    (stroke.point2.getX() == point.getX() && stroke.point2.getY() == point.getY())) {

                strokes.remove(stroke);
                break;
            }
            i++;
        }
    }

    //#TODO глобальный баг с кнопками
    int index = 0;
    public void drawProcess()
    {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e)
            {
                if(isAddVertex)
                {
                    isTwoPoints = false;
                    points.add(new Point(e.getX(), e.getY()));
                    names.add("" + index);
                    index++;
                    repaint();
                }
                else if (isRemVertex)
                {
                    //#TODO удаление ицендентных ребер
                    isTwoPoints = false;

                    int x = e.getX() - 10;
                    int y = e.getY() - 10;


                    for (int i = 0; i < points.size(); ++i)
                        //#TODO исправить баг с удалением вершины
                        if (x < points.get(i).getX() + 25 & x > points.get(i).getX() - 25 & y < points.get(i).getY() + 25 & y > points.get(i).getY() - 25)
                        {
                            for(int j = 0; j < points.size(); j++)
                                incLineRemover(points.get(i));
                            vDeleteIndex = i;
                            break;
                        }
                    repaint();
                }
                else if(isAddEdge)
                {
                    //#TODO исправить баг с некорректным рисованием после повторного нажатия кнопки "[+] ребро"
                    lineCreator(e);
                    repaint();
                }
                else if(isRemEdge)
                {
                    //#TODO баг с точками
                    lineRemover(e);
                    repaint();
                }
                else if(isSort)
                {
                    qwer();
                    Algorithm algorithm = new Algorithm(digraph);
                    JOptionPane.showConfirmDialog(null, algorithm.sort(), "Результат сортировки", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                }
            }
        });
    }

    private void drawPoints(Graphics g)
    {
        Graphics2D gPoints = (Graphics2D) g;
        if (vDeleteIndex != -1)
        {
            gPoints.setColor(Color.white);
            gPoints.fillOval(points.get(vDeleteIndex).x, points.get(vDeleteIndex).y, 20, 20);
            points.remove(vDeleteIndex);
            vDeleteIndex = -1;
        }
        gPoints.setColor(Color.blue);
        for (int i = 0; i < points.size(); ++i)
        {
            gPoints.fillOval(points.get(i).x, points.get(i).y, 20, 20);
            //#TODO индексы что-то
            gPoints.drawString(""+ i + "", points.get(i).x, points.get(i).y);
        }
    }

    private void drawStrokes(Graphics g)
    {
        Graphics2D gStrokes = (Graphics2D) g;
        gStrokes.setColor(Color.gray);

        for (Line stroke : strokes)
        {
            gStrokes.drawLine(stroke.getPoint1().x + 10, stroke.getPoint1().y + 10, stroke.getPoint2().x + 10, stroke.getPoint2().y + 10);
        }

    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        drawStrokes(g);
        drawPoints(g);
    }
}
