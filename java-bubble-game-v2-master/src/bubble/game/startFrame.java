package bubble.game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import bubble.game.music.BGM;
import bubble.game.state.EnemyWay;
import bubble.stage1.BubbleFrame;
import bubble.stage1.Enemy;
import bubble.stage1.GameOver;
import bubble.stage1.Player;
import lombok.Getter;
import lombok.Setter;


public class startFrame extends JFrame {
 
	private JLabel backgroundMap;

	public startFrame() {
		initObject();
		initSetting();
		initListener();
		setVisible(true);
	}

	private void initObject() {
		backgroundMap = new JLabel(new ImageIcon("image/maingroundMap.png"));
		setContentPane(backgroundMap);
	}

	private void initSetting() {
		setSize(1000, 640);
		setLayout(null); // absoulte 레이아웃 (자유롭게 그림을 그릴 수 있다)
		setLocationRelativeTo(null); // JFrame 가운데 배치하기
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // x버튼으로 창을 끌 때 JVM 같이 종료하기
	}
	private void GotoStage()
	{
		new SelectStageFrame();
	}
	private void initListener() {
		addKeyListener(new KeyAdapter() {

			// 키보드 클릭 이벤트 핸들러
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_SPACE:
					dispose();
					GotoStage();
					break;
				}
			}
			
		});
	}
	public static void main(String[] args) {
		new startFrame();
	}
	}