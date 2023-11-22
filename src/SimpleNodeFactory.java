public class SimpleNodeFactory extends AbstractNodeFactory {

    public SimpleNodeFactory(int numberOfNodes, int numberOfByzantineNodes) {
        super(numberOfNodes, numberOfByzantineNodes);
    }

    @Override
    protected int getNumberOfRounds() {
        return 1;
    }

    @Override
    public AbstractNode createHonestNode(int id) {
        return new SimpleNode(id, super.sharedData, super.roundBarrier, super.verificator);
    }

    @Override
    public IByzantineNode createByzantineNode(int id) {
        return new ByzantineSimpleNode(id, super.sharedData, super.roundBarrier, super.verificator);
    }
}
