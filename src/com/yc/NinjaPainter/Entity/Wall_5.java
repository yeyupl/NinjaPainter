package com.yc.NinjaPainter.Entity;

import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import com.yc.NinjaPainter.BaseActivity;
import com.yc.NinjaPainter.Constant;
import com.yc.NinjaPainter.GameActivity;

public class Wall_5 extends Wall{

	private BitmapTextureAtlas mBitmapTexture;
	private TextureRegion mWall,mWallBlank;
	private Sprite mWallSprite,mWallBlankSprite;

	@Override
	protected void loadResources(){
		this.mBitmapTexture = new BitmapTextureAtlas(256, 256, TextureOptions.DEFAULT);
		this.mWall = ((BaseActivity)this.context).createTR(this.mBitmapTexture, "wall_left_top_"+((GameActivity)this.context).getStage()+".png", 0, 0);
		this.mWallBlank = ((BaseActivity)this.context).createTR(this.mBitmapTexture, "wall_blank_"+((GameActivity)this.context).getStage()+".png", 44, 0);
		((BaseActivity)this.context).loadTR(this.mBitmapTexture );
	};
	 
	@Override
    protected void drawWall(){
		this.mWallBlankSprite = new Sprite(this.x,this.y,this.mWallBlank);
		this.mScene.attachChild(this.mWallBlankSprite);
	
    	this.mWallSprite = new Sprite(this.x,this.y,this.mWall);
    	this.mScene.attachChild(this.mWallSprite);
	};
	 
	@Override
	public int move(Player mPlayer){
		switch(mPlayer.getDirection()){
			case Constant.UP:
				mPlayer.right();
				return Constant.RIGHT;
			case Constant.LEFT:
				mPlayer.down();
				return Constant.DOWN;
			default:
				mPlayer.turn();
				return Constant.STAY;
		}
	}

	@Override
	public boolean check(Player mPlayer) {
		if(mPlayer.getDirection()==Constant.LEFT || mPlayer.getDirection()==Constant.UP){
			return true;
		}
		return false;
	};

}