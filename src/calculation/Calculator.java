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
			"6", "1", "2", "3", "0", ".", "π" };

	private final String[] CLEAR = { "AC", "Backspace" };
	private final String[] SYMBOL = { "/", "*", "-", "+" ,"="};
 
	private JButton keys[] = new JButton[KEYS.length];
	private JButton clear[] = new JButton[CLEAR.length];
	private JButton symbol[] = new JButton[SYMBOL.length];
	public JTextField resultText = new JTextField("0"); //单行的文本框
 
	private boolean vbegin = true;// 控制输入，true为重新输入，false为接着输入
	private boolean equals_flag = true;// true为未输入=，false表示输入=
	private boolean isContinueInput = true;// true为正确，可以继续输入，false错误，输入锁定
 
	final int MAXLEN = 500;
	final double  PI = 3.141592657;
	
	public Calculator() {
		super();
		init();  //初始化界面
		this.setBackground(Color.LIGHT_GRAY);
		this.setTitle("计算器");
		this.setLocation(300, 300);
		this.setResizable(false); //不可以再修改大小
		this.pack(); //根据窗口中布局以及组件的size来确定frame的最佳大小
	}
 
	private void init() {
		resultText.setHorizontalAlignment(JTextField.RIGHT);  //文本的布局为靠右
		resultText.setEditable(false);   //false为只读模式
		resultText.setBackground(Color.white);
		list = new ArrayList<String>();
		//创建一个列表，list是Collection的接口，ArrayList是list的实现类
		initLayout();//界面初始化
		initActionEvent();//添加组件事件处理，button响应
	}
	
	public void initLayout() {
		JPanel calckeysPanel = new JPanel();  //创建一个面板
		calckeysPanel.setLayout(new GridLayout(5, 3, 3, 3));  //设置面板为网格面板
		for (int i = 0; i < KEYS.length; i++) {
			keys[i] = new JButton(KEYS[i]);
			calckeysPanel.add(keys[i]);
			keys[i].setForeground(Color.blue);  //设置按键字体的颜色
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
 //用add为BorderLayout添加组件时，需要两个参数，位置和组件
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc= new GridBagConstraints();//定义一个GridBagConstraints
		gbc.fill = GridBagConstraints.BOTH;  //当格子有剩余空间时，填充空间
		gbc.insets = new Insets(3, 3, 3, 3);  //可以调整组件周围的大小空间
		
		gbc.gridx = 0;  //描述网格开始处于哪个位置
		gbc.gridy = 0;
		//gbc.gridwidth = 1;   //描述该组件在横向上所占网格的个数
		//gbc.gridheight = 1;
		panel1.add(symbol[0], gbc);//  “/号”
		
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
		panel1.add(clear[1], gbc);//“backspace”
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel1.add(symbol[2], gbc);//“-”
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.ipady =35;  //组件内部填充空间，ipady即给组件的最小高度添加多少空间
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
		//Frame只是一个框架，窗口现实的组件都是加在Frame上的内容面板contentPane上的
 
	}
 
	public void initActionEvent() {   //对每个按键进行添加动作监听器
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
	
	//组建发生操作时调用
	public void actionPerformed(ActionEvent e) {
		String label = e.getActionCommand();  //依赖于按钮上的字符串，get到的是标签
		if (label.equals(CLEAR[1])) {
			handleBackspace();    //软键盘删除处理操作
		} else if (label.equals(CLEAR[0])) {
			list.clear();
			resultText.setText("0");
			vbegin = true;  //表示重新输入
			equals_flag = true;  //表示未输入"="
		} else {
			handle(label);
		}
	}
 ////
	private void handleBackspace() {
		String text = resultText.getText();
		list.add(text);   //把文本内容添加到list上
		int i = text.length();
		if (i > 0 && list.size() > 0) {
			text = text.substring(0, i - 1);  //返回此字符串的指定子字符串，但不包括i-1
			list.remove(list.size() - 1); // 移除栈顶的那个元素
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
		if (equals_flag == false) { //&& "π0123456789.()+-*/^".indexOf(key) != -1
			list.add(text);
			vbegin = false;  //表示不重新输入
		}
 
		if (!list.isEmpty()) {
			TipChecker(list.get(list.size() - 1), key);
		} else {
			TipChecker("#", key);
		}
		if (isContinueInput && "π0123456789.()+-*/^".indexOf(key) != -1) {
			list.add(key);
		}
         //indexOf 方法返回一个整数值，指出 String 对象内子字符串的开始位置。如果没有找到子字符串，则返回-1。
		// 若输入正确，则将输入信息显示到显示器上
		if (isContinueInput && "π0123456789.()+-*/^".indexOf(key) != -1) {
			if (equals_flag == false && ("+-*/^".indexOf(key) != -1)) {
				vbegin = false;
				equals_flag = true;
				printText(key);
			} else if (equals_flag == false
					&& ("π0123456789.()".indexOf(key) != -1)) {
				vbegin = true;
				equals_flag = true;
				printText(key);
			} else {
				printText(key);
			}
 
		} else if (isContinueInput && equals_flag && key.equals("=")) {
			isContinueInput = false;// 表明不可以继续输入
			equals_flag = false;// 表明已经输入=
			vbegin = true;// 重新输入标志设置true
			process(resultText.getText()); // 整个程序的核心，计算表达式的值并显示
			list.clear();
		}
		isContinueInput = true;
	}
 
	private void printText(String key) {
		if (vbegin) {
			resultText.setText(key);// 清屏后输出
			// firstDigit = false;
		} else {
			resultText.setText(resultText.getText() + key);
		}
		vbegin = false;
	}
 
	/*
	 * 检测函数，对str进行前后语法检测 为Tip的提示方式提供依据，与TipShow()配合使用 编号 字符 其后可以跟随的合法字符 1 （
	 * 数字|（|-|.|函数 2 ） 算符|）|  ^ 3 . 数字|算符|）|  ^ 4 数字 .|数字|算符|）|  ^ 5 算符
	 * 数字|（|.|函数 6   ^ （ |. | 数字 7 函数 数字|（|.
	 * 
	 * 小数点前后均可省略，表示0 数字第一位可以为0
	 */
	private void TipChecker(String tipcommand1, String tipcommand2) {
		// Tipcode1表示错误类型，Tipcode2表示名词解释类型
		int Tipcode1 = 0, Tipcode2 = 0;
		// 表示命令类型
		int tiptype1 = 0, tiptype2 = 0;
		// 括号数
		int bracket = 0;
		// “+-*/ ^”不能作为第一位
		if (tipcommand1.compareTo("#") == 0   //tipcommand1跟“#”进行比较，同种数据类型的才可以进行比较
				&& (tipcommand2.compareTo("/") == 0
						|| tipcommand2.compareTo("*") == 0
						|| tipcommand2.compareTo("+") == 0
						|| tipcommand2.compareTo(")") == 0 || tipcommand2
						.compareTo("^") == 0)) {
			Tipcode1 = -1;
		}
		// 定义存储字符串中最后一位的类型
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
			}else if ("π".indexOf(tipcommand1) != -1){
				tiptype1 = 7;
			}
			// 定义欲输入的按键类型
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
			}else if ("π".indexOf(tipcommand2) != -1){
				tiptype2 = 7;
			}
			
 
			switch (tiptype1) {
			case 1:
				// 左括号后面直接接右括号,“+*/”（负号“-”不算）,或者" ^"
				if (tiptype2 == 2
						|| (tiptype2 == 5 && tipcommand2.compareTo("-") != 0)
						|| tiptype2 == 6)
					Tipcode1 = 1;
				break;
			case 2:
				// 右括号后面接左括号，数字，“+-*/^...π”
				if (tiptype2 == 1 || tiptype2 == 3 || tiptype2 == 4|| tiptype2 == 7)
 
					Tipcode1 = 2;
				break;
			case 3:
				// “.”后面接左括号，π
				if (tiptype2 == 1 || tiptype2 == 7)
					Tipcode1 = 3;
				// 连续输入两个“.”
				if (tiptype2 == 3)
					Tipcode1 = 8;
				break;
			case 4:
				// 数字后面直接接左括号和π
				if (tiptype2 == 1 || tiptype2 == 7)
					Tipcode1 = 4;
				break;
			case 5:
				// “+-*/”后面直接接右括号，“+-*/ ^”
				if (tiptype2 == 2 || tiptype2 == 5 || tiptype2 == 6)
					Tipcode1 = 5;
				break;
			case 6:
				// “ ^”后面直接接右括号，“+-*/ ^π”
				if (tiptype2 == 2 || tiptype2 == 5 || tiptype2 == 6 || tiptype2 == 7)
					Tipcode1 = 6;
				break;
			case 7:
				//"π"之后只能为"+-*/^)"不能为"π(.0123456789"
				if (tiptype2 == 1 || tiptype2 == 3 || tiptype2 == 4 || tiptype2 == 7){
					Tipcode1 = 7;
				}					
				break;
			}
		}
		// 检测小数点的重复性，Tipcode1=0,表明满足前面的规则
		if (Tipcode1 == 0 && tipcommand2.compareTo(".") == 0) {
			int tip_point = 0;
			for (int i = 0; i < list.size(); i++) {
				// 若之前出现一个小数点点，则小数点计数加1
				if (list.get(i).equals(".")) {
					tip_point++;
				}
				// 若出现以下几个运算符之一，小数点计数清零
				if (list.get(i).equals("^") || list.get(i).equals("/")
						|| list.get(i).equals("*") || list.get(i).equals("-")
						|| list.get(i).equals("+") || list.get(i).equals("(")
						|| list.get(i).equals(")")) {
					tip_point = 0;
				}
			}
			tip_point++;
			// 若小数点计数大于1，表明小数点重复了
			if (tip_point > 1) {
				Tipcode1 = 8;
			}
		}
		// 检测右括号是否匹配
		if (Tipcode1 == 0 && tipcommand2.compareTo(")") == 0) {
			int tip_right_bracket = 0;
			for (int i = 0; i < list.size(); i++) {
				// 如果出现一个左括号，则计数加1
				if (list.get(i).equals("(")) {
					tip_right_bracket++;
				}
				// 如果出现一个右括号，则计数减1
				if (list.get(i).equals(")")) {
					tip_right_bracket--;
				}
			}
			// 如果右括号计数=0,表明没有响应的左括号与当前右括号匹配
			if (tip_right_bracket == 0) {
				Tipcode1 = 10;
			}
 
		}
		// 检查输入=的合法性
		if (Tipcode1 == 0 && tipcommand2.compareTo("=") == 0) {
			// 括号匹配数
			int tip_bracket = 0;
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).equals("(")) {
					tip_bracket++;
				}
				if (list.get(i).equals(")")) {
					tip_bracket--;
				}
			}
			// 若大于0，表明左括号还有未匹配的
			if (tip_bracket > 0) {
				Tipcode1 = 9;
				bracket = tip_bracket;
			} else if (tip_bracket == 0) {
				// 若前一个字符是以下之一，表明=号不合法
				if ("+-*/".indexOf(tipcommand1) != -1) {
					Tipcode1 = 5;
				}
			}
		}
 
		if (Tipcode1 != 0) {
			isContinueInput = false;// 表明不可以继续输入
		}
	}
 
	/*
	 * 计算表达式 从左向右扫描，数字入number栈，运算符入operator栈 
	 * +-基本优先级为1，
	 * ×÷基本优先级为2，
	 * √^基本优先级为4 
	 * 括号内层运算符比外层同级运算符优先级高4
	 * 当前运算符优先级高于栈顶压栈，
	 * 低于栈顶弹出一个运算符与两个数进行运算
	 * 重复直到当前运算符大于栈顶
	 * 扫描完后对剩下的运算符与数字依次计算
	 */
	public void process(String str) {
		int weightPlus = 0, topOp = 0, topNum = 0, flag = 1, weightTemp = 0;
		// weightPlus为同一（）下的基本优先级，weightTemp临时记录优先级的变化
		// topOp为weight[]，operator[]的计数器；topNum为number[]的计数器
		// flag为正负数的计数器，1为正数，-1为负数
		int weight[]; // 保存operator栈中运算符的优先级，以topOp计数
		double number[]; // 保存数字，以topNum计数
		char ch, ch_gai, operator[];// operator[]保存运算符，以topOp计数
		String num;// 记录数字，str以+-*/() ! ^分段，+-*/() ^字符之间的字符串即为数字
		weight = new int[MAXLEN];
		number = new double[MAXLEN];
		operator = new char[MAXLEN];			
		String expression = str.replace("π",String.valueOf(PI));//将字符串中的π用PI
		// 建议用split代替字符串分割
		StringTokenizer expToken = new StringTokenizer(expression, "+-*/()^");
	   //	StringTokenizer(str,".",true);  构造一个用来解析str的StringTokenizer对象，
		 //并提供一个指定的分隔符，同时，指定是否返回分隔符。
		int i = 0;
		while (i < expression.length()) {
			ch = expression.charAt(i);  //从左往右第i个字符
			// 判断正负数
			if (i == 0) {
				if (ch == '-')
					flag = -1;
			} else if (expression.charAt(i - 1) == '(' && ch == '-')
				flag = -1;
			// 取得数字，并将正负符号转移给数字,E是科学计数
			if (ch <= '9' && ch >= '0' || ch == '.' || ch == 'E') {
				num = expToken.nextToken();//分割后的StringTokenizer中的下一个索引数据
				ch_gai = ch;
				// 取得整个数字
				while (i < expression.length()
						&& (ch_gai <= '9' && ch_gai >= '0' || ch_gai == '.' || ch_gai == 'E')) {
					ch_gai = expression.charAt(i++);
				}
				// 将指针退回之前的位置，即每个数字的末尾位置
				if (i >= expression.length())
					i -= 1;
				else {
					i -= 2;
				}
				if (num.compareTo(".") == 0)
					number[topNum++] = 0;
				// 将正负符号转移给数字
				else {
					number[topNum++] = Double.parseDouble(num) * flag;
					flag = 1;
				}
			}
			// 计算运算符的优先级
			if (ch == '(')
				weightPlus += 4;
			if (ch == ')')
				weightPlus -= 4;
			if (ch == '-' && flag == 1 || ch == '+' || ch == '*' || ch == '/'
					|| ch == '^') {
 
				switch (ch) {
				// +-的优先级最低，为1
				case '+':
				case '-':
					weightTemp = 1 + weightPlus;
					break;
				// x/的优先级稍高，为2
				case '*':
				case '/':
					weightTemp = 2 + weightPlus;
					break;
				default:
					weightTemp = 4 + weightPlus;
					break;
				}
				// 如果当前优先级大于堆栈顶部元素，则直接入栈
				if (topOp == 0 || weight[topOp - 1] < weightTemp) {
					weight[topOp] = weightTemp;
					operator[topOp] = ch;
					topOp++;
					// 否则将堆栈中运算符逐个取出，直到当前堆栈顶部运算符的优先级小于当前运算符
				} else {
					while (topOp > 0 && weight[topOp - 1] >= weightTemp) {
						switch (operator[topOp - 1]) {
						// 取出数字数组的相应元素进行运算
						case '+':
							number[topNum - 2] += number[topNum - 1];
							break;
						case '-':
							number[topNum - 2] -= number[topNum - 1];
							break;
						case '*':
							number[topNum - 2] *= number[topNum - 1];
							break;
						// 判断除数为0的情况
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
						// 计算时进行角度弧度的判断及转换
						}
						// 继续取堆栈的下一个元素进行判断
						topNum--;
						topOp--;
					}
					// 将运算符入堆栈
					weight[topOp] = weightTemp;
					operator[topOp] = ch;
					topOp++;
				}
			}
			i++;
		}
		// 依次取出堆栈的运算符进行运算
		while (topOp > 0) {
			// +-x直接将数组的后两位数取出运算
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
			// 涉及到除法时要考虑除数不能为零的情况
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
			// 取堆栈下一个元素计算
			topNum--;
			topOp--;
		}
		// 如果是数字太大，提示错误信息
		if (number[0] > 7.3E306) {
			// showError(3, str_old);
			return;
		}
		// 输出最终结果
		resultText.setText(String.valueOf(FP(number[0])));
 
	}
 
	public double FP(double n) {
		// NumberFormat format=NumberFormat.getInstance(); //创建一个格式化类f
		// format.setMaximumFractionDigits(18); //设置小数位的格式
		DecimalFormat format = new DecimalFormat("0.#############");
		return Double.parseDouble(format.format(n));
	}
}

