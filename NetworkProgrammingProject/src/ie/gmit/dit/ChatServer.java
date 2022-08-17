package ie.gmit.dit;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ChatServer {
	public static final int ServerSocket = 4444; // ServerSocket on port 4444
	private static ExecutorService pool = Executors.newFixedThreadPool(100); // Initialising client pool
	static ArrayList<Socket> clientSockets = new ArrayList<>(); // Initialising ArrayList for client sockets
	static int i = 0;

	public static void main(String[] args) {
		connectServer();
	}

	public static void connectServer() {
		try (ServerSocket server = new ServerSocket(ServerSocket)) { // server listening on port 4444
			System.out.println("▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓");
			System.out.println("           Listening for connection on port " + ServerSocket);
			System.out.println("▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓");
			while (true) {
				try {
					Socket connection = server.accept(); // accept the incoming client request
					System.out.println("   Client connected from host " + connection.getInetAddress() + ", port "
							+ connection.getPort());
					System.out.println("▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓");


					Callable<Void> task = new ServerThread(connection);
					clientSockets.add(connection);
					pool.submit(task);
					i++;
				} catch (IOException ex) {
				}
			}
		} catch (IOException ex) {
			System.err.println("Could not start Chat Server, port in use");
		}
	}

	private static class ServerThread implements Callable<Void> {
		private Socket connection;
		String received;

		// constructor that takes incoming client request
		ServerThread(Socket connection) {
			this.connection = connection;
		}

		// public void run() {
		public Void call() {
			try { // get the input stream
				DataInputStream input = new DataInputStream(connection.getInputStream());

				while (true) {
					received = input.readUTF(); // message from the ChatClient
					for (Socket skt : clientSockets) {
						// to send the message to everybody else in the ClientSocket ArrayList
						if (!skt.equals(connection)) { 
							// get the output stream to selected socket
							DataOutputStream out = new DataOutputStream(skt.getOutputStream());
							// send the received message to the other clients
							out.writeUTF(received);
							out.flush();
						}
					}
				}

			} catch (EOFException e) {
				// ignore
			} catch (IOException ex) {
				System.err.println("Socket is closed.");
			} finally {
				try {
					connection.close();
					System.out.println("        Client on port number " + connection.getPort() + " ended chat!");
					System.out.println("▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓");

				} catch (IOException ioe) {
				}
			}
			return null;
		}
	}
}
