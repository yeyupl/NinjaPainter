package com.yc.NinjaPainter.Entity;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.RotationByModifier;
import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.util.modifier.SequenceModifier.ISubSequenceModifierListener;

import com.yc.NinjaPainter.BaseActivity;
import com.yc.NinjaPainter.Constant;
import com.yc.NinjaPainter.GameActivity;

public class Wall_8 extends Wall{

	private BitmapTextureAtlas mBitmapTexture;
	private TextureRegion mWall;
	private TiledTextureRegion mStar;
	private Sprite mWallSprite;
	private AnimatedSprite mStarSprite;
	private boolean isClear = false;
	
	@Override
	protected void loadResources(){
		this.mBitmapTexture = new BitmapTextureAtlas(1024, 1024, TextureOptions.DEFAULT);
		this.mWall = ((BaseActivity)this.context).createTR(this.mBitmapTexture, "wall_blank_"+((GameActivity)this.context).getStage()+".png", 0, 0);
		this.mStar = ((BaseActivity)this.context).createTTR(this.mBitmapTexture, "star_glod.png", 44, 0,12,1);
		((BaseActivity)this.context).loadTR(this.mBitmapTexture );
	};
	 
	@Override
    protected void drawWall(){
    	this.mWallSprite = new Sprite(this.x,this.y,this.mWall);
    	this.mScene.attachChild(this.mWallSprite);
    	
    	this.mStarSprite = new AnimatedSprite(this.x,this.y,this.mStar);
    	this.mStarSprite.animate(100, true);
    	this.mScene.attachChild(this.mStarSprite);
    	
	}
	 
	@Override
	public int move(Player mPlayer){
		if(this.isClear){
			return Constant.GOON;
		}else{
			SequenceEntityModifier modifier = new SequenceEntityModifier(
					new RotationByModifier(0.5f, 360),
					new ScaleModifier(0.5f, 1, 0)					
			);
			modifier.setRemoveWhenFinished(true);
			modifier.setSubSequenceModifierListener(new ISubSequenceModifierListener<IEntity>(){
				@Override
				public void onSubSequenceStarted(IModifier<IEntity> pModifier,IEntity pItem, int pIndex) {					
				}
				@Override
				public void onSubSequenceFinished(IModifier<IEntity> pModifier,IEntity pItem, int pIndex) {
					((BaseActivity)Wall_8.this.context).runOnUpdateThread(new Runnable(){
						@Override
						public void run() {
							Wall_8.this.mStarSprite.detachSelf();
						}				
					});	
				}
				
			});
			this.mStarSprite.registerEntityModifier(modifier);
			this.isClear = true;
			((GameActivity)this.context).addStar();
			return Constant.STAR_AWAY;
		}
	}
}