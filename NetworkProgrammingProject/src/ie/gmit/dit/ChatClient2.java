package ie.gmit.dit;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChatClient2 {
	private static DataOutputStream output = null;
	private static DataInputStream input = null;
	private static Socket socket = null;

	public static void main(String[] args) throws IOException {
		connectToServer();
		sendAndReceive();
	}

	// method that connects to the server on port 4444
	public static void connectToServer() {
		final int SERVER_PORT = 4444;

		try {
			InetAddress ip = InetAddress.getByName("localhost"); // getting localhost ip
			socket = new Socket(ip, SERVER_PORT); // establish the connection to the server
			// lets user know connection created
			System.out.println("▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓");
			System.out.println("    Connected to ChatServer on IP " + ip);
			System.out.println("▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓");
			System.out.println("Your message:                          Reply:");
			output = new DataOutputStream(socket.getOutputStream()); // getting input stream
			input = new DataInputStream(socket.getInputStream()); // getting output stream
		} catch (SocketException se) { // in the event the server is stopped
			System.err.println("Chat Server disconnected, cannot start chat.");
		} catch (EOFException eofe) {
			// ignore
		} catch (UnknownHostException uhe) {
			System.err.println("Can't connect to server");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public static void sendAndReceive() throws IOException {
		Scanner scn = new Scanner(System.in); // try with resources

		// send thread
		Thread send = new Thread(new Runnable() {
			public void run() {
				while (true) {
					// read the message to deliver.
					String message = scn.nextLine(); // allows user to enter message
					if (message.equals("\\q")) { // if the user enters \q, exit chat
						System.out.println("Exiting Chat...");
						break; // to exit the while true infinite loop
					}
					try {
						// write on the output stream
						output.writeUTF(message);
					} catch (IOException e) {
						System.err.println("A client or server has closed the connection");
					}
				}
				// closing the connection to the server
				try {
					scn.close();
					socket.close();
					output.close();
					
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
				System.out.println("Chat ended!"); // message to user after chat has ended
			}
		});

		// receive thread
		Thread receive = new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						// reads in the received message
						String msg = input.readUTF();
						System.out.println("                                       " + msg);
						// catching the IOException that occurs when outputMessage socket is close
					} catch (IOException ioe) {
						break;
						// catching the NullPointerException that occurs if the ChatServer isn't listening.
					} catch (NullPointerException ioe) { 
						break;
					} 
				}
			}
		});
		send.start(); // start the send thread
		receive.start(); // start the receive thread
	}
}
