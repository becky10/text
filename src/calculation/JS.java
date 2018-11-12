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
    	
    	JTextField t1;  //�����ı���
    	JLabel l1;      //����ʾͼ���ı�����ͬʱ��ʾ����  ������ˮƽ��ֱ�Ķ��뷽ʽ
    	JButton b[]= new JButton[16];    //������ť����
    	String s[]={"0","1","2","3","4","5","6","7","8","9","+","-","*","/","C","="};
    	
    	Calcutask(){
    	     	setSize(400,300);   //���ô��ڵĴ�С
    	     	  //JFrame��һ��content pane  ���ڿ�����ʾ���������������ʾ���������������
    	     	Container c=getContentPane();   //��ȡc���������
    	     	c.setLayout(new GridLayout(5,4,10,10));   //����Ϊ���粼��  �������У��������Լ����֮���ˮƽ��ֱ��ࣩ
    	     	l1=new JLabel("My calculation");  //��ǩ��� ��ʾ������������
    	     	c.add(l1);
    	     	c.add(new JLabel());  //��ӿձ�ǩ����
    	     	c.add(new JLabel());
    	     	t1=new JTextField("0");   //�ı������ ��ʾ��������
    	     	c.add(t1);
    	     	
    	     	int i;
    	     	for(i=0;i<16;i++) {   //����16����ť���
    	     		b[i]=new JButton(s[i]);
    	     		c.add(b[i]);     //��ÿ����ť��ӵ�����
    	     		b[i].addActionListener(this);  //ע���¼�������������
    	     		
    	     	}
    	     	
    	     	setVisible(true);   //���ÿɼ���
    	     	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //���ڹر�ʱ�˳�����    	
    	}
    	
    	public void mousePressed(MouseEvent e) {}  //������ʵ�ֽӿ���ļ������󷽷�
    	public void mouseReleased(MouseEvent e) {}
    	public void mouseEntered(MouseEvent e) {}
    	public void mouseExited(MouseEvent e) {}
    	public void mouseClicked(MouseEvent e) {}
    	public void actionPerformed(ActionEvent e) {
    		String str=""; int i;
    		
    		for(i=0;i<=9;i++) {     //��ʾ�������ݵĹ��̼����
    			if(e.getSource()==b[i]) {
    				n=n*10+i;
    				str=String.valueOf(n);
    				t1.setText(str);
    			}
    		}
    		for(i=10;i<13;i++) {
    			if(e.getSource()==b[i]) {
    				m1=Double.parseDouble(t1.getText());
    				if(flag==10)  m2=m1+m2;   //������λ���������û�е���������û�а���C��
    				                          //��=����������㲢���浽m2
    				else if(flag==11)   m2=m2-m1;
    				else if(flag==12)   m2=m2*m1;
    				else if(flag==13)   m2=m2/m1;
    				else m2=m1;   //���û����������������㣬�򱣴浱ǰ���ݵ�m2
    				if(i==10)  flag=10;   //��¼�����
    				else if(i==11) flag=11;
    				else if(i==12) flag=12;
    				else flag=13;
    				
    				str=String.valueOf(m2);
    				t1.setText(str);   //��ʾ�������������
    				n=0;      //n��ԭΪ0����¼�´���������
    				break;   //����ҵ���ƥ�����������������ͼ�¼���˳�Ѱ�ҹ���
    				
    			}
    		if(e.getSource()==b[15]) {     //����յ���������ǡ�=��
    			m1=Double.parseDouble(t1.getText());     //�������е��ַ���ת��Ϊdouble���͵�
    			if(flag==10)    m2=m2+m1;   //�ж���������������㣬��ʾ������
    			else if(flag==11)   m2=m2-m1;
    			else if(flag==12)   m2=m2*m1;
				else if(flag==13)   m2=m2/m1;
    			str=String.valueOf(m2);
				t1.setText(str);   //��ʾ�������������
				n=0;      //n��ԭΪ0����¼�´���������
				flag=0;      //��flag��ԭΪ0������û��δ����������
    		}
    		if(e.getSource()==b[14]) {    //����¼��ǡ�C�������������������ԭΪ0
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

