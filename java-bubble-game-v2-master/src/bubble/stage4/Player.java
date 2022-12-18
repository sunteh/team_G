package bubble.stage4;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import bubble.game.SelectStageFrame;
import bubble.game.startFrame;
import bubble.game.music.GameOverBGM;
import bubble.game.state.PlayerWay;
import lombok.Getter;
import lombok.Setter;

// class Player -> new 가능한 애들!! 게임에 존재할 수 있음. (추상메서드를 가질 수 없다.)
@Getter
@Setter
public class Player extends JLabel implements Moveable {
	public static int stage = 1;
 	private BubbleFrame_stage_4_1 mContext;
 	private BubbleFrame_stage_4_2 mContext2;
 	private BubbleFrame_stage_4_3 mContext3;
	private List<Bubble> bubbleList;
	private GameOver gameOver;
	
	// 위치 상태
	private int x;
	private int y;
	
	// 플레이어의 방향
	private PlayerWay playerWay;

	// 움직임 상태
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	
	// 벽에 충돌한 상태
	private boolean leftWallCrash;
	private boolean rightWallCrash;
	
	// 플레이어 속도 상태
	private final int SPEED = 4;
	private final int JUMPSPEED = 2; // up, down
	
	private int state = 0; // 0 : live , 1 : die

	private ImageIcon playerR, playerL, playerRdie, playerLdie;

	public Player(BubbleFrame_stage_4_1 mContext, BubbleFrame_stage_4_2 mContext2, BubbleFrame_stage_4_3 mContext3) {
		if(stage == 1) {
			this.mContext = mContext;
			initObject();
			initSetting();
			initBackgroundPlayerService();
		}
		else if(stage ==2)
		{
			this.mContext2 = mContext2;
			initObject();
			initSetting();
			initBackgroundPlayerService();
		}
		else if(stage ==3)
		{
			this.mContext3 = mContext3;
			initObject();
			initSetting();
			initBackgroundPlayerService();
		}
	}

	private void initObject() {
		playerR = new ImageIcon("image/playerR.png");
		playerL = new ImageIcon("image/playerL.png");
		playerRdie = new ImageIcon("image/playerRdie.png");
		playerLdie = new ImageIcon("image/playerLdie.png");
		bubbleList = new ArrayList<>();
	}

	private void initSetting() {
		if(Player.stage == 1) {	
			x = 80;
			y = 435;
		}
		else if(Player.stage == 2) {	
			x = 80;
			y = 435;
		}
		else if(Player.stage == 3) {	
			x = 80;
			y = 435;
		}
		
		
		left = false;
		right = false;
		up = false;
		down = false;
		
		leftWallCrash = false;
		rightWallCrash = false;

		playerWay = PlayerWay.RIGHT;
		
		setIcon(playerR);
		setSize(50, 50);
		setLocation(x, y);
	}
	
	private void initBackgroundPlayerService() {
		new Thread(new BackgroundPlayerService(this)).start();
	}
	
	@Override
	public void attack() {
		new Thread(()->{
			if(Player.stage == 1) {
				Bubble bubble = new Bubble(mContext, null, null);
				mContext.add(bubble);
				bubbleList.add(bubble);
				if(playerWay == PlayerWay.LEFT) {
					bubble.left();
				}else {
					bubble.right();
				}
			}
			else if(Player.stage ==2)
			{
				Bubble bubble = new Bubble(null, mContext2, null);
				mContext2.add(bubble);
				bubbleList.add(bubble);
				if(playerWay == PlayerWay.LEFT) {
					bubble.left();
				}else {
					bubble.right();
				}
			}
			else if(Player.stage ==3)
			{
				Bubble bubble = new Bubble(null, null, mContext3);
				mContext3.add(bubble);
				bubbleList.add(bubble);
				if(playerWay == PlayerWay.LEFT) {
					bubble.left();
				}else {
					bubble.right();
				}
			}
		}).start();
	}

	// 이벤트 핸들러
	@Override
	public void left() {
		//System.out.println("left");
		playerWay = PlayerWay.LEFT;
		left = true;
		new Thread(()-> {
			while(left && getState() == 0) {
				setIcon(playerL);
				x = x - SPEED;
				setLocation(x, y);
				try {
					Thread.sleep(10); // 0.01초
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
			}
		}).start();

	}

	@Override
	public void right() {
		//System.out.println("right");
		playerWay = PlayerWay.RIGHT;
		right = true;
		new Thread(()-> {
			while(right && getState() == 0) {
				setIcon(playerR);
				x = x + SPEED;
				setLocation(x, y);
				try {
					Thread.sleep(10); // 0.01초
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
			}
		}).start();
		

	}

	// left + up, right + up
	@Override
	public void up() {
		//System.out.println("up");
		up = true;
		new Thread(()->{
			for(int i=0; i<130/JUMPSPEED; i++) {
				y = y - JUMPSPEED;
				setLocation(x, y);
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			up = false;
			down();
			
		}).start();
	}

	@Override
	public void down() {
		//System.out.println("down");
		down = true;
		new Thread(()->{
			while(down) {
				y = y + JUMPSPEED;
				setLocation(x, y);
				try {
					Thread.sleep(3);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			down = false;
		}).start();
	}
	
	public void die() {
		new Thread(() -> {
			setState(1);
			setIcon(PlayerWay.RIGHT == playerWay ? playerRdie : playerLdie);
			new GameOverBGM();
			if(Player.stage == 1) {
				mContext.getBgm().stopBGM(); //음악이 멈춤
			}
			else if(Player.stage == 2)
			{
				mContext2.getBgm().stopBGM();
			}
			else if(Player.stage == 3)
			{
				mContext3.getBgm().stopBGM();
			}
			try {				
				if(!isUp() && !isDown()) up();
				
				if(Player.stage == 1) {
					gameOver = new GameOver(mContext);
					mContext.add(gameOver);
					Thread.sleep(2000);
					mContext.remove(this);
					mContext.repaint(); //음악이 멈춤
					Thread.sleep(1000);
					new startFrame();
					mContext.dispose();
				}
				else if(Player.stage == 2)
				{
					gameOver = new GameOver(mContext);
					mContext2.add(gameOver);
					Thread.sleep(2000);
					mContext2.remove(this);
					mContext2.repaint();
					Thread.sleep(1000);
					new startFrame();
					mContext2.dispose();
				}
				else if(Player.stage == 3)
				{
					gameOver = new GameOver(mContext);
					mContext3.add(gameOver);
					Thread.sleep(2000);
					mContext3.remove(this);
					mContext3.repaint();
					Thread.sleep(1000);
					new startFrame();
					mContext3.dispose();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("플레이어 사망.");
		}).start();
	}
}
