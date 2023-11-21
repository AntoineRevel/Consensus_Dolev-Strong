public class Main {
    public static void main(String[] args) {
        int numberOfNodes = 13;
        int numberOfByzantineNodes = 0;

        ByzantineBroadcastSimulation simulation = new ByzantineBroadcastSimulation(numberOfNodes, numberOfByzantineNodes, Protocols.SIMPLE_PROTOCOL);
        simulation.startSimulation();
    }
}