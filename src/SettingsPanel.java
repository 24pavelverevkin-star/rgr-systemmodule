import javax.swing.*;
import java.awt.*;
import widgets.ChooseData;
import widgets.ChooseRandom;

public class SettingsPanel extends JPanel {
    private ChooseData chooseDataFinishTime;
    private ChooseData chooseDataTrucksCount;
    private ChooseData chooseDataSeedersCount;
    
    // Генераторы случайных величин (РГР Этап 2) [cite: 292, 1950]
    private ChooseRandom chooseRandomTruckTravel; // Время грузовика в пути
    private ChooseRandom chooseRandomSeederWork;  // Время высева сеялки
    private ChooseRandom chooseRandomSeederLoad;  // Время заправки сеялки от грузовика
    private ChooseRandom chooseRandomTruckLoad;   // Время загрузки грузовика на складе

    public SettingsPanel() {
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

        // Настройка генераторов случайных чисел [cite: 1511]
        chooseRandomTruckTravel = new ChooseRandom();
        chooseRandomTruckTravel.setTitle("Час вантажівки у дорозі");
        
        chooseRandomSeederWork = new ChooseRandom();
        chooseRandomSeederWork.setTitle("Час висіву сеялкою");
        
        chooseRandomSeederLoad = new ChooseRandom();
        chooseRandomSeederLoad.setTitle("Час заправки сеялки");

        chooseRandomTruckLoad = new ChooseRandom();
        chooseRandomTruckLoad.setTitle("Час завантаження вантажівки");

        add(chooseDataFinishTime);
        add(chooseDataTrucksCount);
        add(chooseDataSeedersCount);
        add(chooseRandomTruckTravel);
        add(chooseRandomSeederWork);
        add(chooseRandomSeederLoad);
        add(chooseRandomTruckLoad);
    }

    // Публичные геттеры для доступа из модели и GUI 
    public ChooseData getChooseDataFinishTime() { return chooseDataFinishTime; }
    public ChooseData getChooseDataTrucksCount() { return chooseDataTrucksCount; }
    public ChooseData getChooseDataSeedersCount() { return chooseDataSeedersCount; }
    public ChooseRandom getChooseRandomTruckTravel() { return chooseRandomTruckTravel; }
    public ChooseRandom getChooseRandomSeederWork() { return chooseRandomSeederWork; }
    public ChooseRandom getChooseRandomSeederLoad() { return chooseRandomSeederLoad; }
    public ChooseRandom getChooseRandomTruckLoad() { return chooseRandomTruckLoad; }
}