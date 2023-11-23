import java.util.concurrent.CyclicBarrier;

public abstract class AbstractNodeFactory {
    protected SharedData sharedData;
    protected CyclicBarrier roundBarrier;
    protected BBVerificator verificator;

    public AbstractNodeFactory(int numberOfNodes, int numberOfByzantineNodes, int numberOfRounds) {
        this.sharedData = new SharedData(numberOfNodes, numberOfByzantineNodes, numberOfRounds);
        this.roundBarrier = new CyclicBarrier(numberOfNodes, this::handleRoundCompletion);
        this.verificator = new BBVerificator(numberOfNodes);
    }

    private void handleRoundCompletion() {
        int currentRound = sharedData.getAndIncrementRound();
        System.out.println("--- End of round " + currentRound + " --- \n");

        if (currentRound == 0) {
            System.out.println("--- Start round(s) --- ");
        } else if (currentRound == sharedData.getNumberOfRounds()) {
            System.out.println(" --- Final decision --- ");
        }
    }

    public BBVerificator getVerificator() {
        return verificator;
    }

    public abstract AbstractNode createHonestNode(int id);

    public abstract IByzantineNode createByzantineNode(int id);
}

