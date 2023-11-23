import java.util.concurrent.CyclicBarrier;

public class SimpleNode extends AbstractNode {
    public SimpleNode(int id, SharedData sharedData, CyclicBarrier roundBarrier, BBVerificator verificator) {
        super(id, sharedData, roundBarrier, verificator);
    }

    @Override
    protected void executeProtocol() {
        System.out.println(id+": "+Thread.currentThread().getName() + (super.isLeader ? " I'm leader" : ""));
    }

    @Override
    protected ConsensusValue endPhase() {
        return reedMessage(sharedData.getLeaderId());
    }
}
