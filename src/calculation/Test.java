package calculation;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Test extends JPanel{
    static final long serialVersionUID=1L;
    public Test() {
    	this.setLayout(new GridBagLayout());
    	this.setOpaque(true); //设置为true时，原样显示组件中的每个像素
    	GridBagConstraints c=new GridBagConstraints();
    	JButton b=new JButton("One");
    	c.gridx = 0;
    	c.gridy = 0;
    	c.gridwidth = 2;
    	c.gridheight = 1;
    	this.add(b,c);
    	c.gridy++;
    	b = new JButton("Two");
    	this.add(b,c);
    	c.gridx = 0;
    	c.gridy = 0;
    	c.gridwidth = 2;
    	c.gridheight = 2;
    	b = new JButton("Three");
    	this.add(b,c);
    	c.gridx = 0;
    	c.gridy = 2;
    	c.gridwidth = 4;
    	c.gridheight = 1;
    	this.add(new JTextField(35),c);	
    }
    
    public static void main(String [] args) {
    	JFrame f = new JFrame("GridBagLayout 2");
    	JPanel p = new Test();
    	f.getContentPane().add(p);
    	f.pack();
    	f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    	f.setVisible(true);
    }
    
}
