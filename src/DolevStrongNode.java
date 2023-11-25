import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class DolevStrongNode extends AbstractNode {
    private final List<ConsensusValue> values;

    public DolevStrongNode(int id, SharedData sharedData, CyclicBarrier roundBarrier, BBVerifier verifier) {
        super(id, sharedData, roundBarrier, verifier);
        this.values = new ArrayList<>();
    }

    @Override
    protected void executeProtocol() {
        int currentRound = sharedData.getCurrentRound();
        List<Message> receivedMessage = getAllReceivedMessages();
        for (Message message :receivedMessage){
            if (message.getSigners().length==currentRound){
                say(message.toString());
                values.add(message.getValue());
            }
        }

    }

    @Override
    protected ConsensusValue endPhase() {
        ConsensusValue consensusValue = containsOnlyOneConsensusValue();
        if (consensusValue!=null){
            say("Get " + consensusValue + " from all");
            return consensusValue;
        } else {
            say("Get " + values.toString() + " -> Choose ⊥");
        }
        return ConsensusValue.BOTTOM;
    }

    private ConsensusValue containsOnlyOneConsensusValue() {
        if (values.isEmpty()) return null;

        ConsensusValue firstValue = values.get(0);
        for (ConsensusValue value : values) {
            if (value != firstValue) {
                return null;
            }
        }
        return firstValue;
    }
}
