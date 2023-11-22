import java.util.concurrent.CyclicBarrier;

public abstract class NodeFactory {
    protected SharedData sharedData;
    protected CyclicBarrier roundBarrier;

    public NodeFactory(int numberOfNodes, int numberOfByzantineNodes) {
        int numberOfRounds = getNumberOfRounds();
        this.sharedData = new SharedData(numberOfNodes, numberOfByzantineNodes, numberOfRounds);
        this.roundBarrier = new CyclicBarrier(numberOfNodes, this::handleRoundCompletion);
    }

    private void handleRoundCompletion() {
        int currentRound = sharedData.getAndIncrementRound();
        System.out.println("--- End of round " + currentRound + " --- \n");

        if (currentRound == 0) {
            System.out.println("--- Start round(s) --- ");
        } else if (currentRound == sharedData.getNumberOfRounds()) {
            System.out.println(" --- Making final decision --- ");
        }
    }

    protected abstract int getNumberOfRounds();

    public abstract Node createHonestNode(int id);

    public abstract Node createByzantineNode(int id);
}
