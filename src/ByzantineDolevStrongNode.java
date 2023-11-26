import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class ByzantineDolevStrongNode extends DolevStrongNode implements IByzantineNode{
    public ByzantineDolevStrongNode(int id, SharedData sharedData, CyclicBarrier roundBarrier, BBVerifier verifier) {
        super(id, sharedData, roundBarrier, verifier);
    }

    @Override
    protected ConsensusValue startPhase() {
        byzantineStartPhase();
        return null;
    }

    @Override
    protected void executeProtocol() {
        List<Message> receivedMessage = getAllReceivedMessages();
        for (Message message : receivedMessage) {
            if (containsKDistinct(message.getSigners())) {
                values.add(message.getValue());
                say(message.toString());
                if (message.getValue()==ConsensusValue.R)broadcast(message);
            }
        }

    }
}
