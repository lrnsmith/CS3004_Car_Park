package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EntranceClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 1234);
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader responseReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        while(true){
            inputReader.readLine();
            printWriter.println("ENTRANCE");
            System.out.println(responseReader.readLine());
        }
    }
}
