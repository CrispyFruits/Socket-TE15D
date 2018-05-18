package server_side;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ServerClient {

	private PrintStream to_client;
	private BufferedReader from_client;
	private String name;
	private int timeSeconds = 5;
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
					System.out.println("Waiting for line...");

					String cmd = from_client.readLine();
					System.out.println("Got line");
					System.out.println(name + " WROTE: " + cmd);

					if(cmd.equals("ready")){
						startTimer();
					}

					else if (cmd.equals("count")) {
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

	public void startTimer(){

		Runnable timer = new Runnable() {
			@Override
			public void run() {
				for (int i = timeSeconds ; i >= 0 ; i--){
					to_client.println("TIME:" + i);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
				to_client.println("timer_over");
			}
		};
		new Thread(timer).start();
		System.out.println("START TIMER");

	}

}
