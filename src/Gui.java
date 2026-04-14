import javax.swing.*;
import java.awt.*;
// Импортируем компонент StatisticsManager (убедитесь, что пакет указан верно для вашего фреймворка)
import widgets.stat.StatisticsManager; 

public class Gui extends JFrame {
    private SettingsPanel settingsPanel;
    private TestPanel testPanel;
    
    // Добавляем поле для менеджера статистики (РГР Етап 5)
    private StatisticsManager statisticsManager; 

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
        
        // --- ДОБАВЛЕНО ДЛЯ 5 ЭТАПА ---
        // Вкладка Stat 
        statisticsManager = new StatisticsManager();
        // Передаем фабрику моделей с помощью лямбда-выражения
        statisticsManager.setFactory((d) -> new Model(d, this));
        tabbedPane.addTab("Stat", statisticsManager);
        // -----------------------------

        // Вкладка Info
        tabbedPane.addTab("Info", new InfoPanel());

        splitPane.setRightComponent(tabbedPane);

        // Автоматичне оновлення осей діаграм
        settingsPanel.getChooseDataFinishTime().addCaretListener(e -> updateDiagrams());
        
        updateDiagrams(); // Початкове налаштування

        // Запуск моделювання при натисканні на кнопку "Старт" (для режиму Test)
        settingsPanel.getButtonStart().addActionListener(e -> startTest());
    }

    // Геттери для доступу до панелей з класу Model
    public SettingsPanel getSettingsPanel() { return settingsPanel; }
    public TestPanel getTestPanel() { return testPanel; }
    public StatisticsManager getStatisticsManager() { return statisticsManager; } // Добавлен геттер

    // Метод запуску процесу моделювання у режимі тестування
    private void startTest() {
        // Очищаємо діаграми перед новим запуском
        testPanel.getDiagramSeederQueue().clear();
        testPanel.getDiagramTruckQueue().clear();

        // Створюємо диспетчера
        process.Dispatcher dispatcher = new process.Dispatcher();

        // Створюємо модель
        Model model = new Model(dispatcher, this);

        // Робимо кнопку «Старт» недоступною на період роботи моделі
        settingsPanel.getButtonStart().setEnabled(false);
        dispatcher.addDispatcherFinishListener(() -> settingsPanel.getButtonStart().setEnabled(true));

        // Готуємо модель до роботи у режимі тестування
        model.initForTest();

        // Запускаємо модель
        dispatcher.start();
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