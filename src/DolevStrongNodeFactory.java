public class DolevStrongNodeFactory extends AbstractNodeFactory {
    public DolevStrongNodeFactory(int numberOfNodes, int numberOfByzantineNodes) {
        super(numberOfNodes, numberOfByzantineNodes, numberOfByzantineNodes + 1);
    }

    @Override
    public AbstractNode createHonestNode(int id) {
        return new DolevStrongNode(id,sharedData,roundBarrier,verifier);
    }

    @Override
    public IByzantineNode createByzantineNode(int id) {
        return new ByzantineDolevStrongNode(id,sharedData,roundBarrier,verifier);
    }
}
