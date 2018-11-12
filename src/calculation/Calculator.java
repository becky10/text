package calculation;

import java.awt.BorderLayout;
import java.awt.Color;
//import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
//import java.awt.RenderingHints.Key;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;
 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
//import javax.swing.LayoutFocusTraversalPolicy;
 
//import org.omg.CORBA.PRIVATE_MEMBER;
 
public class Calculator extends JFrame implements ActionListener {
	static final long serialVersionUID=1L;
	private ArrayList<String> list;
	private final String[] KEYS = { "(", ")", "^", "7", "8", "9", "4", "5",
			"6", "1", "2", "3", "0", ".", "��" };

	private final String[] CLEAR = { "AC", "Backspace" };
	private final String[] SYMBOL = { "/", "*", "-", "+" ,"="};
 
	private JButton keys[] = new JButton[KEYS.length];
	private JButton clear[] = new JButton[CLEAR.length];
	private JButton symbol[] = new JButton[SYMBOL.length];
	public JTextField resultText = new JTextField("0"); //���е��ı���
 
	private boolean vbegin = true;// �������룬trueΪ�������룬falseΪ��������
	private boolean equals_flag = true;// trueΪδ����=��false��ʾ����=
	private boolean isContinueInput = true;// trueΪ��ȷ�����Լ������룬false������������
 
	final int MAXLEN = 500;
	final double  PI = 3.141592657;
	
	public Calculator() {
		super();
		init();  //��ʼ������
		this.setBackground(Color.LIGHT_GRAY);
		this.setTitle("������");
		this.setLocation(300, 300);
		this.setResizable(false); //���������޸Ĵ�С
		this.pack(); //���ݴ����в����Լ������size��ȷ��frame����Ѵ�С
	}
 
	private void init() {
		resultText.setHorizontalAlignment(JTextField.RIGHT);  //�ı��Ĳ���Ϊ����
		resultText.setEditable(false);   //falseΪֻ��ģʽ
		resultText.setBackground(Color.white);
		list = new ArrayList<String>();
		//����һ���б�list��Collection�Ľӿڣ�ArrayList��list��ʵ����
		initLayout();//�����ʼ��
		initActionEvent();//�������¼�����button��Ӧ
	}
	
	public void initLayout() {
		JPanel calckeysPanel = new JPanel();  //����һ�����
		calckeysPanel.setLayout(new GridLayout(5, 3, 3, 3));  //�������Ϊ�������
		for (int i = 0; i < KEYS.length; i++) {
			keys[i] = new JButton(KEYS[i]);
			calckeysPanel.add(keys[i]);
			keys[i].setForeground(Color.blue);  //���ð����������ɫ
		}
		for (int i = 0; i < SYMBOL.length; i++) {
			symbol[i] = new JButton(SYMBOL[i]);
			symbol[i].setForeground(Color.red);
		}
		for (int i = 0; i < CLEAR.length; i++) {
			clear[i] = new JButton(CLEAR[i]);
			clear[i].setForeground(Color.red);
		}
 
		JPanel text = new JPanel();
		text.setLayout(new BorderLayout());
		text.add("Center", resultText);
 //��addΪBorderLayout������ʱ����Ҫ����������λ�ú����
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc= new GridBagConstraints();//����һ��GridBagConstraints
		gbc.fill = GridBagConstraints.BOTH;  //��������ʣ��ռ�ʱ�����ռ�
		gbc.insets = new Insets(3, 3, 3, 3);  //���Ե��������Χ�Ĵ�С�ռ�
		
		gbc.gridx = 0;  //��������ʼ�����ĸ�λ��
		gbc.gridy = 0;
		//gbc.gridwidth = 1;   //����������ں�������ռ����ĸ���
		//gbc.gridheight = 1;
		panel1.add(symbol[0], gbc);//  ��/�š�
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		//gbc.gridwidth = 1;
		//gbc.gridheight = 1;
		panel1.add(clear[0], gbc);//  "AC"
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		//gbc.gridwidth = 1;
		//gbc.gridheight = 1;
		panel1.add(symbol[1], gbc);//"*"
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 2;
		panel1.add(clear[1], gbc);//��backspace��
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel1.add(symbol[2], gbc);//��-��
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.ipady =35;  //����ڲ����ռ䣬ipady�����������С�߶���Ӷ��ٿռ�
		panel1.add(symbol[3], gbc);//"+"
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		//gbc.ipadx = 10;
		//gbc.ipady = 33;
		panel1.add(symbol[4], gbc);//"="
		
		getContentPane().setLayout(new BorderLayout(3, 3));
		getContentPane().add("Center", calckeysPanel);
		getContentPane().add("East", panel1);
		getContentPane().add("North", text);
		//Frameֻ��һ����ܣ�������ʵ��������Ǽ���Frame�ϵ��������contentPane�ϵ�
 
	}
 
	public void initActionEvent() {   //��ÿ������������Ӷ���������
		for (int i = 0; i < KEYS.length; i++) {
			keys[i].addActionListener(this);
		}
		for (int i = 0; i < CLEAR.length; i++) {
			clear[i].addActionListener(this);
		}
		for (int i = 0; i < SYMBOL.length; i++) {
			symbol[i].addActionListener(this);
		}
	}
	
	//�齨��������ʱ����
	public void actionPerformed(ActionEvent e) {
		String label = e.getActionCommand();  //�����ڰ�ť�ϵ��ַ�����get�����Ǳ�ǩ
		if (label.equals(CLEAR[1])) {
			handleBackspace();    //�����ɾ���������
		} else if (label.equals(CLEAR[0])) {
			list.clear();
			resultText.setText("0");
			vbegin = true;  //��ʾ��������
			equals_flag = true;  //��ʾδ����"="
		} else {
			handle(label);
		}
	}
 ////
	private void handleBackspace() {
		String text = resultText.getText();
		list.add(text);   //���ı�������ӵ�list��
		int i = text.length();
		if (i > 0 && list.size() > 0) {
			text = text.substring(0, i - 1);  //���ش��ַ�����ָ�����ַ�������������i-1
			list.remove(list.size() - 1); // �Ƴ�ջ�����Ǹ�Ԫ��
			if (text.length() == 0) {
				list.clear();
				resultText.setText("0");
				vbegin = true;
				equals_flag = true;
			} else {
				resultText.setText(text);
			}
		}
	}
 
	public void handle(String key) {
		String text = resultText.getText();
		if (equals_flag == false) { //&& "��0123456789.()+-*/^".indexOf(key) != -1
			list.add(text);
			vbegin = false;  //��ʾ����������
		}
 
		if (!list.isEmpty()) {
			TipChecker(list.get(list.size() - 1), key);
		} else {
			TipChecker("#", key);
		}
		if (isContinueInput && "��0123456789.()+-*/^".indexOf(key) != -1) {
			list.add(key);
		}
         //indexOf ��������һ������ֵ��ָ�� String ���������ַ����Ŀ�ʼλ�á����û���ҵ����ַ������򷵻�-1��
		// ��������ȷ����������Ϣ��ʾ����ʾ����
		if (isContinueInput && "��0123456789.()+-*/^".indexOf(key) != -1) {
			if (equals_flag == false && ("+-*/^".indexOf(key) != -1)) {
				vbegin = false;
				equals_flag = true;
				printText(key);
			} else if (equals_flag == false
					&& ("��0123456789.()".indexOf(key) != -1)) {
				vbegin = true;
				equals_flag = true;
				printText(key);
			} else {
				printText(key);
			}
 
		} else if (isContinueInput && equals_flag && key.equals("=")) {
			isContinueInput = false;// ���������Լ�������
			equals_flag = false;// �����Ѿ�����=
			vbegin = true;// ���������־����true
			process(resultText.getText()); // ��������ĺ��ģ�������ʽ��ֵ����ʾ
			list.clear();
		}
		isContinueInput = true;
	}
 
	private void printText(String key) {
		if (vbegin) {
			resultText.setText(key);// ���������
			// firstDigit = false;
		} else {
			resultText.setText(resultText.getText() + key);
		}
		vbegin = false;
	}
 
	/*
	 * ��⺯������str����ǰ���﷨��� ΪTip����ʾ��ʽ�ṩ���ݣ���TipShow()���ʹ�� ��� �ַ� �����Ը���ĺϷ��ַ� 1 ��
	 * ����|��|-|.|���� 2 �� ���|��|  ^ 3 . ����|���|��|  ^ 4 ���� .|����|���|��|  ^ 5 ���
	 * ����|��|.|���� 6   ^ �� |. | ���� 7 ���� ����|��|.
	 * 
	 * С����ǰ�����ʡ�ԣ���ʾ0 ���ֵ�һλ����Ϊ0
	 */
	private void TipChecker(String tipcommand1, String tipcommand2) {
		// Tipcode1��ʾ�������ͣ�Tipcode2��ʾ���ʽ�������
		int Tipcode1 = 0, Tipcode2 = 0;
		// ��ʾ��������
		int tiptype1 = 0, tiptype2 = 0;
		// ������
		int bracket = 0;
		// ��+-*/ ^��������Ϊ��һλ
		if (tipcommand1.compareTo("#") == 0   //tipcommand1����#�����бȽϣ�ͬ���������͵Ĳſ��Խ��бȽ�
				&& (tipcommand2.compareTo("/") == 0
						|| tipcommand2.compareTo("*") == 0
						|| tipcommand2.compareTo("+") == 0
						|| tipcommand2.compareTo(")") == 0 || tipcommand2
						.compareTo("^") == 0)) {
			Tipcode1 = -1;
		}
		// ����洢�ַ��������һλ������
		else if (tipcommand1.compareTo("#") != 0) {
			if (tipcommand1.compareTo("(") == 0) {
				tiptype1 = 1;
			} else if (tipcommand1.compareTo(")") == 0) {
				tiptype1 = 2;
			} else if (tipcommand1.compareTo(".") == 0) {
				tiptype1 = 3;
			} else if ("0123456789".indexOf(tipcommand1) != -1) {
				tiptype1 = 4;
			} else if ("+-*/".indexOf(tipcommand1) != -1) {
				tiptype1 = 5;
			} else if ("^".indexOf(tipcommand1) != -1) {
				tiptype1 = 6;
			}else if ("��".indexOf(tipcommand1) != -1){
				tiptype1 = 7;
			}
			// ����������İ�������
			if (tipcommand2.compareTo("(") == 0) {
				tiptype2 = 1;
			} else if (tipcommand2.compareTo(")") == 0) {
				tiptype2 = 2;
			} else if (tipcommand2.compareTo(".") == 0) {
				tiptype2 = 3;
			} else if ("0123456789".indexOf(tipcommand2) != -1) {
				tiptype2 = 4;
			} else if ("+-*/".indexOf(tipcommand2) != -1) {
				tiptype2 = 5;
			} else if ("^".indexOf(tipcommand2) != -1) {
				tiptype2 = 6;
			}else if ("��".indexOf(tipcommand2) != -1){
				tiptype2 = 7;
			}
			
 
			switch (tiptype1) {
			case 1:
				// �����ź���ֱ�ӽ�������,��+*/�������š�-�����㣩,����" ^"
				if (tiptype2 == 2
						|| (tiptype2 == 5 && tipcommand2.compareTo("-") != 0)
						|| tiptype2 == 6)
					Tipcode1 = 1;
				break;
			case 2:
				// �����ź���������ţ����֣���+-*/^...�С�
				if (tiptype2 == 1 || tiptype2 == 3 || tiptype2 == 4|| tiptype2 == 7)
 
					Tipcode1 = 2;
				break;
			case 3:
				// ��.������������ţ���
				if (tiptype2 == 1 || tiptype2 == 7)
					Tipcode1 = 3;
				// ��������������.��
				if (tiptype2 == 3)
					Tipcode1 = 8;
				break;
			case 4:
				// ���ֺ���ֱ�ӽ������źͦ�
				if (tiptype2 == 1 || tiptype2 == 7)
					Tipcode1 = 4;
				break;
			case 5:
				// ��+-*/������ֱ�ӽ������ţ���+-*/ ^��
				if (tiptype2 == 2 || tiptype2 == 5 || tiptype2 == 6)
					Tipcode1 = 5;
				break;
			case 6:
				// �� ^������ֱ�ӽ������ţ���+-*/ ^�С�
				if (tiptype2 == 2 || tiptype2 == 5 || tiptype2 == 6 || tiptype2 == 7)
					Tipcode1 = 6;
				break;
			case 7:
				//"��"֮��ֻ��Ϊ"+-*/^)"����Ϊ"��(.0123456789"
				if (tiptype2 == 1 || tiptype2 == 3 || tiptype2 == 4 || tiptype2 == 7){
					Tipcode1 = 7;
				}					
				break;
			}
		}
		// ���С������ظ��ԣ�Tipcode1=0,��������ǰ��Ĺ���
		if (Tipcode1 == 0 && tipcommand2.compareTo(".") == 0) {
			int tip_point = 0;
			for (int i = 0; i < list.size(); i++) {
				// ��֮ǰ����һ��С����㣬��С���������1
				if (list.get(i).equals(".")) {
					tip_point++;
				}
				// ���������¼��������֮һ��С�����������
				if (list.get(i).equals("^") || list.get(i).equals("/")
						|| list.get(i).equals("*") || list.get(i).equals("-")
						|| list.get(i).equals("+") || list.get(i).equals("(")
						|| list.get(i).equals(")")) {
					tip_point = 0;
				}
			}
			tip_point++;
			// ��С�����������1������С�����ظ���
			if (tip_point > 1) {
				Tipcode1 = 8;
			}
		}
		// ����������Ƿ�ƥ��
		if (Tipcode1 == 0 && tipcommand2.compareTo(")") == 0) {
			int tip_right_bracket = 0;
			for (int i = 0; i < list.size(); i++) {
				// �������һ�������ţ��������1
				if (list.get(i).equals("(")) {
					tip_right_bracket++;
				}
				// �������һ�������ţ��������1
				if (list.get(i).equals(")")) {
					tip_right_bracket--;
				}
			}
			// ��������ż���=0,����û����Ӧ���������뵱ǰ������ƥ��
			if (tip_right_bracket == 0) {
				Tipcode1 = 10;
			}
 
		}
		// �������=�ĺϷ���
		if (Tipcode1 == 0 && tipcommand2.compareTo("=") == 0) {
			// ����ƥ����
			int tip_bracket = 0;
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).equals("(")) {
					tip_bracket++;
				}
				if (list.get(i).equals(")")) {
					tip_bracket--;
				}
			}
			// ������0�����������Ż���δƥ���
			if (tip_bracket > 0) {
				Tipcode1 = 9;
				bracket = tip_bracket;
			} else if (tip_bracket == 0) {
				// ��ǰһ���ַ�������֮һ������=�Ų��Ϸ�
				if ("+-*/".indexOf(tipcommand1) != -1) {
					Tipcode1 = 5;
				}
			}
		}
 
		if (Tipcode1 != 0) {
			isContinueInput = false;// ���������Լ�������
		}
	}
 
	/*
	 * ������ʽ ��������ɨ�裬������numberջ���������operatorջ 
	 * +-�������ȼ�Ϊ1��
	 * ���»������ȼ�Ϊ2��
	 * ��^�������ȼ�Ϊ4 
	 * �����ڲ�����������ͬ����������ȼ���4
	 * ��ǰ��������ȼ�����ջ��ѹջ��
	 * ����ջ������һ�����������������������
	 * �ظ�ֱ����ǰ���������ջ��
	 * ɨ������ʣ�µ���������������μ���
	 */
	public void process(String str) {
		int weightPlus = 0, topOp = 0, topNum = 0, flag = 1, weightTemp = 0;
		// weightPlusΪͬһ�����µĻ������ȼ���weightTemp��ʱ��¼���ȼ��ı仯
		// topOpΪweight[]��operator[]�ļ�������topNumΪnumber[]�ļ�����
		// flagΪ�������ļ�������1Ϊ������-1Ϊ����
		int weight[]; // ����operatorջ������������ȼ�����topOp����
		double number[]; // �������֣���topNum����
		char ch, ch_gai, operator[];// operator[]�������������topOp����
		String num;// ��¼���֣�str��+-*/() ! ^�ֶΣ�+-*/() ^�ַ�֮����ַ�����Ϊ����
		weight = new int[MAXLEN];
		number = new double[MAXLEN];
		operator = new char[MAXLEN];			
		String expression = str.replace("��",String.valueOf(PI));//���ַ����еĦ���PI
		// ������split�����ַ����ָ�
		StringTokenizer expToken = new StringTokenizer(expression, "+-*/()^");
	   //	StringTokenizer(str,".",true);  ����һ����������str��StringTokenizer����
		 //���ṩһ��ָ���ķָ�����ͬʱ��ָ���Ƿ񷵻طָ�����
		int i = 0;
		while (i < expression.length()) {
			ch = expression.charAt(i);  //�������ҵ�i���ַ�
			// �ж�������
			if (i == 0) {
				if (ch == '-')
					flag = -1;
			} else if (expression.charAt(i - 1) == '(' && ch == '-')
				flag = -1;
			// ȡ�����֣�������������ת�Ƹ�����,E�ǿ�ѧ����
			if (ch <= '9' && ch >= '0' || ch == '.' || ch == 'E') {
				num = expToken.nextToken();//�ָ���StringTokenizer�е���һ����������
				ch_gai = ch;
				// ȡ����������
				while (i < expression.length()
						&& (ch_gai <= '9' && ch_gai >= '0' || ch_gai == '.' || ch_gai == 'E')) {
					ch_gai = expression.charAt(i++);
				}
				// ��ָ���˻�֮ǰ��λ�ã���ÿ�����ֵ�ĩβλ��
				if (i >= expression.length())
					i -= 1;
				else {
					i -= 2;
				}
				if (num.compareTo(".") == 0)
					number[topNum++] = 0;
				// ����������ת�Ƹ�����
				else {
					number[topNum++] = Double.parseDouble(num) * flag;
					flag = 1;
				}
			}
			// ��������������ȼ�
			if (ch == '(')
				weightPlus += 4;
			if (ch == ')')
				weightPlus -= 4;
			if (ch == '-' && flag == 1 || ch == '+' || ch == '*' || ch == '/'
					|| ch == '^') {
 
				switch (ch) {
				// +-�����ȼ���ͣ�Ϊ1
				case '+':
				case '-':
					weightTemp = 1 + weightPlus;
					break;
				// x/�����ȼ��Ըߣ�Ϊ2
				case '*':
				case '/':
					weightTemp = 2 + weightPlus;
					break;
				default:
					weightTemp = 4 + weightPlus;
					break;
				}
				// �����ǰ���ȼ����ڶ�ջ����Ԫ�أ���ֱ����ջ
				if (topOp == 0 || weight[topOp - 1] < weightTemp) {
					weight[topOp] = weightTemp;
					operator[topOp] = ch;
					topOp++;
					// ���򽫶�ջ����������ȡ����ֱ����ǰ��ջ��������������ȼ�С�ڵ�ǰ�����
				} else {
					while (topOp > 0 && weight[topOp - 1] >= weightTemp) {
						switch (operator[topOp - 1]) {
						// ȡ�������������ӦԪ�ؽ�������
						case '+':
							number[topNum - 2] += number[topNum - 1];
							break;
						case '-':
							number[topNum - 2] -= number[topNum - 1];
							break;
						case '*':
							number[topNum - 2] *= number[topNum - 1];
							break;
						// �жϳ���Ϊ0�����
						case '/':
							if (number[topNum - 1] == 0) {
								// showError(1, str_old);
								return;
							}
							number[topNum - 2] /= number[topNum - 1];
							break;
 
						case '^':
							number[topNum - 2] = Math.pow(number[topNum - 2],
									number[topNum - 1]);
							break;
						// ����ʱ���нǶȻ��ȵ��жϼ�ת��
						}
						// ����ȡ��ջ����һ��Ԫ�ؽ����ж�
						topNum--;
						topOp--;
					}
					// ����������ջ
					weight[topOp] = weightTemp;
					operator[topOp] = ch;
					topOp++;
				}
			}
			i++;
		}
		// ����ȡ����ջ���������������
		while (topOp > 0) {
			// +-xֱ�ӽ�����ĺ���λ��ȡ������
			switch (operator[topOp - 1]) {
			case '+':
				number[topNum - 2] += number[topNum - 1];
				break;
			case '-':
				number[topNum - 2] -= number[topNum - 1];
				break;
			case '*':
				number[topNum - 2] *= number[topNum - 1];
				break;
			// �漰������ʱҪ���ǳ�������Ϊ������
			case '/':
				if (number[topNum - 1] == 0) {
					// showError(1, str_old);
					return;
				}
				number[topNum - 2] /= number[topNum - 1];
				break;
 
			case '^':
				number[topNum - 2] = Math.pow(number[topNum - 2],
						number[topNum - 1]);
				break;
 
			}
			// ȡ��ջ��һ��Ԫ�ؼ���
			topNum--;
			topOp--;
		}
		// ���������̫����ʾ������Ϣ
		if (number[0] > 7.3E306) {
			// showError(3, str_old);
			return;
		}
		// ������ս��
		resultText.setText(String.valueOf(FP(number[0])));
 
	}
 
	public double FP(double n) {
		// NumberFormat format=NumberFormat.getInstance(); //����һ����ʽ����f
		// format.setMaximumFractionDigits(18); //����С��λ�ĸ�ʽ
		DecimalFormat format = new DecimalFormat("0.#############");
		return Double.parseDouble(format.format(n));
	}
}

