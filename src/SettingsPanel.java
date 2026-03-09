import javax.swing.*;
import java.awt.*;
import widgets.ChooseData;
import widgets.ChooseRandom;

public class SettingsPanel extends JPanel {
    private ChooseData chooseDataFinishTime;
    private ChooseData chooseDataTrucksCount;
    private ChooseData chooseDataSeedersCount;
    
    // Генератори випадкових величин для Варіанту 4
    private ChooseRandom chooseRandomTruckTravel; // Час вантажівки у дорозі
    private ChooseRandom chooseRandomSeederWork;  // Час висіву сеялки
    private ChooseRandom chooseRandomSeederLoad;  // Час заправки сеялки
    private ChooseRandom chooseRandomTruckLoad;   // Час завантаження вантажівки на складі
    
    private JButton buttonStart;

    public SettingsPanel() {
        setLayout(new GridLayout(9, 1, 5, 5)); // Збільшено кількість рядків
        setBorder(BorderFactory.createTitledBorder("Параметри моделі"));

        chooseDataFinishTime = new ChooseData();
        chooseDataFinishTime.setTitle("Час моделювання");
        chooseDataFinishTime.setInt(1000);

        chooseDataTrucksCount = new ChooseData();
        chooseDataTrucksCount.setTitle("Кількість вантажівок");
        chooseDataTrucksCount.setInt(3);

        chooseDataSeedersCount = new ChooseData();
        chooseDataSeedersCount.setTitle("Кількість сівалок");
        chooseDataSeedersCount.setInt(5);

        // Налаштування генераторів
        chooseRandomTruckTravel = new ChooseRandom();
        chooseRandomTruckTravel.setTitle("Час вантажівки у дорозі");
        
        chooseRandomSeederWork = new ChooseRandom();
        chooseRandomSeederWork.setTitle("Час висіву сеялкою");
        
        chooseRandomSeederLoad = new ChooseRandom();
        chooseRandomSeederLoad.setTitle("Час заправки сеялки");

        chooseRandomTruckLoad = new ChooseRandom();
        chooseRandomTruckLoad.setTitle("Час завантаження вантажівки");

        buttonStart = new JButton("Старт");

        add(chooseDataFinishTime);
        add(chooseDataTrucksCount);
        add(chooseDataSeedersCount);
        add(chooseRandomTruckTravel);
        add(chooseRandomSeederWork);
        add(chooseRandomSeederLoad);
        add(chooseRandomTruckLoad);
        add(buttonStart);
    }

    // Публічні геттери для доступу 
    public ChooseData getChooseDataFinishTime() { return chooseDataFinishTime; }
    public ChooseRandom getChooseRandomTruckTravel() { return chooseRandomTruckTravel; }
    public ChooseRandom getChooseRandomSeederWork() { return chooseRandomSeederWork; }
    public ChooseRandom getChooseRandomSeederLoad() { return chooseRandomSeederLoad; }
    public ChooseRandom getChooseRandomTruckLoad() { return chooseRandomTruckLoad; }
    public JButton getButtonStart() { return buttonStart; }
}