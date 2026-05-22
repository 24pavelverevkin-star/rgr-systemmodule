import javax.swing.*;
import java.awt.*;
import widgets.stat.StatisticsManager; 
import widgets.experiments.ExperimentManager;
import widgets.trans.TransProcessManager;

public class Gui extends JFrame {
    private SettingsPanel settingsPanel;
    private TestPanel testPanel;
    
    private StatisticsManager statisticsManager; 
    private ExperimentManager experimentManager; 
    private TransProcessManager transProcessManager; 

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

        JPanel tzPanel = new JPanel(new BorderLayout());
        tzPanel.add(new JScrollPane(UIFactory.createTzPane()), BorderLayout.CENTER);
        tabbedPane.addTab("ТЗ", tzPanel);

        testPanel = new TestPanel();
        tabbedPane.addTab("Test", testPanel);
        
        statisticsManager = new StatisticsManager();
        statisticsManager.setFactory((d) -> new Model(d, this));
        tabbedPane.addTab("Stat", statisticsManager);
        
        experimentManager = new ExperimentManager();
        experimentManager.setFactory((d) -> new Model(d, this));
        tabbedPane.addTab("Regres", experimentManager);

        transProcessManager = new TransProcessManager();
        transProcessManager.setFactory((d) -> new Model(d, this));
        tabbedPane.addTab("Transient", transProcessManager);

        tabbedPane.addTab("Info", new InfoPanel());

        splitPane.setRightComponent(tabbedPane);

        settingsPanel.getChooseDataFinishTime().addCaretListener(e -> updateDiagrams());
        
        updateDiagrams(); 

        settingsPanel.getButtonStart().addActionListener(e -> startTest());
    }

    public SettingsPanel getSettingsPanel() { return settingsPanel; }
    public TestPanel getTestPanel() { return testPanel; }
    public StatisticsManager getStatisticsManager() { return statisticsManager; }
    public ExperimentManager getExperimentManager() { return experimentManager; }
    public TransProcessManager getTransProcessManager() { return transProcessManager; }

    private void startTest() {
        testPanel.getDiagramSeederQueue().clear();
        testPanel.getDiagramTruckQueue().clear();

        process.Dispatcher dispatcher = new process.Dispatcher();

        Model model = new Model(dispatcher, this);

        settingsPanel.getButtonStart().setEnabled(false);
        dispatcher.addDispatcherFinishListener(() -> settingsPanel.getButtonStart().setEnabled(true));

        model.initForTest();

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