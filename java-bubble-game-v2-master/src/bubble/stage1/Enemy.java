package bubble.stage1;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import bubble.game.state.EnemyWay;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Enemy extends JLabel implements Moveable {

	private BubbleFrame mContext;
	private BubbleFrame2 mContext2;
	private BubbleFrame3 mContext3;
	private Player player; // 플레이어 추가. 
	
	// 위치 상태
	public static int x;
	public static int y;
	private int X;
	private int Y;
	// 적군의 방향
	private EnemyWay enemyWay;

	// 움직임 상태
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	
	private int state; // 0(살아있는 상태), 1(물방울에 갇힌 상태)
	
	// 적군 속도 상태
	private final int SPEED = 3;
	private final int JUMPSPEED = 1;

	private ImageIcon enemyR, enemyL;

	public Enemy(BubbleFrame mContext,BubbleFrame2 mContext2,BubbleFrame3 mContext3,EnemyWay enemyWay) {
		if(Player.stage == 1) {	
			this.mContext = mContext;
			this.player = mContext.getPlayer();
			initObject();
			initSetting();
			initBackgroundEnemyService();
			initEnemyDirection(enemyWay);
		}
		else if(Player.stage == 2)
		{
			this.mContext2 = mContext2;
			this.player = mContext2.getPlayer();
			initObject();
			initSetting();
			initBackgroundEnemyService();
			initEnemyDirection(enemyWay);
		}
		else if(Player.stage == 3)
		{
			this.mContext3 = mContext3;
			this.player = mContext3.getPlayer();
			initObject();
			initSetting();
			initBackgroundEnemyService();
			initEnemyDirection(enemyWay);
		}
		}

	private void initObject() {
		enemyR = new ImageIcon("image/enemyR.png");
		enemyL = new ImageIcon("image/enemyL.png");
	}

	private void initSetting() {
		x = 480;
		y = 178;
		X = x;
		Y = y;
		left = false;
		right = false;
		up = false;
		down = false;
		
		state = 0;

		setSize(50, 50);
		setLocation(x, y);
	}
	
	private void initEnemyDirection(EnemyWay enemyWay) {
		if(EnemyWay.RIGHT == enemyWay) {
			enemyWay = EnemyWay.RIGHT;
			setIcon(enemyR);
			right();
		}else {
			enemyWay = EnemyWay.LEFT;
			setIcon(enemyL);
			left();
		}
	}
	
	private void initBackgroundEnemyService() {
		new Thread(new BackgroundEnemyService(this)).start();
	}

	@Override
	public void left() {
		//System.out.println("left");
		enemyWay = EnemyWay.LEFT;
		left = true;
		new Thread(()-> {
			while(left) {
				setIcon(enemyL);
				X = X - SPEED;
				setLocation(X, Y);
				if (Math.abs(X - player.getX()) < 50 && Math.abs(Y - player.getY()) < 50) {
					if (player.getState() == 0 && getState() == 0) 
						player.die();
				}
				try {
					Thread.sleep(10); // 0.01초
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
			}
			x = X;
			y = Y;
		}).start();

	}

	@Override
	public void right() {
		//System.out.println("right");
		enemyWay = EnemyWay.RIGHT;
		right = true;
		new Thread(()-> {
			while(right) {
				setIcon(enemyR);
				X = X + SPEED;
				setLocation(X, Y);
				if (Math.abs(X - player.getX()) < 50 && Math.abs(Y - player.getY()) < 50) {
					if (player.getState() == 0 && getState() == 0) 
						player.die();
				}
				try {
					Thread.sleep(10); // 0.01초
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
			}
			x=X;
			y=Y;
		}).start();
		

	}

	@Override
	public void up() {
		//System.out.println("up");
		up = true;
		new Thread(()->{
			for(int i=0; i<130/JUMPSPEED; i++) {
				Y = Y - JUMPSPEED;
				setLocation(X, Y);
				if (Math.abs(X - player.getX()) < 50 && Math.abs(Y - player.getY()) < 50) {
					if (player.getState() == 0 && getState() == 0) 
						player.die();
				}
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			up = false;
			x = X;
			y = Y;
			down();
			
		}).start();
	}

	@Override
	public void down() {
		//System.out.println("down");
		down = true;
		new Thread(()->{
			while(down) {
				Y = Y + JUMPSPEED;
				setLocation(X, Y);
				if (Math.abs(X - player.getX()) < 50 && Math.abs(Y - player.getY()) < 50) {
					if (player.getState() == 0 && getState() == 0) 
						player.die();
				}
				try {
					Thread.sleep(3);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			x = X;
			y = Y;
			down = false;
		}).start();
	}
}
