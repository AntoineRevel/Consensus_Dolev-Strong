import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class DolevStrongNode extends AbstractNode {
    protected final List<ConsensusValue> values;

    public DolevStrongNode(int id, SharedData sharedData, CyclicBarrier roundBarrier, BBVerifier verifier) {
        super(id, sharedData, roundBarrier, verifier);
        this.values = new ArrayList<>();
    }

    @Override
    protected ConsensusValue startPhase() {
        ConsensusValue consensusValue = super.startPhase();
        if (isLeader)values.add(consensusValue);
        return consensusValue;
    }

    @Override
    protected void executeProtocol() {
        List<Message> receivedMessage = getAllReceivedMessages();
        for (Message message : receivedMessage) {
            if (containsKDistinct(message.getSigners())) {
                values.add(message.getValue());
                say("Get " + message);
                broadcast(message);
            }
        }

    }

    protected boolean containsKDistinct(int[] signers) {
        int distinctCount = 0;
        for (int i = 0; i < signers.length; i++) {
            boolean isDistinct = true;
            if (signers[i] == id) return false;
            for (int j = 0; j < signers.length; j++) {
                if (i != j && signers[i] == signers[j]) {
                    isDistinct = false;
                    break;
                }
            }
            if (isDistinct) distinctCount++;
        }

        return distinctCount == sharedData.getCurrentRound();
    }

    @Override
    protected ConsensusValue endPhase() {
        ConsensusValue consensusValue = containsOnlyOneConsensusValue();
        if (consensusValue != null) {
            say("Get " + consensusValue + " from all " + values.size());
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
