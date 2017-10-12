/* 
Copyright Paul James Mutton, 2001-2004, http://www.jibble.org/

This file is part of Jibble NetDraw (JNetDraw).

This software is dual-licensed, allowing you to choose between the GNU
General Public License (GPL) and the www.jibble.org Commercial License.
Since the GPL may be too restrictive for use in a proprietary application,
a commercial license is also provided. Full license information can be
found at http://www.jibble.org/licenses/

$Author: pjm2 $
$Id: ClientOutputThread.java,v 1.2 2004/02/01 13:27:08 pjm2 Exp $

*/

package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientOutputThread extends Thread {
    
    public ClientOutputThread(ClientList clientList, String line) {
        this.clientList = clientList;
        this.line = line;
    }
    
    public void run() {
        ClientHandler[] clients = clientList.getClients();
        for (int i = 0; i < clients.length; i++) {
            ClientHandler clientHandler = clients[i];
            try {
                clientHandler.writeLine(line);
            }
            catch (NullPointerException e) {
                continue;
            }
            catch (IOException e) {
                clientList.delete(clientHandler);
                continue;
            }
        }
    }
    
    private ClientList clientList = null;
    private String line = null;
    
}