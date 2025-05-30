module election {
    struct VoteMessage {
        int candidateId;
        long timestamp;
        string messageId;
    };

    interface ReliableMessageReceiver {
        void receiveVote(VoteMessage message);
        void acknowledge(string messageId);
    }
}
