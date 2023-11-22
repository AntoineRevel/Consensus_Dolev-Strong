public class SimpleProtocolFactory extends NodeFactory {

    public SimpleProtocolFactory(int numberOfNodes, int numberOfByzantineNodes) {
        super(numberOfNodes, numberOfByzantineNodes);
    }

    @Override
    protected int getNumberOfRounds() {
        return 1;
    }

    @Override
    public Node createHonestNode(int id) {
        return new SimpleProtocolNode(id, super.sharedData, super.roundBarrier, super.verificator);
    }

    @Override
    public IByzantineNode createByzantineNode(int id) {
        return new ByzantineSimpleProtocolNode(id, super.sharedData, super.roundBarrier, super.verificator);
    }
}
