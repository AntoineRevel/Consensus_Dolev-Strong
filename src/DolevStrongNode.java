import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class DolevStrongNode extends IntermediateNode {
    public DolevStrongNode(int id, SharedData sharedData, CyclicBarrier roundBarrier, BBVerifier verifier) {
        super(id, sharedData, roundBarrier, verifier);
    }

    @Override
    protected void executeProtocol() {
        List<Message> receivedMessages=getAllReceivedMessages();

    }


}
