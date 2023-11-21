public class SharedData {
    private final int numberOfByzantineNodes;
    private final int numberOfNodes;
    private final Integer[][] communicationMatrix;

    public SharedData(int numberOfNodes, int numberOfByzantineNodes) {
        this.numberOfNodes = numberOfNodes;
        this.numberOfByzantineNodes = numberOfByzantineNodes;
        communicationMatrix = new Integer[numberOfNodes][numberOfNodes];
    }

    public synchronized void sendMessage(int sender, int receiver, Integer message) {
        communicationMatrix[sender][receiver] = message;
    }

    public synchronized Integer receiveMessage(int sender, int receiver) {
        return communicationMatrix[sender][receiver];
    }

    public int getNumberOfByzantineNodes() {
        return numberOfByzantineNodes;
    }

    public int getNumberOfNodes() {
        return numberOfNodes;
    }

}
