package SpaceInvader;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

interface MAP_POSITION_INFO{
	int GAME_SPACE_LEFT = 2,
		GAME_SPACE_RIGHT = 63,
		MAX_MAP_X = 86,
		MAX_MAP_Y = 25,
		SCOREBOARD_LEFT = 67,
		SCOREBOARD_RIGHT = 82,
		SCOREBOARD_TOP = 3,
		SCOREBOARD_BOTTOM = 5,
		SCORE_TEXT_X = 69,
		SCORE_TEXT_Y = 4,
		SCORE_X = 77,
		SCORE_Y = 4,
		SIGN_X = 74,
		SIGN_Y = 23;
}

public class MainGame {
	private final int PLAYER_START_X = MAP_POSITION_INFO.GAME_SPACE_RIGHT/2;
	private final int PLAYER_START_Y = MAP_POSITION_INFO.MAX_MAP_Y - 5;
	
	private MapManager mapManager = MapManager.createManager();
	private ScreenFrame screenFrame = new ScreenFrame();
	private EnemyHandler enemyHandler = EnemyHandler.createEnemyHandler();
	private PlayerObject playerObj = new PlayerObject(PLAYER_START_X, PLAYER_START_Y);
	private static boolean isGameOver = false;
	
	public static void main(String[] args) {
		new MainGame();
		// 적 총알 발사
		// 재시작을 어떻게 할까..
	}
	
	public MainGame() {
		new Thread(new GameThread()).start();
		screenFrame.addKeyListener(new PlayerKeyListener(playerObj, mapManager));
		
	}
	
	public void resetGame() {
		isGameOver = false;
		playerObj.setPosX(PLAYER_START_X);
		playerObj.setPosY(PLAYER_START_Y);
		
		mapManager.insertObj(playerObj,playerObj.getImage());
		mapManager.resetScore();
	}
	
	public void createEnemies() {
		int enemyNum = 8;
		int enemyX = 10;
		int enemyY = 2;
		for(int i = 0; i < enemyNum; i++) {
			enemyHandler.addEnemy(new EnemyObject(enemyX,enemyY));
			enemyX += 10;
			if(enemyX > 45) {
				enemyX = 15;
				enemyY = 4;
			}
		}
	}
	
	public void restart() {
		new Thread(new GameThread()).start();
	}
	
	public static void setGameOver() {
		isGameOver = true;
	}
	
	public static boolean getGameOver() {
		return isGameOver;
	}
	
	class GameThread implements Runnable {

		@Override
		public void run() {
			mapManager.clearMap();
			resetGame();
			createEnemies();
			enemyHandler.moveAllEnemies();
			while(!isGameOver) {
				screenFrame.printAllMap(mapManager);
			}
			new Thread(new GameThread()).start();
			
		}
		
	}

}

abstract class GameObject{
	protected int posX;
	protected int posY;
	protected int objSize;
	protected String remove;
	protected String image;
	
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	
	public int getObjSize() {
		return objSize;
	}
	private void setObjSize() {
		objSize = image.length()/2;
	}
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	GameObject(int posX, int posY, String image){
		this.posX = posX;
		this.posY = posY;
		this.image = image;
		setObjSize();
		setRemove();
	}
	
	public String getRemove() {
		return remove;
	}
	private void setRemove() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < getImage().length(); i++) {
			sb.append(" ");
		}
		remove = sb.toString();
	}
}


class PlayerObject extends GameObject{
	private static final String PLAYER_IMAGE = ">-0-<";
	
	PlayerObject(int posX, int posY){
		super(posX, posY, PLAYER_IMAGE);
	}
}

class EnemyObject extends GameObject{
	private static final String ENEMY_IMAGE = "[XUX]";
	
	EnemyObject(int posX, int posY){
		super(posX, posY, ENEMY_IMAGE);
	}
}

class BulletObject extends GameObject{
	private static final String BULLET_IMAGE = "!";
	private MapManager mapManager = MapManager.createManager();
	private EnemyHandler enemyHandler = EnemyHandler.createEnemyHandler();
	
	BulletObject(int posX, int posY){
		super(posX, posY - 1, BULLET_IMAGE);
		shoot();
	}
	
	public void shoot() {
		new Thread(new Runnable() {
		    public void run() {
				for(int i = posY; i >= 0; i--) {
					if(mapManager.map[i][posX] != ' ') {
						enemyHandler.hitEnemy(posX, i);
						return;
					}
					mapManager.map[i][posX] = BULLET_IMAGE.charAt(0);
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					mapManager.map[i][posX] = ' ';
				}
		    }
		}).start();
	}
}


class MapManager{         //맵(char형 2차원 배열)에 관여하는 클래스
	static MapManager mapManager = null;
	private int gameScore = 0;
	private int killScore = 10;
	
	char[][] map = new char[MAP_POSITION_INFO.MAX_MAP_Y][MAP_POSITION_INFO.MAX_MAP_X];

	private MapManager(){}
	public static MapManager createManager () {
		if (mapManager == null) {
			mapManager = new MapManager();
			mapManager.createMap();
		}
		
		return mapManager;
	}
	
	private void createMap() {
		for (int i = 0; i < MAP_POSITION_INFO.MAX_MAP_Y; i++) {
			for (int j = 0; j <MAP_POSITION_INFO.MAX_MAP_X - 1; j++) {
				if( (j == MAP_POSITION_INFO.GAME_SPACE_LEFT) || (j == MAP_POSITION_INFO.GAME_SPACE_RIGHT) ) {
					map[i][j] = '#';
				}
				else if ((j < MAP_POSITION_INFO.GAME_SPACE_LEFT) || (j > MAP_POSITION_INFO.GAME_SPACE_RIGHT)) {
					map[i][j] = '.';
				}
				else {
					map[i][j] = ' ';
				}
			}
			map[i][MAP_POSITION_INFO.MAX_MAP_X-1] = '\n';
		}
		
		insertScoreboard();
		insertScore();
		insertSign();
	}
	

	
	public void insertObj(GameObject gameObj, String image) {
		char[] objChar = image.toCharArray();
		int objX = gameObj.getPosX();
		int objY = gameObj.getPosY();
		int objSize = gameObj.getObjSize();
		
		for(int i = 0; i < objChar.length; i++) {
			map[objY] [objX - objSize + i] = objChar[i];
		}
	}
	
	boolean tryInMap(GameObject gameObj, int x, int y) {         
		int objX = gameObj.getPosX() + x;			   
		int objY = gameObj.getPosY() + y;			   
		int objSize = gameObj.getObjSize();		  
		
		if ( (objX - objSize) < MAP_POSITION_INFO.GAME_SPACE_LEFT+1 || (objX + objSize) > MAP_POSITION_INFO.GAME_SPACE_RIGHT-1)  {
			return false;
		}
		
		if ( objY < 0 ||  objY > MAP_POSITION_INFO.MAX_MAP_Y - 1) {
			return false;
		}
		
		return true;
	}
	
	void insertScoreboard() {
		char[] scoreText = "SCORE:".toCharArray(); 
		map[MAP_POSITION_INFO.SCOREBOARD_TOP][MAP_POSITION_INFO.SCOREBOARD_LEFT] = '┌';
		map[MAP_POSITION_INFO.SCOREBOARD_TOP][MAP_POSITION_INFO.SCOREBOARD_RIGHT] = '┐';
		map[MAP_POSITION_INFO.SCOREBOARD_BOTTOM][MAP_POSITION_INFO.SCOREBOARD_LEFT] = '└';
		map[MAP_POSITION_INFO.SCOREBOARD_BOTTOM][MAP_POSITION_INFO.SCOREBOARD_RIGHT] = '┘';
		
		map[MAP_POSITION_INFO.SCOREBOARD_TOP+1][MAP_POSITION_INFO.SCOREBOARD_LEFT] = '│';
		map[MAP_POSITION_INFO.SCOREBOARD_TOP+1][MAP_POSITION_INFO.SCOREBOARD_RIGHT] = '│';
		
		for(int i = MAP_POSITION_INFO.SCOREBOARD_TOP; i <= MAP_POSITION_INFO.SCOREBOARD_BOTTOM; i += 2) {
			for(int j = MAP_POSITION_INFO.SCOREBOARD_LEFT + 1; j < MAP_POSITION_INFO.SCOREBOARD_RIGHT; j++) {
				map[i][j] = '─';
			}
		}
		
		for(int i = MAP_POSITION_INFO.SCOREBOARD_LEFT + 1; i < MAP_POSITION_INFO.SCOREBOARD_RIGHT; i++) {
			map[MAP_POSITION_INFO.SCOREBOARD_TOP + 1][i] = ' ';
		}
		
		for(int i = 0; i < scoreText.length; i++) {
			map[MAP_POSITION_INFO.SCORE_TEXT_Y][MAP_POSITION_INFO.SCORE_TEXT_X + i] = scoreText[i];
		}
	}
	
	void insertScore() {
		char[] cScore =  Integer.toString(gameScore).toCharArray();
		for (int i = 0; i < cScore.length; i++) {
			map[MAP_POSITION_INFO.SCORE_Y][MAP_POSITION_INFO.SCORE_X + i] = cScore[i];
		}
	}
	
	void insertSign() {
		char[] sign = "by P.Kim".toCharArray();
		
		for(int i = 0; i < sign.length; i++) {
			map[MAP_POSITION_INFO.SIGN_Y][MAP_POSITION_INFO.SIGN_X + i] = sign[i];
		}
	}
	
	public String getStringRow(int index) {
		return String.valueOf(map[index]);
	}
	
	public void addScore() {
		gameScore += killScore;
		insertScore();
	}
	
	public void resetScore() {
		int scoreSpace = 5;
		gameScore = 0;
				for (int i = 0; i < scoreSpace; i++) {
			map[MAP_POSITION_INFO.SCORE_Y][MAP_POSITION_INFO.SCORE_X + i] = ' ';
		}
	}
	
	public void clearMap() {
		for(int i = 0; i < MAP_POSITION_INFO.MAX_MAP_Y; i++) {
			for(int j = MAP_POSITION_INFO.GAME_SPACE_LEFT; j < MAP_POSITION_INFO.GAME_SPACE_RIGHT; j++) {
				map[i][j] = ' ';
			}
		}
	}
}

class PlayerKeyListener extends KeyAdapter{
	private PlayerObject playerObj =null;
	private MapManager mapManager = null; 
	
	private final Set<Integer> pressed = new TreeSet<Integer>();
	
	PlayerKeyListener(PlayerObject playerObj, MapManager mapManager){
		this.playerObj = playerObj;
		this.mapManager = mapManager;
		
		//JPanel panel = (JPanel) screenFrame.getContentPane();
		
		//panel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "topLeft");
		//panel.getActionMap().put("topLeft", topLeftPressed);
	}
	
	/*
	Action topLeftPressed = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(mapManager.tryInMap(playerObj,-1,-1)) {
				playerObj.setPosX(playerObj.getPosX() - 1);
				playerObj.setPosY(playerObj.getPosY() - 1);
				System.out.println("top left");
			}
		}
	};*/
	
	
	public void keyPressed (KeyEvent e) {
		int keyCode = e.getKeyCode();
		pressed.add(keyCode);
		mapManager.insertObj(playerObj,playerObj.getRemove());
		if(pressed.size() > 1) {
			Integer[] pressedArr = pressed.toArray(new Integer[] {});
	        if (pressedArr[0] == KeyEvent.VK_LEFT && pressedArr[1] == KeyEvent.VK_UP && mapManager.tryInMap(playerObj,-1,-1)) {
	        	playerObj.setPosX(playerObj.getPosX() - 1);
	        	playerObj.setPosY(playerObj.getPosY() - 1);
	        } else if (pressedArr[0] == KeyEvent.VK_UP && pressedArr[1] == KeyEvent.VK_RIGHT && mapManager.tryInMap(playerObj,+1,-1)) {
	        	playerObj.setPosX(playerObj.getPosX() + 1);
	        	playerObj.setPosY(playerObj.getPosY() - 1);
	        } else if (pressedArr[0] == KeyEvent.VK_RIGHT && pressedArr[1] == KeyEvent.VK_DOWN && mapManager.tryInMap(playerObj,+1,+1)) {
	        	playerObj.setPosX(playerObj.getPosX() + 1);
	        	playerObj.setPosY(playerObj.getPosY() + 1);
	        } else if (pressedArr[0] == KeyEvent.VK_LEFT && pressedArr[1] == KeyEvent.VK_DOWN && mapManager.tryInMap(playerObj,-1,1)) {
	        	playerObj.setPosX(playerObj.getPosX() - 1);
	        	playerObj.setPosY(playerObj.getPosY() + 1);
	        }
		}
		else {
			if(keyCode == KeyEvent.VK_LEFT && mapManager.tryInMap(playerObj,-1,0)) {
				playerObj.setPosX(playerObj.getPosX() - 1);
				System.out.println("left");
			}
			if(keyCode == KeyEvent.VK_RIGHT && mapManager.tryInMap(playerObj,1,0)) {
				playerObj.setPosX(playerObj.getPosX() + 1);
				System.out.println("right");
			}
			if(keyCode == KeyEvent.VK_UP && mapManager.tryInMap(playerObj,0,-1)) {
				playerObj.setPosY(playerObj.getPosY() - 1);
				System.out.println("up");
			}
			if(keyCode == KeyEvent.VK_DOWN && mapManager.tryInMap(playerObj,0,1)) {
				playerObj.setPosY(playerObj.getPosY() + 1);
				System.out.println("down");
			}
			if(keyCode == KeyEvent.VK_SPACE) {
				new BulletObject(playerObj.getPosX(),playerObj.getPosY());
				System.out.println("shot");
			}
			if(keyCode == KeyEvent.VK_Y) {
				if(MainGame.getGameOver()) {
					
				}
			}
		}
		mapManager.insertObj(playerObj,playerObj.getImage());
	}
	public void keyReleased(KeyEvent e) {
		pressed.remove(Integer.valueOf(e.getKeyCode()));
	}
}


class ScreenFrame extends JFrame{  //JFrame 출력에 관여하는 클래스.
	JTextArea jTextArea;
	Font font;
	//JPanel panel;
	ScreenFrame(){
		setSize(1460,930);
		setTitle("Let's Play Space Invaders");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		jTextArea = new JTextArea(25,85);
		font = new Font("Consolas",Font.PLAIN,30);

		jTextArea.setFont(font);
		jTextArea.setEnabled(false);
		jTextArea.setDisabledTextColor(Color.black);
		
		//panel = (JPanel) getContentPane();
	}
	
	public void add() {
		this.add(jTextArea);
	}
	
	public void printAllMap(MapManager mapManager) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < MAP_POSITION_INFO.MAX_MAP_Y; i++) {
			sb.append(mapManager.getStringRow(i));
		}
		jTextArea.setText(sb.toString());
		add();
		this.setVisible(true);
	}
	/*
	public JPanel getPanel() {
		return panel;
	}*/
}


class EnemyHandler{
	ArrayList<EnemyObject> enemies = new ArrayList<EnemyObject>();
	MapManager mapManager = MapManager.mapManager;
	static EnemyHandler enemyHandler = null;
	final int ENEMY_MOVE_X = 8;
	final int ENEMY_MOVE_Y = 1;
	final int ENEMY_SPEED = 500;
	
	private EnemyHandler(){}
	public static EnemyHandler createEnemyHandler () {
		if (enemyHandler == null) {
			enemyHandler = new EnemyHandler();
		}
		
		return enemyHandler;
	}
	
	
	public void addEnemy(EnemyObject enemy) {
		enemies.add(enemy);
		mapManager.insertObj(enemy,enemy.getImage());
	}
	
	public void moveAllEnemies() {
		new Thread(new Runnable() {
		    public void run() {
		    	while(!MainGame.getGameOver()) {
			    	moveAllX(1);
			    	moveAllY(1);
			    	moveAllX(-1);
			    	moveAllY(1);
		    	}
		    }
		}).start(); 

	}
	
	private void moveAllX(int direction) {
		for(int i = 0; i <ENEMY_MOVE_X; i++) {
			for(int j = 0; j < enemies.size(); j++) {
				EnemyObject enemy = enemies.get(j);
				mapManager.insertObj(enemy, enemy.getRemove());
				enemy.setPosX(enemy.getPosX() + direction);
				mapManager.insertObj(enemy, enemy.getImage());
			}
			try {
				Thread.sleep(ENEMY_SPEED);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void moveAllY(int direction) {
		for(int i = 0; i <ENEMY_MOVE_Y; i++) {
			for(int j = 0; j < enemies.size(); j++) {
				EnemyObject enemy = enemies.get(j);
				mapManager.insertObj(enemy, enemy.getRemove());
				enemy.setPosY(enemy.getPosY() + direction);
				mapManager.insertObj(enemy, enemy.getImage());
			}
			try {
				Thread.sleep(ENEMY_SPEED);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void hitEnemy(int x, int y) {
		EnemyObject enemy;
		for(int i = 0; i < enemies.size(); i++) {
			enemy = enemies.get(i);
			if(enemy.getPosY() == y) {
				if((enemy.getPosX() - enemy.getObjSize()) <= x && (enemy.getPosX() + enemy.getObjSize()) >= x) {
					mapManager.insertObj(enemy, enemy.getRemove());
					mapManager.addScore();
					enemies.remove(i);
					System.out.println("Hit");
					if(checkGameOver()) {
						MainGame.setGameOver();
					}
				}
			}
		}
	}
	
	public boolean checkGameOver() {
		if(enemies.isEmpty()) {
			return true;
		}
		return false;
	}
}

