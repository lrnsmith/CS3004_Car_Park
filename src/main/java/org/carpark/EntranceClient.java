package org.carpark;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EntranceClient {
    public static void main(String[] args) throws IOException {
        // Connect to entrance socket on server
        Socket socket = new Socket("localhost", 1234);
        // Used to write to socket connection (make requests)
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        // Used to accept keyboard inputs
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        // Used to read socket connection (receive responses)
        BufferedReader responseReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        // Program loop
        while(true){
            // Wait for keyboard input
            inputReader.readLine();
            // Send request
            printWriter.println("ENTRANCE");
            // Print response
            System.out.println(responseReader.readLine());
        }
    }
}
