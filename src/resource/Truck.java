import process.Actor;
import process.DispatcherFinishException;

public class Truck extends Actor {
    @Override
    protected void rule() throws DispatcherFinishException {
        getDispatcher().printToProtocol("  " + getNameForProtocol() + " розпочала роботу (заглушка).");
    }
}