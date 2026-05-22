import process.Dispatcher;
import process.MultiActor;
import process.QueueForTransactions;
import stat.DiscretHisto;
import stat.Histo;
import stat.IHisto;
import widgets.stat.IStatisticsable;
import widgets.experiments.IExperimentable;
import widgets.trans.ITransProcesable;
import widgets.trans.ITransMonitoring;

import java.util.HashMap;
import java.util.Map;

public class Model implements IStatisticsable, IExperimentable, ITransProcesable {
    private Dispatcher dispatcher;
    private Gui gui;
    
    private QueueForTransactions<Truck> queueTruckQueue;
    private QueueForTransactions<Seeder> queueSeederQueue;
    
    private MultiActor trucks;
    private MultiActor seeders;
    
    private DiscretHisto histoTruckQueue = new DiscretHisto();
    private DiscretHisto histoSeederQueue = new DiscretHisto();
    
    private Histo histoTruckWait = new Histo();
    private Histo histoSeederWait = new Histo();
    
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

        double fTime = gui.getSettingsPanel().getChooseDataFinishTime().getDouble();
        getOriginalTruck().setFinishTime(fTime);
        getOriginalSeeder().setFinishTime(fTime);

        getQueueTruckQueue().setPainter(gui.getTestPanel().getDiagramTruckQueue().getPainter());
        getQueueSeederQueue().setPainter(gui.getTestPanel().getDiagramSeederQueue().getPainter());
        
        dispatcher.setProtocolFileName("Console");
    }

    @Override
    public void initForStatistics() {
    }

    @Override
    public Map<String, IHisto> getStatistics() {
        Map<String, IHisto> map = new HashMap<>();
        map.put("Довжина черги вантажівок", histoTruckQueue);
        map.put("Довжина черги сівалок", histoSeederQueue);
        map.put("Час простою вантажівок", histoTruckWait);
        map.put("Час простою сівалок", histoSeederWait);
        return map;
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

    public Truck getOriginalTruck() {
        if (originalTruck == null) {
            originalTruck = new Truck();
            originalTruck.setNameForProtocol("Вантажівка");
            originalTruck.setFinishTime(gui.getSettingsPanel().getChooseDataFinishTime().getDouble());
            
            originalTruck.setQueueSeederQueue(getQueueSeederQueue());
            originalTruck.setQueueTruckQueue(getQueueTruckQueue());
            originalTruck.setHistoForActorWaitingTime(histoTruckWait);
            
            rnd.Randomable rndTravel = gui.getSettingsPanel().getChooseRandomTruckTravel().getRandom();
            if (rndTravel == null) rndTravel = new rnd.Negexp(2.0);
            originalTruck.setRndTravel(rndTravel);
            
            rnd.Randomable rndLoad = gui.getSettingsPanel().getChooseRandomTruckLoad().getRandom();
            if (rndLoad == null) rndLoad = new rnd.Negexp(1.5);
            originalTruck.setRndLoadAtWarehouse(rndLoad);
            
            rnd.Randomable rndUnload = gui.getSettingsPanel().getChooseRandomSeederLoad().getRandom();
            if (rndUnload == null) rndUnload = new rnd.Negexp(1.0);
            originalTruck.setRndUnload(rndUnload);
            
            originalTruck.setMaxPortions(3); 
        }
        return originalTruck;
    }

    public Seeder getOriginalSeeder() {
        if (originalSeeder == null) {
            originalSeeder = new Seeder();
            originalSeeder.setNameForProtocol("Сівалка");
            originalSeeder.setFinishTime(gui.getSettingsPanel().getChooseDataFinishTime().getDouble());
            
            originalSeeder.setQueueSeederQueue(getQueueSeederQueue());
            originalSeeder.setHistoForActorWaitingTime(histoSeederWait);
            
            rnd.Randomable rndSow = gui.getSettingsPanel().getChooseRandomSeederWork().getRandom();
            if (rndSow == null) rndSow = new rnd.Negexp(3.0);
            originalSeeder.setRndSow(rndSow);
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

    @Override
    public void initForExperiment(double factor) {
        getTrucks().setNumberOfClones((int) Math.round(factor));
        dispatcher.setProtocolFileName(""); 
    }

    @Override
    public Map<String, Double> getResultOfExperiment() {
        Map<String, Double> results = new HashMap<>();
        
        results.put("Середня черга сівалок", histoSeederQueue.getAverage());
        results.put("Середня черга вантажівок", histoTruckQueue.getAverage());
        results.put("Середній час простою сівалок", histoSeederWait.getAverage());
        results.put("Середній час простою вантаж.", histoTruckWait.getAverage());
        
        return results;
    }

    @Override
    public void initForTrans(double finishTime) {
        gui.getSettingsPanel().getChooseDataFinishTime().setDouble(finishTime);
        getOriginalTruck().setFinishTime(finishTime);
        getOriginalSeeder().setFinishTime(finishTime);
    }

    @Override
    public Map<String, ITransMonitoring> getMonitoringObjects() {
        Map<String, ITransMonitoring> map = new HashMap<>();
        
        map.put("Черга вантажівок", (ITransMonitoring) getQueueTruckQueue());
        map.put("Черга сівалок", (ITransMonitoring) getQueueSeederQueue());
        
        return map;
    }
}