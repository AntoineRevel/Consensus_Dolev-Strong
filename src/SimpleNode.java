import java.util.concurrent.CyclicBarrier;

public class SimpleNode extends AbstractNode {
    public SimpleNode(int id, SharedData sharedData, CyclicBarrier roundBarrier, BBVerifier verifier) {
        super(id, sharedData, roundBarrier, verifier);
    }

    @Override
    protected void executeProtocol() {
        say(Thread.currentThread().getName() + (super.isLeader ? " I'm leader" : ""));
    }

    @Override
    protected ConsensusValue endPhase() {
        return reedMessage(sharedData.getLeaderId());
    }
}
