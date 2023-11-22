import java.util.concurrent.CyclicBarrier;

public class SimpleNode extends AbstractNode {
    public SimpleNode(int id, SharedData sharedData, CyclicBarrier roundBarrier, BBVerificator verificator) {
        super(id, sharedData, roundBarrier, verificator);
    }

    @Override
    public void executeProtocol() {
        System.out.println(Thread.currentThread().getName() + " id : " + super.id + " " + (super.isLeader ? " Leader " : ""));
    }

    @Override
    public ConsensusValue endPhase() {
        return super.reedMessage(sharedData.getLeaderId());
    }
}
