import java.util.concurrent.CyclicBarrier;

public class SimpleProtocolNode extends Node {
    public SimpleProtocolNode(int id, SharedData sharedData, CyclicBarrier roundBarrier) {
        super(id, sharedData, roundBarrier);
    }

    @Override
    protected void executeProtocol() {

    }
}
