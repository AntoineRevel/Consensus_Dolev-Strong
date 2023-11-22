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
        AbstractNodeFactory factory = createFactory(protocol);

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
    }

    private AbstractNodeFactory createFactory(Protocols protocol) {
        return switch (protocol) {
            case SIMPLE_PROTOCOL -> new SimpleNodeFactory(numberOfNodes, numberOfByzantineNodes);
            default -> throw new IllegalArgumentException("Unrecognized protocol type");
        };
    }
}

