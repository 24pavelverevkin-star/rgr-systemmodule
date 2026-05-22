import java.util.function.BooleanSupplier;
import process.Actor;
import process.DispatcherFinishException;
import process.QueueForTransactions;
import rnd.Randomable;

public class Truck extends Actor {
    private double finishTime;
    private QueueForTransactions<Seeder> queueSeederQueue;
    private QueueForTransactions<Truck> queueTruckQueue;
    
    private Randomable rndTravel;
    private Randomable rndLoadAtWarehouse;
    private Randomable rndUnload;
    
    private int maxPortions = 3; 

    public void setFinishTime(double finishTime) { this.finishTime = finishTime; }
    public void setQueueSeederQueue(QueueForTransactions<Seeder> q) { this.queueSeederQueue = q; }
    public void setQueueTruckQueue(QueueForTransactions<Truck> q) { this.queueTruckQueue = q; }
    public void setRndTravel(Randomable r) { this.rndTravel = r; }
    public void setRndLoadAtWarehouse(Randomable r) { this.rndLoadAtWarehouse = r; }
    public void setRndUnload(Randomable r) { this.rndUnload = r; }
    public void setMaxPortions(int portions) { this.maxPortions = portions; }

    @Override
    protected void rule() throws DispatcherFinishException {
        BooleanSupplier seederAvailable = () -> queueSeederQueue.size() > 0;

        while (getDispatcher().getCurrentTime() <= finishTime) {
            getDispatcher().printToProtocol("  " + getNameForProtocol() + " завантажується на складі.");
            holdForTime(rndLoadAtWarehouse.next());

            getDispatcher().printToProtocol("  " + getNameForProtocol() + " їде на поле.");
            holdForTime(rndTravel.next());

            int portions = maxPortions;
            queueTruckQueue.addLast(this); 

            while (portions > 0) {
                waitForCondition(seederAvailable, "чекає на порожню сівалку");

                Seeder seeder = queueSeederQueue.removeFirst();

                getDispatcher().printToProtocol("  " + getNameForProtocol() + " пересипає зерно в " + seeder.getNameForProtocol());
                holdForTime(rndUnload.next());

                seeder.setLoaded(true);
                portions--;
            }

            queueTruckQueue.remove(this); 
            
            getDispatcher().printToProtocol("  " + getNameForProtocol() + " повертається на склад.");
            holdForTime(rndTravel.next());
        }
    }
}