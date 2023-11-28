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
        ServerSocket entranceSocket = new ServerSocket(1234);
        System.out.println("Entrance sockets open, awaiting connection...");
        Socket entranceSocketA = entranceSocket.accept();
        new EntranceThread(entranceSocketA, "EntA").start();
        Socket entranceSocketB = entranceSocket.accept();
        new EntranceThread(entranceSocketB, "EntB").start();


        ServerSocket exitSocket = new ServerSocket(4321);
        System.out.println("Exit sockets open, awaiting connection...");
        Socket exitSocketA = exitSocket.accept();
        new ExitThread(exitSocketA, "ExitA").start();
        Socket exitSocketB = exitSocket.accept();
        new ExitThread(exitSocketB, "ExitB").start();
        System.out.println("Initialisation complete. Awaiting requests...");
    }

    // Method for getting lock and adjusting count; one method is used for both exit and entrance for simplicity
    public static int AdjustCount(String threadID, boolean increment) throws InterruptedException {
        // Engage lock using thread ID; the ternary operator and if/return combination is used to ensure that the lock
        // is unengaged, in case this function is called when the lock is engaged through some kind of concurrency mishap
        lockThreadID = (lockThreadID != null) ? lockThreadID : threadID;
        if(!lockThreadID.equals(threadID)) return -1;
        // Queue is emptied
        EmptyQueue();
        // Count is either incremented or decremented. This could equally be done with an integer parameter but this
        // approach leaves less room for error
        count += (increment) ? 1 : -1;
        // Lock disengaged; function ends successfully
        lockThreadID = null;
        return 1;
    }
    private static void KeepCountInBounds(){
        count = Math.min(count, 6);
        count = Math.max(count, 0);
    }
    private static void EmptyQueue(){
        // Remove from queue and from count until one is empty; exitQueue should never be more than count
        while(count > 0 && exitQueue > 0) {
            exitQueue--;
            count--;
            // In case of erroneous
            exitQueue = Math.min(exitQueue, count);
        }
    }
}
