package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while((message = bufferedReader.readLine()) != null){
                String response = this.handleRequest();
                System.out.println(this.threadID+": "+message+" | RESPONSE: "+response+" | COUNT: "+Server.getCount() + " | LOCK: "+Server.getLockThreadID());
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
