import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class SharedData {
    private final int numberOfNodes;
    private final int numberOfByzantineNodes;
    protected final int numberOfRounds;
    private final AtomicInteger currentRound = new AtomicInteger(0);
    private Message[][] communicationMatrix;
    private Message[][] nextCommunicationMatrix;


    public SharedData(int numberOfNodes, int numberOfByzantineNodes, int numberOfRounds) {
        this.numberOfNodes = numberOfNodes;
        this.numberOfByzantineNodes = numberOfByzantineNodes;
        this.numberOfRounds = numberOfRounds;
        communicationMatrix = new Message[numberOfNodes][numberOfNodes];
        for (int i = 0; i < numberOfNodes; i++) {
            Arrays.fill(communicationMatrix[i], Message.getNewMessage(null));
        }
        nextCommunicationMatrix = new Message[numberOfNodes][numberOfNodes];
        for (int i = 0; i < numberOfNodes; i++) {
            Arrays.fill(nextCommunicationMatrix[i], Message.getNewMessage(null));
        }
    }


    public synchronized void sendMessage(int sender, int receiver, Message message) { // TODO qand utiliser synchronized
        nextCommunicationMatrix[sender][receiver] = message;
    }

    public synchronized Message readMessage(int sender, int receiver) {
        return communicationMatrix[sender][receiver];
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
        communicationMatrix=nextCommunicationMatrix;
        nextCommunicationMatrix = new Message[numberOfNodes][numberOfNodes];
        for (int i = 0; i < numberOfNodes; i++) {
            Arrays.fill(nextCommunicationMatrix[i], Message.getNewMessage(null));
        }
        return currentRound.getAndIncrement();
    }

    public int getLeaderId() {
        return 0;
    }

    public int getNumberOfByzantineNodes() {
        return numberOfByzantineNodes;
    }


    public void print() {
        for (Message[] row : communicationMatrix)
            System.out.println(Arrays.toString(row));
    }
}
//TODO je voudrais que les methode getAndIncrementRound setLeaderId reedMessage et sendMessage soit accesible uniquemend depuis AbstractNode