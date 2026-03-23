import process.Dispatcher;
import process.MultiActor;
import qusystem.QueueForTransactions;
import stat.Histo;

public class Model {
    private Dispatcher dispatcher;
    private Gui gui;
    
    // Структурні компоненти моделі
    private QueueForTransactions<Truck> queueTruckQueue;
    private QueueForTransactions<Seeder> queueSeederQueue;
    
    // Багатоканальні актори (бригади)
    private MultiActor trucks;
    private MultiActor seeders;
    
    // Гістограми ініціалізуємо одразу
    private Histo histoTruckQueue = new Histo();
    private Histo histoSeederQueue = new Histo();
    
    public Model(Dispatcher dispatcher, Gui gui) {
        this.dispatcher = dispatcher;
        this.gui = gui;
        componentsToStartList(); 
    }
    
    // Реєстрація акторів у диспетчері для старту
    public void componentsToStartList() {
        dispatcher.addStartingActor(getTrucks());
        dispatcher.addStartingActor(getSeeders());
    }
    
    // Підготовка моделі до візуального тестування
    public void initForTest() {
        // Прив'язка черг до Painter-ів діаграм на графічному інтерфейсі
        getQueueTruckQueue().setPainter(gui.getTestPanel().getDiagramTruckQueue().getPainter());
        getQueueSeederQueue().setPainter(gui.getTestPanel().getDiagramSeederQueue().getPainter());
        
        // Увімкнення виводу подій в консоль
        dispatcher.setProtocolToConsole(true);
    }
    
    // --- Методи відкладеної ініціалізації ---
    
    public QueueForTransactions<Truck> getQueueTruckQueue() {
        if (queueTruckQueue == null) {
            queueTruckQueue = new QueueForTransactions<>("Черга вантажівок", dispatcher);
        }
        return queueTruckQueue;
    }
    
    public QueueForTransactions<Seeder> getQueueSeederQueue() {
        if (queueSeederQueue == null) {
            queueSeederQueue = new QueueForTransactions<>("Черга сівалок", dispatcher);
        }
        return queueSeederQueue;
    }
    
    public MultiActor getTrucks() {
        if (trucks == null) {
            trucks = new MultiActor();
            trucks.setNameForProtocol("Бригада вантажівок");
            int count = gui.getSettingsPanel().getChooseDataTrucksCount().getInt();
            for (int i = 0; i < count; i++) {
                Truck truck = new Truck();
                truck.setNameForProtocol("Вантажівка " + (i + 1));
                trucks.add(truck);
            }
        }
        return trucks;
    }
    
    public MultiActor getSeeders() {
        if (seeders == null) {
            seeders = new MultiActor();
            seeders.setNameForProtocol("Бригада сівалок");
            int count = gui.getSettingsPanel().getChooseDataSeedersCount().getInt();
            for (int i = 0; i < count; i++) {
                Seeder seeder = new Seeder();
                seeder.setNameForProtocol("Сівалка " + (i + 1));
                seeders.add(seeder);
            }
        }
        return seeders;
    }
}