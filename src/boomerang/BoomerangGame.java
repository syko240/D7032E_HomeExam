package boomerang;

class BoomerangGame {
    private GameInitializer gameInitializer;
    private CommunicationHandler communicationHandler;

    public BoomerangGame(String[] params) throws Exception {
        communicationHandler = new CommunicationHandler();

        if (params.length == 3) {
            int port = Integer.parseInt(params[0]);
            int numPlayers = Integer.valueOf(params[1]);
            int numBots = Integer.valueOf(params[2]);

            communicationHandler.startServer(port, numPlayers, numBots);
            gameInitializer = new GameInitializer();
            gameInitializer.startGame();
        } else if (params.length == 2) {
            String ipAddress = params[0];
            int port = Integer.parseInt(params[1]);
            communicationHandler.startClient(ipAddress, port);
        } else {
            System.out.println("Please provide the correct arguments.");
            System.out.println("Server syntax: java BoomerangGame numPlayers numBots");
            System.out.println("Client syntax: IP");
        }
    }
    public static void main(String argv[]) {
        try {
            new BoomerangGame(argv);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}