import javax.swing.*;
import java.awt.*;
import widgets.ChooseData;
import widgets.ChooseRandom;

public class SettingsPanel extends JPanel {
    private ChooseData chooseDataFinishTime;
    private ChooseData chooseDataTrucksCount;
    private ChooseData chooseDataSeedersCount;
    
    // Генератори випадкових величин (РГР Етап 2)
    private ChooseRandom chooseRandomTruckTravel; 
    private ChooseRandom chooseRandomSeederWork;  
    private ChooseRandom chooseRandomSeederLoad;  
    private ChooseRandom chooseRandomTruckLoad;   
    
    // Кнопка керування моделюванням
    private JButton buttonStart;

    public SettingsPanel() {
        // GridLayout(8, 1) вміщує 7 параметрів + 1 кнопку
        setLayout(new GridLayout(8, 1, 5, 5));
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

        chooseRandomTruckTravel = new ChooseRandom();
        chooseRandomTruckTravel.setTitle("Час вантажівки у дорозі");
        
        chooseRandomSeederWork = new ChooseRandom();
        chooseRandomSeederWork.setTitle("Час висіву сеялкою");
        
        chooseRandomSeederLoad = new ChooseRandom();
        chooseRandomSeederLoad.setTitle("Час заправки сеялки");

        chooseRandomTruckLoad = new ChooseRandom();
        chooseRandomTruckLoad.setTitle("Час завантаження вантажівки");

        // Ініціалізація кнопки
        buttonStart = new JButton("Старт");

        // Додавання всіх компонентів на панель
        add(chooseDataFinishTime);
        add(chooseDataTrucksCount);
        add(chooseDataSeedersCount);
        add(chooseRandomTruckTravel);
        add(chooseRandomSeederWork);
        add(chooseRandomSeederLoad);
        add(chooseRandomTruckLoad);
        add(buttonStart); // 8-й компонент
    }

    // Геттери для доступу до компонентів
    public ChooseData getChooseDataFinishTime() { return chooseDataFinishTime; }
    public ChooseData getChooseDataTrucksCount() { return chooseDataTrucksCount; }
    public ChooseData getChooseDataSeedersCount() { return chooseDataSeedersCount; }
    public ChooseRandom getChooseRandomTruckTravel() { return chooseRandomTruckTravel; }
    public ChooseRandom getChooseRandomSeederWork() { return chooseRandomSeederWork; }
    public ChooseRandom getChooseRandomSeederLoad() { return chooseRandomSeederLoad; }
    public ChooseRandom getChooseRandomTruckLoad() { return chooseRandomTruckLoad; }
    public JButton getButtonStart() { return buttonStart; }
}