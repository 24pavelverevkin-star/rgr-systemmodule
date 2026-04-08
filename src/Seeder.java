import java.util.function.BooleanSupplier;
import process.Actor;
import process.DispatcherFinishException;
import process.QueueForTransactions;
import rnd.Randomable;

public class Seeder extends Actor {
    private double finishTime;
    private QueueForTransactions<Seeder> queueSeederQueue;
    private Randomable rndSow;
    private boolean isLoaded = false;

    // Методы инициализации
    public void setFinishTime(double finishTime) { this.finishTime = finishTime; }
    public void setQueueSeederQueue(QueueForTransactions<Seeder> q) { this.queueSeederQueue = q; }
    public void setRndSow(Randomable r) { this.rndSow = r; }
    
    // Вызывается грузовиком, когда он пересыпал зерно
    public void setLoaded(boolean loaded) { this.isLoaded = loaded; }

    @Override
    protected void rule() throws DispatcherFinishException {
        // Создаем условие, выполнения которого будет ждать сеялка
        BooleanSupplier loadedCondition = () -> isLoaded;

        while (getDispatcher().getCurrentTime() <= finishTime) {
            // Сеялка пуста, становится в очередь на загрузку
            queueSeederQueue.addLast(this);
            getDispatcher().printToProtocol("  " + getNameForProtocol() + " чекає на завантаження зерном.");

            // Ждем, пока грузовик не пересыпет зерно и не вызовет setLoaded(true)
            waitForCondition(loadedCondition, "має бути завантажена зерном");

            // Сбрасываем флаг для следующего цикла
            isLoaded = false;

            getDispatcher().printToProtocol("  " + getNameForProtocol() + " починає сівбу.");
            // Имитация процесса посева
            holdForTime(rndSow.next());
        }
    }
}