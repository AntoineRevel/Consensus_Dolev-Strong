public class Main {
    public static void main(String[] args) {
        int numberOfNodes = 5;
        int numberOfByzantineNodes = 1;
        Protocols protocol = Protocols.SIMPLE;

        ByzantineBroadcastSimulation simulation = new ByzantineBroadcastSimulation(numberOfNodes, numberOfByzantineNodes, protocol);
        simulation.startSimulation();
    }
}