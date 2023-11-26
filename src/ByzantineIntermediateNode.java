import java.util.concurrent.CyclicBarrier;

public class ByzantineIntermediateNode extends IntermediateNode implements IByzantineNode {
    public ByzantineIntermediateNode(int id, SharedData sharedData, CyclicBarrier roundBarrier, BBVerifier verifier) {
        super(id, sharedData, roundBarrier, verifier);
    }

    @Override
    protected ConsensusValue startPhase() {
        //byzantineStartPhase();
        //super.startPhase();
        return null;
    }

    @Override
    protected void executeProtocol() {
        if (sharedData.getCurrentRound() == 1) byzantineStartPhase();
        //super.executeProtocol();
    }
}
