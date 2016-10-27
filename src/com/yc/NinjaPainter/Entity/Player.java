package com.yc.NinjaPainter.Entity;

import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.AlphaModifier;
import org.anddev.andengine.entity.modifier.IEntityModifier;
import org.anddev.andengine.entity.modifier.LoopEntityModifier;
import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.modifier.RotationModifier;
import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.TiledSprite;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.util.modifier.IModifier;

import android.content.Context;

import com.yc.NinjaPainter.BaseActivity;
import com.yc.NinjaPainter.Constant;
import com.yc.NinjaPainter.GameActivity;
public class Player extends Entity{
	
	private Context context;
	private GameActivity activity;
	private Scene mScene;
	private int x,y;
	private TiledTextureRegion mPlayer,mStar;
	private AnimatedSprite mPlayerSprite;
	private BitmapTextureAtlas mBitmapTexture;
	private boolean isMove = true;  //当前是否可移动
	private int direction = Constant.LEFT;
	private int painter = Constant.NO_PAINTER;  //当前获得的刷子
	private MoveModifier mMoveModifier;
	
	public Player(Context context,Scene mScene,int pX,int pY) {
		this.context = context;
		this.activity = (GameActivity)this.context;
		this.mScene = mScene;
		this.x = pX;
		this.y = pY;
		this.init();
	}
	
	private void init(){
		this.mBitmapTexture = new BitmapTextureAtlas(1024, 1024, TextureOptions.DEFAULT);
		this.mPlayer = ((BaseActivity)this.context).createTTR(this.mBitmapTexture, "player.png",0, 0,20,2);
		this.mStar = ((BaseActivity)this.context).createTTR(this.mBitmapTexture, "player_star.png",0, 116,4,1);
		((BaseActivity)this.context).loadTR(this.mBitmapTexture );
		
		this.mPlayerSprite = new AnimatedSprite(0,0,this.mPlayer);
    	this.mScene.attachChild(this.mPlayerSprite);
    	this.up();    	
	}

	public void setPos(int pX,int pY){
		this.x = pX;
		this.y = pY;		
	}
	
	public int getPosX(){
		return this.x;
	}
	public int getPosY(){
		return this.y;
	}
	
	public void ladder(){
		this.mPlayerSprite.reset();
		this.mPlayerSprite.setPosition(this.getUpPosX(this.x), this.getUpPosY(this.y));
		long[] duration = new long[20];
		java.util.Arrays.fill(duration,20);
		this.mPlayerSprite.animate(duration, 20, 39, true);
		this.direction = Constant.UP;
	}
	
	public void up(){
		this.mPlayerSprite.reset();
		this.mPlayerSprite.setPosition(this.getUpPosX(this.x), this.getUpPosY(this.y));
		long[] duration = new long[20];
		java.util.Arrays.fill(duration,20);
		this.mPlayerSprite.animate(duration, 0, 19, true);	
		this.direction = Constant.UP;
	}
	
	public void down(){
		this.up();
		this.mPlayerSprite.setRotation(180);
		this.mPlayerSprite.setPosition(this.getDownPosX(this.x), this.getDownPosY(this.y));
		this.direction = Constant.DOWN;
	}
	
	public void left(){
		this.up();
		this.mPlayerSprite.setRotation(-90);
		this.mPlayerSprite.setPosition(this.getLeftPosX(this.x), this.getLeftPosY(this.y));
		this.direction = Constant.LEFT;
	}
	
	public void right(){
		this.up();
		this.mPlayerSprite.setRotation(90);
		this.mPlayerSprite.setPosition(this.getRightPosX(this.x), this.getRightPosY(this.y));
		this.direction = Constant.RIGHT;
	}
	
	private float getUpPosX(int fX){
		return this.activity.getWallPosX(fX);
	}
	
	private float getUpPosY(int fY){
		return this.activity.getWallPosY(fY)+this.activity.getWallHeight()-this.getHeight();
	}
	
	private float getDownPosX(int fX){
		return this.activity.getWallPosX(fX);
	}
	
	private float getDownPosY(int fY){
		return this.activity.getWallPosY(fY);
	}
	
	private float getLeftPosX(int fX){
		return this.activity.getWallPosX(fX)+this.activity.getWallWidth()-this.getWidth()/2-this.getHeight()/2;
	}
	
	private float getLeftPosY(int fY){
		return this.activity.getWallPosY(fY)+this.activity.getWallHeight()-this.getWidth()/2-this.getHeight()/2;
	}
	
	private float getRightPosX(int fX){
		return this.activity.getWallPosX(fX)+this.getHeight()/2-this.getWidth()/2;
	}
	
	private float getRightPosY(int fY){
		return this.activity.getWallPosY(fY)+this.activity.getWallHeight()-this.getWidth()/2-this.getHeight()/2;
	}
	
	
	private float getMovePosX(int fX){
		float posX = 0;
		switch(this.direction){
			case Constant.UP:
			case Constant.LADDER:
				posX = this.getUpPosX(fX);
				break;
			case Constant.DOWN:
				posX = this.getDownPosX(fX);
				break;
			case Constant.LEFT:
				posX = this.getLeftPosX(fX);
				break;
			case Constant.RIGHT:
				posX = this.getRightPosX(fX);
				break;	
		}
		return posX;
	}
	
	private float getMovePosY(int fY){
		float posY = 0;
		switch(this.direction){
			case Constant.UP:
			case Constant.LADDER:
				posY = this.getUpPosY(fY);
				break;
			case Constant.DOWN:
				posY = this.getDownPosY(fY);
				break;
			case Constant.LEFT:
				posY = this.getLeftPosY(fY);
				break;
			case Constant.RIGHT:
				posY = this.getRightPosY(fY);
				break;	
		}
		return posY;
	}
	
	public void stopMove(){
		if(this.mMoveModifier!=null){
			this.mPlayerSprite.unregisterEntityModifier(this.mMoveModifier);
			this.mMoveModifier = null;
		}
	}
	
	public void moveTo(final int tX,final int tY){
		this.stopMove();
		Player.this.setPos(tX, tY);	
		this.mMoveModifier = new MoveModifier(0.1f, this.getMovePosX(this.x), this.getMovePosX(tX), this.getMovePosY(this.y),this.getMovePosY(tY),new IEntityModifier.IEntityModifierListener(){
			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier,IEntity pItem) {}
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier,IEntity pItem) {
				((GameActivity)Player.this.context).movePlayer();
			}
			
		});
		this.mPlayerSprite.registerEntityModifier(this.mMoveModifier);
	}
	
	public boolean isMove(){
		return this.isMove;
	}
	
	public void setMove(boolean isMove){
		this.isMove = isMove;
	}
	
	public float getWidth(){
		return this.mPlayerSprite.getWidth();
	}
	
	public float getHeight(){
		return this.mPlayerSprite.getHeight();
	}
	
	//方向
	public void turn(){
		switch(this.direction){
			case Constant.UP:
				this.down();
				break;
			case Constant.DOWN:
				this.up();
				break;
			case Constant.LEFT:
				this.right();
				break;
			case Constant.RIGHT:
				this.left();
				break;
		}
	}


	public void setPainter(int painter) {
		this.painter = painter;		
		//加入刷子标识星星
		TiledSprite starSprite = new TiledSprite(7,7,this.mStar.clone());
		switch(this.painter){
			case Constant.RED_PAINTER:
				starSprite.setCurrentTileIndex(0);
				break;
			case Constant.BLUE_PAINTER:
				starSprite.setCurrentTileIndex(1);
				break;
			case Constant.GREEN_PAINTER:
				starSprite.setCurrentTileIndex(2);
				break;
			case Constant.PINK_PAINTER:
				starSprite.setCurrentTileIndex(3);
				break;
		}
		starSprite.clearEntityModifiers();
		final LoopEntityModifier entityModifier = new LoopEntityModifier(
				new SequenceEntityModifier(
						new RotationModifier(0.3f, 0, 360),
						new AlphaModifier(0.3f, 1, 0.5f),
						new AlphaModifier(0.3f, 0.5f, 1),
						new ScaleModifier(0.3f, 1, 0.5f),
						new ScaleModifier(0.3f, 0.5f, 1f)
				)
		);
		starSprite.registerEntityModifier(entityModifier);
		this.mPlayerSprite.attachChild(starSprite);
	}

	public int getPainter() {
		return this.painter;
	}
	
	public void setDirection(int dir){
		this.direction = dir;
	}
	
	public int getDirection() {
		return this.direction;
	}
}