import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import Client.NetDrawClient;
import Server.NetDrawServer;
import Server.ServerUI;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public class Login extends Thread{
	public static JFrame frm = null;
	
	private static JPanel button = null;
	private static JPanel radio = null;
		
	public static JButton connect = null;	
	
	private static JRadioButton serverBt = null;
	private static JRadioButton clientBt = null;
	private static ButtonGroup bGroup = null;
	
	private static boolean c1 = false;
	private static boolean c2 = false;
	
	public void run() {
		frm = new JFrame("Painter");
		frm.setBounds(600,300,300,70);
		frm.setLayout(new GridLayout());
		frm.setResizable(false);
		
		radio = new JPanel();
		serverBt = new JRadioButton("Server");
		clientBt = new JRadioButton("Client");
		bGroup = new ButtonGroup();
		bGroup.add(serverBt);
		bGroup.add(clientBt);
		radio.add(serverBt);
		radio.add(clientBt);
		
		frm.add(radio);
		
		button = new JPanel();
		connect = new JButton("Start");
		
		ActionAdapter buttonListener1 = new ActionAdapter(){//add ActionEvent to Connect button
            public void actionPerformed(ActionEvent e) {
            	c1 = clientBt.isSelected();//Client mode
        		c2 = serverBt.isSelected();//Server mode
               if (c1) {//Go Client mode
                  clientBt.setEnabled(false);
                  serverBt.setEnabled(false);
                  connect.setEnabled(false);
                  frm.dispose();
                  NetDrawClient client = new NetDrawClient();                  
                  //When you start chat, you can not change.
               }
               else if(c2){//Go Server mode
            	   clientBt.setEnabled(false);
                   serverBt.setEnabled(false);
                   connect.setEnabled(false);
                   ServerUI server = new ServerUI();
                   server.start();
                   frm.dispose();
               }
               else{  	   
               }//do nothing.       
            }
         };
 		connect.addActionListener(buttonListener1);
		button.add(connect);
		
		frm.add(button);
		
		frm.setVisible(true);
		frm.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
	class ActionAdapter implements ActionListener {
		   public void actionPerformed(ActionEvent e) {}
		}

}
