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
    
    // Количество сеялок, которые может загрузить один грузовик
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
        // Условие наличия сеялки в очереди
        BooleanSupplier seederAvailable = () -> queueSeederQueue.size() > 0;

        while (getDispatcher().getCurrentTime() <= finishTime) {
            getDispatcher().printToProtocol("  " + getNameForProtocol() + " завантажується на складі.");
            holdForTime(rndLoadAtWarehouse.next());

            getDispatcher().printToProtocol("  " + getNameForProtocol() + " їде на поле.");
            holdForTime(rndTravel.next());

            int portions = maxPortions;
            // Регистрируем грузовик в очереди на поле
            queueTruckQueue.addLast(this); 

            // Цикл выгрузки зерна сеялкам
            while (portions > 0) {
                // Если сеялок нет, грузовик ожидает их появления
                waitForCondition(seederAvailable, "чекає на порожню сівалку");

                // Забираем первую сеялку из очереди
                Seeder seeder = queueSeederQueue.removeFirst();

                getDispatcher().printToProtocol("  " + getNameForProtocol() + " пересипає зерно в " + seeder.getNameForProtocol());
                // Имитация затрат времени на пересыпку зерна
                holdForTime(rndUnload.next());

                // Уведомляем сеялку об окончании загрузки
                seeder.setLoaded(true);
                portions--;
            }

            // Грузовик пуст, покидает очередь
            queueTruckQueue.remove(this); 
            
            getDispatcher().printToProtocol("  " + getNameForProtocol() + " повертається на склад.");
            holdForTime(rndTravel.next());
        }
    }
}