package com.yc.NinjaPainter.Entity;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.util.modifier.IModifier.IModifierListener;

import com.yc.NinjaPainter.BaseActivity;
import com.yc.NinjaPainter.Constant;
import com.yc.NinjaPainter.GameActivity;

public class Wall_10 extends Wall{

	private BitmapTextureAtlas mBitmapTexture;
	private TextureRegion mWall,mAnim;
	private TiledTextureRegion mPainter;
	private Sprite mWallSprite;
	private AnimatedSprite mPainterSprite;
	
	@Override
	protected void loadResources(){
		this.mBitmapTexture = new BitmapTextureAtlas(1024, 1024, TextureOptions.DEFAULT);
		this.mWall = ((BaseActivity)this.context).createTR(this.mBitmapTexture, "wall_blank_"+((GameActivity)this.context).getStage()+".png", 0, 0);
		this.mAnim = ((BaseActivity)this.context).createTR(this.mBitmapTexture, "painter_red_anim.png", 44, 0);
		this.mPainter = ((BaseActivity)this.context).createTTR(this.mBitmapTexture, "painter_red.png", 0, 44,16,1);
		((BaseActivity)this.context).loadTR(this.mBitmapTexture );
	};
	 
	@Override
    protected void drawWall(){
    	this.mWallSprite = new Sprite(this.x,this.y,this.mWall);
    	this.mScene.attachChild(this.mWallSprite);
    	
    	this.mPainterSprite = new AnimatedSprite(this.x,this.y,this.mPainter);
    	this.mPainterSprite.animate(100, true);
    	this.mScene.attachChild(this.mPainterSprite);
    	
	}
	 
	@Override
	public int move(Player mPlayer) {
		if (mPlayer.getPainter() != Constant.RED_PAINTER) {
			mPlayer.setPainter(Constant.RED_PAINTER);
			this.showAnim();
		}
		return Constant.RED_PAINTER;
	};
	
	private void showAnim(){
		final Sprite anim = new Sprite(this.x+10,this.y+10,this.mAnim.clone());
		this.mScene.attachChild(anim);
		ScaleModifier move = new ScaleModifier(0.5f,3, 0f);
		move.setRemoveWhenFinished(true);
		move.addModifierListener(new IModifierListener<IEntity>() {			
			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {}			
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				((BaseActivity)Wall_10.this.context).runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
						anim.detachSelf();
					}				
				});	
			}
		});
		anim.registerEntityModifier(move);
	}

}