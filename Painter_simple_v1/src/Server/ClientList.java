package Server;

import java.util.*;

import javax.swing.JTextArea;

public class ClientList {
	ServerUI sv;
	
    
    public ClientList() {
    }
    
    public synchronized void add(ClientHandler client) {
        synchronized (list) {
            list.add(client);
        }
        sv.msgTextArea.append("* " + client.getClientName() + " just joined the server.\n");
        ClientOutputThread clientOutputThread = new ClientOutputThread(this, "alert " + client.getClientName() + " just joined the server.\n");
        clientOutputThread.start();
    }
    
    public synchronized void delete(ClientHandler client) {
        client.killThreads();
        sv.msgTextArea.append("* " + client.getClientName() + " just left the server.\n");
        synchronized (list) {
            list.remove(client);
        }
        ClientOutputThread clientOutputThread = new ClientOutputThread(this, "alert " + client.getClientName() + " just left the server.\n");
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
    	sv.msgTextArea.append("Connected clients: ");
        Iterator clientIt = list.iterator();
        while (clientIt.hasNext()) {
        	sv.msgTextArea.append(" " + ((ClientHandler)clientIt.next()).getClientName());
        }
        sv.msgTextArea.append("\n");
    }
    
    private List list = Collections.synchronizedList(new LinkedList());
    
}