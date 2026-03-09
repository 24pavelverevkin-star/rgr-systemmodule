import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame {
    
    // Оголошуємо поля класу, щоб вони були доступні в усьому коді
    private SettingsPanel settingsPanel;
    private TestPanel testPanel; // Додано поле для панелі тестування

    public Gui() {
        setTitle("Моделювання посівної компанії - Веревкін Павло");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 600);
        setLocationRelativeTo(null);

        // Головний роздільник
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(300);
        add(splitPane, BorderLayout.CENTER);

        // 1. Створюємо ліву панель налаштувань
        settingsPanel = new SettingsPanel();
        splitPane.setLeftComponent(settingsPanel);

        // 2. Створюємо праву панель з вкладками
        JTabbedPane tabbedPane = new JTabbedPane();

        // Вкладка "ТЗ"
        JPanel tzPanel = new JPanel(new BorderLayout());
        tzPanel.add(new JScrollPane(UIFactory.createTzPane()), BorderLayout.CENTER);
        tabbedPane.addTab("ТЗ", tzPanel);

        // Вкладка "Test" (РГР Етап 2) - Створюємо ДО того, як додавати слухача
        testPanel = new TestPanel();
        tabbedPane.addTab("Test", testPanel);

        // Вкладка "Info"
        tabbedPane.addTab("Info", new InfoPanel());

        splitPane.setRightComponent(tabbedPane);

        // 3. Налаштовуємо слухача для автоматичного оновлення осей діаграм
        // Тепер testPanel вже існує, тому помилки не буде
        settingsPanel.getChooseDataFinishTime().addCaretListener(e -> {
            try {
                double time = settingsPanel.getChooseDataFinishTime().getDouble();
                // Налаштування масштабу діаграм на основі введеного часу
                testPanel.getDiagramSeederQueue().setHorizontalMaxText(String.valueOf(time));
                testPanel.getDiagramTruckQueue().setHorizontalMaxText(String.valueOf(time));
            } catch (Exception ex) {
                // Ігноруємо порожні або некоректні значення при введенні
            }
        });
        
        // Викликаємо оновлення один раз при запуску для початкових значень
        updateDiagramScales();
    }

    // Допоміжний метод для початкового налаштування осей
    private void updateDiagramScales() {
        double time = settingsPanel.getChooseDataFinishTime().getDouble();
        testPanel.getDiagramSeederQueue().setHorizontalMaxText(String.valueOf(time));
        testPanel.getDiagramTruckQueue().setHorizontalMaxText(String.valueOf(time));
    }

    public SettingsPanel getSettings() {
        return settingsPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Gui().setVisible(true));
    }
}