package server_side;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {

	public static ArrayList<ServerClient> clients = new ArrayList<>();

	public static int PORT = 12345;
	ServerSocket server;

	public Server() {

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
					clients.add(client);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		};

		new Thread(connection_listener).start();

		System.out.println("SERVER START COMPLETE");

	}

	public static void messageAllClients(String msg) {

		for (int i = 0; i < clients.size(); i++) {

			ServerClient serverClient = clients.get(i);

			if (serverClient.getName() == null) {
				clients.remove(i);
				i--;
			} else {
				serverClient.printMessage(msg);
			}
		}

	}
}
