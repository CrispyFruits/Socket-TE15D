package client_side;

import javafx.scene.input.KeyEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ConnectingClient {

	public static String HOST = "localhost";
	public static int PORT = 12345;

	private PrintStream to_server;
	private BufferedReader from_server;
	private Scanner sc;
	private String name;
	private Socket socket;
	private  String time;

	public ConnectingClient() {

		/* sc = new Scanner(System.in);
		System.out.print("Skriv in ditt namn: ");
		name = sc.nextLine(); */

		System.out.println("CONNECTING TO SERVER " + HOST + " ON PORT " + PORT);

		try {

			socket = new Socket(HOST, PORT);

			to_server = new PrintStream(socket.getOutputStream());
			from_server = new BufferedReader(new InputStreamReader(socket.getInputStream()));



		} catch (IOException e) {
			System.out.println("FEL");
			e.printStackTrace();
		}



		Runnable output_r = () -> {

			while (true) {
				try {
					String output = from_server.readLine();
					System.out.println("FROM SERVER: " + output);

					if(output.contains("TIME:")){
						setTimeText(output);
					}

					if (output.contains("TIME:0")){
						Game.disableButtons();
						to_server.println(Game.getCurrentHand());
					}

					if(output.equals("WON:")){
						System.out.println("You won!");
					}

					if(output.equals("LOST:")){
						System.out.println("You lost.");
					}

					if(output.equals("DRAW:")){
						System.out.println("It's a draw.");
					}

				} catch (IOException e) {
					System.out.println("LOST CONNECTION TO SERVER");
					System.exit(0);
				}
			}

		};

		new Thread(output_r).start();
	}

	public void readyUp(String c){

			to_server.println(c);

	}

	private void setTimeText(String time){
		Game.timeText.setText(time);
	}
}
