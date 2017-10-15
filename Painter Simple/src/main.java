import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

public class main{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Login l = new Login();
		l.start();
	}

} 
class Login extends Thread{
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
		serverBt.setSelected(true);
		clientBt = new JRadioButton("Client");
		bGroup = new ButtonGroup();
		bGroup.add(serverBt);
		bGroup.add(clientBt);
		radio.add(serverBt);
		radio.add(clientBt);
		
		frm.add(radio);
		
		button = new JPanel();
		connect = new JButton("Start");
		
		ActionAdapter buttonListener1 = new ActionAdapter(){
            public void actionPerformed(ActionEvent e) {
            	c1 = clientBt.isSelected();
        		c2 = serverBt.isSelected();
               if (c1) {
                  clientBt.setEnabled(false);
                  serverBt.setEnabled(false);
                  connect.setEnabled(false);
                  frm.dispose();
                  NetDrawClient client = new NetDrawClient();
               }
               else if(c2){
            	   clientBt.setEnabled(false);
                   serverBt.setEnabled(false);
                   connect.setEnabled(false);
                   ServerUI server = new ServerUI();
                   server.start();
                   frm.dispose();
               }
               else{  	   
               }
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
class ComboBoxCellRenderer extends JLabel implements ListCellRenderer {
    
    public ComboBoxCellRenderer(JComboBox comboBox) {
        this.comboBox = comboBox;
        setOpaque(true);
    }
    
    public Component getListCellRendererComponent(JList listbox, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if(UIManager.getLookAndFeel().getName().equals("CDE/Motif")) {
            if (index == -1) {
                setOpaque(false);
            }
            else {
                setOpaque(true);
            }
        }
        else {
            setOpaque(true);
        }

        if (value == null) {
            setText("");
            setIcon(null);
        }
        
        setIcon(((ComboBoxItem)value).getImageIcon());
        setText(value.toString());
        setBackground(UIManager.getColor("ComboBox.background"));
        setForeground(UIManager.getColor("ComboBox.foreground"));
        return this;
      }
    
    JComboBox comboBox = null;
    
}
class ComboBoxItem {
    
    public ComboBoxItem(BufferedImage image, String title) {
        this.imageIcon = new ImageIcon(image, title);
        this.title = title;
    }
    
    public String toString() {
        return title;
    }
    
    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    ImageIcon imageIcon;
    String title;
    
}
class ImageStore {
    private ImageStore() {
        
    }

    public static ComboBoxItem getColorItem(String hexColor) {
        BufferedImage image = new BufferedImage(18, 18, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D)image.getGraphics();
        g.setColor(Color.decode(hexColor));
        g.fillRect(0, 0, 17, 17);
        return new ComboBoxItem(image, hexColor);
    }
    
    public static ComboBoxItem getLineThicknessItem(String thickness) {
        BufferedImage image = new BufferedImage(18, 18, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D)image.getGraphics();
        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(0, 0, 17, 17);
        g.setColor(new Color(51, 51, 153, 255));
        g.setStroke(new BasicStroke(Float.parseFloat(thickness)));
        g.drawLine(1, 9, 16, 9);
        return new ComboBoxItem(image, thickness);
    }

    public static BufferedImage getImage(int imageNum) {
        BufferedImage image = new BufferedImage(18, 18, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D)image.getGraphics();
        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(0, 0, 17, 17);
        
        g.setColor(new Color(51, 51, 153, 255));
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        switch (imageNum) {
            case 0:
                g.drawLine(1, 1, 4, 2);
                g.drawLine(4, 2, 5, 5);
                g.drawLine(5, 5, 7, 10);
                g.drawLine(7, 10, 13, 15);
                g.drawLine(13, 15, 16, 16);
                break;
            case 1:
                g.drawLine(2, 2, 15, 15);
                break;
            case 2:
                g.drawRect(2, 2, 15, 15);
                break;
            case 3:
                g.drawOval(2, 2, 15, 15);
                break;
            case 4:
                g.drawString("Text", 0, 13);
                break;
            case 5:
                g.drawRect(3, 1, 11, 15);
                g.drawLine(3, 4, 14, 4);
                break;
            case 6:
                g.drawLine(2, 2, 15, 15);
                g.drawLine(2, 15, 15, 2);
                g.drawRect(2, 2, 14, 14);
                break;
        }
        
        return image;
    }
    
}
class InputThread extends Thread {
	private BufferedWriter bwriter = null;
	private synchronized void sendLine(String line) throws IOException {
        bwriter.write(line);
        bwriter.newLine();
        bwriter.flush();
    }
	
    public InputThread(BufferedReader breader, JTextArea textArea, JScrollPane scrollPane, NetDrawImage image) {
        this.breader = breader;
        this.textArea = textArea;
        this.scrollPane = scrollPane;
        this.image = image;
    }

    public void run() {
        boolean running = true;
        try {
            while (running) {
                String input = breader.readLine();
                if (input == null) {
                    break;
                }
                StringTokenizer tokenizer = new StringTokenizer(input);
                if (tokenizer.countTokens() < 2) {
                    continue;
                }
                String type = tokenizer.nextToken();
                if (type.equals("alert")) {
                    String newText = "*";
                    while (tokenizer.hasMoreTokens()) {
                        newText = newText + " " + tokenizer.nextToken();
                    }
                    textArea.append(newText + "\r\n");
                    textArea.setCaretPosition(textArea.getText().length());
                }
                else if (type.equals("Connected")){
                	String clist = "Connected Client :\n";
                	for(int i=3; i < input.split(" ").length; i++){
                		clist+=input.split(" ")[i]+"\n";
                	}
                	NetDrawClient.userList.setText(clist);
                }
                else if (type.equals("clear")) {
                    String by = tokenizer.nextToken();
                    textArea.append("* Drawing cleared by " + by + ".\r\n");
                    textArea.setCaretPosition(textArea.getText().length());
                    image.clearGraphics();
                }
                else if (type.equals("line")) {
                    try {
                        int x1 = Integer.parseInt(tokenizer.nextToken());
                        int y1 = Integer.parseInt(tokenizer.nextToken());
                        int x2 = Integer.parseInt(tokenizer.nextToken());
                        int y2 = Integer.parseInt(tokenizer.nextToken());
                        Color color = new Color(Integer.parseInt(tokenizer.nextToken()));
                        BasicStroke stroke = new BasicStroke(Float.parseFloat(tokenizer.nextToken()));
                        image.drawBufferedLine(x1, y1, x2, y2, color, stroke);
                    }
                    catch (Exception e) {
                    	textArea.append("May have lost a Line packet: " + input);
                    }
                }
                else if (type.equals("box")) {
                    try {
                        int x = Integer.parseInt(tokenizer.nextToken());
                        int y = Integer.parseInt(tokenizer.nextToken());
                        int width = Integer.parseInt(tokenizer.nextToken());
                        int height = Integer.parseInt(tokenizer.nextToken());
                        Color color = new Color(Integer.parseInt(tokenizer.nextToken()));
                        BasicStroke stroke = new BasicStroke(Float.parseFloat(tokenizer.nextToken()));
                        boolean filled = Boolean.valueOf(tokenizer.nextToken()).booleanValue();
                        image.drawBufferedBox(x, y, width, height, color, stroke, filled);
                    }
                    catch (Exception e) {
                    	textArea.append("May have lost a Box packet: " + input);
                    }
                    image.repaint();
                }
                else if (type.equals("oval")) {
                    try {
                        int x = Integer.parseInt(tokenizer.nextToken());
                        int y = Integer.parseInt(tokenizer.nextToken());
                        int width = Integer.parseInt(tokenizer.nextToken());
                        int height = Integer.parseInt(tokenizer.nextToken());
                        Color color = new Color(Integer.parseInt(tokenizer.nextToken()));
                        BasicStroke stroke = new BasicStroke(Float.parseFloat(tokenizer.nextToken()));
                        boolean filled = Boolean.valueOf(tokenizer.nextToken()).booleanValue();
                        image.drawBufferedOval(x, y, width, height, color, stroke, filled);
                    }
                    catch (Exception e) {
                    	textArea.append("May have lost an Oval packet: " + input);
                    }
                }
                else if (type.equals("uml")) {
                    try {
                        int x = Integer.parseInt(tokenizer.nextToken());
                        int y = Integer.parseInt(tokenizer.nextToken());
                        int width = Integer.parseInt(tokenizer.nextToken());
                        int height = Integer.parseInt(tokenizer.nextToken());
                        Color color = new Color(Integer.parseInt(tokenizer.nextToken()));
                        BasicStroke stroke = new BasicStroke(Float.parseFloat(tokenizer.nextToken()));
                        image.drawBufferedBox(x, y, width, height, color, stroke, false);
                        if (height > 20) {
                            image.drawBufferedLine(x, y+20, x+width, y+20, color, stroke);
                        }
                    }
                    catch (Exception e) {
                    	textArea.append("May have lost a UML packet: " + input);
                    }
                }
                else if (type.equals("text")) {
                    try {
                        int x = Integer.parseInt(tokenizer.nextToken());
                        int y = Integer.parseInt(tokenizer.nextToken());
                        Color color = new Color(Integer.parseInt(tokenizer.nextToken()));
                        String text = tokenizer.nextToken();
                        while (tokenizer.hasMoreTokens()) {
                            text = text + " " + tokenizer.nextToken();
                        }
                        image.drawBufferedText(x, y, color, text);
                    }
                    catch (Exception e) {
                    	textArea.append("May have lost a Text packet: " + input);
                    }
                }
                
            }
        }
        catch (IOException e) {
            image.setEnabled(false);
        }
    }

    private BufferedReader breader;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private NetDrawImage image;

}
class NetDrawClient extends JComponent{
    private JFrame frame;
    public static JTextArea msgTextArea = new JTextArea();
    public static JTextArea userList = new JTextArea();
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
    private JCheckBox filledCheckBox = new JCheckBox("filled", false);
    
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

        image = new NetDrawImage(bwriter, graphicsWidth, graphicsHeight, lineStyle, lineColor, lineThickness, filledCheckBox);
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
        graphicsToolBarPanel.add(lineThickness);
        graphicsToolBarPanel.add(filledCheckBox);
        JToolBar graphicsToolBar = new JToolBar();
        graphicsToolBar.add(graphicsToolBarPanel);

        JPanel graphicsOptions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        graphicsOptions.add(graphicsToolBar);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, graphicsScrollPane, msgScrollPane);
        splitPane.setOneTouchExpandable(true);
        
        userList.setEditable(false);
        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());
        pane.add(graphicsOptions, BorderLayout.NORTH);
        pane.add(splitPane, BorderLayout.CENTER);
        pane.add(userList,BorderLayout.EAST);
        
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
                    JOptionPane.showMessageDialog(null, "Could not connect to " + serverHostname + " : "+port, "Cannot connect", JOptionPane.ERROR_MESSAGE); 
                }
            }
        });
        
        menuItem2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (socket != null) {
                    try {
                    	userList.setText("");
                    	msgTextArea.setText("");
                    	image.clearGraphics();
                        bwriter.write("quit");
                        bwriter.newLine();
                        bwriter.flush();
                        socket.close();
                    }
                    catch (IOException ie) {
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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
class NetDrawImage extends JComponent implements MouseListener, MouseMotionListener {
    
    public NetDrawImage(BufferedWriter bwriter, int width, int height, JComboBox lineStyle, JComboBox lineColor, JComboBox lineThickness, JCheckBox filledCheckBox) {
        this.bwriter = bwriter;
        this.lineStyle = lineStyle;
        this.lineColor = lineColor;
        this.lineThickness = lineThickness;
        this.filledCheckBox = filledCheckBox;
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.setSize(width, height);
        this.setPreferredSize(new Dimension(width, height));
        this.setMinimumSize(new Dimension(width, height));
        this.setBackground(Color.white);
        this.setOpaque(true);
        startX = image.getMinX();
        startY = image.getMinY();
        endX = startX+image.getWidth();
        endY = startY+image.getHeight();
        imageGraphics = (Graphics2D)image.getGraphics();
        imageGraphics.setColor(Color.white);
        imageGraphics.fillRect(0, 0, width-1, height-1);
        
        this.updateUI();

        this.addMouseMotionListener(this);
        this.addMouseListener(this);

    }
    
    public void mouseDragged(MouseEvent e) {
        if (enabled) {
            e.consume();
            int newX = e.getX();
            int newY = e.getY();
            String style = (String)lineStyle.getSelectedItem().toString();
            
            if(newX > endX){
        		return;
        	}else if(newY > endY){
        		return;
        	}
            if (style.equals("Freehand")) {
            	
                drawBufferedLine(lastX, lastY, newX, newY, currentColor, currentStroke);
                try {
                    sendLine("line " + lastX + " " + lastY + " " + newX + " " + newY + " " + currentColor.getRGB() + " " + currentStroke.getLineWidth());
                }
                catch (IOException ie) {
                    this.setEnabled(false);
                }
                lastX = newX;
                lastY = newY;
            }
            else if (style.equals("Line")) {            	
                paintComponent(this.getGraphics());
                this.drawClientLine(lastX, lastY, newX, newY);
            }
            else if (style.equals("Box")) {
                paintComponent(this.getGraphics());
                this.drawClientBox(Math.min(lastX,newX), Math.min(lastY,newY), Math.abs(newX-lastX), Math.abs(newY-lastY), filledCheckBox.isSelected());
            }
            else if (style.equals("Oval")) {
                paintComponent(this.getGraphics());
                this.drawClientOval(Math.min(lastX,newX), Math.min(lastY,newY), Math.abs(newX-lastX), Math.abs(newY-lastY), filledCheckBox.isSelected());
            }
            else if (style.equals("Text")) {
                Graphics g = this.getGraphics();
                paintComponent(g);
                g.drawString("Text...", newX, newY);
            }
            else if (style.equals("Clear Area")) {
                paintComponent(this.getGraphics());
                currentColor = Color.yellow;
                this.drawClientBox(Math.min(lastX,newX), Math.min(lastY,newY), Math.abs(newX-lastX), Math.abs(newY-lastY), false);
            }
        }
    }
    
    public void drawBufferedLine(int x1, int y1, int x2, int y2, Color color, Stroke stroke) {
        Graphics2D componentGraphics = (Graphics2D)this.getGraphics();        
        componentGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        imageGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);        
        componentGraphics.setStroke(stroke);
        componentGraphics.setColor(color);
        componentGraphics.drawLine(x1, y1, x2, y2);
        imageGraphics.setStroke(stroke);
        imageGraphics.setColor(color);
        imageGraphics.drawLine(x1, y1, x2, y2);
    }
    
    public void drawBufferedBox(int x1, int y1, int width, int height, Color color, Stroke stroke, boolean filled) {
        Graphics2D componentGraphics = (Graphics2D)this.getGraphics();
        componentGraphics.setStroke(stroke);
        componentGraphics.setColor(color);
        imageGraphics.setStroke(stroke);
        imageGraphics.setColor(color);
        
        if (filled) {
            componentGraphics.fillRect(x1, y1, width, height);
            imageGraphics.fillRect(x1, y1, width, height);
        }
        else {
            componentGraphics.drawRect(x1, y1, width, height);
            imageGraphics.drawRect(x1, y1, width, height);
        }
    }
    
    public void drawBufferedOval(int x1, int y1, int width, int height, Color color, Stroke stroke, boolean filled) {
        Graphics2D componentGraphics = (Graphics2D)this.getGraphics();        
        componentGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        imageGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);        
        componentGraphics.setStroke(stroke);
        componentGraphics.setColor(color);
        imageGraphics.setStroke(stroke);
        imageGraphics.setColor(color);
        
        if (filled) {
            componentGraphics.fillOval(x1, y1, width, height);
            imageGraphics.fillOval(x1, y1, width, height);
        }
        else {
            componentGraphics.drawOval(x1, y1, width, height);
            imageGraphics.drawOval(x1, y1, width, height);
        }
    }
    
    public void drawBufferedText(int x, int y, Color color, String text) {
        Graphics2D componentGraphics = (Graphics2D)this.getGraphics();        
        componentGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        imageGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);        
        componentGraphics.setColor(color);
        componentGraphics.drawString(text, x, y);
        imageGraphics.setColor(color);
        imageGraphics.drawString(text, x, y);
    }
    
    public void drawClientLine(int x1, int y1, int x2, int y2) {
        Graphics2D componentGraphics = (Graphics2D)this.getGraphics();
        componentGraphics.setColor(currentColor);
        componentGraphics.setStroke(currentStroke);
        componentGraphics.drawLine(x1, y1, x2, y2);
    }
    
    public void drawClientBox(int x1, int y1, int width, int height, boolean filled) {
        Graphics2D componentGraphics = (Graphics2D)this.getGraphics();
        componentGraphics.setColor(currentColor);
        componentGraphics.setStroke(currentStroke);
        if (filled) {
            componentGraphics.fillRect(x1, y1, width, height);
        }
        else {
            componentGraphics.drawRect(x1, y1, width, height);
        }
    }
    
    public void drawClientOval(int x1, int y1, int width, int height, boolean filled) {
        Graphics2D componentGraphics = (Graphics2D)this.getGraphics();
        componentGraphics.setColor(currentColor);
        componentGraphics.setStroke(currentStroke);
        if (filled) {
            componentGraphics.fillOval(x1, y1, width, height);
        }
        else {
            componentGraphics.drawOval(x1, y1, width, height);
        }
    }
    
    public void clearGraphics() {
        imageGraphics.setColor(Color.white);
        imageGraphics.fillRect(0, 0, image.getWidth()-1, image.getWidth()-1);
        repaint();
    }
    
    public void setBufferedWriter(BufferedWriter bwriter) {
        this.bwriter = bwriter;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void mousePressed(MouseEvent e) {        
        if (enabled) {
            currentColor = Color.decode((String)lineColor.getSelectedItem().toString());
            currentStroke = new BasicStroke(Float.parseFloat((String)lineThickness.getSelectedItem().toString()));
            e.consume();
            lastX = e.getX();
            lastY = e.getY();
            String style = (String)lineStyle.getSelectedItem().toString();

        	if(lastX>endX){
        		lastX = endX;
        		return;
	        }else if(lastY>endY){
	        	lastY=endY;
        		return;
        	}
            if (style.equals("Freehand")) {

            	
                this.drawBufferedLine(lastX, lastY, lastX, lastY, currentColor, currentStroke);
                try {
                    sendLine("line " + lastX + " " + lastY + " " + lastX + " " + lastY + " " + currentColor.getRGB() + " " + currentStroke.getLineWidth());
                }
                catch (IOException ie) {
                    this.setEnabled(false);
                }
            }
            else if (style.equals("Line")) {
            }
            else if (style.equals("Box")) {
            }
            else if (style.equals("Oval")) {
            }
            else if (style.equals("Text")) {
                Graphics g = this.getGraphics();
                g.drawString("Text...", lastX, lastY);
            }
            else if (style.equals("Clear Area")) {
            }
        }
    }
    
    public void mouseReleased(MouseEvent e) {
        if (enabled) {
            e.consume();
            int newX = e.getX();
            int newY = e.getY();
            String style = (String)lineStyle.getSelectedItem().toString();	
            if(newX > endX){
        		newX = endX;
        	}else if(newY > endY){
        		newY = endY;
        	}
            if (style.equals("Freehand")) {
            }
            else if (style.equals("Line")) {
                this.drawBufferedLine(lastX, lastY, newX, newY, currentColor, currentStroke);
                try {
                    sendLine("line " + lastX + " " + lastY + " " + newX + " " + newY + " " + currentColor.getRGB() + " " + currentStroke.getLineWidth());
                }
                catch (IOException ie) {
                    this.setEnabled(false);
                }
            }
            else if (style.equals("Box")) {
            	this.drawClientBox(Math.min(lastX,newX), Math.min(lastY,newY), Math.abs(newX-lastX), Math.abs(newY-lastY), filledCheckBox.isSelected());
                try {
                    sendLine("box " + Math.min(lastX,newX) + " " + Math.min(lastY,newY) + " " + Math.abs(newX-lastX) + " " + Math.abs(newY-lastY) + " " + currentColor.getRGB() + " " + currentStroke.getLineWidth() + " " + filledCheckBox.isSelected());
                }
                catch (IOException ie) {
                    this.setEnabled(false);
                }
            }
            else if (style.equals("Oval")) {
            	this.drawClientOval(Math.min(lastX,newX), Math.min(lastY,newY), Math.abs(newX-lastX), Math.abs(newY-lastY), filledCheckBox.isSelected());
                try {
                    sendLine("oval " + Math.min(lastX,newX) + " " + Math.min(lastY,newY) + " " + Math.abs(newX-lastX) + " " + Math.abs(newY-lastY) + " " + currentColor.getRGB() + " " + currentStroke.getLineWidth() + " " + filledCheckBox.isSelected());
                }
                catch (IOException ie) {
                    this.setEnabled(false);
                }
            }
            else if (style.equals("Text")) {
                Graphics2D g = (Graphics2D)this.getGraphics();
                String text = null;
                try {
                    text = JOptionPane.showInputDialog(null, "Please enter your text to add to the drawing view:", "Drawing Text", JOptionPane.QUESTION_MESSAGE).trim();
                }
                catch (Exception any) {
                    paintComponent(g);
                    return;
                }
                if (text == null || text == "") {
                    paintComponent(g);
                    return;
                }
                paintComponent(g);
                this.drawBufferedText(newX, newY, currentColor, text);
                try {
                    sendLine("text " + newX + " " + newY + " " + currentColor.getRGB() + " " + text);
                }
                catch (IOException ie) {
                    this.setEnabled(false);
                }
            }
            else if (style.equals("Clear Area")) {
            	this.drawClientBox(Math.min(lastX,newX), Math.min(lastY,newY), Math.abs(newX-lastX), Math.abs(newY-lastY), false);
                try {
                    sendLine("box " + Math.min(lastX,newX) + " " + Math.min(lastY,newY) + " " + Math.abs(newX-lastX) + " " + Math.abs(newY-lastY) + " " + Color.white.getRGB() + " " + currentStroke.getLineWidth()+ " " + "true");
                }
                catch (IOException ie) {
                    this.setEnabled(false);
                }
                repaint();
            }
        }
    }
    public void mouseMoved(MouseEvent e) {
    }
    public void mouseEntered(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }
    public void mouseClicked(MouseEvent e) {
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(image, null, null);
    }
    
    private synchronized void sendLine(String line) throws IOException {
        bwriter.write(line);
        bwriter.newLine();
        bwriter.flush();
    }
    
    private BufferedImage image;
    private BufferedWriter bwriter = null;
    private JComboBox lineStyle = null;
    private JComboBox lineColor = null;
    private JComboBox lineThickness = null;
    private JCheckBox filledCheckBox = null;
    private boolean mouseDown = false;
    private int lastX = 0;
    private int lastY = 0;
    private int startX = 0;
    private int startY = 0;
    private int endX = 0;
    private int endY = 0;
    private Color currentColor = null;
    private BasicStroke currentStroke = null;
    private Graphics2D imageGraphics = null;
    private boolean enabled = false;
}
class ClientHandler extends Thread {
	ServerUI sv;
    
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
        	sv.msgTextArea.append("Bad details received during login: " + firstLine+"\n");
        }
    }
    
    public void killThreads() {
        try {
            socket.close();
        }
        catch (IOException e) {
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
class ClientInputThread extends Thread {
    
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
class ClientList {
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
        String clist="Connected Clients : ";
        Iterator clientIt = list.iterator();
        while (clientIt.hasNext()) {
        	clist+=((ClientHandler)clientIt.next()).getClientName()+" ";
        }
        String clist2 = "Connected Clients : \n";
        for(int i=3; i < clist.split(" ").length; i++){
        	clist2+=clist.split(" ")[i]+"\n";
        }
        sv.userList.setText(clist2);
        ClientOutputThread clientListOutputThread = new ClientOutputThread(this, clist);
        clientListOutputThread.start();
    }
    
    public synchronized void delete(ClientHandler client) {
        client.killThreads();
        sv.msgTextArea.append("* " + client.getClientName() + " just left the server.\n");
        synchronized (list) {
            list.remove(client);
        }
        ClientOutputThread clientOutputThread = new ClientOutputThread(this, "alert " + client.getClientName() + " just left the server.\n");
        clientOutputThread.start();
        String clist="Connected Clients : ";
        Iterator clientIt = list.iterator();
        while (clientIt.hasNext()) {
        	clist+=((ClientHandler)clientIt.next()).getClientName()+" ";
        }
        String clist2 = "Connected Clients : \n";
        for(int i=3; i < clist.split(" ").length; i++){
        	clist2+=clist.split(" ")[i]+"\n";
        }
        sv.userList.setText(clist2);
        ClientOutputThread clientListOutputThread = new ClientOutputThread(this, clist);
        clientListOutputThread.start();
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
class ClientOutputThread extends Thread {
    
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
class ClientWatcherThread extends Thread {
    
    public ClientWatcherThread(ClientList clientList) {
        this.clientList = clientList;
    }
    
    public void run() {
        ClientHandler clientHandler = null;
        boolean running = true;
        
        while (running) {
            ClientHandler[] clients = clientList.getClients();
            for (int i = 0; i < clients.length; i++) {
                try {
                    clientHandler = clients[i];
                    BufferedWriter bwriter = clientHandler.getBufferedWriter();
                    bwriter.write("ping");
                    bwriter.newLine();
                    bwriter.flush();
                }
                catch (IOException e) {
                    clientList.delete(clientHandler);
                    continue;
                }
                catch (Exception e) {
                    continue;
                }
            }
            try {
                Thread.sleep(30000);
            }
            catch (InterruptedException e) {
            }
        }
    }
    
    private ClientList clientList = null;
    
}
class NetDrawServer extends Thread{
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
class ServerUI extends Thread{
	private int portNum=0;
	private JFrame frame;
	public static JTextArea msgTextArea = new JTextArea();
	public static JTextArea userList = new JTextArea();
    private JScrollPane msgScrollPane = new JScrollPane();
    private int width = 600;
    private int height = 500;
    
    public void run(){
    	frame = new JFrame("Server paint");
    	frame.setBounds(600, 300, width, height);
        frame.setLayout(new GridLayout());
        msgScrollPane.getViewport().setView(msgTextArea);
        msgTextArea.setEditable(false);
        msgTextArea.setLineWrap(true);
        userList.setEditable(false);
        userList.setLineWrap(true);;
        frame.add(msgScrollPane);
        frame.add(userList);
    	try {
            while (portNum == 0) {
                this.portNum = Integer.parseInt(JOptionPane.showInputDialog(null, "Please enter port number:", "Server paint", JOptionPane.QUESTION_MESSAGE).trim());
            }
        }
        catch (Exception e) {
            System.exit(0);
        }
    	
        
        NetDrawServer nd = new NetDrawServer(msgTextArea, portNum);
        nd.start();
        frame.setResizable(false);
    	frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public JTextArea getTextArea(){
    	return msgTextArea;
    }
}
