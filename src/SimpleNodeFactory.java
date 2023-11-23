public class SimpleNodeFactory extends AbstractNodeFactory {
    public SimpleNodeFactory(int numberOfNodes, int numberOfByzantineNodes) {
        super(numberOfNodes, numberOfByzantineNodes, 1);
    }

    @Override
    public AbstractNode createHonestNode(int id) {
        return new SimpleNode(id, sharedData, roundBarrier, verificator);
    }

    @Override
    public IByzantineNode createByzantineNode(int id) {
        return new ByzantineSimpleNode(id, sharedData, roundBarrier, verificator);
    }
}
