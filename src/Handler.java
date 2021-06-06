import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;

public class Handler implements Runnable {

    private final Socket clientSocket;

    Handler(Socket clientSocket, int timeout) throws IOException {
        this.clientSocket = clientSocket;
        this.clientSocket.setSoTimeout(timeout);
    }

    @Override
    public void run() {
        // Reads request from the client.
        String request;
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            request = br.readLine();
        } catch (IOException e) {
            System.out.println("!!! err !!! reading request !!!" + "\n");
            return;
        }

        // Request is parsed to obtain url
        // Request type and everything after the next space is removed
        String url = request.substring(0, request.indexOf(" ", request.indexOf(" ") + 1)).substring(request.indexOf(' ') + 1);

        if (request.substring(0, request.indexOf(' ')).equals("CONNECT"))
            System.out.println("New HTTPS requests -- unsupported: " + request + "\n");
        else
            try {
                System.out.println("New request: " + request + "\n");

                sendToClient(url);
            } catch (Exception e) {
                System.out.println("request: " + request + " -- unsupported" + "\n");
            }

        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void sendToClient(String url) throws IOException {
        // If data is an image (verified by URL extension) data is written to a client by BufferedImage
        String ext = url.substring(url.lastIndexOf("."));
        if ((ext.contains(".png")) || ext.contains(".jpg") ||
                ext.contains(".jpeg") || ext.contains(".gif")) {
            BufferedImage image = ImageIO.read(new URL(url));
            ImageIO.write(image, ext.substring(1), clientSocket.getOutputStream());
            image.flush();
            return;
        }

        // Retrieves URL Connection object and gets an input stream from the connection
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(clientSocket.getOutputStream()));

        for (String line; (line = reader.readLine()) != null; )
            writer.write(line);

        // Flushes the output stream forcing any buffered output bytes to be written out
        writer.flush();
        writer.close();
        // Closes the stream
        reader.close();
    }

}
