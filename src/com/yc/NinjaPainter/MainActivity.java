package com.yc.NinjaPainter;

import org.anddev.andengine.entity.modifier.AlphaModifier;
import org.anddev.andengine.entity.modifier.DelayModifier;
import org.anddev.andengine.entity.modifier.LoopEntityModifier;
import org.anddev.andengine.entity.modifier.ParallelEntityModifier;
import org.anddev.andengine.entity.modifier.RotationByModifier;
import org.anddev.andengine.entity.modifier.RotationModifier;
import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.SpriteBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.sprite.TiledSprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.content.Intent;

public class MainActivity extends BaseActivity {
	private BitmapTextureAtlas mBitmapTexture,mPageBitmapTexture;
	private TextureRegion mBackgroundTextureRegion;
	private TextureRegion mStarTR,mHelpTR,mAboutTR;
	private TiledTextureRegion mStartTTR,mHelpTTR,mAboutTTR;
	private Sprite mHelpSprite,mAboutSprite;
	private TiledSprite mAbout;
	private TiledSprite mHelp;
	private TiledSprite mStart;

	@Override
	public void onLoadResources() {
		this.mBitmapTexture = new BitmapTextureAtlas(1024, 1024, TextureOptions.DEFAULT);
		this.mBackgroundTextureRegion = this.createTR(this.mBitmapTexture, "main_bg.jpg", 0, 0);
		
		this.mStarTR = this.createTR(this.mBitmapTexture, "star.png", 720, 0);
		
		this.mStartTTR = this.createTTR(this.mBitmapTexture, "menu_start.png", 0, 480, 1, 2);
		this.mHelpTTR = this.createTTR(this.mBitmapTexture, "menu_help.png", 200, 480, 1, 2);
		this.mAboutTTR = this.createTTR(this.mBitmapTexture, "menu_about.png", 400, 480, 1, 2);
		
		this.mPageBitmapTexture = new BitmapTextureAtlas(1024, 1024, TextureOptions.DEFAULT);
		this.mHelpTR = this.createTR(this.mPageBitmapTexture, "help.jpg", 0, 0);
		this.mAboutTR = this.createTR(this.mPageBitmapTexture, "about.png", 0, 480);
		
		this.loadTR(this.mBitmapTexture);
		this.loadTR(this.mPageBitmapTexture);
		
		this.setMusic(this.createMusic("sound/music_main.mp3"));

	}

	@Override
	public Scene onLoadScene() {		
		this.mScene = new Scene();
		this.mScene.setTouchAreaBindingEnabled(true);
		this.mScene.setBackground(new SpriteBackground(new Sprite(0,0,this.mBackgroundTextureRegion)));
		
		Sprite mStar = new Sprite(80,30,this.mStarTR);
		final LoopEntityModifier entityModifier = new LoopEntityModifier(
					new SequenceEntityModifier(
							new RotationModifier(0.5f, 0, 90),
							new AlphaModifier(1, 1, 0),
							new AlphaModifier(1, 0, 1),
							new ScaleModifier(0.5f, 1, 0.5f),
							new DelayModifier(0.5f),
							new ParallelEntityModifier(
									new ScaleModifier(0.5f, 0.5f, 0.8f),
									new RotationByModifier(0.5f, 90)
							),
							new ParallelEntityModifier(
									new ScaleModifier(0.5f,0.8f, 1),
									new RotationModifier(0.5f, 180, 0)
							)
					)
			);

		mStar.registerEntityModifier(entityModifier);
		this.mScene.attachChild(mStar);
		
		this.mStart = new TiledSprite(260, 200, this.mStartTTR){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY){
				switch(pSceneTouchEvent.getAction()){
					case TouchEvent.ACTION_DOWN:
						this.setCurrentTileIndex(1);
						return true;
					case TouchEvent.ACTION_UP:
						this.setCurrentTileIndex(0);
						Intent intent = new Intent(MainActivity.this,StageActivity.class);
			    		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						return true;
				}
				return false;
			}
		};
		this.mScene.attachChild(this.mStart);
		this.mScene.registerTouchArea(this.mStart);
		
		this.mHelp = new TiledSprite(260, 270, this.mHelpTTR){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY){
				switch(pSceneTouchEvent.getAction()){
					case TouchEvent.ACTION_DOWN:
						this.setCurrentTileIndex(1);
						return true;
					case TouchEvent.ACTION_UP:
						this.setCurrentTileIndex(0);
						MainActivity.this.runOnUiThread(new Runnable(){
							@Override
							public void run() {
								MainActivity.this.showHelp();			
							}					
						});
						return true;
				}
				return false;
			}
		};
		this.mScene.attachChild(this.mHelp);
		this.mScene.registerTouchArea(this.mHelp);
		
		
		this.mAbout = new TiledSprite(260, 340, this.mAboutTTR){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY){
				switch(pSceneTouchEvent.getAction()){
					case TouchEvent.ACTION_DOWN:
						this.setCurrentTileIndex(1);
						return true;
					case TouchEvent.ACTION_UP:
						this.setCurrentTileIndex(0);
						MainActivity.this.runOnUiThread(new Runnable(){
							@Override
							public void run() {
								MainActivity.this.showAbout();				
							}					
						});
						return true;
				}
				return false;
			}
		};		
		this.mScene.attachChild(this.mAbout);
		this.mScene.registerTouchArea(this.mAbout);
		
		this.mHelpSprite = new Sprite(0,0,this.mHelpTR){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY){
				MainActivity.this.runOnUiThread(new Runnable(){
					@Override
					public void run() {
						MainActivity.this.hideHelp();					
					}					
				});
				return true;
			}
		};
		
		this.mAboutSprite = new Sprite(0,0,this.mAboutTR){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY){
				MainActivity.this.runOnUiThread(new Runnable(){
					@Override
					public void run() {
						MainActivity.this.hideAbout();					
					}					
				});
				return true;
			}
		};
		
		return this.mScene;
	}
	
	private void showHelp(){
		this.mScene.attachChild(this.mHelpSprite);
		this.mScene.registerTouchArea(this.mHelpSprite);
	}
	private void hideHelp(){
		this.mScene.detachChild(this.mHelpSprite);
		this.mScene.unregisterTouchArea(this.mHelpSprite);
	}
	
	private void showAbout(){
		this.mScene.attachChild(this.mAboutSprite);
		this.mScene.registerTouchArea(this.mAboutSprite);
	}
	private void hideAbout(){
		this.mScene.detachChild(this.mAboutSprite);
		this.mScene.unregisterTouchArea(this.mAboutSprite);
	}
}