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
        System.out.printf("Termination property satisfied: %s", "All honest nodes have output values.");//t.println(String.format("%-" + maxPrefixLength + "s %s", terminationPrefix, "All honest nodes have output values."));
        return true;
    }

    private boolean checkAgreement() {
        ConsensusValue firstHonestNodeValue = null;
        for (int i = 0; i < areNodesHonest.length; i++) {
            if (areNodesHonest[i]) {
                if (firstHonestNodeValue == null) {
                    firstHonestNodeValue = outputValues[i];
                } else if (outputValues[i] != firstHonestNodeValue) {
                    System.out.println("Agreement property not satisfied: Honest nodes " + i + " have different output values.");
                    return false;
                }
            }
        }

        if (firstHonestNodeValue == null) {
            System.out.println("Agreement property could not be verified: No output values from honest nodes.");
            return true;
        }

        System.out.println("Agreement property satisfied: All honest nodes have the same output value.");
        return true;
    }

    private boolean checkValidity() {
        if (leaderInputValue == null) {
            System.out.println("Validity property cannot be verified: Leader is not honest or leader's input value is not set.");
            return true;
        }

        for (int i = 0; i < areNodesHonest.length; i++) {
            if (areNodesHonest[i] && outputValues[i] != leaderInputValue) {
                System.out.println("Validity property not satisfied: An honest node has a different output value than the leader's input value.");
                return false;
            }
        }

        System.out.println("Validity property satisfied: All honest nodes have the same output value as the leader's input value.");
        return true;

    }

    public boolean verifyBB() {
        return checkTermination() && checkAgreement() && checkValidity();
    }


}
