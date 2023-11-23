public class Main {
    public static void main(String[] args) {
        int numberOfNodes = 10;
        int numberOfByzantineNodes = 0;
        Protocols protocol = Protocols.INTERMEDIATE;

        ByzantineBroadcastSimulation simulation = new ByzantineBroadcastSimulation(numberOfNodes, numberOfByzantineNodes, protocol);
        simulation.startSimulation();
    }
}