package services;

public class RMSender {

    private election.ReliableMessageReceiverPrx receiver;
    private java.util.Map<String, election.VoteMessage> pendingMessages;

    public RMSender(election.ReliableMessageReceiverPrx receiver) {
        this.receiver = receiver;
        this.pendingMessages = new java.util.HashMap<>();
    }

    public void sendVote(election.VoteMessage message) {
        pendingMessages.put(message.messageId, message);
        trySend(message.messageId);
    }

    private void trySend(String messageId) {
        election.VoteMessage msg = pendingMessages.get(messageId);
        if (msg == null) return;

        System.out.println("📤 Enviando voto con ID: " + messageId);
        receiver.receiveVote(msg);

        new java.util.Timer().schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                if (pendingMessages.containsKey(messageId)) {
                    System.out.println("⏳ Reintentando envío: " + messageId);
                    trySend(messageId);
                }
            }
        }, 3000);
    }

    public void handleAck(String messageId) {
        System.out.println("✅ Acuse recibido, eliminando mensaje: " + messageId);
        pendingMessages.remove(messageId);
    }
}