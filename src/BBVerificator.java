import java.util.Arrays;

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

    public synchronized void setNodeHonest(int id) {
        areNodesHonest[id] = true;
    }

    public synchronized void setOutputValue(int id, ConsensusValue value) {
        outputValues[id] = value;
    }

    private boolean checkTermination() {
        System.out.println(Arrays.toString(areNodesHonest));
        System.out.println(Arrays.toString(outputValues));
        for (int i = 0; i < areNodesHonest.length; i++) {
            if (areNodesHonest[i]) {
                if (outputValues[i] == null) {
                    System.out.println("Termination property not satisfied: Honest node " + i + " has no output value.");
                    return false;
                }
            }
        }
        System.out.println("Termination property satisfied: All honest nodes have output values.");
        return true;
    }

    private boolean checkAgreement() {
        return false;
    }

    private boolean checkValidity() {
        if (leaderInputValue != null) {

        }
        return false;
    }

    public boolean verifyBB() {
        return checkTermination() && checkAgreement() && checkValidity();
    }


}
