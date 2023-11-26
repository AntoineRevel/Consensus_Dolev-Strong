import java.util.concurrent.CyclicBarrier;

public class ByzantineSimpleNode extends SimpleNode implements IByzantineNode {
    public ByzantineSimpleNode(int id, SharedData sharedData, CyclicBarrier roundBarrier, BBVerifier verifier) {
        super(id, sharedData, roundBarrier, verifier);
    }

    @Override
    protected ConsensusValue startPhase() {
        byzantineStartPhase();
        return null;
    }
}
