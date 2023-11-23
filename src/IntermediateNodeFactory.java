public class IntermediateNodeFactory extends AbstractNodeFactory{
    public IntermediateNodeFactory(int numberOfNodes, int numberOfByzantineNodes) {
        super(numberOfNodes, numberOfByzantineNodes, 2);
    }

    @Override
    public AbstractNode createHonestNode(int id) {
        return new IntermediateNode(id,super.sharedData, super.roundBarrier,super.verificator);
    }

    @Override
    public IByzantineNode createByzantineNode(int id) {
        return null;
    }
}
