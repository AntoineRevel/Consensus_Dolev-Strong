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
        return new SimpleProtocolNode(id, super.sharedData, super.roundBarrier);
    }

    @Override
    public Node createByzantineNode(int id) {
        return new SimpleProtocolNode(id, super.sharedData, super.roundBarrier);
    }
}
