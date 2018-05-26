package server_side;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {

	public static ArrayList<ServerClient> clients;

	public static int PORT = 12345;
	ServerSocket server;
	public static int playersReady = 0;
	public static String firstHand = null;
	public static String secondHand = null;

	public Server() {
		clients = new ArrayList<ServerClient>();

		System.out.println("STARTING SERVER ON PORT " + PORT);

		try {
			server = new ServerSocket(PORT);
		} catch (IOException e) {
			System.out.println("COULDN'T START SERVER");
			System.exit(0);
		}

		Runnable connection_listener = () -> {

			while (true) {
				try {

					ServerClient client = new ServerClient(server.accept());
					if(Server.clients.size() < 2) {
                        Server.clients.add(client);
                    }
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		};

		new Thread(connection_listener).start();

		System.out.println("SERVER START COMPLETE");

	}

	public static void startCountDown() {

		for (ServerClient sc: clients
			 ) {
			sc.startTimer();

		}

	}

	public static void setHands(String hand) {
		for (ServerClient sc: clients){
			 if( firstHand == null) {
				 firstHand = hand;
				 System.out.println("first hand: " + firstHand);
			 }
			 else {
				secondHand = hand;
				 System.out.println("second hand: " + secondHand);
			 }

			 if(firstHand != null && secondHand != null) {
				compareHands(firstHand, secondHand);
			 }
		}
	}


	private static void compareHands(String firstHand, String secondHand) {
		if (firstHand.equals("rock") && secondHand.equals("paper")) {
			clients.get(0).youLost();
			clients.get(1).youWon();
		}
		else if (firstHand.equals("rock") && secondHand.equals("scissors")) {
			clients.get(0).youWon();
			clients.get(1).youLost();
		}

		else if (firstHand.equals("paper") && secondHand.equals("scissors")) {
			clients.get(0).youLost();
			clients.get(1).youWon();
		}
		else if (firstHand.equals("paper") && secondHand.equals("rock")) {
			clients.get(0).youWon();
			clients.get(1).youLost();
		}

		else if (firstHand.equals("scissors") && secondHand.equals("rock")) {
			clients.get(0).youLost();
			clients.get(1).youWon();
		}
		else if (firstHand.equals("scissors") && secondHand.equals("paper")) {
			clients.get(0).youWon();
			clients.get(1).youLost();
		}
		else if (firstHand.equals(secondHand)) {
			for (ServerClient sc: clients
				 ) {
				sc.draw();
			}
		}
	}
}

