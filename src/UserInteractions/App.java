package UserInteractions;

import Digraph.*;
import Algorithm.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.Random;

/**
 * Костяк GUI приложения визуализации топологической сортировки
 * @author artbutko
 * @version 0.4
 * #TODO удаление ребер, привязка к структуре (Digraph), вывод ответа окном.
 */

public class App extends JFrame
{
    private JFrame frame;
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
    JButton buttonNext;
    JButton buttonPrev;
    JButton buttonResult;

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

        /* Кнопка назад */
        buttonPrev = new JButton("<=");
        toolBar.add(buttonPrev);
        buttonPrev.setVisible(false);
        buttonPrev.setActionCommand("Prev");
        buttonPrev.addActionListener(listener);

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

        /* Кнопка результат */
        buttonResult = new JButton("результат");
        toolBar.add(buttonResult);
        buttonResult.setVisible(false);
        buttonResult.setActionCommand("Result");
        buttonResult.addActionListener(listener);


        /* Кнопка вперед */
        buttonNext = new JButton("=>");
        toolBar.add(buttonNext);
        buttonNext.setVisible(false);
        buttonNext.setActionCommand("Next");
        buttonNext.addActionListener(listener);

    }

    /** класс для отслеживания нажатий кнопок */
    private class Listener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == buttonResult)
            {
            System.out.println(e.getActionCommand());
            canvas.setPressedButton(buttonResult);
            }
            if(e.getSource() == buttonPrev)
            {
                System.out.println(e.getActionCommand());
                canvas.setPressedButton(buttonPrev);
            }
            else if (e.getSource() == buttonAddVert)
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
                if (!canvas.digraph.isEmpty())
                {
                    System.out.println(e.getActionCommand());
                    canvas.setPressedButton(buttonSort);
                    toolBar.remove(buttonAddVert);
                    toolBar.remove(buttonRemVert);
                    toolBar.remove(buttonAddEdge);
                    toolBar.remove(buttonRemEdge);
                    toolBar.remove(buttonSort);
                    //toolBar.remove(buttonSort);
                    buttonPrev.setVisible(true);
                    buttonNext.setVisible(true);
                    buttonResult.setVisible(true);
                    toolBar.repaint();
                }
                else
                {
                    JOptionPane.showMessageDialog(frame,
                            "Your Digraph is empty.",
                            "Error",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
            else if(e.getSource() == buttonNext){
                System.out.println(e.getActionCommand());
                canvas.setPressedButton(buttonNext);
            }
        }
    }

    /** метод создания панели для рисования */
    private void createCanvas()
    {
        canvas = new DrawPanel();
        canvas.setLayout(new FlowLayout());
        canvas.setBackground(new java.awt.Color(246, 180, 180));
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
        canvas.drawProcess();

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
    /** Ориентированный граф */
    public Digraph digraph;

    /** Массив вершин для пошаговой отрисовки алгоритма */
    private ArrayList<Vertex> vertexes;

    /** Поле для MouseEvent, для фиксации двух кликов мыши */
    private boolean isTwoVertices = false;

    private boolean  isAddVertex, isRemVertex, isAddEdge, isRemEdge, isSort;

    /** Конструктор класса */
    DrawPanel()
    {
        digraph = new Digraph();
        vertexes = new ArrayList<Vertex>();
        edgeBuff = new Edge(null, null);
    }

    private void setButtonsLocked()
    {
        isAddVertex = false;
        isRemVertex = false;
        isAddEdge = false;
        isRemEdge = false;
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
                System.out.println("OK_SORT");
                int i = 0;
                Algorithm algorithm = new Algorithm(digraph);
                JOptionPane.showConfirmDialog(null, algorithm.sort(), "Результат сортировки", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                digraph.states.setStartState();
                for(String key: digraph.getMap().keySet()){
                    digraph.getMap().get(key).setColor(digraph.states.getState().get(i));
                    i++;
                }
                System.out.println(i);
                isSort = true;
                repaint();
            }
            case "Next" -> {
                isSort = false;
                System.out.println("OK_NEXT");
                digraph.states.nextState();
                int i = 0;
                for(String key: digraph.getMap().keySet()){
                    digraph.getMap().get(key).setColor(digraph.states.getState().get(i));
                    i++;
                }
                repaint();
            }
            case "Prev" -> {
                isSort = false;
                System.out.println("OK_PREV");
                digraph.states.prevState();
                int i = 0;
                for(String key: digraph.getMap().keySet()){
                    digraph.getMap().get(key).setColor(digraph.states.getState().get(i));
                    i++;
                }
                repaint();
            }
            case "Result" -> {
                isSort = true;
                System.out.println("RESULT");
                repaint();
            }
        }
    }


    public Edge edgeBuff;

    private void lineCreator(MouseEvent event)
    {
        int x = event.getX();
        int y = event.getY();

        if (!isTwoVertices)
        {
            for (String key: digraph.getMap().keySet())
                if (x < digraph.getElement(key).getX() + 25 & x > digraph.getElement(key).getX() - 25 & y < digraph.getElement(key).getY() + 25 & y >digraph.getElement(key).getY() - 25)
                {
                    edgeBuff.vSetSource(digraph.getElement(key));
                    isTwoVertices = true;
                    break;
                }
            System.out.println("Vertex 1");

        }
        else
        {
            for (String key : digraph.getMap().keySet())
                if (x < digraph.getElement(key).getX() + 25 & x > digraph.getElement(key).getX() - 25 & y < digraph.getElement(key).getY() + 25 & y >digraph.getElement(key).getY() - 25)
                {

                    edgeBuff.vSetStock(digraph.getElement(key));
                    digraph.addEdge(edgeBuff);
                    edgeBuff.vGetSource().addVNext(edgeBuff.vGetStock());

                    System.out.println("Vertex 2");
                    isTwoVertices = false;
                    break;
                }
        }
    }

    private void lineRemover(MouseEvent event)
    {
        int x = event.getX();
        int y = event.getY();

        if (!isTwoVertices)
        {
            for (String key: digraph.getMap().keySet())
                if (x < digraph.getElement(key).getX() + 25 & x > digraph.getElement(key).getX() - 25 & y < digraph.getElement(key).getY() + 25 & y >digraph.getElement(key).getY() - 25)
                {
                    edgeBuff.vSetSource(digraph.getElement(key));
                    isTwoVertices = true;
                    break;
                }
            System.out.println("Vertex 1");
        }
        else
        {
            for (String key : digraph.getMap().keySet())
                if (x < digraph.getElement(key).getX() + 25 & x > digraph.getElement(key).getX() - 25 & y < digraph.getElement(key).getY() + 25 & y >digraph.getElement(key).getY() - 25)
                {
                    edgeBuff.vSetStock(digraph.getElement(key));
                    digraph.removeEdge(edgeBuff.vGetSource(), edgeBuff.vGetStock());
                    System.out.println("Vertex 2");
                    isTwoVertices = false;
                    break;
                }
        }
    }

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
                    digraph.addVertex(definition);
                    digraph.getElement(definition).setPoint(new Point(event.getX(), event.getY()));
                    index++;

                    repaint();
                }
                else if (isRemVertex)
                {
                    isTwoVertices = false;

                    int x = event.getX() - 10;
                    int y = event.getY() - 10;

                    for (String key : digraph.getMap().keySet()){
                        if (x < digraph.getElement(key).getX() + 25 & x > digraph.getElement(key).getX() - 25 & y < digraph.getElement(key).getY() + 25 & y >digraph.getElement(key).getY() - 25)
                        {
                            digraph.removeVertex(digraph.getElement(key));
                            break;
                        }
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
                    lineRemover(event);
                    repaint();
                }
            }
        });
    }


    private void drawVertices(Graphics g)
    {
        Graphics2D gVertices = (Graphics2D) g;


        for (String key: digraph.getMap().keySet()){
            gVertices.setColor(digraph.getElement(key).getColor());
            gVertices.fillOval((int)digraph.getElement(key).getX(), (int)digraph.getElement(key).getY(), 20, 20);
            gVertices.drawString(digraph.getElement(key).getName(), (int)digraph.getElement(key).getX(), (int)digraph.getElement(key).getY());
        }
        /**
         IntStream.range(0, digraph.getMap().size()).forEach(i -> {
         gVertices.fillOval((int) vertices.get(i).x, (int) vertices.get(i).y, 20, 20);
         gVertices.drawString(digraph.get(i), (int) vertices.get(i).x, (int) vertices.get(i).y);
         });
         */

    }

    private void drawLines(Graphics g)
    {
        Graphics2D gEdges = (Graphics2D) g;
        gEdges.setColor(Color.black);

        for (Edge edge: digraph.getEdges())
        {
            gEdges.drawLine((int)edge.vGetSource().getX() + 10, (int)edge.vGetSource().getY() + 10, (int)edge.vGetStock().getX() + 10, (int)edge.vGetStock().getY() + 10);
            // Нарисовать стрелку
            System.out.println((int)edge.vGetSource().getX() + 10 + " " + (int)edge.vGetSource().getY() + 10 + " " + (int)edge.vGetStock().getX() + 10 + " " + (int)edge.vGetStock().getY() + 10);
        }

    }

    private void drawResult(Graphics g)
    {
        Graphics2D gResult = (Graphics2D) g;

        Algorithm algorithm = new Algorithm(digraph);
        ArrayList<String> arr = algorithm.sort();
        int Y = 200;
        int X = 40;
        int size = 40;

        for (String element : arr)
        {
            gResult.setColor(digraph.getElement(element).getColor());
            gResult.fillOval(X, Y, 20, 20);
            gResult.drawString(digraph.getElement(element).getName(), X, Y);
            X += 40;
        }

        X = 50;
        Y += 10;
        int count = 0;
        boolean Switch = true;
        Random rand = new Random();

        for (String label : arr)
        {
            for (Vertex Next : digraph.getElement(label).getVNext())
            {
                float red = rand.nextFloat();
                float green = rand.nextFloat();
                float blue = rand.nextFloat();
                int index = arr.indexOf(Next.getName());
                gResult.setColor(new Color(red, green, blue));
                if (Switch)
               {
                   gResult.drawLine(X + 40 * count, Y, X + 40 * count, Y + size);
                   gResult.drawLine(X + 40 * index, Y, X + 40 * index, Y + size);
                   gResult.drawLine(X + 40 * count, Y + size, X + 40 * index, Y + size);
                   Switch = false;
               }
               else
               {
                    gResult.drawLine(X + 40 * count, Y, X + 40 * count, Y - size);
                    gResult.drawLine(X + 40 * index, Y, X + 40 * index, Y - size);
                    gResult.drawLine(X + 40 * count, Y - size, X + 40 * index, Y - size);
                    Switch = true;
                    size += 20;
               }
            }
            count++;
        }
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (isSort)
        {
            drawResult(g);
        }
        else
        {
            drawLines(g);
            drawVertices(g);
        }
    }
}