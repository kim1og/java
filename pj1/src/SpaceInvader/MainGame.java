package SpaceInvader;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import javax.swing.JFrame;
import javax.swing.JTextArea;

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
	public static void main(String[] args) {
		MapManager mapManager = MapManager.createManager();
		ScreenFrame screenFrame = new ScreenFrame();
		
		final int PLAYER_START_X = MAP_POSITION_INFO.GAME_SPACE_RIGHT/2;
		final int PLAYER_START_Y = MAP_POSITION_INFO.MAX_MAP_Y - 5;
		int enemyNum = 8;
		int enemyX = 10;
		int enemyY = 2;
		
		PlayerObject playerObj = new PlayerObject(PLAYER_START_X, PLAYER_START_Y);
		//new PlayerKeyListener(playerObj);
		EnemyObject[] enemies = new EnemyObject[enemyNum]; 
		
		for(int i = 0; i < enemyNum; i++) {
			enemies[i] = new EnemyObject(enemyX,enemyY);
			enemyX += 10;
			if(enemyX > 45) {
				enemyX = 15;
				enemyY = 4;
			}
			mapManager.insertObj(enemies[i],enemies[i].ENEMY_IMAGE);
		}
		
		mapManager.insertObj(playerObj,playerObj.PLAYER_IMAGE);
		
		screenFrame.printAllMap(mapManager);
		screenFrame.addKeyListener(new PlayerKeyListener(playerObj, mapManager, screenFrame));
		
		//방향키 추가하기.
		//총알 발사는 함수로 따로
	}
}

class GameObject{
	int posX;
	int posY;
	int objSize;

	String image;
	
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
	public void setObjSize(int objSize) {
		this.objSize = objSize;
	}
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	GameObject(int posX, int posY){
		this.posX = posX;
		this.posY = posY;
	}
}


class PlayerObject extends GameObject{
	final String PLAYER_IMAGE = ">-0-<";
	final int PLAYER_SIZE = 2;
	final String REMOVE_PLAYER = "     ";
	
	PlayerObject(int posX, int posY){
		super(posX, posY);
		super.setObjSize(PLAYER_SIZE);
		super.setImage(PLAYER_IMAGE);
	}
}

class EnemyObject extends GameObject{
	final String ENEMY_IMAGE = "[XUX]";
	final int ENEMY_SIZE = 2;
	
	EnemyObject(int posX, int posY){
		super(posX, posY);
		super.setObjSize(ENEMY_SIZE);
		super.setImage(ENEMY_IMAGE);
	}
}

class bulletObject extends GameObject{
	final String BULLET_IMAGE = "!";
	final int BULLET_SIZE = 0;
	
	bulletObject(int posX, int posY){
		super(posX, posY);
		super.setObjSize(BULLET_SIZE);
		super.setImage(BULLET_IMAGE);
	}
}


class MapManager{         //맵(char형 2차원 배열)에 관여하는 클래스
	static MapManager mapManager = null;
	
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
		insertScore(0);
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
	
	boolean tryInMap(GameObject gameObj) {         
		int objX = gameObj.getPosX();			   
		int objY = gameObj.getPosY();			   
		int objSize = gameObj.getObjSize();		  
		
		if ( (objX - objSize) < MAP_POSITION_INFO.GAME_SPACE_LEFT+1 || (objX + objSize) > MAP_POSITION_INFO.GAME_SPACE_RIGHT-1)  {
			return false;
		}
		
		if ( objY < 0 ||  objY > MAP_POSITION_INFO.MAX_MAP_Y) {
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
	
	void insertScore(int score) {
		char[] cScore =  Integer.toString(score).toCharArray();
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
}

class PlayerKeyListener extends KeyAdapter{
	PlayerObject playerObj =null;
	MapManager mapManager = null; 
	ScreenFrame screenFrame = null;
	
	PlayerKeyListener(PlayerObject playerObj, MapManager mapManager, ScreenFrame screenFrame){
		this.playerObj = playerObj;
		this.mapManager = mapManager;
		this.screenFrame = screenFrame;
	}
	
	public void keyPressed (KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if(keyCode == KeyEvent.VK_LEFT && mapManager.tryInMap(playerObj)) {
			mapManager.insertObj(playerObj,playerObj.REMOVE_PLAYER);
			playerObj.setPosX(playerObj.getPosX() - 1);
			mapManager.insertObj(playerObj,playerObj.PLAYER_IMAGE);
			screenFrame.printAllMap(mapManager);
			System.out.println("left");
		}
	}
	public void keyReleased() {}
}


class ScreenFrame extends JFrame{  //JFrame 출력에 관여하는 클래스.
	JTextArea jTextArea;
	Font font;

	ScreenFrame(){
		super.setSize(1460,930);
		super.setTitle("Let's Play Space Invaders");
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		jTextArea = new JTextArea(25,85);
		font = new Font("Consolas",Font.PLAIN,30);

		jTextArea.setFont(font);
		jTextArea.setEnabled(false);
		jTextArea.setDisabledTextColor(Color.black);
	}
	
	public void add() {
		this.add(jTextArea);
	}
	
	public void printAllMap(MapManager mapManager) {
		for(int i = 0; i < MAP_POSITION_INFO.MAX_MAP_Y; i++) {
			jTextArea.append(mapManager.getStringRow(i));
		}
		
		add();
		this.setVisible(true);
	}
}

