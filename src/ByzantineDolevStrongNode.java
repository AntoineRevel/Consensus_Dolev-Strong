import java.util.concurrent.CyclicBarrier;

public class ByzantineDolevStrongNode extends DolevStrongNode implements IByzantineNode{
    public ByzantineDolevStrongNode(int id, SharedData sharedData, CyclicBarrier roundBarrier, BBVerifier verifier) {
        super(id, sharedData, roundBarrier, verifier);
    }

    @Override
    protected void startPhase() {
        byzantineStartPhase();
    }

    @Override
    protected void executeProtocol() {
        if (sharedData.getCurrentRound() == 1) byzantineStartPhase();
    }
}
