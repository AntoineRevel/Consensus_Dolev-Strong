import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class DolevStrongNode extends AbstractNode {
    public DolevStrongNode(int id, SharedData sharedData, CyclicBarrier roundBarrier, BBVerifier verifier) {
        super(id, sharedData, roundBarrier, verifier);
    }

    @Override
    protected void executeProtocol() {
    }

    @Override
    protected ConsensusValue endPhase() {
        return null;
    }


}
