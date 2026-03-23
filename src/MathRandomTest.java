import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import stat.Histo;
import widgets.ChooseData;
import widgets.ChooseRandom;
import widgets.Diagram;

public class MathRandomTest extends JFrame {
    private ChooseRandom chooseRandom; 
    private ChooseData sampleSizeInput;
    private JButton startButton;
    private Diagram diagram;
    private JTextArea textArea;
    private Histo histo;

    public MathRandomTest() {
        setTitle("Тестування розподілів (Лаб 2) - Веревкін Павло");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        JPanel controlPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        
        chooseRandom = new ChooseRandom();
        chooseRandom.setTitle("Закон розподілу");
        
        sampleSizeInput = new ChooseData();
        sampleSizeInput.setTitle("Обсяг вибірки (N*100)");
        sampleSizeInput.setInt(400); 
        
        startButton = new JButton("Генерувати");
        startButton.addActionListener(e -> runTest());

        controlPanel.add(chooseRandom);
        controlPanel.add(sampleSizeInput);
        controlPanel.add(startButton);

        diagram = new Diagram();
        diagram.setTitleText("Гістограма щільності розподілу");
        
        textArea = new JTextArea(12, 40);
        textArea.setEditable(false);
        histo = new Histo(); 

        add(controlPanel, BorderLayout.NORTH);
        add(diagram, BorderLayout.CENTER);
        add(new JScrollPane(textArea), BorderLayout.SOUTH);
    }

    private void runTest() {
        try {
            int size = sampleSizeInput.getInt();
            StringBuilder sb = new StringBuilder(); // Створюємо буфер для чисел
            
            histo.init(); 
            diagram.clear();
            
            for (int i = 0; i < size; i++) {
                double value = chooseRandom.next(); 
                histo.add(value); 
                
                // Додаємо число і відразу перенос рядка
                sb.append(String.format("%.4f\n", value));
            }

            // Встановлюємо весь текст у textArea
            textArea.setText(sb.toString()); 
            // Перемотуємо в початок списку
            textArea.setCaretPosition(0);

            // Малюємо гістограму для звіту
            histo.showRelFrec(diagram); 
            
        } catch (Exception ex) {
            textArea.setText("Помилка: Перевірте коректність параметрів.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MathRandomTest().setVisible(true));
    }
}