package bubble.stage3;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameOver extends JLabel{
	
	// 의존성 콤포지션
	private BubbleFrame_stage_3_1 mContext;
	private BubbleFrame_stage_3_2 mContext2;
	private BubbleFrame_stage_3_3 mContext3;
	// 위치 상태
	private int x;
	private int y;

	// 움직임 상태
	private boolean down;

	private ImageIcon gameOver;

	public GameOver(BubbleFrame_stage_3_1 mContext) {
		if(Player.stage == 1) {
			this.mContext = mContext;
			initObject();
			initSetting();
		}
		else if(Player.stage == 2)
		{
			this.mContext2 = mContext2;
			initObject();
			initSetting();
		}
		else if(Player.stage == 3)
		{
			this.mContext3 = mContext3;
			initObject();
			initSetting();
		}
	}
	
	private void initObject() {
		gameOver = new ImageIcon("image/GameOverText.png");
	}
	
	private void initSetting() {
		down = false;
		
		x = 150;
		y = 150;
		
		setIcon(gameOver);
		setSize(700,187);
		setLocation(20,40);
		
	}



}
