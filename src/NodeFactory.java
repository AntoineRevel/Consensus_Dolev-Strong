import java.util.concurrent.CyclicBarrier;

public abstract class NodeFactory {
    protected SharedData sharedData;
    protected CyclicBarrier roundBarrier;

    public NodeFactory(int numberOfNodes, int numberOfByzantineNodes) {
        int numberOfRounds = getNumberOfRounds();
        this.sharedData = new SharedData(numberOfNodes, numberOfByzantineNodes, numberOfRounds);
        this.roundBarrier = new CyclicBarrier(numberOfNodes, () -> System.out.println(" --- End of round " + sharedData.getCurrentRound() +" --- \n\n        Round "+sharedData.incrementRound()));
        //TODO if round=0 -> starting , if round > numberOfRounds ->decision
    }

    protected SharedData getSharedData() {
        return sharedData;
    }
    protected abstract int getNumberOfRounds();

    public abstract Node createHonestNode(int id);

    public abstract Node createByzantineNode(int id);
}
