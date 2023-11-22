import java.util.concurrent.CyclicBarrier;

public class ByzantineSimpleProtocolNode extends SimpleProtocolNode implements IByzantineNode{
    public ByzantineSimpleProtocolNode(int id, SharedData sharedData, CyclicBarrier roundBarrier, BBVerificator verificator) {
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
