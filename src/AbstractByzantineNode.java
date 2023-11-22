import java.util.concurrent.CyclicBarrier;

public abstract class AbstractByzantineNode extends AbstractNode {
    public AbstractByzantineNode(int id, SharedData sharedData, CyclicBarrier roundBarrier, BBVerificator verificator) {
        super(id, sharedData, roundBarrier, verificator);
    }

    @Override
    protected void executeProtocol() {

    }

    @Override
    protected ConsensusValue endPhase() {
        return null;
    }
}
