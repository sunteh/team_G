package bubble.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import bubble.game.music.BGM;
import bubble.game.state.EnemyWay;
import bubble.stage1.BubbleFrame;
import bubble.stage1.Enemy;
import bubble.stage1.GameOver;
import bubble.stage1.Player;
import bubble.stage2.BubbleFrame_stage_2_1;
import bubble.stage3.BubbleFrame_stage_3_1;
import bubble.stage4.BubbleFrame_stage_4_1;
import lombok.Getter;
import lombok.Setter;


public class SelectStageFrame extends JFrame implements ActionListener{
 
	private JLabel backgroundMap;
	private JButton b1,b2,b3,b4,b5;
	public SelectStageFrame() {
		initObject();
		initSetting();
		setVisible(true);
	}

	private void initObject() {
		backgroundMap = new JLabel(new ImageIcon("image/SelectGroundMap.png"));
		setContentPane(backgroundMap);
		ImageIcon img = new ImageIcon("image/StageButton1.png");
		b1 = new JButton(img);
		b1.setBounds(50,200,100,100);
		b1.setBorderPainted(false);
		b1.setContentAreaFilled(false);
		b1.setFocusPainted(false);
		b1.setOpaque(false);
		b1.setSize(175,175);
		b1.addActionListener(this);//이벤트메소드호출
		add(b1);
		ImageIcon img2 = new ImageIcon("image/StageButton2.png");
		b2 = new JButton(img2);
		b2.setBorderPainted(false);
		b2.setBounds(292,200,100,100);
		b2.setContentAreaFilled(false);
		b2.setFocusPainted(false);
		b2.setOpaque(false);
		b2.setSize(175,175);
		b2.addActionListener(this);
		add(b2);
		ImageIcon img3 = new ImageIcon("image/StageButton3.png");
		b3 = new JButton(img3);
		b3.setBounds(534,200,100,100);
		b3.addActionListener(this);
		b3.setBorderPainted(false);
		b3.setContentAreaFilled(false);
		b3.setFocusPainted(false);
		b3.setOpaque(false);
		b3.setSize(175,175);
		add(b3);
		ImageIcon img4 = new ImageIcon("image/StageButton4.png");
		b4 = new JButton(img4);
		b4.setBounds(776,200,100,100);
		b4.setBorderPainted(false);
		b4.setContentAreaFilled(false);
		b4.setFocusPainted(false);
		b4.setOpaque(false);
		b4.setSize(175,175);
		b4.addActionListener(this);
		add(b4);
	}

	private void initSetting() {
		
		setSize(1000, 640);
		setLayout(null); // absoulte 레이아웃 (자유롭게 그림을 그릴 수 있다)
		setLocationRelativeTo(null); // JFrame 가운데 배치하기
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // x버튼으로 창을 끌 때 JVM 같이 종료하기
		
	}
	private void GotoStage()
	{
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==b1) {
			new BubbleFrame();
			dispose();
		}else if(e.getSource()==b2) {
			new BubbleFrame_stage_2_1();
			dispose();
		}
		else if(e.getSource()==b3)
		{
			new BubbleFrame_stage_3_1();
			dispose();
		}
		else if(e.getSource()==b4)
		{
			new BubbleFrame_stage_4_1();
			dispose();
		}
		
	}



	
	}
