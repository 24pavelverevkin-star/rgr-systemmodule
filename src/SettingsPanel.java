import javax.swing.*;
import java.awt.*;
import widgets.ChooseData;

public class SettingsPanel extends JPanel {
    private ChooseData chooseDataFinishTime;
    private ChooseData chooseDataTrucksCount;
    private ChooseData chooseDataSeedersCount;
    private ChooseData chooseDataTruckCapacity;

    public SettingsPanel() {
        setLayout(new GridLayout(8, 1, 5, 5));
        setBorder(BorderFactory.createTitledBorder("Налаштування параметрів"));

        chooseDataFinishTime = new ChooseData();
        chooseDataFinishTime.setTitle("Час моделювання");
        chooseDataFinishTime.setInt(1000);

        chooseDataTrucksCount = new ChooseData();
        chooseDataTrucksCount.setTitle("Кількість вантажівок");
        chooseDataTrucksCount.setInt(3);

        chooseDataSeedersCount = new ChooseData();
        chooseDataSeedersCount.setTitle("Кількість сівалок");
        chooseDataSeedersCount.setInt(5);

        chooseDataTruckCapacity = new ChooseData();
        chooseDataTruckCapacity.setTitle("Місткість вантажівки (заправок)");
        chooseDataTruckCapacity.setInt(3);

        add(chooseDataFinishTime);
        add(chooseDataTrucksCount);
        add(chooseDataSeedersCount);
        add(chooseDataTruckCapacity);
    }

    // Гетери для доступу з головного вікна та моделі
    public ChooseData getChooseDataFinishTime() { return chooseDataFinishTime; }
    public ChooseData getChooseDataTrucksCount() { return chooseDataTrucksCount; }
    public ChooseData getChooseDataSeedersCount() { return chooseDataSeedersCount; }
    public ChooseData getChooseDataTruckCapacity() { return chooseDataTruckCapacity; }
}