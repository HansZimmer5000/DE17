package Server;

import java.net.*;

import javax.swing.*;

import java.awt.*;
import java.io.*;

public class NetDrawServer extends Thread{
	private JTextArea msgTextArea;
	private int portNum;
	public NetDrawServer(JTextArea textArea, int portNum){
		this.msgTextArea = textArea;
		this.portNum=portNum;
	}
    
    public void run() {
        ClientList clientList = new ClientList();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(portNum);
        }
        catch (IOException e) {
        	msgTextArea.append("Could not start the server socket.\n");
        	msgTextArea.append("There is already a service running on port " + portNum + ".\n");
        }
        
        ClientWatcherThread clientWatcherThread = new ClientWatcherThread(clientList);
        clientWatcherThread.start();
        
        msgTextArea.append("Server is running on port " + portNum + ".\n");
        
        while (running) {
            try {
                Socket socket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(socket, clientList);
                handler.start();
            }
            catch (IOException e) {           	
            }
            catch (Exception e) {            	
            }
        }
    }

    private boolean running = true;
}