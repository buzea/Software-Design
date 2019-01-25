package connections;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MainServer extends Thread {
	public static final int                                        portNumber = 9990;
	private static      MainServer                                 instance;
	public              List<connections.ServerToClientConnection> connections;

	private MainServer() {
		super();
		connections = new ArrayList<connections.ServerToClientConnection>();
	}

	public static MainServer getInstance() {
		if (instance == null) {
			instance = new MainServer();
		}
		return instance;
	}

	public static void main(String[] args) {
		MainServer mainServer = MainServer.getInstance();
		mainServer.start();
	}

	public void run() {
		int count = 0;
		System.out.println("Starting the multiple socket server at port: " + portNumber);
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(portNumber);
			System.out.println("Multiple Socket Server Initialized");

			while (true) {
				Socket client = serverSocket.accept();
				connections.ServerToClientConnection thread = new connections.ServerToClientConnection(client, ++count);
				connections.add(thread);
				thread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (serverSocket != null)
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

	}

	public List<connections.ServerToClientConnection> getConnections() {
		return connections;
	}

}
