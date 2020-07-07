import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Костяк GUI приложения визуализации топологической сортировки
 * @author artbutko
 * @version 0.1
 * #TODO привязать кнопки к методам, рисование на поле, удаление. P.S Перегружать методы взаимодействия по кнопке.
 * #TODO массив "кругов" (вершин) с координатами и радиусами вершин, аналогично с ребрами графа.
 */

public class App extends JFrame
{
    private JMenuBar menu;
    private JPanel toolBar;
    private JPanel canvas;

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

        JButton buttonAddVert = new JButton("[+] вершина");
        toolBar.add(buttonAddVert);
        /*buttonAddVert.addActionListener(e -> {

        });*/

        JButton buttonRemVert = new JButton("[-] вершина");
        toolBar.add(buttonRemVert);

        JButton buttonAddEdge = new JButton("[+] ребро");
        toolBar.add(buttonAddEdge);

        JButton buttonRemEdge = new JButton("[-] ребро");
        toolBar.add(buttonRemEdge);

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