import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public abstract class Node implements Runnable {
    protected final int id;
    protected final SharedData sharedData;
    private final CyclicBarrier roundBarrier;

    public Node(int id, SharedData sharedData, CyclicBarrier roundBarrier) {
        this.id = id;
        this.sharedData = sharedData;
        this.roundBarrier = roundBarrier;
    }

    @Override
    public void run() {
        int numberOfRounds = sharedData.getNumberOfRounds();
        for (int round = 0; round < numberOfRounds; round++) {
            executeProtocol();
            try {
                roundBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    protected abstract void executeProtocol();
}