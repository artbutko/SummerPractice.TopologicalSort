import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

/**
 * Костяк GUI приложения визуализации топологической сортировки
 * @author artbutko
 * @version 0.2
 * #TODO привязать кнопки к методам, рисование на поле, удаление. P.S Перегружать методы взаимодействия по кнопке.
 * #TODO массив "кругов" (вершин) с координатами и радиусами вершин, аналогично с ребрами графа.
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
            else if(e.getSource() == buttonSort)
            {
                System.out.println(e.getActionCommand());
                canvas.setPressedButton(buttonSort);
            }
            canvas.drawProcess();
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

    int deleteIndex = -1;

    public ArrayList<Point> points;
    public ArrayList<BasicStroke> strokes;

    DrawPanel()
    {
        points = new ArrayList<Point>();
        strokes = new ArrayList<BasicStroke>();
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

    public void drawProcess()
    {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e)
            {
                if(isAddVertex)
                {
                    points.add(new Point(e.getX() - 10, e.getY() - 10));
                    repaint();
                }
                else if (isRemVertex)
                {
                    int x = e.getX();
                    int y = e.getY();
                    for (int i = 0; i < points.size(); ++i)
                        //#TODO исправить баг с удалением вершины
                        if (x < points.get(i).getX() + 25 & x > points.get(i).getX() - 25 & y < points.get(i).getY() + 25 & y > points.get(i).getY() - 25)
                        {
                            deleteIndex = i;
                            break;
                        }
                    repaint();
                }
                else if(isAddEdge)
                {

                }
                else if(isRemEdge)
                {

                }

            }
        });
    }

    private void drawPoints(Graphics g)
    {
        Graphics2D gPoints = (Graphics2D) g;
        if (deleteIndex != -1)
        {
            gPoints.setColor(Color.white);
            gPoints.fillOval(points.get(deleteIndex).x, points.get(deleteIndex).y, 20, 20);
            points.remove(deleteIndex);
            deleteIndex = -1;
        }
        gPoints.setColor(Color.blue);
        for (Point point : points)
        {
            gPoints.fillOval(point.x, point.y, 20, 20);
            gPoints.drawString("1", point.x, point.y);
        }
    }


    private void drawStrokes(Graphics g)
    {
        Graphics2D gStrokes = (Graphics2D) g;
        gStrokes.setColor(Color.gray);
        var bs1 = new BasicStroke(1, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_ROUND, 1.0f, new float[]{2f, 0f, 2f}, 2f);
        for (BasicStroke stroke : strokes)
        {
            gStrokes.setStroke(bs1);
            gStrokes.drawLine(20, 80, 250, 80);
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


