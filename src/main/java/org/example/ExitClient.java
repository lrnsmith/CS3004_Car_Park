package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ExitClient {
    public static void main(String[] args) throws IOException {
        // Establish socket connection
        Socket socket = new Socket("localhost", 4321);
        // Used to write to socket connection (make requests)
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        // Used to accept keyboard inputs
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        // Program loop
        while(true){
            // Wait for keyboard input
            inputReader.readLine();
            // Send request; no response is needed for exit client so this is not checked
            printWriter.println("EXIT");
        }
    }
}
