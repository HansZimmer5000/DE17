package Client;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import java.net.*;
import java.io.*;

public class NetDrawClient extends JComponent{
    private JFrame frame;
    public static JTextArea msgTextArea = new JTextArea();
    private JScrollPane graphicsScrollPane = new JScrollPane();
    private JScrollPane msgScrollPane = new JScrollPane();
    private NetDrawImage netDrawImage = null;
    private NetDrawImage image;
    private JMenuItem menuItem1;
    private JMenuItem menuItem2;
    private JMenuItem menuItem3;
    private JComboBox lineStyle = new JComboBox();
    private JComboBox lineColor = new JComboBox();
    private JComboBox lineThickness = new JComboBox();

    private Socket socket = null;
    private BufferedReader breader = null;
    private BufferedWriter bwriter = null;
    private int port=0;
    private String userName = null;
    private int width = 1000;
    private int height = 800;
    private int graphicsWidth = 800;
    private int graphicsHeight = 500;
    
    public NetDrawClient() {
        frame = new JFrame("Client paint");
        // Setup menus        
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu menu1 = new JMenu("Server");
        menu1.setMnemonic(KeyEvent.VK_F);
        menu1.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");
        menuBar.add(menu1);

        JMenuItem menuItem1 = new JMenuItem("Connect", KeyEvent.VK_C);
        menuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
        menuItem1.getAccessibleContext().setAccessibleDescription("Connect to a JNetDraw Server");
        menu1.add(menuItem1);

        JMenuItem menuItem2 = new JMenuItem("Disconnect", KeyEvent.VK_D);
        menuItem2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.ALT_MASK));
        menuItem2.getAccessibleContext().setAccessibleDescription("Disconnect from the JNetDraw Server");
        menu1.add(menuItem2);

        JMenuItem menuItem3 = new JMenuItem("Exit", KeyEvent.VK_X);
        menuItem3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.ALT_MASK));
        menuItem3.getAccessibleContext().setAccessibleDescription("Exit the JNetDraw Client");
        menu1.add(menuItem3);
        
        JMenu menu2 = new JMenu("Drawing");
        menu2.setMnemonic(KeyEvent.VK_R);
        menu2.getAccessibleContext().setAccessibleDescription("Drawing options");
        menuBar.add(menu2);
        
        JMenuItem menuItem1_1 = new JMenuItem("Clear", KeyEvent.VK_L);
        menuItem1_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
        menuItem1_1.getAccessibleContext().setAccessibleDescription("Clear the drawing window for all users");
        menu2.add(menuItem1_1);
        
        lineStyle.setRenderer(new ComboBoxCellRenderer(lineStyle));
        lineStyle.addItem(new ComboBoxItem(ImageStore.getImage(0), "Freehand"));
        lineStyle.addItem(new ComboBoxItem(ImageStore.getImage(1), "Line"));
        lineStyle.addItem(new ComboBoxItem(ImageStore.getImage(2), "Box"));
        lineStyle.addItem(new ComboBoxItem(ImageStore.getImage(3), "Oval"));
        lineStyle.addItem(new ComboBoxItem(ImageStore.getImage(4), "Text"));
        lineStyle.addItem(new ComboBoxItem(ImageStore.getImage(5), "Pseudo-UML"));
        lineStyle.addItem(new ComboBoxItem(ImageStore.getImage(6), "Clear Area"));
        
        lineColor.setRenderer(new ComboBoxCellRenderer(lineColor));
        lineColor.addItem(ImageStore.getColorItem("#000000"));
        lineColor.addItem(ImageStore.getColorItem("#999999"));
        lineColor.addItem(ImageStore.getColorItem("#ffffff"));

        lineColor.addItem(ImageStore.getColorItem("#990000"));
        lineColor.addItem(ImageStore.getColorItem("#009900"));
        lineColor.addItem(ImageStore.getColorItem("#000099"));
        lineColor.addItem(ImageStore.getColorItem("#999900"));
        lineColor.addItem(ImageStore.getColorItem("#990099"));
        lineColor.addItem(ImageStore.getColorItem("#009999"));

        lineColor.addItem(ImageStore.getColorItem("#ff0000"));
        lineColor.addItem(ImageStore.getColorItem("#00ff00"));
        lineColor.addItem(ImageStore.getColorItem("#0000ff"));
        lineColor.addItem(ImageStore.getColorItem("#ffff00"));
        lineColor.addItem(ImageStore.getColorItem("#ff00ff"));
        lineColor.addItem(ImageStore.getColorItem("#00ffff"));

        lineThickness.setRenderer(new ComboBoxCellRenderer(lineThickness));
        lineThickness.addItem(ImageStore.getLineThicknessItem("1"));
        lineThickness.addItem(ImageStore.getLineThicknessItem("2"));
        lineThickness.addItem(ImageStore.getLineThicknessItem("4"));
        lineThickness.addItem(ImageStore.getLineThicknessItem("8"));

        lineStyle.setSelectedIndex(0);
        lineColor.setSelectedIndex(5);
        lineThickness.setSelectedIndex(1);

        msgScrollPane.getViewport().setView(msgTextArea);
        msgTextArea.setEditable(false);
        msgTextArea.setLineWrap(true);

        image = new NetDrawImage(bwriter, graphicsWidth, graphicsHeight, lineStyle, lineColor, lineThickness);
        graphicsScrollPane.getViewport().setView(image);
        graphicsScrollPane.setPreferredSize(new Dimension(800, 500));
        image.setEnabled(false);

        try {
            while (port == 0) {
            	this.port = Integer.parseInt(JOptionPane.showInputDialog(null, "Please enter port number:", "Client paint", JOptionPane.QUESTION_MESSAGE).trim());
            }
            while (userName == null || userName.trim().equals("")) {
                userName = JOptionPane.showInputDialog(null, "Please enter your user name:", "Client paint", JOptionPane.QUESTION_MESSAGE).trim();
            }
        }
        catch (Exception e) {
            System.exit(0);
        }

        JPanel graphicsToolBarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        graphicsToolBarPanel.add(lineStyle);
        graphicsToolBarPanel.add(lineColor);
        JToolBar graphicsToolBar = new JToolBar();
        graphicsToolBar.add(graphicsToolBarPanel);

        JPanel graphicsOptions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        graphicsOptions.add(graphicsToolBar);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, graphicsScrollPane, msgScrollPane);
        splitPane.setOneTouchExpandable(true);

        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());
        pane.add(graphicsOptions, BorderLayout.NORTH);
        pane.add(splitPane, BorderLayout.CENTER);
        
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (socket != null) {
                    try {
                        bwriter.write("quit");
                        bwriter.newLine();
                        bwriter.flush();
                        socket.close();
                    }
                    catch (IOException ie) {
                        // Do nothing.
                    }
                    socket = null;
                }
                System.exit(0);
            }
        });
        
        menuItem1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String serverHostname = JOptionPane.showInputDialog("Server address:");
                if (serverHostname == null) {
                    return;
                }
                try {
                    socket = new Socket(serverHostname, port);
                    breader = new BufferedReader (new InputStreamReader(socket.getInputStream()));
                    bwriter = new BufferedWriter (new OutputStreamWriter(socket.getOutputStream()));
                    bwriter.write("join " + userName);
                    bwriter.newLine();
                    bwriter.flush();
                    image.setBufferedWriter(bwriter);
                    InputThread inputThread = new InputThread(breader, msgTextArea, msgScrollPane, image);
                    inputThread.start();
                    image.setEnabled(true);
                    image.clearGraphics();
                }
                catch (IOException ie) {
                    JOptionPane.showMessageDialog(null, "Could not connect to " + serverHostname + ":1333", "Cannot connect", JOptionPane.ERROR_MESSAGE); 
                }
            }
        });
        menuItem2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (socket != null) {
                    try {
                        bwriter.write("quit");
                        bwriter.newLine();
                        bwriter.flush();
                        socket.close();
                    }
                    catch (IOException ie) {
                        // Do nothing.
                    }
                    socket = null;
                }
            }
        });
        menuItem3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (socket != null) {
                    try {
                        bwriter.write("quit");
                        bwriter.newLine();
                        bwriter.flush();
                        socket.close();
                    }
                    catch (IOException ie) {
                        // Do nothing.
                    }
                    socket = null;
                }
                System.exit(0);
            }
        });
        menuItem1_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    bwriter.write("clear " + userName);
                    bwriter.newLine();
                    bwriter.flush();
                }
                catch (Exception ie) {
                    
                }
                if (image != null ) {
                    image.clearGraphics();
                }
            }
        });
        
        frame.setBounds(200,100,width, height);        
        SwingUtilities.updateComponentTreeUI(this);
        frame.setVisible(true);
        
    }
}