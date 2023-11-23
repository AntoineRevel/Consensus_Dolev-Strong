import java.util.concurrent.CyclicBarrier;

public class IntermediateNode extends AbstractNode{

    public IntermediateNode(int id, SharedData sharedData, CyclicBarrier roundBarrier, BBVerificator verificator) {
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
