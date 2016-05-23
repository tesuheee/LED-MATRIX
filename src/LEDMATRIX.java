import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class LEDMATRIX extends JFrame implements ActionListener {
	String version = "ver 0.5";
	JLabel lbl[] = new JLabel[8];
	LED led[][] = new LED[8][8];
	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();
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
		tf.setText("int pat[8] = { 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0}");
		p1.setLayout(null);
		p2.setLayout(new BorderLayout());
		add(p1, BorderLayout.CENTER);
		add(p2, BorderLayout.SOUTH);
		p1.add(tf);
		p2.add(reverse, BorderLayout.EAST);
		p2.add(reset, BorderLayout.WEST);
		reverse.addActionListener(this);
		reset.addActionListener(this);

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
				return 1;
			} else {
				return 0;
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
			for (int i = 0; i < 8; i++) {
				ary[i] = led[i][0].getNum() * 128 + led[i][1].getNum() * 64 + led[i][2].getNum() * 32 + led[i][3].getNum() * 16 + led[i][4].getNum() * 8 + led[i][5].getNum() * 4 + led[i][6].getNum() * 2 + led[i][7].getNum() * 1;
			}
			tf.setText("int pat[8] = { " + "0x"+Integer.toHexString(ary[0]) + ", " +  "0x"+Integer.toHexString(ary[1]) + ", " +  "0x"+Integer.toHexString(ary[2]) + ", " +  "0x"+Integer.toHexString(ary[3]) + ", " +  "0x"+Integer.toHexString(ary[4]) + ", " +  "0x"+Integer.toHexString(ary[5]) + ", " +  "0x"+Integer.toHexString(ary[6]) + ", " +  "0x"+Integer.toHexString(ary[7]) + " };");
		}
	}
}