public class SimpleProtocolFactory extends AbstractNodeFactory {

    public SimpleProtocolFactory(int numberOfNodes, int numberOfByzantineNodes) {
        super(numberOfNodes, numberOfByzantineNodes);
    }

    @Override
    protected int getNumberOfRounds() {
        return 1;
    }

    @Override
    public AbstractNode createHonestNode(int id) {
        return new SimpleProtocolNode(id, super.sharedData, super.roundBarrier, super.verificator);
    }

    @Override
    public AbstractByzantineNode createByzantineNode(int id) {
        return new ByzantineSimpleProtocolNode(id, super.sharedData, super.roundBarrier, super.verificator);
    }
}
