import process.Actor;
import process.DispatcherFinishException;

public class Truck extends Actor {
    private double finishTime;

    public void setFinishTime(double finishTime) {
        this.finishTime = finishTime;
    }

    @Override
    protected void rule() throws DispatcherFinishException {
        // Цикл працює, поки віртуальний час не досягне ліміту
        while (getDispatcher().getCurrentTime() <= finishTime) {
            getDispatcher().printToProtocol("  " + getNameForProtocol() + " працює (заглушка).");
            holdForTime(1.0); // Імітація витраченого часу
        }
    }
}