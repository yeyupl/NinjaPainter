package com.yc.NinjaPainter.Entity;

import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import com.yc.NinjaPainter.BaseActivity;
import com.yc.NinjaPainter.Constant;

public class Wall_9 extends Wall{

	private BitmapTextureAtlas mBitmapTexture;
	private TextureRegion mWall;
	private Sprite mWallSprite;
	
	@Override
	protected void loadResources(){
		this.mBitmapTexture = new BitmapTextureAtlas(256, 256, TextureOptions.DEFAULT);
		this.mWall = ((BaseActivity)this.context).createTR(this.mBitmapTexture, "ladder.png", 0, 0);
		((BaseActivity)this.context).loadTR(this.mBitmapTexture );
	};
	 
	@Override
    protected void drawWall(){
    	this.mWallSprite = new Sprite(this.x,this.y,this.mWall);
    	this.mScene.attachChild(this.mWallSprite);
	};
	 
	@Override
	public int move(Player mPlayer){
		mPlayer.ladder();
		return Constant.LADDER;
	}
}