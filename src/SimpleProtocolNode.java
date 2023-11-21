import java.util.concurrent.CyclicBarrier;

public class SimpleProtocolNode extends Node {
    public SimpleProtocolNode(int id, SharedData sharedData, CyclicBarrier roundBarrier) {
        super(id, sharedData, roundBarrier);
    }

    @Override
    protected void executeProtocol() {
        System.out.println(super.id + " | " + super.sharedData.getCurrentRound() + " ,leader : " + super.isLeader);

    }
}
