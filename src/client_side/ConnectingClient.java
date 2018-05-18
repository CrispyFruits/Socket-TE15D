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

			to_server.println(name);


		} catch (IOException e) {
			e.printStackTrace();
		}

		Runnable input_r = () -> {

			while (true) {
				try {
					if(from_server.readLine().contains("TIME:")){
                        setTimeText(from_server.readLine());
                    }
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		};

		new Thread(input_r).start();

		Runnable output_r = () -> {

			while (true) {
				try {
					System.out.println(from_server.readLine());
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

	public String getTime() {
		return time;
	}

	private void setTimeText(String time){
		Game.timeText.setText(time);
	}
}
