package bubble.stage2;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

// 메인스레드 바쁨 - 키보드 이벤트를 처리하기 바쁨.
// 백그라운드에서 계속 관찰
public class BackgroundEnemyService implements Runnable{

	private BufferedImage image;
	private Enemy enemy;
	private int COUNT = 0; // 점프 카운트
	private int FIRST = 0;     // 바닥 도착 여부 0 바닥, 1 꼭대기
	private int BOTTOMCOLOR = -131072; // 바닥 빨강.
	
	// 플레이어, 버블
	public BackgroundEnemyService(Enemy enemy) {
		this.enemy = enemy;
		try {
			if(Player.stage == 1) {
				image = ImageIO.read(new File("image/backgroundMapService2_1.png"));
				}
				else if(Player.stage == 2)
				{
					image = ImageIO.read(new File("image/backgroundMapService2_2.png"));
				}
				else if(Player.stage == 3)
				{
					image = ImageIO.read(new File("image/backgroundMapService2_3.png"));
				}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	} 
	
	@Override
	public void run() {
		while(enemy.getState()==0) {
			
			// 색상 확인
			Color leftColor = new Color(image.getRGB(enemy.getX() - 10, enemy.getY() + 25));
			Color rightColor = new Color(image.getRGB(enemy.getX() + 50 + 15, enemy.getY() + 25));
			// -2가 나온다는 뜻은 바닥에 색깔이 없이 흰색
			Color bottomColor2 = new Color(image.getRGB(enemy.getX() + 10, enemy.getY() + 50 + 5));
			int bottomColor = image.getRGB(enemy.getX() + 10, enemy.getY() + 50 + 5) // -1
					+ image.getRGB(enemy.getX()+50 - 10, enemy.getY() + 50 + 5); // -1
			
			// 바닥 충돌 확인
			if(bottomColor != -2) { // 흰색이 아닐때. 
				//System.out.println("바닥에 충돌함");
				enemy.setDown(false);
			}else { // -2 일때 실행됨 => 내 바닥색깔이 하얀색이라는 것
				if(!enemy.isUp() && !enemy.isDown()) {
					//System.out.println("다운 실행됨");
					enemy.down();
				}
			}
			
			// 바닥 도착
			if (bottomColor == BOTTOMCOLOR)	FIRST = 1;
			
			// 꼭대기 도착.
			
			
			// 오른쪽 구석
			if (COUNT >=2
					&& rightColor.getRed() == 255
					&& rightColor.getGreen() == 0 
					&& rightColor.getBlue() == 0) {
				enemy.setRight(false);
				enemy.setLeft(true);	
				if(COUNT >= 2) {
					enemy.left();
					enemy.up();
					double randomValue = Math.random();
					int intValue = (int)(randomValue * 3)+1;
					if (intValue >= 2)
					{
						enemy.up();
					}
					COUNT = 0;
					}
				}
			// 왼쪽 구석. 	
			else if(COUNT >= 3  
					&& leftColor.getRed() == 255
					&& leftColor.getGreen() == 0 
					&& leftColor.getBlue() == 0) {
				enemy.setLeft(false);
				enemy.setRight(true);
				if(COUNT >= 3) {
					enemy.right();
					enemy.up();
					double randomValue = Math.random();
					int intValue = (int)(randomValue * 2)+1;
					if (intValue == 2)
					{
						enemy.up();
					}
					COUNT = 0;
				}
				}
			else if(leftColor.getRed() == 255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 0) {
				enemy.setLeft(false);
				if(!enemy.isRight()) {
					COUNT++;
					enemy.right();	
				}
			}else if(rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0) {
				enemy.setRight(false);
				if(!enemy.isLeft()) {
					COUNT++;
					enemy.left();	
				}
			}
			if(!enemy.isUp()&&rightColor.getRed() == 0 && rightColor.getGreen() == 0 && rightColor.getBlue() == 255 && bottomColor2.getBlue() != 255)
			{
				enemy.setRight(false);
				if(!enemy.isUp()&&!enemy.isLeft())
				{
					COUNT++;
					enemy.left();
				}
			}
			else if(!enemy.isUp()&&leftColor.getRed() == 0 && leftColor.getGreen() == 0 && leftColor.getBlue() == 255&& bottomColor2.getBlue() == 255)
			{
				enemy.setLeft(false);
				if(!enemy.isRight())
				{
					COUNT++;
					enemy.right();
				}
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
