/**
 *
 * @author Natalia Paratsikidou
 * @author Nikolaos Roumpoutsos
 * 
 */

package server;

//import server.Textfile;
import java.net.*;
import java.io.IOException;

import java.net.Socket;

public class Server {

    static final String USAGE = "java Server [hostname] [port] ";
    static String filename = "words.txt";//consider passing it as parameter

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        int port = 2222;
        String host = "localhost";
        boolean listening = true;
        if (args.length > 1) {
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println(USAGE);
                System.exit(0);
            }
            host=args[0];
        
        if (args[0].equalsIgnoreCase("-h") || args[0].equalsIgnoreCase("-help")) {
                   System.out.println(USAGE);
                   System.exit(1);
        }

        }

        try {
            
            //create an IP address and the server's socket to this address and port 2222
            InetAddress addr = InetAddress.getByName(host);
            ServerSocket serversocket = new ServerSocket(port, 1000, addr);

            while (listening) {    // the main server's loop
                System.out.println("Listening to connections...");
                Socket clientsocket = serversocket.accept();
                //creates a thread in the connection socket that accepted the client
                Handler hangmanhandler = new Handler(clientsocket, filename);
                hangmanhandler.setPriority(hangmanhandler.getPriority() + 1);
                hangmanhandler.start();
            }
            serversocket.close(); 
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }

    }
}
