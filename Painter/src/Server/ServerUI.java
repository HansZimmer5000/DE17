package Server;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

public class ServerUI extends Thread{
	private int portNum=0;
	private JFrame frame;
	public static JTextArea msgTextArea = new JTextArea();
    private JScrollPane msgScrollPane = new JScrollPane();
    private int width = 600;
    private int height = 500;
    
    public void run(){
    	frame = new JFrame("Server paint");
    	frame.setBounds(600, 300, width, height);
        frame.setLayout(new GridLayout());
        //msgTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        msgScrollPane.getViewport().setView(msgTextArea);
        msgTextArea.setEditable(false);
        msgTextArea.setLineWrap(true);
        frame.add(msgScrollPane);
    	try {
            while (portNum == 0) {
                this.portNum = Integer.parseInt(JOptionPane.showInputDialog(null, "Please enter port number:", "Server paint", JOptionPane.QUESTION_MESSAGE).trim());
            }
        }
        catch (Exception e) {
            System.exit(0);
        }
    	frame.setResizable(false);
    	frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        NetDrawServer nd = new NetDrawServer(msgTextArea, portNum);
        nd.start();    
    }
    
    public JTextArea getTextArea(){
    	return msgTextArea;
    }
}
