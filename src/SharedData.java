import java.util.concurrent.atomic.AtomicInteger;

public class SharedData {
    private final int numberOfNodes;
    private final int numberOfByzantineNodes;
    private int leaderId = 0;
    protected final int numberOfRounds;
    private final AtomicInteger currentRound = new AtomicInteger(0);
    private final ConsensusValue[][] communicationMatrix;


    public SharedData(int numberOfNodes, int numberOfByzantineNodes, int numberOfRounds) {
        this.numberOfNodes = numberOfNodes;
        this.numberOfByzantineNodes = numberOfByzantineNodes;
        this.numberOfRounds = numberOfRounds;
        communicationMatrix = new ConsensusValue[numberOfNodes][numberOfNodes];
    }

    public synchronized void sendMessage(int sender, int receiver, ConsensusValue message) {
        communicationMatrix[sender][receiver] = message;
    }

    public synchronized ConsensusValue reedMessage(int sender, int receiver) {
        return communicationMatrix[sender][receiver];
    }

    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    public int getAndIncrementRound() {
        return currentRound.getAndIncrement();
    }


    public int getLeaderId() {
        return leaderId;
    }

    public int getNumberOfByzantineNodes() {
        return numberOfByzantineNodes;
    }

    public void setLeaderId(int leaderId) {
        this.leaderId = leaderId;
    }
}
