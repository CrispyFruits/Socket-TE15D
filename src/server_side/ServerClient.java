package server_side;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ServerClient {

	private PrintStream to_client;
	private BufferedReader from_client;
	private String name;

	public ServerClient(Socket socket) {

		try {

			to_client = new PrintStream(socket.getOutputStream());
			from_client = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			name = from_client.readLine();
			to_client.println("WELCOME " + name);

		} catch (IOException e) {
			e.printStackTrace();
		}

		Runnable output_r = () -> {

			while (true) {
				try {

					String cmd = from_client.readLine();

					System.out.println(name + " WROTE: " + cmd);
					if (cmd.equals("count")) {
						for (int i = 1; i <= 10; i++) {
							to_client.println(i);
						}
					}

					else {
						String msg = cmd;
						Server.messageAllClients(name + ": " + msg);
					}

				} catch (IOException e) {
					System.out.println(name + " DISCONNECTED");
					name = null;
					break;

				}
			}

		};

		new Thread(output_r).start();

	}

	public void printMessage(String msg) {
		to_client.println(msg);
	}

	public String getName() {
		return this.name;
	}

}
