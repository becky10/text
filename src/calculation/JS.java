package calculation;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;


    class Calcutask extends JFrame implements ActionListener,MouseListener{
    	/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private double n=0;
    	private double m1=0,m2=0;
    	private int flag=0;
    	
    	JTextField t1;  //单行文本框
    	JLabel l1;      //可显示图像文本或者同时显示二者  可设置水平垂直的对齐方式
    	JButton b[]= new JButton[16];    //创建按钮数组
    	String s[]={"0","1","2","3","4","5","6","7","8","9","+","-","*","/","C","="};
    	
    	Calcutask(){
    	     	setSize(400,300);   //设置窗口的大小
    	     	  //JFrame有一个content pane  窗口可以显示的所有组件都是显示在这个内容主板上
    	     	Container c=getContentPane();   //获取c的内容面板
    	     	c.setLayout(new GridLayout(5,4,10,10));   //设置为网络布局  五行四列（行列数以及组件之间的水平垂直间距）
    	     	l1=new JLabel("My calculation");  //标签组件 显示计算器的名字
    	     	c.add(l1);
    	     	c.add(new JLabel());  //添加空标签对象
    	     	c.add(new JLabel());
    	     	t1=new JTextField("0");   //文本框组件 显示运算数据
    	     	c.add(t1);
    	     	
    	     	int i;
    	     	for(i=0;i<16;i++) {   //创建16个按钮组件
    	     		b[i]=new JButton(s[i]);
    	     		c.add(b[i]);     //将每个按钮添加到内容
    	     		b[i].addActionListener(this);  //注册事件监听器件对象
    	     		
    	     	}
    	     	
    	     	setVisible(true);   //设置可见性
    	     	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //窗口关闭时退出程序    	
    	}
    	
    	public void mousePressed(MouseEvent e) {}  //以下是实现接口类的几个抽象方法
    	public void mouseReleased(MouseEvent e) {}
    	public void mouseEntered(MouseEvent e) {}
    	public void mouseExited(MouseEvent e) {}
    	public void mouseClicked(MouseEvent e) {}
    	public void actionPerformed(ActionEvent e) {
    		String str=""; int i;
    		
    		for(i=0;i<=9;i++) {     //显示输入数据的过程及结果
    			if(e.getSource()==b[i]) {
    				n=n*10+i;
    				str=String.valueOf(n);
    				t1.setText(str);
    			}
    		}
    		for(i=10;i<13;i++) {
    			if(e.getSource()==b[i]) {
    				m1=Double.parseDouble(t1.getText());
    				if(flag==10)  m2=m1+m2;   //如果两次或两次以上没有点击运算符而没有按“C”
    				                          //或“=”键，则计算并保存到m2
    				else if(flag==11)   m2=m2-m1;
    				else if(flag==12)   m2=m2*m1;
    				else if(flag==13)   m2=m2/m1;
    				else m2=m1;   //如果没有连续的运算符运算，则保存当前数据到m2
    				if(i==10)  flag=10;   //记录运算符
    				else if(i==11) flag=11;
    				else if(i==12) flag=12;
    				else flag=13;
    				
    				str=String.valueOf(m2);
    				t1.setText(str);   //显示连续的运算过程
    				n=0;      //n还原为0，记录下次输入数据
    				break;   //如果找到了匹配的运算符并完成运算和记录就退出寻找过程
    				
    			}
    		if(e.getSource()==b[15]) {     //如果收到的运算符是“=”
    			m1=Double.parseDouble(t1.getText());     //将括号中的字符串转换为double类型的
    			if(flag==10)    m2=m2+m1;   //判断运算符，并作计算，显示计算结果
    			else if(flag==11)   m2=m2-m1;
    			else if(flag==12)   m2=m2*m1;
				else if(flag==13)   m2=m2/m1;
    			str=String.valueOf(m2);
				t1.setText(str);   //显示连续的运算过程
				n=0;      //n还原为0，记录下次输入数据
				flag=0;      //将flag还原为0，表明没有未处理的运算符
    		}
    		if(e.getSource()==b[14]) {    //如果事件是“C”即作清除，各变量还原为0
    			m1=0;
    			m2=0;
    			n=0;
    			flag=0;
    			t1.setText("0");
    		}
    			
    		}
    	}
    }	
    	
    	
    public class JS{
		public static void main(String[] args) {
	    	Calcutask my=new Calcutask();
	}
	}

