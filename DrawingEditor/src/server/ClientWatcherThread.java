/* 
Copyright Paul James Mutton, 2001-2004, http://www.jibble.org/

This file is part of Jibble NetDraw (JNetDraw).

This software is dual-licensed, allowing you to choose between the GNU
General Public License (GPL) and the www.jibble.org Commercial License.
Since the GPL may be too restrictive for use in a proprietary application,
a commercial license is also provided. Full license information can be
found at http://www.jibble.org/licenses/

$Author: pjm2 $
$Id: ClientWatcherThread.java,v 1.2 2004/02/01 13:27:08 pjm2 Exp $

*/

package server;

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