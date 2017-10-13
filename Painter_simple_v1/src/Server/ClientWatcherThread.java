package Server;

import java.io.*;
import java.net.*;
import java.util.*;

// This thread just pings all of the clients every 30 seconds
// so we can detect ones that have disconnected, etc.

public class ClientWatcherThread extends Thread {
    
    public ClientWatcherThread(ClientList clientList) {
        this.clientList = clientList;
    }
    
    public void run() {
        ClientHandler clientHandler = null;
        boolean running = true;
        
        while (running) {
            ClientHandler[] clients = clientList.getClients();
            for (int i = 0; i < clients.length; i++) {
                try {
                    clientHandler = clients[i];
                    BufferedWriter bwriter = clientHandler.getBufferedWriter();
                    bwriter.write("ping");
                    bwriter.newLine();
                    bwriter.flush();
                }
                catch (IOException e) {
                    clientList.delete(clientHandler);
                    continue;
                }
                catch (Exception e) {
                    continue;
                }
            }
            try {
                Thread.sleep(30000);
            }
            catch (InterruptedException e) {
                // Stop... carry on!
            }
            
            //clientList.printAllClients();
        }
    }
    
    private ClientList clientList = null;
    
}