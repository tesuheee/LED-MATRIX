import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class LEDMATRIX extends JFrame implements ActionListener, ItemListener {
	String version = "ver 0.9";
	JLabel lbl[] = new JLabel[8];
	LED led[][] = new LED[8][8];
	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();
	JPanel p3 = new JPanel();
	CheckboxGroup cbg = new CheckboxGroup();
	Checkbox cb1 = new Checkbox("10進数", cbg, false);
	Checkbox cb2 = new Checkbox("16進数", cbg, true);
	JButton reverse = new JButton("反転");
	JButton reset = new JButton("リセット");
	JTextField tf = new JTextField();
	int ary[] = new int[8];

	public static void main(String[] args) {
		LEDMATRIX f = new LEDMATRIX();
		f.setVisible(true);
	}

	public LEDMATRIX() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("LEDマトリクス " + version);
		setSize(500, 500);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		tf.setSize(500, 40);
		tf.setLocation(0, 20);
		tf.setHorizontalAlignment(JLabel.CENTER);
		tf.setFont(new Font("", 0, 15));
		tf.setBorder(null);
		tf.setBackground(new Color(240, 240, 240));
		tf.setText("int pat[8] = { 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff };");
		cb1.addItemListener(this);
		cb2.addItemListener(this);
		reverse.addActionListener(this);
		reset.addActionListener(this);
		p1.setLayout(null);
		p2.setLayout(new BorderLayout());
		p3.setLayout(new FlowLayout());
		add(p1, BorderLayout.CENTER);
		add(p2, BorderLayout.SOUTH);
		p1.add(tf);
		p2.add(reverse, BorderLayout.EAST);
		p2.add(reset, BorderLayout.WEST);
		p2.add(p3, BorderLayout.CENTER);
		p3.add(cb1);
		p3.add(cb2);

		// ラベルの生成
		int x = 98, y = 75;
		for (int i = 0; i < 8; i++, x += 40) {
			lbl[i] = new JLabel();
			lbl[i].setText("B" + i);
			lbl[i].setLocation(x, y);
			lbl[i].setSize(30, 30);
			p1.add(lbl[i]);
		}

		// マトリクスの生成
		x = 90;
		y = 100;
		for (int i = 0; i < 8; i++, y += 40) {
			for (int j = 0; j < 8; j++, x += 40) {
				led[i][j] = new LED(x, y);
				p1.add(led[i][j]);
			}
			x = 90;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == reverse)
			for (int i = 0; i < 8; i++)
				for (int j = 0; j < 8; j++)
					led[i][j].reverse();
		else if (e.getSource() == reset)
			for (int i = 0; i < 8; i++)
				for (int j = 0; j < 8; j++)
					led[i][j].setLow();
	}

	public int letCheck(int col) {
		int num = 0;
		for (int i = 0; i < 8; i++) {
			num = num + led[col][i].getNum();
		}
		return num;
	}

	class LED extends JButton implements ActionListener {
		boolean H = false;

		public LED(int x, int y) {
			setSize(30, 30);
			setLocation(x, y);
			setBackground(Color.WHITE);
			setBorderPainted(false);
			addActionListener(this);
		}

		public int getNum() {
			if (H) {
				return 0; //カソード側なのでLにして点灯させる
			} else {
				return 1;
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			H = !H;
			update();
		}

		public void reverse() {
			H = !H;
			update();
		}

		public void setLow() {
			H = false;
			update();
		}

		void update() {
			if (H) {
				setBackground(Color.RED);
			} else {
				setBackground(Color.WHITE);
			}
			// ひどいところ
			for (int i = 0; i < 8; i++) {
				ary[i] = led[i][0].getNum() * 128 + led[i][1].getNum() * 64 + led[i][2].getNum() * 32 + led[i][3].getNum() * 16 + led[i][4].getNum() * 8 + led[i][5].getNum() * 4 + led[i][6].getNum() * 2 + led[i][7].getNum() * 1;
			}
			if (cb1.getState()) {
				tf.setText("int pat[8] = { " + ary[0] + ", " + ary[1] + ", " + ary[2] + ", " + ary[3] + ", " + ary[4] + ", " + ary[5] + ", " + ary[6] + ", " + ary[7] + " };");
			} else {
				tf.setText("int pat[8] = { " + "0x" + Integer.toHexString(ary[0]) + ", " + "0x" + Integer.toHexString(ary[1]) + ", " + "0x" + Integer.toHexString(ary[2]) + ", " + "0x" + Integer.toHexString(ary[3]) + ", " + "0x" + Integer.toHexString(ary[4]) + ", " + "0x" + Integer.toHexString(ary[5]) + ", " + "0x" + Integer.toHexString(ary[6]) + ", " + "0x" + Integer.toHexString(ary[7]) + " };");
			}
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				led[i][j].update();
			}
		}
	}
}