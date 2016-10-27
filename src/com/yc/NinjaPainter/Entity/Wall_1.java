package com.yc.NinjaPainter.Entity;

import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import com.yc.NinjaPainter.BaseActivity;
import com.yc.NinjaPainter.Constant;
import com.yc.NinjaPainter.GameActivity;

public class Wall_1 extends Wall{

	private BitmapTextureAtlas mBitmapTexture;
	private TextureRegion mWall;
	private Sprite mWallSprite;

	@Override
	protected void loadResources(){
		this.mBitmapTexture = new BitmapTextureAtlas(256, 256, TextureOptions.DEFAULT);
		this.mWall = ((BaseActivity)this.context).createTR(this.mBitmapTexture, "wall_"+((GameActivity)this.context).getStage()+".png", 0, 0);
		((BaseActivity)this.context).loadTR(this.mBitmapTexture );
	};
	 
	@Override
    protected void drawWall(){
    	this.mWallSprite = new Sprite(this.x,this.y,this.mWall);
    	this.mScene.attachChild(this.mWallSprite);
	};
	 
	@Override
	public int move(Player mPlayer){
		 mPlayer.turn();
		 return Constant.STAY;
	}

	@Override
	public boolean check(Player mPlayer) {
		return false;
	};

}