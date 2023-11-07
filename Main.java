public class Main {
    public static void main(String[] args) throws Exception {
        VotingData vd = new VotingData();
        PollingDevice pd = new PollingDevice(vd);
        pd.screen();
    }
}
