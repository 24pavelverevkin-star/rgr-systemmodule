import process.Dispatcher;
import process.MultiActor;
import process.QueueForTransactions;
import stat.DiscretHisto; 

public class Model {
    private Dispatcher dispatcher;
    private Gui gui;
    
    private QueueForTransactions<Truck> queueTruckQueue;
    private QueueForTransactions<Seeder> queueSeederQueue;
    
    private MultiActor trucks;
    private MultiActor seeders;
    
    // Гістограми розміру черг
    private DiscretHisto histoTruckQueue = new DiscretHisto();
    private DiscretHisto histoSeederQueue = new DiscretHisto();
    
    // Оригінали об'єктів для бригад
    private Truck originalTruck;
    private Seeder originalSeeder;
    
    public Model(Dispatcher dispatcher, Gui gui) {
        this.dispatcher = dispatcher;
        this.gui = gui;
        componentsToStartList(); 
    }
    
    public void componentsToStartList() {
        dispatcher.addStartingActor(getTrucks());
        dispatcher.addStartingActor(getSeeders());
    }
    
    public void initForTest() {
        // Підключення діаграм з TestPanel
        getQueueTruckQueue().setPainter(gui.getTestPanel().getDiagramTruckQueue().getPainter());
        getQueueSeederQueue().setPainter(gui.getTestPanel().getDiagramSeederQueue().getPainter());
        
        dispatcher.setProtocolFileName("Console"); 
    }
    
    public QueueForTransactions<Truck> getQueueTruckQueue() {
        if (queueTruckQueue == null) {
            queueTruckQueue = new QueueForTransactions<>("Черга вантажівок", dispatcher, histoTruckQueue);
        }
        return queueTruckQueue;
    }
    
    public QueueForTransactions<Seeder> getQueueSeederQueue() {
        if (queueSeederQueue == null) {
            queueSeederQueue = new QueueForTransactions<>("Черга сівалок", dispatcher, histoSeederQueue);
        }
        return queueSeederQueue;
    }

    // --- Оригінали акторів та їх ініціалізація ---

    public Truck getOriginalTruck() {
        if (originalTruck == null) {
            originalTruck = new Truck();
            originalTruck.setNameForProtocol("Вантажівка");
            originalTruck.setFinishTime(gui.getSettingsPanel().getChooseDataFinishTime().getDouble());
            
            // Підключаємо черги
            originalTruck.setQueueSeederQueue(getQueueSeederQueue());
            originalTruck.setQueueTruckQueue(getQueueTruckQueue());
            
            // Точні назви методів з твого SettingsPanel.java
            originalTruck.setRndTravel(gui.getSettingsPanel().getChooseRandomTruckTravel().getRandom());
            originalTruck.setRndLoadAtWarehouse(gui.getSettingsPanel().getChooseRandomTruckLoad().getRandom());
            originalTruck.setRndUnload(gui.getSettingsPanel().getChooseRandomSeederLoad().getRandom()); // Час заправки
            
            // Вантажівка може завантажити, наприклад, 3 сівалки за один рейс
            originalTruck.setMaxPortions(3); 
        }
        return originalTruck;
    }

    public Seeder getOriginalSeeder() {
        if (originalSeeder == null) {
            originalSeeder = new Seeder();
            originalSeeder.setNameForProtocol("Сівалка");
            originalSeeder.setFinishTime(gui.getSettingsPanel().getChooseDataFinishTime().getDouble());
            
            // Підключаємо чергу сівалок
            originalSeeder.setQueueSeederQueue(getQueueSeederQueue());
            
            // Точна назва методу з твого SettingsPanel.java
            originalSeeder.setRndSow(gui.getSettingsPanel().getChooseRandomSeederWork().getRandom());
        }
        return originalSeeder;
    }
    
    // --- Створення бригад за допомогою MultiActor ---

    public MultiActor getTrucks() {
        if (trucks == null) {
            trucks = new MultiActor();
            trucks.setNameForProtocol("Бригада вантажівок");
            trucks.setOriginal(getOriginalTruck()); 
            // Беремо кількість вантажівок з інтерфейсу
            trucks.setNumberOfClones(gui.getSettingsPanel().getChooseDataTrucksCount().getInt()); 
        }
        return trucks;
    }
    
    public MultiActor getSeeders() {
        if (seeders == null) {
            seeders = new MultiActor();
            seeders.setNameForProtocol("Бригада сівалок");
            seeders.setOriginal(getOriginalSeeder());
            // Беремо кількість сівалок з інтерфейсу
            seeders.setNumberOfClones(gui.getSettingsPanel().getChooseDataSeedersCount().getInt());
        }
        return seeders;
    }
}