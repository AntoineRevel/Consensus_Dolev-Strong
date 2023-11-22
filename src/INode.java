public interface INode extends Runnable {

    /**
     * Leader chooses an initial input value and sends it to all other nodes.
     */
    void startPhase();

    /**
     * Executes the node's specific actions or logic during each round of the protocol.
     */
    void executeProtocol();

    /**
     * Node's ecision rules at the end of the simulation and determines its final output.
     *
     * @return The final output value of the node.
     */
    ConsensusValue endPhase();
}
