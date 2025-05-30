public class ProxyClient {
    public static void main(String[] args) {
        int status = 0;
        com.zeroc.Ice.Communicator communicator = null;

        try {
            communicator = com.zeroc.Ice.Util.initialize(args);

            com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy("Receiver:default -p 10000");
            election.ReliableMessageReceiverPrx proxy = election.ReliableMessageReceiverPrx.checkedCast(base);

            if (proxy == null) {
                throw new Error("Proxy inválido");
            }

            election.VoteMessage message = new election.VoteMessage(42, System.currentTimeMillis(), java.util.UUID.randomUUID().toString());
            proxy.receiveVote(message);

            System.out.println("✅ Mensaje enviado correctamente al servidor");

        } catch (Exception e) {
            System.err.println("❌ Error en el cliente proxy: " + e.getMessage());
            status = 1;
        }

        if (communicator != null) {
            try {
                communicator.destroy();
            } catch (Exception e) {
                System.err.println("❌ Error al cerrar el comunicador: " + e.getMessage());
                status = 1;
            }
        }

        System.exit(status);
    }
}
