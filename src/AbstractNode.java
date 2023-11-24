import java.util.ArrayList;
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
    protected final BBVerifier verifier;
    private final int numberOfNodes;

    public AbstractNode(int id, SharedData sharedData, CyclicBarrier roundBarrier, BBVerifier verifier) {
        this.id = id;
        this.isLeader = sharedData.getLeaderId() == id;
        this.sharedData = sharedData;
        this.roundBarrier = roundBarrier;
        this.verifier = verifier;
        this.numberOfNodes = sharedData.getNumberOfNodes();
    }

    protected void say(String message) {
        System.out.println(id + ": " + message);
    }

    protected void sendMessage(int receiver, ConsensusValue message) {
        sharedData.sendMessage(id, receiver, message);
    }

    protected ConsensusValue reedMessage(int sender) {
        return sharedData.readMessage(sender, id);
    }

    protected void broadcast(ConsensusValue message) {
        for (int receiver = 0; receiver < numberOfNodes; receiver++) {
            sendMessage(receiver, message);
        }
    }

    protected List<Message> getAllReceivedMessages() {
        List<Message> receivedMessages = new ArrayList<>();
        for (int sender = 0; sender < numberOfNodes; sender++) {
            ConsensusValue messageValue = reedMessage(sender);
            if (messageValue != null) {
                receivedMessages.add(new Message(messageValue, sender));
            }
        }
        return receivedMessages;
    }


    /**
     * Leader chooses an initial input value and sends it to all other nodes.
     */
    protected void startPhase() {
        if (isLeader) {
            ConsensusValue inputValue = getDeterministicConsensusValue();
            System.out.println(id + ": Sending " + inputValue + " to all nodes");
            broadcast(inputValue);
            verifier.setLeaderInputValue(inputValue);
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
        return Objects.hash(numberOfNodes, sharedData.getNumberOfByzantineNodes(), sharedData.getNumberOfRounds());
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
            verifier.setNodeHonest(id);
        }

        startPhase();
        waitForOthers();

        int numberOfRounds = sharedData.getNumberOfRounds();
        for (int round = 0; round < numberOfRounds; round++) {
            executeProtocol();
            waitForOthers();
        }


        ConsensusValue outputValue = endPhase();
        verifier.setOutputValue(id, outputValue);
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


    protected void byzantineStartPhase() {
        if (isLeader) {
            ConsensusValue[] allValues = ConsensusValue.values();
            for (int i = 0; i < numberOfNodes; i++) {
                if (i % (allValues.length + 1) != 0) {
                    ConsensusValue valueToSend = allValues[i % allValues.length];
                    sendMessage(i, valueToSend);
                    say("Sending " + valueToSend + " to " + i);
                } else {
                    say("Not sending any message to " + i);
                }

            }
        }
    }

    protected record Message(ConsensusValue value, int sender) {
        @Override
        public String toString() {
            return value + " from " + sender;
        }
    }

    protected static boolean allMessagesHaveSameValue(List<Message> messages) {
        if (messages == null || messages.isEmpty()) {
            return false;
        }

        ConsensusValue firstValue = messages.get(0).value;
        for (Message message : messages) {
            if (message.value != firstValue) {
                return false;
            }
        }
        return true;
    }


}