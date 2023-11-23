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
    protected final BBVerificator verificator;

    public AbstractNode(int id, SharedData sharedData, CyclicBarrier roundBarrier, BBVerificator verificator) {
        this.id = id;
        this.isLeader = sharedData.getLeaderId() == id;
        this.sharedData = sharedData;
        this.roundBarrier = roundBarrier;
        this.verificator = verificator;
        //TODO add nbofNodes
    }

    protected void sendMessage(int receiver, ConsensusValue message) {
        sharedData.sendMessage(id, receiver, message);
    }

    protected ConsensusValue reedMessage(int sender) {
        return sharedData.readMessage(sender, id);
    }

    protected void broadcast(ConsensusValue message) {
        for (int receiver = 0; receiver < sharedData.getNumberOfNodes(); receiver++) {
                sendMessage(receiver, message);
        }
    }

    protected List<Message> getAllReceivedMessages() {
        List<Message> receivedMessages = new ArrayList<>();
        for (int sender = 0; sender < sharedData.getNumberOfNodes(); sender++) {
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
            System.out.println(id+": Sending "+inputValue+" to all nodes");
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


    protected void startPhaseByzantine1() {
        if (isLeader) {
            ConsensusValue[] allValues = ConsensusValue.values();
            int numberOfNodes = sharedData.getNumberOfNodes();

            for (int i = 0; i < numberOfNodes; i++) {
                ConsensusValue valueToSend = allValues[i % allValues.length];
                sendMessage(i, valueToSend);
            }
        }
    }

    protected static class Message{
        private ConsensusValue value;
        private int sender;

        public Message(ConsensusValue value, int sender) {
            this.value = value;
            this.sender = sender;
        }

        public ConsensusValue getValue() {
            return value;
        }

        public int getSender() {
            return sender;
        }
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