import java.util.concurrent.CyclicBarrier;

public class ByzantineIntermediateNode extends IntermediateNode implements IByzantineNode{
    public ByzantineIntermediateNode(int id, SharedData sharedData, CyclicBarrier roundBarrier, BBVerificator verificator) {
        super(id, sharedData, roundBarrier, verificator);
    }

    @Override
    protected void startPhase() {
        //byzantineStartPhase();
    }

    @Override
    protected void executeProtocol() {
        byzantineStartPhase();
    }
}
