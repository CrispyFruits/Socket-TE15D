package server_side;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ServerClient {

	private PrintStream to_client;
	private BufferedReader from_client;
	private String name;
	private String hand;
	private int timeSeconds = 5;
	public ServerClient(Socket socket) {



		try {
			System.out.println("SIZE2: " + Server.clients.size());
			if(Server.clients.size() == 2){
				// REFUSE CONNECTION
				socket.close();
				return;
			}

			to_client = new PrintStream(socket.getOutputStream());
			from_client = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		} catch (IOException e) {
			e.printStackTrace();
		}

		Runnable output_r = () -> {

			while (true) {
				try {

					String cmd = from_client.readLine();
					System.out.println("Got line");
					System.out.println(name + " WROTE: " + cmd);

					if(cmd.equals("ready")){
						Server.playersReady += 1;
						System.out.println(Server.playersReady);
						if(Server.playersReady == 2){
							Server.startCountDown();
						}
					}

					if (cmd.contains("HAND:")){
						hand = cmd.substring(5);
						Server.setHands(hand);
					}


				} catch (IOException e) {
					System.out.println("DISCONNECTED");
					Server.clients.remove(this);
					break;

				}
			}

		};

		new Thread(output_r).start();

	}


	public String getName() {
		return this.name;
	}

	public void startTimer(){

		Runnable timer = new Runnable() {
			@Override
			public void run() {
				for (int i = timeSeconds ; i >= 0 ; i--){
					System.out.println("SENDING TIME TO CLIENT");
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
		System.out.println("START TIMER");
		new Thread(timer).start();

	}

	public void youWon(){
		to_client.println("WON:");
	}

	public void youLost(){
		to_client.println("LOST:");
	}

	public  void draw(){
		to_client.println("DRAW:");
	}

}
