public class Main {
    public static void main(String[] args) {
        int numberOfNodes = 10;
        int numberOfByzantineNodes = 2;

        ByzantineBroadcastSimulation simulation = new ByzantineBroadcastSimulation(numberOfNodes, numberOfByzantineNodes, Protocols.SIMPLE_PROTOCOL);
        simulation.startSimulation();
    }
}