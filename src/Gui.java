import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame {
    
    private SettingsPanel settingsPanel;
    private TestPanel testPanel; 

    public Gui() {
        setTitle("Моделювання посівної компанії - Веревкін Павло");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);

        // Головний роздільник
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(300);
        add(splitPane, BorderLayout.CENTER);

        // 1. Створення лівої панелі (Налаштування)
        settingsPanel = new SettingsPanel();
        splitPane.setLeftComponent(settingsPanel);

        // 2. Створення правої панелі з вкладками
        JTabbedPane tabbedPane = new JTabbedPane();

        // Вкладка "ТЗ"
        JPanel tzPanel = new JPanel(new BorderLayout());
        tzPanel.add(new JScrollPane(UIFactory.createTzPane()), BorderLayout.CENTER);
        tabbedPane.addTab("ТЗ", tzPanel);

        // Вкладка "Test" (Створюємо ПЕРЕД налаштуванням слухача)
        testPanel = new TestPanel();
        tabbedPane.addTab("Test", testPanel);

        // Вкладка "Info"
        tabbedPane.addTab("Info", new InfoPanel());

        splitPane.setRightComponent(tabbedPane);

        // 3. Логіка взаємодії компонентів
        
        // Автоматичне оновлення осей діаграм при зміні часу моделювання
        settingsPanel.getChooseDataFinishTime().addCaretListener(e -> updateDiagramScales());

        // Обробка натискання кнопки "Старт"
        settingsPanel.getButtonStart().addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Запуск моделювання...");
            // Тут буде код запуску моделі на наступному етапі
        });
        
        // Початкове налаштування осей при запуску програми
        updateDiagramScales();
    }

    /**
     * Метод для синхронізації часу моделювання з горизонтальною віссю діаграм
     */
    private void updateDiagramScales() {
        try {
            double time = settingsPanel.getChooseDataFinishTime().getDouble();
            if (testPanel != null) {
                testPanel.getDiagramSeederQueue().setHorizontalMaxText(String.valueOf(time));
                testPanel.getDiagramTruckQueue().setHorizontalMaxText(String.valueOf(time));
            }
        } catch (Exception ex) {
            // Ігноруємо помилки під час введення
        }
    }

    public SettingsPanel getSettings() {
        return settingsPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Gui().setVisible(true));
    }
}