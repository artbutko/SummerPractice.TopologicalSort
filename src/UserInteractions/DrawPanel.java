package UserInteractions;

import Algorithm.Algorithm;
import Digraph.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Random;
import java.util.Timer;
import java.util.Timer.*;


/**
 *  Класс-холст, с которым взаимодействует пользователь
 *  @author artbutko
 *  @version 1.0
 */

public class  DrawPanel extends JPanel
{

    /** Ориентированный граф. */
    public Digraph digraph;

    /** Поле для MouseEvent, для фиксации двух кликов мыши. */
    private boolean isTwoVertices = false;

    /** Поля для активной кнопки (функции). */
    private boolean isAddVertex, isRemVertex, isAddEdge, isRemEdge, isSort;

    /** Буффер для хранения ребра. */
    public Edge eBuffer;

    /** Массив для хранения отсортированного массива. */
    ArrayList<String> sortArray;

    /** Конструктор класса. */
    DrawPanel()
    {
        digraph = new Digraph();
        eBuffer = new Edge(null, null);
    }

    /** Функция "блокировки" кнопок. */
    private void setButtonsLocked()
    {
        isAddVertex = false;
        isRemVertex = false;
        isAddEdge = false;
        isRemEdge = false;
    }

    /** Функция проверки графа на цикл. */
    public boolean isLoop()
    {
        Algorithm algorithm = new Algorithm(digraph);
        sortArray = algorithm.sort();
        return sortArray == null;
    }

    public void getNext()
    {
        isSort = false;
        System.out.println("OK_NEXT");
        digraph.states.nextState();
        int i = 0;
        for(String key: digraph.getMap().keySet())
        {
            digraph.getMap().get(key).setColor(digraph.states.getState().get(i));
            i++;
        }
        repaint();
    }

    /** Функция нажатой кнопки.
     * @param button -- нажатая кнопка. */
    public void setPressedButton(JButton button) {
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

                JOptionPane.showConfirmDialog(null, sortArray, "Результат сортировки", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                for(String key: digraph.getMap().keySet())
                {
                    digraph.getMap().get(key).setColor(digraph.states.getState().get(i));
                    i++;
                }
                System.out.println(i);
                isSort = true;
                repaint();
            }
            case "Next" -> {
                getNext();
            }
            case "Prev" -> {
                isSort = false;
                System.out.println("OK_PREV");
                digraph.states.prevState();
                int i = 0;
                for(String key: digraph.getMap().keySet())
                {
                    digraph.getMap().get(key).setColor(digraph.states.getState().get(i));
                    i++;
                }
                repaint();
            }
            case "ToEnd" -> {
                isSort = false;
                System.out.println("OK_TO_END");
                digraph.states.setFinishState();
                int i = 0;
                for(String key: digraph.getMap().keySet()){
                    digraph.getMap().get(key).setColor(digraph.states.getState().get(i));
                    i++;
                }
                repaint();
            }
            case "ToStart" ->{
                isSort = false;
                System.out.println("OK_TO_START");
                digraph.states.setStartState();
                int i = 0;
                for(String key: digraph.getMap().keySet()){
                    digraph.getMap().get(key).setColor(digraph.states.getState().get(i));
                    i++;
                }
                repaint();
            }
            case "Play" -> {
                isSort = false;
                System.out.println("OK_PLAY");
                digraph.states.setStartState();

                Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        getNext();
                        if (digraph.states.getState() == digraph.states.colors.get(digraph.states.colors.size() - 1))
                        {
                            this.cancel();
                        }
                    }
                };
                timer.schedule(task, new Date(), 500);
            }
            case "Result" -> {
                isSort = true;
                System.out.println("RESULT");
                repaint();
            }
        }
    }

    /** Создание ребра
     * @param event -- событие мыши (нажатие). */
    private void lineCreator(MouseEvent event)
    {
        int x = event.getX();
        int y = event.getY();

        if (!isTwoVertices)
        {
            for (String key: digraph.getMap().keySet())
                if (x < digraph.getElement(key).getX() + 25 & x > digraph.getElement(key).getX() - 25 & y < digraph.getElement(key).getY() + 25 & y >digraph.getElement(key).getY() - 25)
                {
                    eBuffer.vSetSource(digraph.getElement(key));
                    isTwoVertices = true;
                    break;
                }
            System.out.println("Vertex 1");
        }
        else
            for (String key : digraph.getMap().keySet())
                if (x < digraph.getElement(key).getX() + 25 & x > digraph.getElement(key).getX() - 25 & y < digraph.getElement(key).getY() + 25 & y >digraph.getElement(key).getY() - 25)
                {

                    eBuffer.vSetStock(digraph.getElement(key));
                    if (!eBuffer.isSelfEdge())
                    {
                        digraph.addEdge(eBuffer);
                        eBuffer.vGetSource().addVNext(eBuffer.vGetStock());
                    }
                    System.out.println("Vertex 2");
                    isTwoVertices = false;
                    break;
                }
    }

    /** Удаление ребра
     * @param event -- событие мыши (нажатие). */
    private void lineRemover(MouseEvent event)
    {
        int x = event.getX();
        int y = event.getY();

        if (!isTwoVertices)
        {
            for (String key: digraph.getMap().keySet())
                if (x < digraph.getElement(key).getX() + 25 & x > digraph.getElement(key).getX() - 25 & y < digraph.getElement(key).getY() + 25 & y >digraph.getElement(key).getY() - 25)
                {
                    eBuffer.vSetSource(digraph.getElement(key));
                    isTwoVertices = true;
                    break;
                }
            System.out.println("Vertex 1");
        }
        else
            for (String key : digraph.getMap().keySet())
                if (x < digraph.getElement(key).getX() + 25 & x > digraph.getElement(key).getX() - 25 & y < digraph.getElement(key).getY() + 25 & y >digraph.getElement(key).getY() - 25)
                {
                    eBuffer.vSetStock(digraph.getElement(key));
                    digraph.removeEdge(eBuffer.vGetSource(), eBuffer.vGetStock());
                    System.out.println("Vertex 2");
                    isTwoVertices = false;
                    break;
                }
    }

    /** Функция для рисования */
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

                    String definition = JOptionPane.showInputDialog(null, "Введите имя вершины", null);
                    for(String key : digraph.getMap().keySet())
                        if (key.equals(definition))
                        {
                            JOptionPane.showConfirmDialog(null, "Такая вершина уже существует!", "Ошибка", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                    digraph.addVertex(definition);
                    digraph.getElement(definition).setPoint(new Point(event.getX(), event.getY()));
                    repaint();
                }
                else if (isRemVertex)
                {
                    isTwoVertices = false;

                    int x = event.getX() - 10;
                    int y = event.getY() - 10;

                    for (String key : digraph.getMap().keySet())
                        if (x < digraph.getElement(key).getX() + 25 & x > digraph.getElement(key).getX() - 25 & y < digraph.getElement(key).getY() + 25 & y >digraph.getElement(key).getY() - 25)
                        {
                            digraph.removeVertex(digraph.getElement(key));
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
                    lineRemover(event);
                    repaint();
                }
            }
        });
    }

    /** Функция рендеринга и рисования вершин */
    private void drawVertices(Graphics g)
    {
        Graphics2D gVertices = (Graphics2D) g;
        for (String key: digraph.getMap().keySet())
        {
            gVertices.setColor(digraph.getElement(key).getColor());
            gVertices.fillOval((int)digraph.getElement(key).getX(), (int)digraph.getElement(key).getY(), 20, 20);
            gVertices.drawString(digraph.getElement(key).getName(), (int)digraph.getElement(key).getX(), (int)digraph.getElement(key).getY());
        }
    }

    /** Функция рендеринга и рисования ребер */
    private void drawLines(Graphics g)
    {
        Graphics2D gEdges = (Graphics2D) g;
        gEdges.setColor(Color.black);

        for (Edge edge: digraph.getEdges())
        {
            double x1 = edge.vGetSource().getX();
            double x2 = edge.vGetStock().getX();
            double y1 = edge.vGetSource().getY();
            double y2 = edge.vGetStock().getY();

            gEdges.drawLine((int)edge.vGetSource().getX() + 10, (int)edge.vGetSource().getY() + 10, (int)edge.vGetStock().getX() + 10, (int)edge.vGetStock().getY() + 10);
            gEdges.drawLine((int)(x1 + (x2 - x1)/2 + 5 * (y2 - y1)/(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)))) + 10,
                    (int)(y1 + (y2 - y1)/2 - 5 * (x2 - x1)/(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)))) + 10,
                    (int)(x1 + (x2 - x1)/2 - 5 * (y2 - y1)/(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)))) + 10,
                    (int)(y1 + (y2 - y1)/2 + 5 * (x2 - x1)/(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)))) + 10) ;

            gEdges.drawLine((int)(x1 + (x2 - x1)/2 + 5 * (y2 - y1)/(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)))) + 10,
                    (int)(y1 + (y2 - y1)/2 - 5 * (x2 - x1)/(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)))) + 10, (int)x2 + 10, (int)y2 + 10);
            gEdges.drawLine((int)(x1 + (x2 - x1)/2 - 5 * (y2 - y1)/(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)))) + 10,
                    (int)(y1 + (y2 - y1)/2 + 5 * (x2 - x1)/(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)))) + 10, (int)x2 + 10, (int)y2 + 10);
        }

    }

    /** Функция рендеринга и рисования результата */
    private void drawResult(Graphics g)
    {
        System.out.println("resssss");
        Graphics2D gResult = (Graphics2D) g;
        int count = 0;
        boolean isSwitch = true;
        Random rand = new Random();
        int size = 40;

        for (String element : sortArray)
        {
            gResult.setColor(Color.BLACK);
            gResult.fillOval(40 + count * 40, 180, 20, 20);
            gResult.drawString(digraph.getElement(element).getName(), 40 + count * 40, 180);
            count++;
        }
        count = 0;
        for (String label : sortArray)
        {
            for (Vertex Next : digraph.getElement(label).getVNext())
            {
                float red = rand.nextFloat();
                float green = rand.nextFloat();
                float blue = rand.nextFloat();
                int index = sortArray.indexOf(Next.getName());
                int nowIndex = sortArray.indexOf(label);
                gResult.setColor(new Color(red, green, blue));
                gResult.setStroke(new BasicStroke(3));

                if (nowIndex + 1 == index)
                    gResult.drawLine(50 + 40 * count, 190, 50 + 40 * index, 190);
                else if (isSwitch)
                {
                    gResult.drawLine(50 + 40 * count, 190, 50 + 40 * count, 190 + size);
                    gResult.drawLine(50 + 40 * index, 190, 50 + 40 * index, 190 + size);
                    gResult.drawLine(50 + 40 * count, 190 + size, 50 + 40 * index, 190 + size);
                    isSwitch = false;
                }
                else
                {
                    gResult.drawLine(50 + 40 * count, 190, 50 + 40 * count, 190 - size);
                    gResult.drawLine(50 + 40 * index, 190, 50 + 40 * index, 190 - size);
                    gResult.drawLine(50 + 40 * count, 190 - size, 50 + 40 * index, 190 - size);
                    isSwitch = true;
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
            drawResult(g);
        else
        {
            drawLines(g);
            drawVertices(g);
        }
    }
}
