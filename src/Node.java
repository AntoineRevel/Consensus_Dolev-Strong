import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public abstract class Node implements Runnable {
    protected final int id;
    protected final boolean isLeader;
    protected ConsensusValue inputValue;
    protected final SharedData sharedData;
    private final CyclicBarrier roundBarrier;

    public Node(int id, SharedData sharedData, CyclicBarrier roundBarrier) {
        this.id = id;
        this.isLeader = sharedData.getLeaderId()==id;
        this.sharedData = sharedData;
        this.roundBarrier = roundBarrier;
    }

    private void startPhase() {
        if (isLeader) {
            inputValue = new Random().nextBoolean() ? ConsensusValue.R : ConsensusValue.B;
        }
        waitForOthers();
    }

    private void waitForOthers() {
        try {
            roundBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void run() {
        startPhase();

        int numberOfRounds = sharedData.getNumberOfRounds();
        for (int round = 0; round < numberOfRounds; round++) {
            executeProtocol();
            waitForOthers();
        }
    }

    protected abstract void executeProtocol();
}