package com.yc.NinjaPainter.Entity;

import org.anddev.andengine.entity.sprite.TiledSprite;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.yc.NinjaPainter.BaseActivity;
import com.yc.NinjaPainter.Constant;
import com.yc.NinjaPainter.GameActivity;

public class Wall_19 extends Wall{

	private BitmapTextureAtlas mBitmapTexture;
	private TiledTextureRegion mWall;
	private TiledSprite mWallSprite;
	
	@Override
	protected void loadResources(){
		this.mBitmapTexture = new BitmapTextureAtlas(256, 256, TextureOptions.DEFAULT);
		this.mWall = ((BaseActivity)this.context).createTTR(this.mBitmapTexture, "wall_edge_"+((GameActivity)this.context).getStage()+".png", 0, 0,2,1);
		((BaseActivity)this.context).loadTR(this.mBitmapTexture );
	};
	 
	@Override
    protected void drawWall(){
    	this.mWallSprite = new TiledSprite(this.x,this.y,this.mWall);
    	this.mScene.attachChild(this.mWallSprite);
    	if(this.y==0 || this.y == ((GameActivity)this.context).getWallPosY(((GameActivity)this.context).getRows()-1)){
    		this.mWallSprite.setCurrentTileIndex(1);
    	}
	};
	 
	@Override
	public int move(Player mPlayer){
		if(this.x==0 || this.y==0 || this.x==((GameActivity)this.context).getWallPosX(((GameActivity)this.context).getCols()-1) || this.y==((GameActivity)this.context).getWallPosY(((GameActivity)this.context).getRows()-1)){
			//边缘死亡
			return Constant.DIE;
		}
		return Constant.GOON;
	}
	
}