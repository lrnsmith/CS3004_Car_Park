package org.carpark;

import java.net.Socket;

public class EntranceThread extends ServerThread {
    int queue;
    public EntranceThread(Socket socket, String threadID) {
        super(socket, threadID);
        this.queue = 0;
    }

    @Override
    public String handleRequest() throws InterruptedException {
        // Increase queue value; this will only be decreased when a car enters
        queue++;
        // Check_space; this is handled in the same request to reduce complexity,
        if (Server.getCount() >= 6) return "FAILURE: Car park full";
        // Retry loop; done using a while loop
        int retryCount = 0;
        while (Server.getLockThreadID() != null && retryCount < 10) {
            // Let the thread sleep for 0.1s
            Thread.sleep(100);
            if (Server.getLockThreadID() == null) break;
            retryCount++;
            // Break out of loop saying the lock could not be obtained
            if (retryCount >= 10) return "FAILURE: Could not obtain lock";
        }
        try {
            // Get minimum of remaining spaces and queue length at entrance
            int difference = Math.min(6-Server.getCount(), queue);
            // Engage lock and increase count; if an error is thrown immediately abort and send this as the response
            Server.AdjustCount(this.threadID, difference);
            // Shorten queue; this should decrease it by the same number as those that entered the car park
            queue -= difference;
            return "OK";
        } catch (Exception e) {
            return "ERROR";
        }
    }
}
