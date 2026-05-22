import javax.swing.*;
import java.awt.*;
import widgets.ChooseData;
import widgets.ChooseRandom;

public class SettingsPanel extends JPanel {
    private ChooseData chooseDataFinishTime;
    private ChooseData chooseDataTrucksCount;
    private ChooseData chooseDataSeedersCount;
    
    private ChooseRandom chooseRandomTruckTravel;
    private ChooseRandom chooseRandomSeederWork;
    private ChooseRandom chooseRandomSeederLoad;
    private ChooseRandom chooseRandomTruckLoad;
    
    private JButton buttonStart;

    public SettingsPanel() {
        setLayout(new GridLayout(9, 1, 5, 5));
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
        chooseRandomTruckTravel.setRandom(new rnd.Negexp(2.0));
        
        chooseRandomSeederWork = new ChooseRandom();
        chooseRandomSeederWork.setTitle("Час висіву сеялкою");
        chooseRandomSeederWork.setRandom(new rnd.Negexp(3.0));
        
        chooseRandomSeederLoad = new ChooseRandom();
        chooseRandomSeederLoad.setTitle("Час заправки сеялки");
        chooseRandomSeederLoad.setRandom(new rnd.Negexp(1.0));

        chooseRandomTruckLoad = new ChooseRandom();
        chooseRandomTruckLoad.setTitle("Час завантаження вантажівки");
        chooseRandomTruckLoad.setRandom(new rnd.Negexp(1.5));

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

    public ChooseData getChooseDataFinishTime() { return chooseDataFinishTime; }
    public ChooseRandom getChooseRandomTruckTravel() { return chooseRandomTruckTravel; }
    public ChooseRandom getChooseRandomSeederWork() { return chooseRandomSeederWork; }
    public ChooseRandom getChooseRandomSeederLoad() { return chooseRandomSeederLoad; }
    public ChooseRandom getChooseRandomTruckLoad() { return chooseRandomTruckLoad; }
    public ChooseData getChooseDataTrucksCount() { return chooseDataTrucksCount; }
    public ChooseData getChooseDataSeedersCount() { return chooseDataSeedersCount; }
    public JButton getButtonStart() { return buttonStart; }
}