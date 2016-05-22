import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class LEDMATRIX extends JFrame implements ActionListener {
	String version = "ver 0.5";
	LED led[][] = new LED[8][8];
	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();
	JButton reverse = new JButton("反転");
	JButton reset = new JButton("リセット");

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

		p1.setLayout(null);
		p2.setLayout(new BorderLayout());
		add(p1, BorderLayout.CENTER);
		add(p2, BorderLayout.SOUTH);
		p2.add(reverse, BorderLayout.EAST);
		p2.add(reset, BorderLayout.WEST);
		reverse.addActionListener(this);
		reset.addActionListener(this);

		int x = 90, y = 100;
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
		if (e.getSource() == reverse) {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					led[i][j].reverse();
				}
			}
		} else if (e.getSource() == reset) {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					led[i][j].setLow();
				}
			}
		}
	}

}

@SuppressWarnings("serial")
class LED extends JButton implements ActionListener {
	boolean H = false;

	public LED(int x, int y) {
		setSize(30, 30);
		setLocation(x, y);
		setBackground(Color.WHITE);
		addActionListener(this);
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
	}

}