public class Main {
    public static void main(String[] args) {
        int numberOfNodes = 11;
        int numberOfByzantineNodes = 5;
        Protocols protocol = Protocols.DOLEV_STRONG;

        ByzantineBroadcastSimulation simulation = new ByzantineBroadcastSimulation(numberOfNodes, numberOfByzantineNodes, protocol);
        simulation.startSimulation();
    }
}