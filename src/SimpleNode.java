import java.util.concurrent.CyclicBarrier;

public class SimpleNode extends AbstractNode {
    public SimpleNode(int id, SharedData sharedData, CyclicBarrier roundBarrier, BBVerificator verificator) {
        super(id, sharedData, roundBarrier, verificator);
    }

    @Override
    protected void executeProtocol() {
        System.out.println(Thread.currentThread().getName() + " id : " + super.id + " " + (super.isLeader ? " Leader " : ""));
    }

    @Override
    protected ConsensusValue endPhase() {
        return super.reedMessage(sharedData.getLeaderId());
    }
}
