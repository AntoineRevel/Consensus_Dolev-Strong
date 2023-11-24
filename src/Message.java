import java.util.Arrays;

public class Message {
    private final ConsensusValue value;
    private final int[] signers;

    private Message(ConsensusValue value, int[] signers) {
        this.value = value;
        this.signers = signers;
    }

    private Message(Message original, int newSignerId) {
        this.value = original.value;
        this.signers = Arrays.copyOf(original.signers, original.signers.length + 1);
        this.signers[this.signers.length - 1] = newSignerId;
    }

    public static Message getNewMessage(ConsensusValue value){
        int[] signers = new int[0];
        return new Message(value,signers);
    }

    public Message addSigner(int id){
        return new Message(this,id);
    }

    @Override
    public String toString() {
        return value + " from " + Arrays.toString(signers);
    }

    public ConsensusValue getValue() {
        return value;
    }
}
