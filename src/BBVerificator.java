import java.util.Arrays;

public class BBVerificator {
    private ConsensusValue leaderInputValue;
    private final boolean[] areNodesHonest;
    private final ConsensusValue[] outputValues;
    private final int alignmentWidth = 13;

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

    /**
     * Check if all honest nodes have output values.
     */
    private boolean checkTermination() {
        System.out.println(Arrays.toString(areNodesHonest));
        System.out.println(Arrays.toString(outputValues) + "\n");
        for (int i = 0; i < areNodesHonest.length; i++) {
            if (areNodesHonest[i] && outputValues[i] == null) {
                System.out.printf("%-" + alignmentWidth + "s %s%n", "Termination:", "\u001B[31mKO\u001B[0m Honest node " + i + " has no output value.");
                return false;
            }
        }
        System.out.printf("%-" + alignmentWidth + "s %s%n", "Termination:", "\u001B[32mOK\u001B[0m");
        return true;
    }

    /**
     * Check if all honest nodes have the same output value as the leader's input value.
     */
    private boolean checkValidity() {
        if (leaderInputValue == null) {
            System.out.printf("%-" + alignmentWidth + "s %s%n", "Validity:", "\u001B[32mOK\u001B[0m Leader is a byzantine node.");
            return true;
        }

        for (int i = 0; i < areNodesHonest.length; i++) {
            if (areNodesHonest[i] && outputValues[i] != leaderInputValue) {
                System.out.printf("%-" + alignmentWidth + "s %s%n", "Validity:", "\u001B[31mKO\u001B[0m An honest node has a different output value than the leader's input value.");
                return false;
            }
        }

        System.out.printf("%-" + alignmentWidth + "s %s%n", "Validity:", "\u001B[32mOK\u001B[0m");
        return true;
    }

    /**
     * Check if all honest nodes have the same output value.
     */
    private boolean checkAgreement() {
        ConsensusValue firstHonestNodeValue = null;
        for (int i = 0; i < areNodesHonest.length; i++) {
            if (areNodesHonest[i]) {
                if (firstHonestNodeValue == null) {
                    firstHonestNodeValue = outputValues[i];
                } else if (outputValues[i] != firstHonestNodeValue) {
                    System.out.printf("%-" + alignmentWidth + "s %s%n", "Agreement:", "\u001B[31mKO\u001B[0m Honest nodes " + i + " have different output values.");
                    return false;
                }
            }
        }

        if (firstHonestNodeValue == null) {
            System.out.printf("%-" + alignmentWidth + "s %s%n", "Agreement:", "\u001B[32mOK\u001B[0m No output values from honest nodes.");
            return true;
        }
        System.out.printf("%-" + alignmentWidth + "s %s%n", "Agreement:", "\u001B[32mOK\u001B[0m " + firstHonestNodeValue);
        return true;
    }

    public boolean verifyBB() {
        boolean terminationCheck = checkTermination();
        boolean validityCheck = checkValidity();
        boolean agreementCheck = checkAgreement();

        System.out.println();
        return terminationCheck && agreementCheck && validityCheck;
    }
}
