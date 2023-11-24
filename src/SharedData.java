import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class SharedData {
    private final int numberOfNodes;
    private final int numberOfByzantineNodes;
    private int leaderId = 0;
    protected final int numberOfRounds;
    private final AtomicInteger currentRound = new AtomicInteger(0);
    private final Message[][] communicationMatrix;


    public SharedData(int numberOfNodes, int numberOfByzantineNodes, int numberOfRounds) {
        this.numberOfNodes = numberOfNodes;
        this.numberOfByzantineNodes = numberOfByzantineNodes;
        this.numberOfRounds = numberOfRounds;
        communicationMatrix = new Message[numberOfNodes][numberOfNodes];
    }


    public synchronized void sendMessage(int sender, int receiver, Message message) {
        communicationMatrix[sender][receiver] = message;
    }

    public synchronized Message readMessage(int sender, int receiver) {
        return communicationMatrix[sender][receiver];

    }
    public synchronized void resetCommunicationMatrix() {
        for (int i = 0; i < numberOfNodes; i++) {
            Arrays.fill(communicationMatrix[i], null);
        }
    }

    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    public int getCurrentRound() {
        return currentRound.get();
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

    public void print(){
        for (Message[] row : communicationMatrix)
            System.out.println(Arrays.toString(row));
    }
}
//TODO je voudrais que les methode getAndIncrementRound setLeaderId reedMessage et sendMessage soit accesible uniquemend depuis AbstractNode