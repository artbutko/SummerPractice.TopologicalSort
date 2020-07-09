package UserInteractions;

import Digraph.*;
import Algorithm.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
    JButton buttonNext;

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
        /* Кнопка вперед */
        buttonNext = new JButton("->");
        toolBar.add(buttonNext);
        buttonNext.setActionCommand("Next");
        buttonNext.addActionListener(listener);
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
            else if(e.getSource() == buttonNext){
                System.out.println(e.getActionCommand());
                canvas.setPressedButton(buttonNext);
                toolBar.remove(buttonAddVert);
                toolBar.remove(buttonRemVert);
                toolBar.remove(buttonAddEdge);
                toolBar.remove(buttonRemEdge);
                toolBar.remove(buttonSort);
                toolBar.repaint();
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
    private boolean  isAddVertex, isRemVertex, isAddEdge, isRemEdge, isSort, isNext;

    /** Поле для MouseEvent, для фиксации двух кликов мыши */
    private boolean isTwoVertices = false;

    /** Конструктор класса */
    DrawPanel()
    {
        digraph = new Digraph();

        edgeBuff = new Edge(null, null);
    }


    private void setButtonsLocked()
    {
        isAddVertex = false;
        isRemVertex = false;
        isAddEdge = false;
        isRemEdge = false;
        isSort = false;
        isNext = false;
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
            case "Next" -> {
                isNext = true;
                System.out.println("OK_NEXT");
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

                    repaint();
                     }
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

                else if(isSort)
                {
                    Algorithm algorithm = new Algorithm(digraph);

                    JOptionPane.showConfirmDialog(null, algorithm.sort(), "Результат сортировки", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
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
        gEdges.setColor(Color.gray);

       for (Edge edge: digraph.getEdges())
        {
            gEdges.drawLine((int)edge.vGetSource().getX() + 10, (int)edge.vGetSource().getY() + 10, (int)edge.vGetStock().getX() + 10, (int)edge.vGetStock().getY() + 10);
            // Нарисовать стрелку
            System.out.println((int)edge.vGetSource().getX() + 10 + " " + (int)edge.vGetSource().getY() + 10 + " " + (int)edge.vGetStock().getX() + 10 + " " + (int)edge.vGetStock().getY() + 10);
        }

    }

    private void drawResult(Graphics g){

    }
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        drawLines(g);
        drawVertices(g);
        drawResult(g);
    }
}
