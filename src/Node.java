import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public abstract class Node implements Runnable {
    protected final int id;
    protected final boolean isLeader;
    protected ConsensusValue outputValue; //in verificationCallss
    protected final SharedData sharedData;
    private final CyclicBarrier roundBarrier;

    public Node(int id, SharedData sharedData, CyclicBarrier roundBarrier) {
        this.id = id;
        this.isLeader = sharedData.getLeaderId() == id;
        this.sharedData = sharedData;
        this.roundBarrier = roundBarrier;
    }

    protected void sendMessage(int receiver, ConsensusValue message) {
        sharedData.sendMessage(id, receiver, message);
    }

    protected ConsensusValue reedMessage(int sender) {
        return sharedData.reedMessage(sender, id);
    }

    private void broadcast(ConsensusValue message) {
        for (int receiver = 0; receiver < sharedData.getNumberOfNodes(); receiver++) {
            if (receiver != id) {
                sendMessage(receiver, message);
            }
        }
    }

    private void startPhase() {
        if (isLeader) {
            ConsensusValue inputValue = getDeterministicConsensusValue();
            System.out.println("Leader (Node ID: " + id + ") has set the input value to " + inputValue);
            broadcast(inputValue);
        }
    }

    private ConsensusValue getDeterministicConsensusValue() {
        List<ConsensusValue> validValues = Arrays.stream(ConsensusValue.values())
                .filter(value -> value != ConsensusValue.BOTTOM)
                .toList();

        int hash = calculateConfigurationHash();

        //Use hash to deterministically choose a value from the enum, except BOTTOM
        return validValues.get(Math.abs(hash) % validValues.size());
    }

    private int calculateConfigurationHash() {
        return Objects.hash(sharedData.getNumberOfNodes(), sharedData.getNumberOfByzantineNodes(), sharedData.getNumberOfRounds());
    }

    @Override
    public void run() {
        startPhase();
        waitForOthers();

        int numberOfRounds = sharedData.getNumberOfRounds();
        for (int round = 0; round < numberOfRounds; round++) {
            executeProtocol();
            waitForOthers();
        }

        outputValue = endPhase();
        System.out.println(outputValue); //set verification Class
    }

    private void waitForOthers() {
        try {
            roundBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    protected abstract void executeProtocol();

    protected abstract ConsensusValue endPhase();
}