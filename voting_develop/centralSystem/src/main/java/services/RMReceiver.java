package services;

public class RMReceiver implements election.ReliableMessageReceiver {

    @Override
    public void receiveVote(election.VoteMessage message, com.zeroc.Ice.Current current) {
        System.out.println("📥 Voto recibido:");
        System.out.println("🆔 ID: " + message.messageId);
        System.out.println("🕒 Timestamp: " + message.timestamp);
        System.out.println("🗳️ Candidato: " + message.candidateId);
    }

    @Override
    public void acknowledge(String messageId, com.zeroc.Ice.Current current) {
        System.out.println("📬 Acuse recibido para el mensaje: " + messageId);
    }
}