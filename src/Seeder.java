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

    public void setFinishTime(double finishTime) { this.finishTime = finishTime; }
    public void setQueueSeederQueue(QueueForTransactions<Seeder> q) { this.queueSeederQueue = q; }
    public void setRndSow(Randomable r) { this.rndSow = r; }
    
    public void setLoaded(boolean loaded) { this.isLoaded = loaded; }

    @Override
    protected void rule() throws DispatcherFinishException {
        BooleanSupplier loadedCondition = () -> isLoaded;

        while (getDispatcher().getCurrentTime() <= finishTime) {
            queueSeederQueue.addLast(this);
            getDispatcher().printToProtocol("  " + getNameForProtocol() + " чекає на завантаження зерном.");

            waitForCondition(loadedCondition, "має бути завантажена зерном");

            isLoaded = false;

            getDispatcher().printToProtocol("  " + getNameForProtocol() + " починає сівбу.");
            holdForTime(rndSow.next());
        }
    }
}