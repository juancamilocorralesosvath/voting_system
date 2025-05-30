public class CentralSystem {
    public static void main(String[] args) {
        int status = 0;
        com.zeroc.Ice.Communicator communicator = null;

        try {
            communicator = com.zeroc.Ice.Util.initialize(args);

            com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("ReceiverAdapter", "default -p 10000");

            services.RMReceiver receiverImpl = new services.RMReceiver();
            com.zeroc.Ice.Object receiver = (com.zeroc.Ice.Object) receiverImpl;

            adapter.add(receiver, com.zeroc.Ice.Util.stringToIdentity("Receiver"));
            adapter.activate();

            System.out.println("🟢 Servidor iniciado. Esperando mensajes...");
            communicator.waitForShutdown();

        } catch (Exception e) {
            System.err.println("❌ Error en el servidor: " + e.getMessage());
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
