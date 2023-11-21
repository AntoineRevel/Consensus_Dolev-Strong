import java.util.concurrent.*;

public class ByzantineBroadcastSimulation {
    private final ExecutorService executor;
    private final int numberOfNodes;
    private final int numberOfByzantineNodes;
    private final Protocols protocol;

    public ByzantineBroadcastSimulation(int numberOfNodes, int numberOfByzantineNodes, Protocols protocol) {
        //TODO verifie si numberOfNodes > numberOfByzantineNodes
        this.numberOfNodes = numberOfNodes;
        this.numberOfByzantineNodes = numberOfByzantineNodes;
        this.protocol = protocol;
        this.executor = Executors.newFixedThreadPool(numberOfNodes);
    }

    public void startSimulation() {
        NodeFactory factory = createFactory(protocol);

        for (int i = 0; i < numberOfNodes; i++) {
            Node node;
            if (i < numberOfByzantineNodes) {
                node = factory.createByzantineNode(i);
            } else {
                node = factory.createHonestNode(i);
            }
            executor.submit(node);
        }
        executor.shutdown();
    }

    private NodeFactory createFactory(Protocols protocol) {
        switch (protocol) {
            case SIMPLE_PROTOCOL:
                return new SimpleProtocolFactory(numberOfNodes, numberOfByzantineNodes);
            case DOLAV_STRONG:

            default:
                throw new IllegalArgumentException("Unrecognized protocol type");
        }
    }


}
