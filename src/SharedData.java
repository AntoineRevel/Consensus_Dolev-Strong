public class SharedData {
    private int numberOfByzantineNodes;
    private final Integer[][] communicationMatrix;

    public SharedData(int numberOfNodes, int numberOfByzantine) {
        this.numberOfByzantineNodes= numberOfByzantine;
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

}
