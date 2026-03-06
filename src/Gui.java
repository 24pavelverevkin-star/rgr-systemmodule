import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame {
    
    // Зберігаємо посилання на панель налаштувань, щоб потім передати дані в модель
    private SettingsPanel settingsPanel;

    public Gui() {
        setTitle("Моделювання посівної компанії - Веревкін Павло");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 600);
        setLocationRelativeTo(null);

        // Головний роздільник
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(300);
        add(splitPane, BorderLayout.CENTER);

        // 1. ЛІВА ПАНЕЛЬ (беремо готовий клас SettingsPanel)
        settingsPanel = new SettingsPanel();
        splitPane.setLeftComponent(settingsPanel);

        // 2. ПРАВА ПАНЕЛЬ з вкладками
        JTabbedPane tabbedPane = new JTabbedPane();

        // Вкладка "ТЗ" (генеруємо через UIFactory)
        JPanel tzPanel = new JPanel(new BorderLayout());
        tzPanel.add(new JScrollPane(UIFactory.createTzPane()), BorderLayout.CENTER);
        tabbedPane.addTab("ТЗ", tzPanel);

        // Вкладка "Info" (беремо готовий клас InfoPanel)
        tabbedPane.addTab("Info", new InfoPanel());

        splitPane.setRightComponent(tabbedPane);
    }

    // Геттер, щоб модель могла достукатися до налаштувань
    public SettingsPanel getSettings() {
        return settingsPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Gui().setVisible(true));
    }
}