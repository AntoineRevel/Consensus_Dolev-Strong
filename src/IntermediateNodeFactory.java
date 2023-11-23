public class IntermediateNodeFactory extends AbstractNodeFactory {
    public IntermediateNodeFactory(int numberOfNodes, int numberOfByzantineNodes) {
        super(numberOfNodes, numberOfByzantineNodes, 2);
    }

    @Override
    public AbstractNode createHonestNode(int id) {
        return new IntermediateNode(id, sharedData, roundBarrier, verificator);
    }

    @Override
    public IByzantineNode createByzantineNode(int id) {
        return new ByzantineIntermediateNode(id, sharedData, roundBarrier, verificator);
    }
}
