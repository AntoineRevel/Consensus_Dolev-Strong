import java.util.concurrent.atomic.AtomicInteger;

public class SharedData {
    private int numberOfNodes;
    private int numberOfByzantineNodes;
    protected int numberOfRounds;
    private AtomicInteger currentRound = new AtomicInteger(0);
    private final Integer[][] communicationMatrix;

    public SharedData(int numberOfNodes, int numberOfByzantineNodes, int numberOfRounds) {
        this.numberOfNodes = numberOfNodes;
        this.numberOfByzantineNodes = numberOfByzantineNodes;
        this.numberOfRounds = numberOfRounds;
        communicationMatrix = new Integer[numberOfNodes][numberOfNodes];
    }

    public synchronized void sendMessage(int sender, int receiver, Integer message) {
        communicationMatrix[sender][receiver] = message;
    }

    public synchronized Integer receiveMessage(int sender, int receiver) {
        return communicationMatrix[sender][receiver];
    }

    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    public int incrementRound() {
        return currentRound.incrementAndGet();
    }
    public int getCurrentRound() {
        return currentRound.get();
    }
}
