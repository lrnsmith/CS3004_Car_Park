package org.example;

import java.net.Socket;

public class EntranceThread extends ServerThread {
    public EntranceThread(Socket socket, String threadID) {
        super(socket, threadID);
    }

    @Override
    public String handleRequest() throws InterruptedException {
        if (Server.getCount() >= 6) return "FAILURE";
        int retryCount = 0;
        while(retryCount < 10){
            Thread.sleep(100);
            if(Server.getLockThreadID() == null) break;
            retryCount++;
        }
        if(retryCount >= 10) return "FAILURE";
        switch (Server.AdjustCount(this.threadID, true)){
            case 1:
                return "OK";
            default:
                return "ERROR";
        }
    }
}
