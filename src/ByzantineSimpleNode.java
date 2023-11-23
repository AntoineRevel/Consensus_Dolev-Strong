import java.util.concurrent.CyclicBarrier;

public class ByzantineSimpleNode extends SimpleNode implements IByzantineNode {
    public ByzantineSimpleNode(int id, SharedData sharedData, CyclicBarrier roundBarrier, BBVerificator verificator) {
        super(id, sharedData, roundBarrier, verificator);
    }

    @Override
    protected void startPhase() {
        super.startPhase();
    }

    @Override
    protected void executeProtocol() {
        super.executeProtocol();
    }

    @Override
    protected ConsensusValue endPhase() {
        return super.endPhase();
    }
}
