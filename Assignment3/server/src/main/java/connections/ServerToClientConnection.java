package connections;

import commands.Command;
import commands.CommandFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ServerToClientConnection extends Thread {
	private Socket             connection;
	private int                ID;
	private ObjectOutputStream outStream;

	public ServerToClientConnection(Socket connection, int iD) {
		super();
		this.connection = connection;
		ID = iD;
	}

	@Override
	public void run() {
		try {
			ObjectInputStream inStream = new ObjectInputStream(connection.getInputStream());
			outStream = new ObjectOutputStream(connection.getOutputStream());
			while (true) {
				String received = (String) inStream.readObject();
				String[] args = received.split("\n");
				Command command = CommandFactory.getCommand(args);
				if (command != null) {
					outStream.writeObject(command.execute());
				} else {
					outStream.writeObject(null);
				}

			}
		} catch (SocketException e) {
			System.out.println("Client " + ID + " lost");
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	public Socket getConnection() {
		return connection;
	}

	public void setConnection(Socket connection) {
		this.connection = connection;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public void sendObj(Object msg) throws IOException {
		if (outStream != null)
			outStream.writeObject(msg);
	}
}
