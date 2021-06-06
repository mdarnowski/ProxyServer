import java.io.IOException;
import java.net.ServerSocket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Server implements Runnable{

	private ServerSocket serverSocket;
	private boolean on = true;

	// Creates new ServerSocket with the integer taken as a parameter that represents TCP port
	Server(int port) {
		new Thread(this).start();

		try {
			// ServerSocket will wait for requests to come in over the network.
			serverSocket = new ServerSocket(port);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	// Accepts new connections while old ones are being handled
	void acceptConnections()  {
		while(on){
			try {
				new Thread(new Handler(serverSocket.accept(), 10000000)).start();
			} catch (IOException e) {
				return;
			}
		}
	}

	final String menu = ("\nEnter: \n*** \"menu\" - to recreate this massage\n" +
			"*** \"time\" - to show local date and time\n" +
			"*** \"q\"    - to turn off the program\n");

	@Override
	public void run() {
		// Checks user input
		// 'menu' - show menu
		// 'time' - show time
		// 'q'    - turn off the program
		Scanner scanner = new Scanner(System.in);
		System.out.println(menu);
		label:
		while(true){
			String s = scanner.nextLine();
			switch (s) {
				case "q":
					on = false;
					System.out.println("turning off...");
					try {
						System.out.println("goodbye," + "\n" + "my user");
						serverSocket.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					break label;
				case "menu":
					System.out.println(menu);
					break;
				case "time":
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					String date = dtf.format(LocalDateTime.now());

					System.out.println(date + "\n");
					break;
			}
		}
		scanner.close();
	}
}
