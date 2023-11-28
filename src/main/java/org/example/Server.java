package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static int count;
    public static int getCount(){
        return count;
    }
    public static int exitQueue;
    private static String lockThreadID;
    public static String getLockThreadID(){
        return lockThreadID;
    }

    public static void main(String[] args) throws IOException {
        new Server().initialise();
    }

    public void initialise() throws IOException {
        // Open socket for entrance clients to connect
        ServerSocket entranceSocket = new ServerSocket(1234);
        System.out.println("Entrance sockets open, awaiting connection...");
        // Accept connection request
        Socket entranceSocketA = entranceSocket.accept();
        // Start thread (using custom Entrance thread class) for socket connection with the ID 'EntA' for Entrance A
        new EntranceThread(entranceSocketA, "EntA").start();
        // See above
        Socket entranceSocketB = entranceSocket.accept();
        new EntranceThread(entranceSocketB, "EntB").start();

        // Identical to previous section, except socket port and thread names are different; a different thread class
        // is also used for exit functionality
        ServerSocket exitSocket = new ServerSocket(4321);
        System.out.println("Exit sockets open, awaiting connection...");
        Socket exitSocketA = exitSocket.accept();
        new ExitThread(exitSocketA, "ExitA").start();
        Socket exitSocketB = exitSocket.accept();
        new ExitThread(exitSocketB, "ExitB").start();
        // Connections finished; await requests
        System.out.println("Initialisation complete. Awaiting requests...");
    }

    // Method for getting lock and adjusting count; one method is used for both exit and entrance for simplicity
    public static void AdjustCount(String threadID, boolean increment) throws Exception {
        // Engage lock using thread ID; the ternary operator and if/return combination is used to ensure that the lock
        // is unengaged, in case this function is called when the lock is engaged through some kind of concurrency mishap
        lockThreadID = (lockThreadID != null) ? lockThreadID : threadID;
        // In case function was called erroneously, check lock and throw exception if held by other thread
        if(!lockThreadID.equals(threadID)) throw new Exception();
        // Queue is emptied
        EmptyQueue();
        // Count is either incremented or decremented. This could equally be done with an integer parameter but this
        // approach leaves less room for error
        count += (increment) ? 1 : -1;
        // Lock disengaged; function ends successfully
        lockThreadID = null;
    }

    private static void EmptyQueue(){
        // Remove from queue and from count until one is empty; exitQueue should never be more than count
        while(count > 0 && exitQueue > 0) {
            exitQueue--;
            count--;
            // In case of excessive queue levels caused by improper use of exit client programs
            exitQueue = Math.min(exitQueue, count);
        }
    }
}
