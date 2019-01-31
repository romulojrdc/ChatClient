import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

public class ClientThread implements Runnable {

	private BufferedReader reader;
	private Socket socket;
	private Client client;
	private PrintWriter writer;
	
    private final LinkedList<String> messagesToSend;
    private boolean hasMessages = false;
    
	public ClientThread(Socket socket, Client client) {
		this.socket = socket;
		this.client = client;
		messagesToSend = new LinkedList<String>();
		 
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException ex) {
			System.out.println("Error getting input stream: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
    public void addNextMessage(String message){
        synchronized (messagesToSend){
            hasMessages = true;
            messagesToSend.push(message);
        }
    }

	public void run() {
		try {
			PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), false);
			InputStream serverInStream = socket.getInputStream();
			Scanner serverIn = new Scanner(serverInStream);

			while (!socket.isClosed()) {
				if (serverInStream.available() > 0) {
					if (serverIn.hasNextLine()) {
						String initialMsg = serverIn.nextLine();
						if (initialMsg.contains("HELLO::")) {
							
						}
						System.out.println(initialMsg);
					}
				}
				if (hasMessages) {
					String nextSend = "";
					synchronized (messagesToSend) {
						nextSend = messagesToSend.pop();
						hasMessages = !messagesToSend.isEmpty();
					}
					serverOut.println(nextSend);
					serverOut.flush();
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}
}
