public class BBVerificator {
    private ConsensusValue leaderInputValue;
    private final boolean[] areNodesHonest;
    private final ConsensusValue[] outputValues;

    public BBVerificator(int numberOfNodes) {
        this.areNodesHonest = new boolean[numberOfNodes];
        this.outputValues = new ConsensusValue[numberOfNodes];
    }

    public synchronized void setLeaderInputValue(ConsensusValue inputValue) {
        this.leaderInputValue = inputValue;
    }

    public synchronized void setNodeHonest(int id, boolean isHonest) {
        areNodesHonest[id] = isHonest;
    }

    public synchronized void setOutputValue(int id, ConsensusValue value) {
        outputValues[id] = value;
    }

    public boolean verifyBB() {
        return checkTermination() && checkAgreement() && checkValidity();
    }

    private boolean checkAgreement() {
        return false;
    }

    private boolean checkTermination() {
        return false;
    }

    private boolean checkValidity() {
        if (leaderInputValue != null) {

        }
        return false;
    }
}
