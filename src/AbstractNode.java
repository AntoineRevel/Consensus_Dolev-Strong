import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public abstract class AbstractNode implements INode {
    protected final int id;
    protected final boolean isLeader;
    protected final SharedData sharedData;
    private final CyclicBarrier roundBarrier;
    protected final BBVerificator verificator;

    public AbstractNode(int id, SharedData sharedData, CyclicBarrier roundBarrier, BBVerificator verificator) {
        this.id = id;
        this.isLeader = sharedData.getLeaderId() == id;
        this.sharedData = sharedData;
        this.roundBarrier = roundBarrier;
        this.verificator = verificator;
    }

    protected void sendMessage(int receiver, ConsensusValue message) {
        sharedData.sendMessage(id, receiver, message);
    }

    protected ConsensusValue reedMessage(int sender) {
        return sharedData.reedMessage(sender, id);
    }

    private void broadcast(ConsensusValue message) {
        for (int receiver = 0; receiver < sharedData.getNumberOfNodes(); receiver++) {
                sendMessage(receiver, message);
        }
    }

    /**
     * Leader chooses an initial input value and sends it to all other nodes.
     */
    protected void startPhase() {
        if (isLeader) { //TODO check if first imput recei from Leader ?
            ConsensusValue inputValue = getDeterministicConsensusValue();
            broadcast(inputValue);
            verificator.setLeaderInputValue(inputValue);
        }
    }

    /**
     * Use hash to deterministically choose a value from the enum, except BOTTOM
     */
    private ConsensusValue getDeterministicConsensusValue() {
        List<ConsensusValue> validValues = Arrays.stream(ConsensusValue.values())
                .filter(value -> value != ConsensusValue.BOTTOM)
                .toList();

        int hash = calculateConfigurationHash();

        return validValues.get(Math.abs(hash) % validValues.size());
    }

    private int calculateConfigurationHash() {
        return Objects.hash(sharedData.getNumberOfNodes(), sharedData.getNumberOfByzantineNodes(), sharedData.getNumberOfRounds());
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
        if (!(this instanceof IByzantineNode)) {
            verificator.setNodeHonest(id);
            System.out.println(id + " je suis honnette");
        }

        startPhase();
        waitForOthers();

        int numberOfRounds = sharedData.getNumberOfRounds();
        for (int round = 0; round < numberOfRounds; round++) {
            executeProtocol();
            waitForOthers();
        }

        ConsensusValue outputValue = endPhase();
        verificator.setOutputValue(id, outputValue);
    }

    /**
     * Executes the node's specific actions during each round of the protocol.
     */
    protected abstract void executeProtocol();

    /**
     * Node's decision rules at the end of the simulation, determines its final output.
     *
     * @return The final output value of the node.
     */
    protected abstract ConsensusValue endPhase();

}