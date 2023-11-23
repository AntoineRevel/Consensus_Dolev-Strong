import java.util.concurrent.*;

public class ByzantineBroadcastSimulation {
    private final ExecutorService executor;
    private final int numberOfNodes;
    private final int numberOfByzantineNodes;
    private final Protocols protocol;

    public ByzantineBroadcastSimulation(int numberOfNodes, int numberOfByzantineNodes, Protocols protocol) {
        if (numberOfNodes < numberOfByzantineNodes) {
            throw new IllegalArgumentException("The total number of nodes must be greater than the number of Byzantine nodes.");
        }
        this.numberOfNodes = numberOfNodes;
        this.numberOfByzantineNodes = numberOfByzantineNodes;
        this.protocol = protocol;
        this.executor = Executors.newFixedThreadPool(numberOfNodes);
    }

    public void startSimulation() {
        AbstractNodeFactory factory = getNodeFactory(protocol);

        for (int i = 0; i < numberOfNodes; i++) {
            INode node;
            if (i < numberOfByzantineNodes) {
                node = factory.createByzantineNode(i);
            } else {
                node = factory.createHonestNode(i);
            }
            executor.submit(node);
        }
        executor.shutdown();

        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        boolean isBBProblemSolved = factory.getVerificator().verifyBB();
        if (isBBProblemSolved) {
            System.out.println("The protocol " + protocol + " is a solution to the Byzantine Broadcast problem with this configuration.");
        } else {
            System.out.println("The protocol " + protocol + " is NOT a solution to the Byzantine Broadcast problem with this configuration.");
        }
    }

    private AbstractNodeFactory getNodeFactory(Protocols protocol) {
        return switch (protocol) {
            case SIMPLE -> new SimpleNodeFactory(numberOfNodes, numberOfByzantineNodes);
            case INTERMEDIATE -> new IntermediateNodeFactory(numberOfNodes,numberOfByzantineNodes);

            default -> throw new IllegalArgumentException("Unrecognized protocol type");
        };
    }
}

