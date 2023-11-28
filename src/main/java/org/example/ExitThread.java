package org.example;

import java.net.Socket;

public class ExitThread extends ServerThread{
    public ExitThread(Socket socket, String threadID) {
        super(socket, threadID);
    }

    @Override
    public String handleRequest() {
        // Abort if car park is already empty; this should only occur when the programs are used improperly,
        // e.g. in testing
        if(Server.getCount() <= 0) return "FAILURE: Car park already empty";
        // If lock is not engaged, go straight to engaging lock and decrementing count
        if(Server.getLockThreadID() == null) {
            try{
                Server.AdjustCount(this.threadID, false);
                return "OK";
            } catch (Exception e){
                return "ERROR";
            }
        }
        // If lock is engaged, add to exit queue instead
        else {
            Server.exitQueue++;
            return "OK: Car will exit when queue begins emptying";
        }
    }
}
