/* 
Copyright Paul James Mutton, 2001-2004, http://www.jibble.org/

This file is part of Jibble NetDraw (JNetDraw).

This software is dual-licensed, allowing you to choose between the GNU
General Public License (GPL) and the www.jibble.org Commercial License.
Since the GPL may be too restrictive for use in a proprietary application,
a commercial license is also provided. Full license information can be
found at http://www.jibble.org/licenses/

$Author: pjm2 $
$Id: ClientInputThread.java,v 1.2 2004/02/01 13:27:08 pjm2 Exp $

*/

package server;

import java.io.*;
import java.net.*;

public class ClientInputThread extends Thread {
    
    public ClientInputThread(BufferedReader breader, ClientList clientList, ClientHandler clientHandler) {
        this.breader = breader;
        this.clientList = clientList;
        this.clientHandler = clientHandler;
    }
    
    public void run() {
        String line = null;
        boolean running = true;
        while (running) {
            try {
                line = breader.readLine();
            }
            catch (IOException e) {
                clientList.delete(clientHandler);
                return;
            }
            if (line == null) {
                clientList.delete(clientHandler);
                return;
            }
            if (line.equals("quit")) {
                clientList.delete(clientHandler);
                return;
            }
            ClientOutputThread clientOutputThread = new ClientOutputThread(clientList, line);
            clientOutputThread.start();
        }
    }
    
    private BufferedReader breader = null;
    private ClientList clientList = null;
    private ClientHandler clientHandler = null;
    
}