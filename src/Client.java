import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) throws Exception {
		
		Client client = new Client();
        client.startClient(PropsConstant.server, PropsConstant.port);
    }

	private void startClient(String server, int port) {
		try {
			Socket socket = new Socket(server, port);
			Thread.sleep(500); // waiting for network communicating.

			ClientThread serverThread = new ClientThread(socket, this);
			Thread serverAccessThread = new Thread(serverThread);
			serverAccessThread.start();
		    Scanner scanner = new Scanner(System.in);
			while (serverAccessThread.isAlive()) {
				if (scanner.hasNextLine()) {
					serverThread.addNextMessage(scanner.nextLine());
				}
			}
		} catch (IOException ex) {
			System.err.println("Fatal Connection error!");
			ex.printStackTrace();
		} catch (InterruptedException ex) {
			System.out.println("Interrupted");
		}
	}
}
