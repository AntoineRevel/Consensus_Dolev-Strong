import java.util.concurrent.CyclicBarrier;

public abstract class NodeFactory {
    protected SharedData sharedData;
    protected CyclicBarrier roundBarrier;

    public NodeFactory(int numberOfNodes, int numberOfByzantineNodes) {
        int numberOfRounds = getNumberOfRounds();
        this.sharedData = new SharedData(numberOfNodes, numberOfByzantineNodes, numberOfRounds);
        this.roundBarrier = new CyclicBarrier(numberOfNodes, () -> System.out.println("End of round " + sharedData.incrementRound()));
    }

    abstract int getNumberOfRounds();

    public abstract Node createHonestNode(int id);

    public abstract Node createByzantineNode(int id);
}
