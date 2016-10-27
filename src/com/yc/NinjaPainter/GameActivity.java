package com.yc.NinjaPainter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.anddev.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.anddev.andengine.engine.camera.hud.controls.DigitalOnScreenControl;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.SpriteBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import android.graphics.Color;

import com.yc.NinjaPainter.Data.LevelMap;
import com.yc.NinjaPainter.Entity.Player;
import com.yc.NinjaPainter.Entity.Wall;
import com.yc.NinjaPainter.Entity.Wall_7;


public class GameActivity extends BaseActivity {
	private static final int  ROWS = 12;
	private static final int COLS = 17;
	private int level = 1;
	private int stage = 1;
	private int[][] levelMap = new int[ROWS][COLS];	
	private BitmapTextureAtlas mBitmapTexture;
	private TextureRegion mBackgroundTextureRegion,mMaskTR,mWinTR,mFailTR;
	private BitmapTextureAtlas mOnScreenControlTexture;
	private TextureRegion mOnScreenControlBaseTextureRegion;
	private TextureRegion mOnScreenControlKnobTextureRegion;
	static private HashMap<String, Wall> wallHash = new HashMap<String,Wall>();
	private Player mPlayer;
	private int mDoorX = 0,mDoorY = 0;
	private float ctlX=0,ctlY=0;
	private boolean isCtr = true; //控制是否可用
	private int score = 0;  //分数
	private int star = 0;   //星星
	private int type = 0; //上一个位置的墙类型
	private int count = 0; //计算同类型墙的连续次数
	private BitmapTextureAtlas mFontTexture;
	private Font mFont;
	private ChangeableText starText;
	private ChangeableText scoreText;
	private BitmapTextureAtlas mFontTexture2;
	private Font mFont2;
	private TextureRegion mBottomTR;
	private DigitalOnScreenControl analogOnScreenControl;


	@Override
	public Engine onLoadEngine() {
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new FillResolutionPolicy(), this.mCamera).setNeedsSound(true).setNeedsMusic(true));
	}

	@Override
	public void onLoadResources() {
		this.level = this.getIntent().getExtras().getInt("level");
		if(this.level<=10){
			this.stage = 1;
		}else if(this.level<=20){
			this.stage = 2;
		}else{
			this.stage = 3;
		}
		this.mBitmapTexture = new BitmapTextureAtlas(1024, 1024, TextureOptions.DEFAULT);
		this.mBackgroundTextureRegion = this.createTR(this.mBitmapTexture , "bg"+this.stage+".jpg", 0, 0);
		this.mBottomTR = this.createTR(this.mBitmapTexture , "bottom_bg.png", 0, 480);
		this.mMaskTR = this.createTR(this.mBitmapTexture , "mask.png", 400, 480);
		this.mWinTR = this.createTR(this.mBitmapTexture , "win.png", 410, 480);
		this.mFailTR = this.createTR(this.mBitmapTexture , "fail.png", 660, 480);
		this.loadTR(this.mBitmapTexture);
		
		this.mOnScreenControlTexture = new BitmapTextureAtlas(256, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mOnScreenControlBaseTextureRegion = this.createTR(this.mOnScreenControlTexture, "onscreen_control_base.png", 0, 0);
		this.mOnScreenControlKnobTextureRegion = this.createTR(this.mOnScreenControlTexture, "onscreen_control_knob.png", 128, 0);
		
		this.loadTR(this.mOnScreenControlTexture);
		
		this.mFontTexture = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFont = this.createFont(this.mFontTexture, "font/droid.ttf", 18, true, Color.YELLOW);
		
		this.loadTR(this.mFontTexture);
		this.loadFont(this.mFont);
		
		this.mFontTexture2 = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFont2 = this.createFont(this.mFontTexture2, "font/droid.ttf", 18, true, Color.RED);
		
		this.loadTR(this.mFontTexture2);
		this.loadFont(this.mFont2);
		
		this.setMusic(this.createMusic("sound/music_stage_"+this.stage+".mp3"));
	}

	@Override
	public Scene onLoadScene() {		
		this.mScene = new Scene();		
		this.mScene.setTouchAreaBindingEnabled(true);
		this.mScene.setBackground(new SpriteBackground(new Sprite(0,0,this.mBackgroundTextureRegion)));

		this.initMap();
		
		this.initCtr();
		
		this.initPlayer();
		
		this.initText();
		
		return this.mScene;
	}
	
	
	private void initText(){
		
		this.mScene.attachChild(new Sprite(158,440,this.mBottomTR));
		
		this.mScene.attachChild(new Text(190,450,this.mFont,"星星:"));
		this.mScene.attachChild(new Text(255,450,this.mFont,"关卡:"));
		this.mScene.attachChild(new Text(330,450,this.mFont,"分数:"));
		this.mScene.attachChild(new Text(435,450,this.mFont,"最高:"));
		
		this.mScene.attachChild(new Text(300,450,this.mFont2,this.level+""));
		this.mScene.attachChild(new Text(480,450,this.mFont2,this.store.getInt("score_"+(this.level-1))+""));
		
		this.starText = new ChangeableText(235,450,this.mFont2,"0",1);
		this.scoreText = new ChangeableText(375,450,this.mFont2,"0",5);
		
		this.mScene.attachChild(this.starText);
		this.mScene.attachChild(this.scoreText);
	}
	
	private void initMap(){
		this.levelMap = LevelMap.getLevelMap(this.level);
		for(int i=0;i<this.levelMap.length;i++){
			for(int j=0;j<this.levelMap[0].length;j++){
				Wall wall = Wall.getWall(this.levelMap[i][j]);
				wall.init(this, this.mScene,this.getWallPosX(j),this.getWallPosY(i));
				wallHash.put(j+"_"+i, wall);
			}
		}
	}
	
	public float getWallWidth(){
		return this.getCameraWidth()/COLS;
	}
	
	public float getWallHeight(){
		return this.getCameraHeight()/ROWS;
	}
	
	public float getWallPosX(int j){
		return  j*this.getWallWidth();
	}
	
	public float getWallPosY(int i){
		return  i*this.getWallHeight();
	}
	
	public int getRows(){
		return ROWS;
	}
	
	public int getCols(){
		return COLS;
	}
	
	private void initCtr(){
		 this.analogOnScreenControl = new DigitalOnScreenControl(0, this.getCameraHeight() - this.mOnScreenControlBaseTextureRegion.getHeight(), this.mCamera, this.mOnScreenControlBaseTextureRegion, this.mOnScreenControlKnobTextureRegion, 0.1f, new IAnalogOnScreenControlListener() {
			@Override
			public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {
				if(GameActivity.this.isCtr && (pValueX!=0 || pValueY!=0)){
					GameActivity.this.setCtr(false);
					GameActivity.this.mPlayer.setMove(true);
					GameActivity.this.ctlX = pValueX;
					GameActivity.this.ctlY = pValueY;
					GameActivity.this.runOnUpdateThread(new Runnable(){
						@Override
						public void run() {
							GameActivity.this.movePlayer();
						}						
					});
				}				
			}
			@Override
			public void onControlClick(final AnalogOnScreenControl pAnalogOnScreenControl) {
				
			}
		});
		this.analogOnScreenControl.getControlBase().setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.analogOnScreenControl.getControlBase().setAlpha(0.55f);
		this.analogOnScreenControl.getControlBase().setScaleCenter(0,128);
		this.analogOnScreenControl.getControlBase().setScale(1.25f);
		this.analogOnScreenControl.getControlKnob().setScale(1.25f);
		this.analogOnScreenControl.getControlKnob().setAlpha(0.5f);
		this.analogOnScreenControl.refreshControlKnobPosition();
		this.mScene.setChildScene(this.analogOnScreenControl);		
	}
	
	private void initPlayer(){
		this.getDoorPos();
		this.mPlayer = new Player(this, this.mScene,this.mDoorX, this.mDoorY);
	}
	
	private void getDoorPos(){
		for(int i=0;i<this.levelMap.length;i++){
			for(int j=0;j<this.levelMap[0].length;j++){
				if(this.levelMap[i][j]==7){
					this.mDoorX = j;
					this.mDoorY = i;
				}
			}
		}
	}
		
	public void movePlayer() {
		if (this.mPlayer.isMove()) {
			// 获得当前位置
			int pX = this.mPlayer.getPosX();
			int pY = this.mPlayer.getPosY();
			int toX = (int) (pX + this.ctlX);
			int toY = (int) (pY + this.ctlY);				
			
			if(toX<0 || toY<0 || toX>COLS-1 || toY>ROWS-1){
				//出界 直接死亡
				this.mPlayer.setMove(false);
				this.onFailGame();
				return;
			}
			
			// 起始方向
			if (this.ctlX < 0) {
				this.mPlayer.left();
			} else if (this.ctlX > 0) {
				this.mPlayer.right();
			} else if (this.ctlY < 0) {
				this.mPlayer.up();
			} else if (this.ctlY > 0) {
				this.mPlayer.down();
			}
			
			if (this.isCanMove(toX, toY)) {		
				this.setType(this.levelMap[toY][toX]);
				this.mPlayer.moveTo(toX, toY);
			}

			Wall wall = wallHash.get(toX + "_" + toY);
			int eventId = wall.move(this.mPlayer);
			switch (eventId) {
				case Constant.STAY: // 停住
					this.setCtr(true);
					this.mPlayer.setMove(false);
					break;
				case Constant.LADDER: // 停住
					this.setCtr(true);
					this.mPlayer.setMove(false);
					break;
				case Constant.DIE: // 死亡
					this.mPlayer.setMove(false);
					this.onFailGame();
					break;
				case Constant.FINISH: // 结束
					this.mPlayer.setMove(false);
					this.onWinGame();					
					break;
				case Constant.PAINTING: // 刷墙
					// 检测是否刷完所有墙，刷完则开门
					this.checkPainted();
					break;
				case Constant.LEFT: // 转向左
					this.ctlX = -1;
					this.ctlY = 0;
					break;
				case Constant.RIGHT: // 转向右
					this.ctlX = 1;
					this.ctlY = 0;
					break;
				case Constant.UP: // 转向上
					this.ctlX = 0;
					this.ctlY = -1;
					break;
				case Constant.DOWN: // 转向下
					this.ctlX = 0;
					this.ctlY = 1;
					break;
			}
		}
	}
	
	private boolean isCanMove(int fX,int fY){			
		Wall wall = wallHash.get(fX+"_"+fY);
		return wall.check(this.mPlayer);
	}
	
	@SuppressWarnings("rawtypes")
	private void checkPainted(){
		Collection walls = wallHash.values();
		for(Iterator iterator=walls.iterator();iterator.hasNext();){
			Wall wall = (Wall) iterator.next();
			if(wall.isPainterWall() && !wall.isPainted()){
				return;
			}
		}
		//开门
		Wall.openDoor((Wall_7) wallHash.get(this.mDoorX+"_"+this.mDoorY));
	}
	
	public void setCtr(boolean isCtr){
		this.isCtr = isCtr;
	}
	
	public void addScore(int score){
		this.score += score;
		this.scoreText.setText(this.score+"");
	}
	
	public void addStar(){
		this.star++;
		this.starText.setText(this.star+"");
	}

	public void setType(int type) {
		if(this.type==type){
			this.count++;
		}else{
			this.count=0;
		}
		this.type = type;
	}
	
	//闯关成功
	private void onWinGame(){
		if(this.score>this.store.getInt("score_"+(this.level-1))){
			this.store.set("score_"+(this.level-1),this.score);  //保存分数
		}
		if(this.star>this.store.getInt("star_"+(this.level-1))){
			this.store.set("star_"+(this.level-1),this.star);  //保存星星数
		}		
		
		this.mScene.clearEntityModifiers();
		this.mScene.clearTouchAreas();
		this.mScene.clearUpdateHandlers();
		this.analogOnScreenControl.clearTouchAreas();
		
		this.mScene.attachChild(new Sprite(0,0,this.getCameraWidth(),this.getCameraHeight(),this.mMaskTR));
		Sprite winSprite = new Sprite((this.getCameraWidth()-this.mWinTR.getWidth())/2,(this.getCameraHeight()-this.mWinTR.getHeight())/2,this.mWinTR){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY){
				GameActivity.this.finish();
				return true;
			}
		};
		this.mScene.attachChild(winSprite);
		this.mScene.registerTouchArea(winSprite);
	}
	
	//闯关失败
	private void onFailGame(){
		this.mScene.clearEntityModifiers();
		this.mScene.clearTouchAreas();
		this.mScene.clearUpdateHandlers();
		this.analogOnScreenControl.clearTouchAreas();
		
		this.mScene.attachChild(new Sprite(0,0,this.getCameraWidth(),this.getCameraHeight(),this.mMaskTR));
		Sprite failSprite = new Sprite((this.getCameraWidth()-this.mFailTR.getWidth())/2,(this.getCameraHeight()-this.mFailTR.getHeight())/2,this.mFailTR){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY){
				GameActivity.this.finish();
				return true;
			}
		};
		this.mScene.attachChild(failSprite);
		this.mScene.registerTouchArea(failSprite);
	}

	public int getType() {
		return this.type;
	}

	public int getCount() {
		return this.count;
	}
	
	public int getStage(){
		return this.stage;
	}
}