import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public class Calculator extends JFrame implements ActionListener {
	JPanel panel;
	JPanel centerPanel;
    JTextField display;
    JTextArea history;

    double num1, num2;
    String operator = "";

    boolean darkMode = false;
    boolean scientific = true;

    ArrayList<JButton> sciButtons = new ArrayList<>();
    ArrayList<JButton> allButtons = new ArrayList<>();

    // LIGHT MODE 
    Color pink = new Color(255, 182, 193);        // baby pink
    Color cremeWhite = new Color(255, 248, 240);  // cream white

    //  DARK MODE 
    Color bgDark = new Color(28, 28, 28);        // smooth charcoal
    Color panelDark = new Color(40, 40, 40);     // display/history

    Color numColor = new Color(60, 60, 60);      // numbers (soft grey)
    Color funcColorDark = new Color(200, 70, 90); // functions (muted rose red)
    

    
    int xMouse, yMouse;
    
    class RoundButton extends JButton {
        public RoundButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
           
        }

        protected void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);

        
            g2.setColor(getBackground());
            g2.fillRoundRect(1, 1, getWidth()-2, getHeight()-2, 40, 40);

           
            FontMetrics fm = g2.getFontMetrics();
            Rectangle r = new Rectangle(0, 0, getWidth(), getHeight());

            int x = (r.width - fm.stringWidth(getText())) / 2;
            int y = (r.height - fm.getHeight()) / 2 + fm.getAscent();

            g2.setColor(getForeground());
            g2.setFont(getFont());
            g2.drawString(getText(), x, y);

            g2.dispose();
        }

        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.GRAY);
            g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 40, 40);
        }
    }

    public Calculator() {

        setUndecorated(true);
        setSize(360,600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5,5));
        getContentPane().setBackground(pink);

        // 🎀 TITLE BAR
        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(pink);
        titleBar.setPreferredSize(new Dimension(0,30));

        JLabel title = new JLabel("≽^• ˕ • ≼ CALCULATOR ≽^• ˕ • ≼", JLabel.CENTER);

        JButton close = new JButton("X");
        close.setBackground(pink);
        close.setFocusPainted(false);
        close.setBorder(BorderFactory.createLineBorder(Color.GRAY,1));
        close.addActionListener(e -> System.exit(0));

        titleBar.add(title, BorderLayout.CENTER);
        titleBar.add(close, BorderLayout.EAST);

        // DRAG WINDOW
        titleBar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                xMouse = e.getX();
                yMouse = e.getY();
            }
        });

        titleBar.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                setLocation(e.getXOnScreen()-xMouse, e.getYOnScreen()-yMouse);
            }
        });

        add(titleBar, BorderLayout.NORTH);

        // DISPLAY + HISTORY PANEL
        JPanel topPanel = new JPanel(new BorderLayout(5,5));
        topPanel.setBackground(pink);

        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 30));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setPreferredSize(new Dimension(0,80));
        display.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        display.setBorder(BorderFactory.createLineBorder(Color.GRAY,2));

        history = new JTextArea();
        history.setEditable(false);
        history.setFont(new Font("Arial", Font.PLAIN, 12));

        JScrollPane scroll = new JScrollPane(history);
        scroll.setPreferredSize(new Dimension(0,120));
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        topPanel.add(display, BorderLayout.NORTH);
        topPanel.add(scroll, BorderLayout.CENTER);

     // NEW CENTER PANEL 
        panel = new JPanel(new GridLayout(6,4,6,6));
        panel.setBackground(pink);
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        
        centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(pink);

        // DISPLAY 
        display.setPreferredSize(new Dimension(0, 80));
        display.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        centerPanel.add(display);

        // HISTORY 
        scroll.setPreferredSize(new Dimension(0, 120));
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        centerPanel.add(scroll);

        // BUTTON PANEL 
       
        centerPanel.add(panel);
        

        // ADD TO FRAME
        add(centerPanel, BorderLayout.CENTER);

        // BUTTON PANEL
        
        String[] btns = {
            "7","8","9","/",
            "4","5","6","*",
            "1","2","3","-",
            "0",".","=","+",
            "C","√","x²","Mode",
            "sin","cos","Dark","Exit"
        };

        for(int i=0;i<btns.length;i++){

        	JButton b = new RoundButton(btns[i]);
        	String text = btns[i];
            b.setFont(new Font("Arial",Font.BOLD,16));
            b.setFocusPainted(false);
            b.setBorder(BorderFactory.createLineBorder(new Color(200,200,200), 2));
            //  LIGHT MODE PATTERN
         // 🔢 NUMBER BUTTONS
            if(text.matches("[0-9]") || text.equals(".")) {
                b.setBackground(cremeWhite);

            } else {
                // ⚙️ ALL FUNCTIONS + OPERATORS
                b.setBackground(pink);
            }

            // BORDERS ADDED 
            b.setBorder(BorderFactory.createLineBorder(Color.GRAY,2));

            b.setOpaque(true);

            b.addActionListener(this);

            panel.add(b);
            allButtons.add(b);

            if(btns[i].equals("sin") || btns[i].equals("cos") ||
               btns[i].equals("√") || btns[i].equals("x²")){
                sciButtons.add(b);
            }
        }


        setVisible(true);
    }

    //  THEME HANDLER
    void refreshTheme(){

    	if(darkMode){

    		getContentPane().setBackground(bgDark);
    		centerPanel.setBackground(bgDark);
    		panel.setBackground(bgDark);

    		display.setBackground(panelDark);
    		history.setBackground(panelDark);

    		display.setForeground(Color.WHITE);
    		history.setForeground(Color.LIGHT_GRAY);
    	} else {

    	    getContentPane().setBackground(pink);
    	    centerPanel.setBackground(pink);
    	    panel.setBackground(pink);

    	    display.setBackground(cremeWhite);
    	    display.setForeground(Color.BLACK);
    	    
    	    history.setBackground(cremeWhite);
    	    history.setForeground(Color.BLACK);
    	}

        for(int i=0;i<allButtons.size();i++){

            JButton b = allButtons.get(i);

            //DARK MODE PATTERN 
            if(darkMode){

                String text = b.getText();

                // 🔢 NUMBER BUTTONS
                if(text.matches("[0-9]") || text.equals(".")) {
                    b.setBackground(numColor);

                } else {
                    // ⚙️ ALL FUNCTIONS + OPERATORS
                    b.setBackground(funcColorDark);
                }

                b.setForeground(Color.WHITE);
            }
            //LIGHT MODE
            else {

                String text = b.getText();

                // SPECIAL BUTTON COLORS
             // 🔢 NUMBER BUTTONS
                if(text.matches("[0-9]") || text.equals(".")) {
                    b.setBackground(cremeWhite);

                } else {
                    // ⚙️ ALL FUNCTIONS + OPERATORS
                    b.setBackground(pink);
                }

                b.setForeground(Color.BLACK);
            }
        }
    } 
    void animateThemeSwitch(Color start, Color end) {

        Timer timer = new Timer(10, null);

        final int steps = 20;
        final int[] currentStep = {0};

        timer.addActionListener(e -> {

            float ratio = (float) currentStep[0] / steps;

            int r = (int)(start.getRed() + ratio * (end.getRed() - start.getRed()));
            int g = (int)(start.getGreen() + ratio * (end.getGreen() - start.getGreen()));
            int b = (int)(start.getBlue() + ratio * (end.getBlue() - start.getBlue()));

            Color intermediate = new Color(r, g, b);

            getContentPane().setBackground(intermediate);
            centerPanel.setBackground(intermediate);
            panel.setBackground(intermediate);

            currentStep[0]++;

            if (currentStep[0] > steps) {
                timer.stop();
                refreshTheme(); 
            }
        });

        timer.start();
    }

    void calculate(){
        num2 = Double.parseDouble(display.getText());
        double res=0;

        switch(operator){
            case "+": res=num1+num2; break;
            case "-": res=num1-num2; break;
            case "*": res=num1*num2; break;
            case "/": res=num2!=0?num1/num2:0; break;
        }

        display.setText(""+res);
        history.append(num1+" "+operator+" "+num2+" = "+res+"\n");
    }

    public void actionPerformed(ActionEvent e){

        String cmd = e.getActionCommand();
        Toolkit.getDefaultToolkit().beep();

        try{
            switch(cmd){

                case "C": display.setText(""); break;

                case "+": case "-": case "*": case "/":
                    num1 = Double.parseDouble(display.getText());
                    operator = cmd;
                    display.setText("");
                    break;

                case "=": calculate(); break;

                case "√":
                    display.setText("" + Math.sqrt(Double.parseDouble(display.getText())));
                    break;

                case "x²":
                    double v = Double.parseDouble(display.getText());
                    display.setText("" + (v*v));
                    break;

                case "sin":
                    display.setText("" + Math.sin(Math.toRadians(Double.parseDouble(display.getText()))));
                    break;

                case "cos":
                    display.setText("" + Math.cos(Math.toRadians(Double.parseDouble(display.getText()))));
                    break;

                case "Mode":
                    scientific = !scientific;
                    for(JButton b:sciButtons) b.setVisible(scientific);
                    break;

                case "Dark":

                    Color start = getContentPane().getBackground();
                    Color end = darkMode ? pink : Color.BLACK;

                    darkMode = !darkMode;

                    animateThemeSwitch(start, end);

                    break;

                case "Exit": System.exit(0);

                default:
                    display.setText(display.getText()+cmd);
            }

        } catch(Exception ex){
            display.setText("Error");
        }
    }

    public static void main(String[] args){
        new Calculator();
    }
}