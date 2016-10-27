package com.yc.NinjaPainter.Entity;

import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.sprite.TiledSprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.content.Context;

import com.yc.NinjaPainter.BaseActivity;

public class QuickBar extends Entity{
	
	private Context context;
	private Scene mScene;
	private BitmapTextureAtlas mBitmapTexture;
	private TextureRegion mRestart,mExit;
	private TiledTextureRegion mSound,mMusic;
	private Sprite mRetartSprite,mExitSprite;
	private TiledSprite mSoundSprite,mMusicSprite;
	private float x,y;
	
	public QuickBar(Context context,Scene mScene,float pX, float pY) {
		this.context = context;
		this.mScene = mScene;
		this.x = pX;
		this.y = pY;
		this.init();
	}
	
	private void init(){
		this.onLoadResources();
		
		this.mRetartSprite = new Sprite(this.x,this.y,this.mRestart){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY){
				switch(pSceneTouchEvent.getAction()){
					case TouchEvent.ACTION_DOWN:
						this.setScale(1.2f);
						return true;
					case TouchEvent.ACTION_UP:
						this.setScale(1f);
						return true;
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};
		
		this.mExitSprite = new Sprite(this.x+40,this.y,this.mExit){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY){
				switch(pSceneTouchEvent.getAction()){
					case TouchEvent.ACTION_DOWN:
						this.setScale(1.2f);
						return true;
					case TouchEvent.ACTION_UP:
						this.setScale(1f);
						((BaseActivity)QuickBar.this.context).finish();
						return true;
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};
		
		this.mSoundSprite = new TiledSprite(this.x+80, this.y, this.mSound){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY){
				switch(pSceneTouchEvent.getAction()){
					case TouchEvent.ACTION_DOWN:
						this.setScale(1.2f);
						return true;
					case TouchEvent.ACTION_UP:
						this.setScale(1f);
						if(((BaseActivity)QuickBar.this.context).isSound()){
							((BaseActivity)QuickBar.this.context).setSoundOn(false);
							this.setCurrentTileIndex(1);
						}else{
							((BaseActivity)QuickBar.this.context).setSoundOn(true);
							this.setCurrentTileIndex(0);
						}
						return true;
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};
		if(!((BaseActivity)this.context).isSound()){
			this.mSoundSprite.setCurrentTileIndex(1);
		}
		this.mScene.attachChild(this.mSoundSprite);
		this.mScene.registerTouchArea(this.mSoundSprite);
		
		this.mMusicSprite = new TiledSprite(this.x+120,this.y, this.mMusic){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY){
				switch(pSceneTouchEvent.getAction()){
					case TouchEvent.ACTION_DOWN:
						this.setScale(1.2f);
						return true;
					case TouchEvent.ACTION_UP:
						this.setScale(1f);
						if(((BaseActivity)QuickBar.this.context).isMusic()){
							((BaseActivity)QuickBar.this.context).setMusicOn(false);
							this.setCurrentTileIndex(1);
						}else{
							((BaseActivity)QuickBar.this.context).setMusicOn(true);
							this.setCurrentTileIndex(0);
						}
						return true;
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};
		if(!((BaseActivity)this.context).isMusic()){
			this.mMusicSprite.setCurrentTileIndex(1);
		}
		this.mScene.attachChild(this.mMusicSprite);
		this.mScene.registerTouchArea(this.mMusicSprite);
	}
	
	private void onLoadResources(){
		this.mBitmapTexture = new BitmapTextureAtlas(256, 256, TextureOptions.DEFAULT);
		this.mRestart = ((BaseActivity)this.context).createTR(this.mBitmapTexture, "btn_restart.png", 0, 0);
		this.mExit = ((BaseActivity)this.context).createTR(this.mBitmapTexture, "btn_exit.png", 33, 0);
		this.mSound = ((BaseActivity)this.context).createTTR(this.mBitmapTexture, "btn_sound.png",66, 0,2,1);
		this.mMusic = ((BaseActivity)this.context).createTTR(this.mBitmapTexture, "btn_music.png", 132, 0,2,1);
		((BaseActivity)this.context).loadTR(this.mBitmapTexture );
	}
	
	public void showRestart(){
		this.mScene.attachChild(this.mRetartSprite);
		this.mScene.registerTouchArea(this.mRetartSprite);	
	}
	
	public void showExit(){
		this.mScene.attachChild(this.mExitSprite);
		this.mScene.registerTouchArea(this.mExitSprite);	
	}
	
	

}