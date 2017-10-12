/* 
Copyright Paul James Mutton, 2001-2004, http://www.jibble.org/

This file is part of Jibble NetDraw (JNetDraw).

This software is dual-licensed, allowing you to choose between the GNU
General Public License (GPL) and the www.jibble.org Commercial License.
Since the GPL may be too restrictive for use in a proprietary application,
a commercial license is also provided. Full license information can be
found at http://www.jibble.org/licenses/

$Author: pjm2 $
$Id: NetDrawServer.java,v 1.2 2004/02/01 13:27:08 pjm2 Exp $

*/

package server;

import java.net.*;
import java.io.*;

public class NetDrawServer {
    
    public NetDrawServer(int port) {
        this.port = port;
    }
    
    public void launch() {
        
        ClientList clientList = new ClientList();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        }
        catch (IOException e) {
            System.out.println("Could not start the server socket.");
            System.out.println("There is already a service running on port " + port + ".");
            System.exit(0);
        }
        
        ClientWatcherThread clientWatcherThread = new ClientWatcherThread(clientList);
        clientWatcherThread.start();
        
        System.out.println("*** Jibble NetDraw Server running on port " + port + " ***");
        
        while (running) {
            try {
                Socket socket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(socket, clientList);
                handler.start();
            }
            catch (IOException e) {
                System.out.println("Somebody jibbled up their connection when connecting.");
            }
            catch (Exception e) {
                System.out.println("Somebody tried to join the server in a jibbly way.");
            }
        }
        
    }

    private boolean running = true;
    private int port;
    
}