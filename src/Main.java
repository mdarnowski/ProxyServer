import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("welcome! \nset port value to proceed...");

        Scanner sc = new Scanner(System.in);
        String string;
        Server proxy;

        // Expects an integer between 1 and 65535
        // Input must match the patter for the program to proceed
        while (true) {
            string = sc.nextLine();
            if (string.matches("^()([1-9]|[1-5]?[0-9]{2,4}|6[1-4][0-9]{3}|65[1-4][0-9]{2}|655[1-2][0-9]|6553[1-5])$")) {
                System.out.println("port will be set to: " + string);
                proxy = new Server(Integer.parseInt(string));
                break;
            } else {
                System.out.println("unacceptable input, try again");
            }
        }
        proxy.acceptConnections();
    }
}