/**
 *
 * @author Natalia Paratsikidou
 * @author Nikolaos Roumpoutsos
 * 
 */

package server;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.IOException;
import java.net.Socket;

public class Handler extends Thread implements Serializable {

    private Socket clientSocket;
    protected String chosenword;
    Server2Client clientdata = new Server2Client();//server's response to client
    protected String Filename;
    Textfile file;
    
    Handler(Socket s, String filename) {
        
            //thread constructor
            file = new Textfile();
            Filename = filename;//filename of the dictionary to  choose word from it
            this.clientSocket = s;
            //initialize Server2Client response values
            clientdata.score = 0;
            clientdata.FailAttempts = 10;
            clientdata.games = 0;
            clientdata.message = "";
            clientdata.info = "";

            //choose word from dictionary
            try {
                chosenword = file.chooseword(filename, file.countlines(filename));
            } catch (IOException ex) {
                Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Chosenword= "+chosenword);
            
            //Creates the dashed word to send to the Client
            clientdata.word = new StringBuffer(file.dashme(chosenword));
            
    }//end of constructor

    @Override
    public void run() {
        try {
            boolean running = true;
            Object clientObj;
            ObjectInputStream in = null;
            ObjectOutputStream out = null;
            
             //creates the output stream to send the object to the client
            try {
                out = new ObjectOutputStream(clientSocket.getOutputStream());
            } catch (IOException e) {
                System.out.println(e.toString());
            }
            //creates the input stream to read the object which the client sent
            try {
                in = new ObjectInputStream(clientSocket.getInputStream());
            } catch (IOException e) {
                System.out.println(e.toString());
                return;
            }
           
            while (running) {
                //read object that the client have sent
                try {
                    clientObj = in.readObject();
                    //System.out.println("Received :" + ((Client2Server) clientObj).clientword);
                } catch (ClassNotFoundException cnfe) {
                    System.out.println(cnfe.toString());
                    return;
                } catch (OptionalDataException ode) {
                    System.out.println(ode.toString());
                    return;
                } catch (IOException ioe) {
                    System.out.println(ioe.toString());
                    return;
                }
                Hangme hangme = new Hangme();//creates the hangme class that implements the hangman cases
                if (clientObj instanceof Client2Server) {
                    if (((Client2Server) clientObj).getaction() == 1) {//Send action
                        //Implement the server's response based on the client's actions on the game
                        hangme.calculate(chosenword, clientdata, (Client2Server) clientObj);
                    }
                    else if (((Client2Server) clientObj).getaction() == 2) {//New Game
                        //Initiallize failattempts and delete previously viewed messages
                        clientdata.FailAttempts = 10;
                        clientdata.message = "";
                        clientdata.info = "";
                        //Make a new choice
                        chosenword = file.chooseword(Filename, file.countlines(Filename));
                        System.out.println(chosenword);
                        clientdata.word = new StringBuffer(file.dashme(chosenword));
                    }
                    else if (((Client2Server) clientObj).getaction() == 3) {//stop the client's running thread in the server side
                        running=false;
                    }
                }
                
                //send the servers response to the client
                try {
                    out.reset();
                    out.writeObject(clientdata);
                    out.flush();
                } catch (IOException e) {
                    System.out.println(e.toString());
                }

            }
            
            //closes the streams if the server wants to stop running
            try {
                out.close();
                in.close();

            } catch (IOException ioe) {
                System.out.println(ioe.toString());
            }

            System.out.println("Connection Closed");
            clientSocket.close();
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }
}
