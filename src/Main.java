public class Main {
    public static void main(String[] args) {
        int numberOfNodes = 5;
        int numberOfByzantineNodes = 0;
        Protocols protocol = Protocols.DOLEV_STRONG;

        ByzantineBroadcastSimulation simulation = new ByzantineBroadcastSimulation(numberOfNodes, numberOfByzantineNodes, protocol);
        simulation.startSimulation();
    }
}