import java.util.concurrent.CyclicBarrier;

public class ByzantineSimpleNode extends SimpleNode implements IByzantineNode {
    public ByzantineSimpleNode(int id, SharedData sharedData, CyclicBarrier roundBarrier, BBVerificator verificator) {
        super(id, sharedData, roundBarrier, verificator);
    }

    @Override
    public void startPhase() {
        super.startPhase();
    }

    @Override
    public void executeProtocol() {
        super.executeProtocol();
    }

    @Override
    public ConsensusValue endPhase() {
        return super.endPhase();
    }
}
