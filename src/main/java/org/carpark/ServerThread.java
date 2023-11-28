package org.carpark;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

// This is an abstract class which two classes inherit. Their only differences are in the handleRequest function, the
// thread run() function is the same.
public abstract class ServerThread extends Thread {
    private Socket socket;
    protected String threadID;
    public ServerThread(Socket socket, String threadID){
        this.socket = socket;
        this.threadID = threadID;
    }
    public void run(){
        try{
            String message = null;
            // This will be used to send responses via the socket connection to the client
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            // Used to read requests made by the client through the socket connection
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // This loop ensures that all requests are received and processed by the thread, and sets the 'message'
            // variable to the content of the request
            while((message = bufferedReader.readLine()) != null){
                // Trigger handleRequest function, different for entrance and exit threads
                String response = this.handleRequest();
                // Print information for transparency
                System.out.println(this.threadID+": "+message+" | RESPONSE: "+response+" | COUNT: "+Server.getCount() + " | LOCK: "+Server.getLockThreadID());
                // Send response to request
                printWriter.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract String handleRequest() throws InterruptedException;
}
