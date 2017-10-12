/* 
Copyright Paul James Mutton, 2001-2004, http://www.jibble.org/

This file is part of Jibble NetDraw (JNetDraw).

This software is dual-licensed, allowing you to choose between the GNU
General Public License (GPL) and the www.jibble.org Commercial License.
Since the GPL may be too restrictive for use in a proprietary application,
a commercial license is also provided. Full license information can be
found at http://www.jibble.org/licenses/

$Author: pjm2 $
$Id: ClientHandler.java,v 1.2 2004/02/01 13:27:08 pjm2 Exp $

*/

package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler extends Thread {
    
    public ClientHandler(Socket socket, ClientList clientList) {
        this.socket = socket;
        this.clientList = clientList;
    }
    
    public void run() {
        String firstLine = null;
        try {
            breader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            firstLine = breader.readLine();
            String[] tokens = this.splitString(firstLine);
            if (tokens[0].equals("join")) {
                name = tokens[1];
                ClientOutputThread clientOutputThread = new ClientOutputThread(clientList, firstLine);
                clientOutputThread.start();
            }
            else {
                socket.close();
                return;
            }
        
            bwriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        
            clientInputThread = new ClientInputThread(breader, clientList, this);
        
            clientList.add(this);
            clientInputThread.start();
        }
        catch (Exception e) {
            System.out.println("Bad details received during login: " + firstLine);
        }
    }
    
    public void killThreads() {
        try {
            socket.close();
        }
        catch (IOException e) {
            // Stop... carry on!
        }
    }
    
    public BufferedWriter getBufferedWriter() {
        return bwriter;
    }
    
    public String getClientName() {
        return name;
    }
    
    public static String[] splitString(String line) {
        StringTokenizer tokenizer = new StringTokenizer(line);
        String[] tokens = new String[tokenizer.countTokens()];
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = tokenizer.nextToken();
        }
        return tokens;
    }
    
    public synchronized void writeLine(String line) throws NullPointerException, IOException {
        bwriter.write(line);
        bwriter.newLine();
        bwriter.flush();
    }
    
    private Socket socket;
    private ClientList clientList;
    
    private String name = null;
    
    private ClientOutputThread clientOutputThread = null;
    private ClientInputThread clientInputThread = null;
    
    private BufferedReader breader = null;
    private BufferedWriter bwriter = null;
    
}