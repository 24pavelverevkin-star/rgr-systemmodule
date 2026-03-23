import process.Actor;
import process.DispatcherFinishException;

public class Seeder extends Actor {
    private double finishTime;

    public void setFinishTime(double finishTime) {
        this.finishTime = finishTime;
    }

    @Override
    protected void rule() throws DispatcherFinishException {
        while (getDispatcher().getCurrentTime() <= finishTime) {
            getDispatcher().printToProtocol("  " + getNameForProtocol() + " працює (заглушка).");
            holdForTime(1.5); // Імітація витраченого часу
        }
    }
}