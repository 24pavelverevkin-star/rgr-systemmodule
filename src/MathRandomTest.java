import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import stat.Histo;
import widgets.ChooseData;
import widgets.Diagram;

public class MathRandomTest extends JFrame {
    private ChooseData sampleSizeInput;
    private JButton startButton;
    private Diagram diagram;
    private JTextArea textArea;
    private Histo histo;

    public MathRandomTest() {
        setTitle("Lab 1 - Verevkin Pavlo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        sampleSizeInput = new ChooseData();
        sampleSizeInput.setTitle("Об'єм вибірки");
        sampleSizeInput.setInt(400);
        
        startButton = new JButton("Start");
        
        topPanel.add(sampleSizeInput);
        topPanel.add(startButton);
        add(topPanel, BorderLayout.NORTH);

        diagram = new Diagram();
        add(diagram, BorderLayout.CENTER);

        textArea = new JTextArea(10, 40);
        add(new JScrollPane(textArea), BorderLayout.SOUTH);

        histo = new Histo();

        startButton.addActionListener(e -> runTest());
    }

    private void runTest() {
        int size = sampleSizeInput.getInt();
        histo.initFromTo(0.0, 1.0, 10);
        for (int i = 0; i < size; i++) {
            histo.add(Math.random());
        }
        diagram.clear();
        histo.showRelFrec(diagram);
        textArea.setText(histo.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MathRandomTest().setVisible(true));
    }
}