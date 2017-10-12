/* 
Copyright Paul James Mutton, 2001-2004, http://www.jibble.org/

This file is part of Jibble NetDraw (JNetDraw).

This software is dual-licensed, allowing you to choose between the GNU
General Public License (GPL) and the www.jibble.org Commercial License.
Since the GPL may be too restrictive for use in a proprietary application,
a commercial license is also provided. Full license information can be
found at http://www.jibble.org/licenses/

$Author: pjm2 $
$Id: ClientList.java,v 1.2 2004/02/01 13:27:08 pjm2 Exp $

*/

package server;

import java.util.*;

public class ClientList {
    
    public ClientList() {
        
    }
    
    public synchronized void add(ClientHandler client) {
        synchronized (list) {
            list.add(client);
        }
        System.out.println("* " + client.getClientName() + " just joined the server.");
        ClientOutputThread clientOutputThread = new ClientOutputThread(this, "alert " + client.getClientName() + " just joined the server.");
        clientOutputThread.start();
    }
    
    public synchronized void delete(ClientHandler client) {
        client.killThreads();
        System.out.println("* " + client.getClientName() + " just left the server.");
        synchronized (list) {
            list.remove(client);
        }
        ClientOutputThread clientOutputThread = new ClientOutputThread(this, "alert " + client.getClientName() + " just left the server.");
        clientOutputThread.start();
    }
    
    public ClientHandler[] getClients() {
        ClientHandler[] clients = new ClientHandler[0];
        synchronized (list) {
            clients = new ClientHandler[list.size()];
            Iterator it = list.iterator();
            for (int i = 0; i < list.size(); i++) {
                clients[i] = (ClientHandler)it.next();
            }
        }
        return clients;
    }
    
    // Non thread-safe and for development purposes only!
    public void printAllClients() {
        System.out.println("Connected clients: ");
        Iterator clientIt = list.iterator();
        while (clientIt.hasNext()) {
            System.out.print(" " + ((ClientHandler)clientIt.next()).getClientName());
        }
        System.out.println();
    }
    
    private List list = Collections.synchronizedList(new LinkedList());
    
}