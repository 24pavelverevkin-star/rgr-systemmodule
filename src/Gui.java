import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame {
    private SettingsPanel settingsPanel;
    private TestPanel testPanel;

    public Gui() {
        setTitle("Моделювання BeanFeast - Веревкін Павло");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(300);
        add(splitPane, BorderLayout.CENTER);

        settingsPanel = new SettingsPanel();
        splitPane.setLeftComponent(settingsPanel);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Вкладка ТЗ
        JPanel tzPanel = new JPanel(new BorderLayout());
        tzPanel.add(new JScrollPane(UIFactory.createTzPane()), BorderLayout.CENTER);
        tabbedPane.addTab("ТЗ", tzPanel);

        // Вкладка Test (РГР Етап 2)
        testPanel = new TestPanel();
        tabbedPane.addTab("Test", testPanel);

        // Вкладка Info
        tabbedPane.addTab("Info", new InfoPanel());

        splitPane.setRightComponent(tabbedPane);

        // Автоматичне оновлення осей діаграм [cite: 521, 2256]
        settingsPanel.getChooseDataFinishTime().addCaretListener(e -> updateDiagrams());
        
        updateDiagrams(); // Початкове налаштування

        settingsPanel.getButtonStart().addActionListener(e -> {
    System.out.println("Кнопка натиснута!"); // Вивід у консоль для тесту
    JOptionPane.showMessageDialog(this, "Запуск моделювання...");
});
    }

    private void updateDiagrams() {
        try {
            double time = settingsPanel.getChooseDataFinishTime().getDouble();
            testPanel.getDiagramSeederQueue().setHorizontalMaxText(String.valueOf(time));
            testPanel.getDiagramTruckQueue().setHorizontalMaxText(String.valueOf(time));
        } catch (Exception ex) {}
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Gui().setVisible(true));
    }
}