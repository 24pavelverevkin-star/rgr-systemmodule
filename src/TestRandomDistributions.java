import javax.swing.*;
import java.awt.*;
import widgets.ChooseData;
import widgets.ChooseRandom;
import widgets.Diagram;
import stat.Histo;

/**
 * Клас для тестування генераторів випадкових величин (Лаб 2)
 */
public class TestRandomDistributions extends JFrame {
    private ChooseRandom chooseRandom;
    private ChooseData sampleSize;
    private JButton startButton;
    private Diagram diagram;
    private JTextArea textArea;
    private Histo histo;

    public TestRandomDistributions() {
        setTitle("Тестування розподілів - Веревкін Павло");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        
        chooseRandom = new ChooseRandom();
        chooseRandom.setTitle("Вибір розподілу");
        
        sampleSize = new ChooseData();
        sampleSize.setTitle("Обсяг вибірки");
        sampleSize.setInt(400); // Варіант 4

        startButton = new JButton("Генерувати");
        startButton.addActionListener(e -> runTest());

        controlPanel.add(chooseRandom);
        controlPanel.add(sampleSize);
        controlPanel.add(startButton);

        diagram = new Diagram();
        diagram.setTitleText("Гістограма розподілу");
        
        textArea = new JTextArea(10, 40);
        histo = new Histo();

        add(controlPanel, BorderLayout.NORTH);
        add(diagram, BorderLayout.CENTER);
        add(new JScrollPane(textArea), BorderLayout.SOUTH);
    }

    private void runTest() {
        int size = sampleSize.getInt();
        histo.init();
        diagram.clear();
        
        for (int i = 0; i < size; i++) {
            histo.add(chooseRandom.next()); // Отримуємо число з генератора
        }

        textArea.setText(histo.toString());
        histo.showRelFrec(diagram);
    }
}