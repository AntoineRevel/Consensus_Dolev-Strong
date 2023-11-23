import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class IntermediateNode extends AbstractNode {
    private List<Message> messages; //TODO choix de type de collection

    public IntermediateNode(int id, SharedData sharedData, CyclicBarrier roundBarrier, BBVerificator verificator) {
        super(id, sharedData, roundBarrier, verificator);
    }

    @Override
    protected void executeProtocol() {
        int currentRound = sharedData.getCurrentRound();
        if (currentRound == 1) {
            int leaderId = sharedData.getLeaderId();
            ConsensusValue leaderValue = reedMessage(leaderId);
            if (leaderValue != null) {
                messages.add(new Message(leaderValue, leaderId));
                broadcast(leaderValue);
            }
        } else if (currentRound == 2) {


        }
    }

    @Override
    protected ConsensusValue endPhase() {
        if (allMessagesHaveSameValue(messages)) {
            return messages.get(0).getValue();
        }
        return ConsensusValue.BOTTOM;
    }
}
