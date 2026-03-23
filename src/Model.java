import process.Dispatcher;
import process.MultiActor;
import process.QueueForTransactions; // Виправлений імпорт пакету
import stat.DiscretHisto; 

public class Model {
    private Dispatcher dispatcher;
    private Gui gui;
    
    private QueueForTransactions<Truck> queueTruckQueue;
    private QueueForTransactions<Seeder> queueSeederQueue;
    
    private MultiActor trucks;
    private MultiActor seeders;
    
    // Гістограми розміру черг
    DiscretHisto histoTruckQueue = new DiscretHisto();
    DiscretHisto histoSeederQueue = new DiscretHisto();
    
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
        getQueueTruckQueue().setPainter(gui.getTestPanel().getDiagramTruckQueue().getPainter());
        getQueueSeederQueue().setPainter(gui.getTestPanel().getDiagramSeederQueue().getPainter());
        
        // Виправлений метод для виводу протоколу в консоль [cite: 1]
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

    // --- Оригінали акторів та їх клонування ---

    public Truck getOriginalTruck() {
        if (originalTruck == null) {
            originalTruck = new Truck();
            originalTruck.setNameForProtocol("Вантажівка");
            originalTruck.setFinishTime(gui.getSettingsPanel().getChooseDataFinishTime().getDouble());
        }
        return originalTruck;
    }

    public Seeder getOriginalSeeder() {
        if (originalSeeder == null) {
            originalSeeder = new Seeder();
            originalSeeder.setNameForProtocol("Сівалка");
            originalSeeder.setFinishTime(gui.getSettingsPanel().getChooseDataFinishTime().getDouble());
        }
        return originalSeeder;
    }
    
    public MultiActor getTrucks() {
        if (trucks == null) {
            trucks = new MultiActor();
            trucks.setNameForProtocol("Бригада вантажівок");
            trucks.setOriginal(getOriginalTruck()); 
            trucks.setNumberOfClones(gui.getSettingsPanel().getChooseDataTrucksCount().getInt()); 
        }
        return trucks;
    }
    
    public MultiActor getSeeders() {
        if (seeders == null) {
            seeders = new MultiActor();
            seeders.setNameForProtocol("Бригада сівалок");
            seeders.setOriginal(getOriginalSeeder());
            seeders.setNumberOfClones(gui.getSettingsPanel().getChooseDataSeedersCount().getInt());
        }
        return seeders;
    }
}