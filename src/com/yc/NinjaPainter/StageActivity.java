package com.yc.NinjaPainter;

import java.util.ArrayList;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.SpriteBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.sprite.TiledSprite;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.util.HorizontalAlign;

import android.content.Intent;
import android.graphics.Color;

import com.yc.NinjaPainter.Data.LevelPos;


public class StageActivity extends BaseActivity {
	private BitmapTextureAtlas mBitmapTexture;
	private TextureRegion mBackgroundTextureRegion,mLevelBg1,mLevelBg2,mLevelBg3;
	private TiledTextureRegion mLevelBg,mStarTTR;
	private ArrayList<TiledSprite> levelSpriteList = new ArrayList<TiledSprite>();
	private BitmapTextureAtlas mFontTexture;
	private Font mFont;

	@Override
	public void onLoadResources() {
		this.mBitmapTexture = new BitmapTextureAtlas(1024, 1024, TextureOptions.DEFAULT);

		this.mBackgroundTextureRegion = this.createTR(this.mBitmapTexture, "main_bg.jpg", 0, 0);
		
		this.mStarTTR = this.createTTR(this.mBitmapTexture, "level_star.png",720, 0,1,3);
		
		this.mLevelBg = this.createTTR(this.mBitmapTexture, "level_bg.png", 770, 0, 5, 1);
		
		this.mLevelBg1 = this.createTR(this.mBitmapTexture, "level_bg1.png", 0, 480);
		this.mLevelBg2 = this.createTR(this.mBitmapTexture, "level_bg2.png", 255, 480);
		this.mLevelBg3 = this.createTR(this.mBitmapTexture, "level_bg3.png", 510, 480);
		
		this.loadTR(this.mBitmapTexture);
		
		this.mFontTexture = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFont = this.createFont(this.mFontTexture, "font/droid.ttf", 28, true, Color.YELLOW);
		
		this.loadTR(this.mFontTexture);
		this.loadFont(this.mFont);
		
		this.setMusic(this.createMusic("sound/music_main.mp3"));
	}

	@Override
	public Scene onLoadScene() {
		this.mScene = new Scene();
		this.mScene.setTouchAreaBindingEnabled(true);
		this.mScene.setBackground(new SpriteBackground(new Sprite(0,0,this.mBackgroundTextureRegion)));

		this.mScene.attachChild(new Sprite(60,180,this.mLevelBg1));
		this.mScene.attachChild(new Sprite(260,180,this.mLevelBg2));
		this.mScene.attachChild(new Sprite(460,180,this.mLevelBg3));
		
		this.mScene.attachChild(new Text(80,185,this.mFont,"1.村庄(简单)"));
		this.mScene.attachChild(new Text(280,185,this.mFont,"2.城镇(中等)"));
		this.mScene.attachChild(new Text(480,185,this.mFont,"3.都市(困难)"));
		
		return this.mScene;
	}
	
	private void createLevelSprites(){
		for(int i=0;i<30;i++){
			final int index = i;
			TiledSprite levelSprite = null;
			if(this.levelSpriteList.size()>i){
				this.levelSpriteList.get(i);
			}
			if(levelSprite==null){
				int[] posXY = LevelPos.getPos(i);
				levelSprite = new TiledSprite(posXY[0],posXY[1],this.mLevelBg.clone()) {
					@Override
					public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
						switch(pSceneTouchEvent.getAction()){
							case TouchEvent.ACTION_DOWN:
								this.setScale(1.1f);
								return true;
							case TouchEvent.ACTION_UP:
								this.setScale(1f);
								if(StageActivity.this.isActive()){
									if(StageActivity.this.isUnlocked(index)){
										Intent intent = new Intent(StageActivity.this,GameActivity.class);
										intent.putExtra("level", index+1);
										intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
										startActivity(intent);
									}else{
										StageActivity.this.showToast("请先通过前面的关卡！");
									}
								}else{
									StageActivity.this.onActive();
								}
								return true;
						}
						return false;
					}
				};	
				TiledSprite starSprite = new TiledSprite(3,28,this.mStarTTR.clone());
				starSprite.setVisible(false);
				levelSprite.attachChild(starSprite);
				levelSprite.attachChild(new Text(8,1, this.mFont, (i<9?"0":"")+(i+1), HorizontalAlign.CENTER));			
				
				
				this.mScene.attachChild(levelSprite);
				this.mScene.registerTouchArea(levelSprite);
				this.levelSpriteList.add(levelSprite);
			}
			
			if(StageActivity.this.isActive()){
				levelSprite.setCurrentTileIndex(4);
				if(StageActivity.this.isUnlocked(index)){
					if(i<10){
						levelSprite.setCurrentTileIndex(1);
					}else if(i<20){
						levelSprite.setCurrentTileIndex(2);
					}else{
						levelSprite.setCurrentTileIndex(3);
					}
					int star = this.store.getInt("star_"+i);
					if(star>0){
						levelSprite.getFirstChild().setVisible(true);
						((TiledSprite)levelSprite.getFirstChild()).setCurrentTileIndex(star-1);
					}
				}
			}

		}
	}
	
	private boolean isUnlocked(int i){
		if(i==0 || i==10 || i==20 || this.store.getInt("score_"+(i-1))>0){
			return true;
		}
		return false;
	}
	
	private boolean isActive(){
		if (this.store.getInt("stage") == 0) {
			//return false;
		}
		return true;
	}
	
	
	private void onActive(){
		
		
	}	
	
	
	@Override
	public void onResumeGame() {
		super.onResumeGame();
		this.createLevelSprites();
	}
}