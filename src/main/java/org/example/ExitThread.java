package org.example;

import java.net.Socket;

public class ExitThread extends ServerThread{
    public ExitThread(Socket socket, String threadID) {
        super(socket, threadID);
    }

    @Override
    public String handleRequest() throws InterruptedException {
        if(Server.getCount() <= 0) return "FAILURE";
        if(Server.getLockThreadID() == null) {
            switch(Server.AdjustCount(this.threadID, false)){
                case 1:
                    return "OK";
                default:
                    return "ERROR";
            }

        }
        else {
            Server.exitQueue++;
            return "QUEUED";
        }
    }
}
