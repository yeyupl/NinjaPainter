package com.yc.NinjaPainter.Entity;

import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.TiledSprite;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.yc.NinjaPainter.BaseActivity;
import com.yc.NinjaPainter.Constant;

public class Wall_7 extends Wall{

	private BitmapTextureAtlas mBitmapTexture;
	private TiledTextureRegion mDoor,mTips;
	private TiledSprite mDoorSprite;
	private AnimatedSprite mTipsSprite;
		
	@Override
	protected void loadResources(){
		this.mBitmapTexture = new BitmapTextureAtlas(1024, 1024, TextureOptions.DEFAULT);
		this.mDoor = ((BaseActivity)this.context).createTTR(this.mBitmapTexture, "door.png", 0, 0,2,1);
		this.mTips = ((BaseActivity)this.context).createTTR(this.mBitmapTexture, "door_tips.png", 0,44,28,1);
		((BaseActivity)this.context).loadTR(this.mBitmapTexture );
	};
	 
	@Override
    protected void drawWall(){
		this.isDoor = true;
		
    	this.mDoorSprite = new TiledSprite(this.x,this.y,this.mDoor);
    	this.mScene.attachChild(this.mDoorSprite);
	};
	 
	@Override
	public int move(Player mPlayer){
		if(this.mDoorSprite.getCurrentTileIndex()==1){
			return Constant.FINISH;
		}
		return Constant.GOON;
	};
	
	
	public void open(){
		this.isOpend = true;
		this.mDoorSprite.setCurrentTileIndex(1);
		this.mTipsSprite = new AnimatedSprite(this.x+11,this.y-20,this.mTips);
		this.mTipsSprite.animate(50,true);
    	this.mScene.attachChild(this.mTipsSprite);
	}
}