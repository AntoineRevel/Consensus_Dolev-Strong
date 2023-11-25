import java.util.concurrent.CyclicBarrier;

public class SimpleNode extends AbstractNode {
    private ConsensusValue receivedValue;
    public SimpleNode(int id, SharedData sharedData, CyclicBarrier roundBarrier, BBVerifier verifier) {
        super(id, sharedData, roundBarrier, verifier);
    }

    @Override
    protected void executeProtocol() {
        receivedValue = reedMessage(sharedData.getLeaderId()).getValue();
        say(Thread.currentThread().getName() + (super.isLeader ? " I'm leader" : ""));
    }

    @Override
    protected ConsensusValue endPhase() {
        if (receivedValue==null){
            receivedValue =ConsensusValue.BOTTOM;
            say("Get nothing choose -> " + receivedValue);
        } else {
            say("Get "+ receivedValue);
        }
        return receivedValue;
    }
}
