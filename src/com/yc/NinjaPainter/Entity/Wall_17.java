package com.yc.NinjaPainter.Entity;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.sprite.TiledSprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.util.modifier.IModifier.IModifierListener;
import org.anddev.andengine.util.modifier.ease.EaseBounceOut;

import android.graphics.Color;

import com.yc.NinjaPainter.BaseActivity;
import com.yc.NinjaPainter.Constant;
import com.yc.NinjaPainter.GameActivity;

public class Wall_17 extends Wall{

	private BitmapTextureAtlas mBitmapTexture;
	private TiledTextureRegion mWall;
	private TiledSprite mWallSprite;
	private BitmapTextureAtlas mFontTexture;
	private Font mFont;
	
	@Override
	protected void loadResources(){
		this.mBitmapTexture = new BitmapTextureAtlas(256, 256, TextureOptions.DEFAULT);
		this.mWall = ((BaseActivity)this.context).createTTR(this.mBitmapTexture, "wall_pink_"+((GameActivity)this.context).getStage()+".png", 0, 0,2,1);
		((BaseActivity)this.context).loadTR(this.mBitmapTexture );

		this.mFontTexture = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFont = ((BaseActivity)this.context).createFont(this.mFontTexture, "font/droid.ttf", 15, true, Color.YELLOW);
		
		((BaseActivity)this.context).loadTR(this.mFontTexture);
		((BaseActivity)this.context).loadFont(this.mFont);
	};
	 
	@Override
    protected void drawWall(){
		this.isPainterWall = true;
		
    	this.mWallSprite = new TiledSprite(this.x,this.y,this.mWall);
    	this.mScene.attachChild(this.mWallSprite);
	};
	 
	@Override
	public int move(Player mPlayer){
		//刷墙
		if(mPlayer.getPainter() == Constant.PINK_PAINTER && this.mWallSprite.getCurrentTileIndex()!=1){
			this.mWallSprite.setCurrentTileIndex(1);
			this.isPainted = true;
			int score = 50+((GameActivity)this.context).getCount()*10;
			((GameActivity)this.context).addScore(score);
			this.showGetMoney(score);	
		}
		return Constant.PAINTING;
	};
	
	//分数效果
	private void showGetMoney(int pNum){		
		final ChangeableText scoreAnimText = new ChangeableText(this.x,this.y+this.mWallSprite.getHeight()/2,this.mFont,"+"+pNum);
		this.mScene.attachChild(scoreAnimText);
		MoveModifier move = new MoveModifier(1f, this.x, this.x, this.y+this.mWallSprite.getHeight()/2, this.y-this.mWallSprite.getHeight()/2, EaseBounceOut.getInstance());
		move.setRemoveWhenFinished(true);
		move.addModifierListener(new IModifierListener<IEntity>() {			
			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {}			
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				((BaseActivity)Wall_17.this.context).runOnUpdateThread(new Runnable(){
					@Override
					public void run() {
						scoreAnimText.detachSelf();
					}				
				});	
			}
		});
		scoreAnimText.registerEntityModifier(move);
	}

}