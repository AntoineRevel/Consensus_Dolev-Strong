import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class NodeFactory {
    protected SharedData sharedData;
    protected CyclicBarrier roundBarrier;
    private AtomicInteger currentRound = new AtomicInteger(0);

    public NodeFactory(int numberOfNodes, int numberOfByzantineNodes) {
        int numberOfRounds = getNumberOfRounds();
        this.sharedData = new SharedData(numberOfNodes, numberOfByzantineNodes, numberOfRounds);
        this.roundBarrier = new CyclicBarrier(numberOfRounds, () -> {
            System.out.println("End of round " + currentRound.incrementAndGet());
        });
    }

    abstract int getNumberOfRounds();

    public abstract Node createHonestNode(int id);

    public abstract Node createByzantineNode(int id);
}
