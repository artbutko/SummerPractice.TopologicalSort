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
        buttonAddVert.setEnabled(true);

        /* Кнопка удаления вершины */
        buttonRemVert = new JButton("[-] вершина");
        toolBar.add(buttonRemVert);
        buttonRemVert.setActionCommand("Remove Vertex");
        buttonRemVert.addActionListener(listener);
        buttonRemVert.setEnabled(false);

        /* Кнопка добавления ребра */
        buttonAddEdge = new JButton("[+] ребро");
        toolBar.add(buttonAddEdge);
        buttonAddEdge.setActionCommand("Add Edge");
        buttonAddEdge.addActionListener(listener);
        buttonAddEdge.setEnabled(false);

        /* Кнопка удаления ребра */
        buttonRemEdge = new JButton("[-] ребро");
        toolBar.add(buttonRemEdge);
        buttonRemEdge.setActionCommand("Remove Edge");
        buttonRemEdge.addActionListener(listener);
        buttonRemEdge.setEnabled(false);

        /* Кнопка сортировки */
        buttonSort = new JButton("сортировка");
        toolBar.add(buttonSort);
        buttonSort.setActionCommand("Sort");
        buttonSort.addActionListener(listener);
        buttonSort.setEnabled(false);
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
                buttonAddVert.setEnabled(true);
                buttonRemVert.setEnabled(false);
                buttonAddEdge.setEnabled(false);
                buttonRemEdge.setEnabled(false);
                buttonSort.setEnabled(false);
            }
            if(e.getSource() == buttonRemVert)
            {
                System.out.println(e.getActionCommand());
                buttonAddVert.setEnabled(false);
                buttonRemVert.setEnabled(true);
                buttonAddEdge.setEnabled(false);
                buttonRemEdge.setEnabled(false);
                buttonSort.setEnabled(false);
            }
            if(e.getSource() == buttonAddEdge)
            {
                System.out.println(e.getActionCommand());
                buttonAddVert.setEnabled(false);
                buttonRemVert.setEnabled(false);
                buttonAddEdge.setEnabled(true);
                buttonRemEdge.setEnabled(false);
                buttonSort.setEnabled(false);
            }
            if(e.getSource() == buttonRemEdge)
            {
                System.out.println(e.getActionCommand());
                buttonAddVert.setEnabled(false);
                buttonRemVert.setEnabled(false);
                buttonAddEdge.setEnabled(false);
                buttonRemEdge.setEnabled(true);
                buttonSort.setEnabled(false);
            }
            if(e.getSource() == buttonSort)
            {
                System.out.println(e.getActionCommand());
                buttonAddVert.setEnabled(false);
                buttonRemVert.setEnabled(false);
                buttonAddEdge.setEnabled(false);
                buttonRemEdge.setEnabled(false);
                buttonSort.setEnabled(true);
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

    /** класс для рисования */
    /*
    Я бы переделал ArrayList в структуру, где помимо точек будут их имена (JLabel).
    При выводе ответа (сортировки) закрашивал бы весь лист белым, затем рассчитывал x/количество_вершин , распологал бы вершины и отрисовывал бы дуги
    #TODO хранение ребер
    #TODO отрисовка ребер
    #TODO связка всего этого со структурой и алгоритмом
     */
    private class DrawPanel extends JPanel {
        private static final long serialVersionUID = 1L;
        public ArrayList<Point> points;

        private int deleteIndex;

        public DrawPanel()
        {
            points = new ArrayList<Point>();
            setBackground(Color.WHITE);
            addMouseListener(new MouseAdapter()
            {
                @Override
                public void mousePressed(MouseEvent e)
                {
                    /* Если кнопка добавления вершины активна, то добавляем точку в массив*/
                    if (buttonAddVert.isEnabled())
                    {
                        buttonRemVert.setEnabled(true);
                        buttonAddEdge.setEnabled(true);
                        buttonRemEdge.setEnabled(true);
                        buttonSort.setEnabled(true);

                        points.add(new Point(e.getX() - 10, e.getY() - 10));
                        repaint();
                    }
                    /* Если кнопка удаления вершины активна, то ищем точку, в которую входят координаты щелчка мыши*/
                    else if (buttonRemVert.isEnabled())
                    {
                        buttonAddVert.setEnabled(true);
                        buttonRemVert.setEnabled(true);
                        buttonAddEdge.setEnabled(true);
                        buttonRemEdge.setEnabled(true);
                        buttonSort.setEnabled(true);

                        int x = e.getX();
                        int y = e.getY();
                        for (int i = 0; i < points.size(); ++i)
                            if (x < points.get(i).getX() + 25 & x > points.get(i).getX() - 25 & y < points.get(i).getY() + 25 & y > points.get(i).getY() - 25) {
                                deleteIndex = i;
                                break;
                            }
                        repaint();
                    }
                }
            });
        }

        /** очень важная штука, рендерит картинку при различных учловиях */
        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            /* Если кнопка удаления вершины активна, то отрисовываем белую точку и удаляем вершину из массива*/
            if (buttonRemVert.isEnabled() && deleteIndex != -1)
            {
                g2.setColor(Color.white);
                g2.fillOval(points.get(deleteIndex).x, points.get(deleteIndex).y, 20, 20);
                points.remove(deleteIndex);
                deleteIndex = -1;
            }
            /*  Отрисовываем каждую точку из массива точек*/
            g2.setColor(Color.red);
            for (Point point : points)
            {
                g2.fillOval(point.x, point.y, 20, 20);
            }

        }
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


