package UserInteractions;

import Digraph.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 *  класс GUI приложения визуализации топологической сортировки
 *  @author artbutko
 *  @version 1.0
 */

public class Application extends JFrame
{
    private final JFrame frame;
    private JMenuBar menu;
    private JPanel toolBar;
    private DrawPanel canvas;
    private final Listener listener = new Listener();

    /** кнопки нужны во всем суперклассе UserInteractions.App */
    private JButton buttonAddVert;
    private JButton buttonRemVert;
    private JButton buttonAddEdge;
    private JButton buttonRemEdge;
    private JButton buttonSort;
    private JButton buttonNext;
    private JButton buttonPrev;
    private JButton buttonResult;

    /** метод создания меню */
    private void createMenuBar() {
        /* header menu */
        menu = new JMenuBar();

        /* "Файл" и его подвкладки */
        JMenu mFile = new JMenu("Файл");


        JMenuItem mFileOpen = new JMenuItem("Открыть");
        mFile.add(mFileOpen);
        mFileOpen.addActionListener(e -> {
            String vertexDataPattern = ".+X\\d{3}Y\\d{3}";
            String edgeDataPattern = "From.+To.+";
            HashMap<String, Vertex> d = new HashMap<String, Vertex>();

            FileReader fileReader;
            try
            {
                fileReader = new FileReader("save.txt");
            }
            catch (IOException exception)
            {
                return ;
            }

            Scanner scanner = new Scanner(fileReader);
            Digraph graph = new Digraph();
            while (scanner.hasNextLine())
            {
                String cur = scanner.nextLine();
                if (cur.equals("."))
                    break;
                if (cur.matches(vertexDataPattern))
                {
                    try
                    {
                        int y, x;
                        y = Integer.parseInt(cur.substring(cur.length() -3));
                        x = Integer.parseInt(cur.substring(cur.length() - 7, cur.length() - 4));
                        String name = cur.substring(0, cur.length() - 8);
                        Vertex vertex = new Vertex(name);
                        vertex.setPoint(x,y);
                        graph.addVertex(vertex);
                    }
                    catch (NumberFormatException exception)
                    {
                        return;
                    }
                }
                else
                    return;
            }

            while (scanner.hasNextLine())
            {
                String cur = scanner.nextLine();

                if (cur.matches(edgeDataPattern))
                {
                    try {
                        String nameFrom = cur.substring(4, cur.indexOf("To"));
                        String nameTo = cur.substring(cur.indexOf("To")+2);

                        graph.addEdge(nameFrom, nameTo);
                    }
                    catch (NumberFormatException exception)
                    {
                        return;
                    }
                }
            }

            canvas.digraph = graph;
            canvas.repaint();
        });

        JMenuItem mFileSave = new JMenuItem("Сохранить");
        mFile.add(mFileSave);
        mFileSave.addActionListener(e -> {
            FileWriter fileWriter;
            try
            {
                fileWriter = new FileWriter("save.txt");
            }
            catch(IOException exception)
            {
                exception.printStackTrace();
                return;
            }

            StringBuilder graphInfo = new StringBuilder();
            for (Vertex v : canvas.digraph.getVertexes())
            {
                StringBuilder vertexInfo = new StringBuilder();
                String xCoordinate = coordinateConverter((int)v.getX());
                String yCoordinate = coordinateConverter((int)v.getY());


                vertexInfo.append(v.getName());
                vertexInfo.append("X");
                vertexInfo.append(xCoordinate);
                vertexInfo.append("Y");
                vertexInfo.append(yCoordinate);
                vertexInfo.append("\n");

                graphInfo.append(vertexInfo);
            }
            graphInfo.append(".\n");

            ArrayList<Edge> edges = canvas.digraph.getEdges();
            for (Edge edge : edges)
            {
                String edgeInfo = "From" +
                        edge.vGetSource().getName() +
                        "To" +
                        edge.vGetStock().getName() +
                        "\n";
                graphInfo.append(edgeInfo);
            }

            try {
                fileWriter.write(graphInfo.toString());
                fileWriter.close();
            }
            catch (IOException exception)
            {
                exception.printStackTrace();
            }
        });

        mFile.addSeparator();

        JMenuItem mFileClose = new JMenuItem("Закрыть");
        mFile.add(mFileClose);
        mFileClose.addActionListener(e -> System.exit(0));

        menu.add(mFile);

        /* "Помощь" и его подвкладки */
        JMenu mHelp = new JMenu("Помощь");

        JMenuItem mHelpAuthors = new JMenuItem("Авторы");
        mHelp.add(mHelpAuthors);
        mHelpAuthors.addActionListener(e -> JOptionPane.showMessageDialog(null, "Артём Бутко, Нам Ё Себ, Дмитрий Самакаев\n" + "https://github.com/artbutko/SummerPractice.TopologicalSort", "Авторы", JOptionPane.INFORMATION_MESSAGE));

        JMenuItem mHelpAlgorithm = new JMenuItem("Алгоритм");
        mHelp.add(mHelpAlgorithm);
        mHelpAlgorithm.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String message = "На компьютере топологическую сортировку можно выполнить за O(n) времени и памяти,\nесли обойти все вершины, используя поиск в глубину, и выводить вершины в момент выхода из неё.\n" +
                        "\n" +
                        "Другими словами алгоритм состоит в следующем:\n" +
                        "- Изначально все вершины белые.\n" +
                        "- Для каждой вершины делаем шаг алгоритма.\n" +
                        "Шаг алгоритма:\n" +
                        "- Если вершина чёрная, ничего делать не надо.\n" +
                        "- Если вершина серая — найден цикл, топологическая сортировка невозможна.\n" +
                        "- Если вершина белая\n" +
                        "   + Красим её в серый\n" +
                        "   + Применяем шаг алгоритма для всех вершин, в которые можно попасть из текущей\n" +
                        "   + Красим вершину в чёрный и помещаем её в начало окончательного списка.";
                JOptionPane.showMessageDialog(null, message, "Алгоритм", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        menu.add(mHelp);
    }

    private String coordinateConverter(int x) {
        String nStringRepresentation = String.valueOf(x);
        StringBuilder result = new StringBuilder();

        int zerosCnt = 3 - nStringRepresentation.length();
        while (zerosCnt != 0)
        {
            result.append(0);
            --zerosCnt;
        }

        result.append(nStringRepresentation);

        return result.toString();
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
                if (canvas.isLoop())
                {
                JOptionPane.showMessageDialog(frame,
                        "Цикл в графе!",
                        "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
                }
                else if (!canvas.digraph.isEmpty())
                {
                    System.out.println(e.getActionCommand());
                    canvas.setPressedButton(buttonSort);
                    toolBar.remove(buttonAddVert);
                    toolBar.remove(buttonRemVert);
                    toolBar.remove(buttonAddEdge);
                    toolBar.remove(buttonRemEdge);
                    toolBar.remove(buttonSort);
                    buttonPrev.setVisible(true);
                    buttonNext.setVisible(true);
                    buttonResult.setVisible(true);
                    toolBar.repaint();
                }
                else
                {
                    JOptionPane.showMessageDialog(frame,
                            "Граф пуст",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE);
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
        canvas.setBackground(new java.awt.Color(229, 229, 229, 169));
    }

    public Application()
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