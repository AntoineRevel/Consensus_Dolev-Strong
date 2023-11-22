public interface IByzantineNode extends INode{

    /**
     * The start phase method for a Byzantine node.
     */
    void startPhase();

    /**
     * Executes the Byzantine node's strategy and logic during each round of the protocol.
     */
    void executeProtocol();

    /**
     * Actions required at the end of the simulation
     * to return or not the node's final decision.
     *
     * @return The value decided by the Byzantine node.
     */
    ConsensusValue endPhase();
}