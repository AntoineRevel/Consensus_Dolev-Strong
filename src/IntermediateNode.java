import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class IntermediateNode extends AbstractNode {
    private final List<Message> messages;

    public IntermediateNode(int id, SharedData sharedData, CyclicBarrier roundBarrier, BBVerifier verifier) {
        super(id, sharedData, roundBarrier, verifier);
        this.messages = new ArrayList<>();
    }

    @Override
    protected void executeProtocol() {
        int currentRound = sharedData.getCurrentRound();
        if (currentRound == 1) {
            int leaderId = sharedData.getLeaderId();
            Message leaderMessage = reedMessage(leaderId);
            ConsensusValue leaderValue = leaderMessage.getValue();
            if (leaderValue != null) {
                say("Received " + leaderValue + " from the leader, sending it to all");
                messages.add(leaderMessage);
                broadcast(leaderMessage);
            }
        } else if (currentRound == 2) {
            List<Message> receivedMessages = getAllReceivedMessages();
            say("Received" + receivedMessages);
            messages.addAll(receivedMessages);
        }
    }

    @Override
    protected ConsensusValue endPhase() {
        if (allMessagesHaveSameValue(messages)) {
            ConsensusValue receivedValue = messages.get(0).getValue();
            say("Get " + receivedValue + " from all");
            return receivedValue;
        } else {
            say("Get " + messages.toString() + " -> Choose ⊥");
        }
        return ConsensusValue.BOTTOM;
    }
}
