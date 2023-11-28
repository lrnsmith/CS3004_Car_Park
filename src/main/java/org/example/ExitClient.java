package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ExitClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 4321);
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        while(true){
            inputReader.readLine();
            printWriter.println("EXIT");
        }
    }
}
