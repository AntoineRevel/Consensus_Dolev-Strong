import java.util.concurrent.CyclicBarrier;

public class SimpleProtocolNode extends Node {
    public SimpleProtocolNode(int id, SharedData sharedData, CyclicBarrier roundBarrier) {
        super(id, sharedData, roundBarrier);
    }

    @Override
    protected void executeProtocol() {
        System.out.println(Thread.currentThread().getName() +" : "+super.id + " | " + super.sharedData.getCurrentRound() + " ,leader : " + super.isLeader);

        System.out.println(super.reedMessage(sharedData.getLeaderId()));

    }
}
