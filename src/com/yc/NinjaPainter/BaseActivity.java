package com.yc.NinjaPainter;

import java.io.IOException;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.audio.music.MusicFactory;
import org.anddev.andengine.audio.sound.Sound;
import org.anddev.andengine.audio.sound.SoundFactory;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.mobclick.android.MobclickAgent;
import com.yc.NinjaPainter.Entity.QuickBar;
import com.yc.NinjaPainter.Utils.SoundUtil;
import com.yc.NinjaPainter.Utils.Store;

public class BaseActivity extends BaseGameActivity{
	protected static final int CAMERA_WIDTH = 720;
	protected static final int CAMERA_HEIGHT = 480;
	public float gold = 0,needGold = 50;
	public Store store;
	protected Camera mCamera;
	public SoundUtil sound;
	private Music mMusic;
	public Scene mScene;
	public QuickBar quickBar;
	
	@Override
	public Engine onLoadEngine() {
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new FillResolutionPolicy(), this.mCamera).setNeedsSound(true).setNeedsMusic(true));
	}

	@Override
	public void onLoadResources() {
		
	}

	@Override
	public Scene onLoadScene() {
		return null;
	}

	@Override
	public void onLoadComplete() {	
		this.quickBar = new QuickBar(this,this.mScene,560,440);
		//this.quickBar.showRestart();
		this.quickBar.showExit();
	}
	

	@Override
	public void onCreate(final Bundle pSavedInstanceState) {
		super.onCreate(pSavedInstanceState);
		        
		MobclickAgent.onError(this); 
		
		MobclickAgent.update(this); 
        		
		this.store = new Store(this);
		
		this.sound = new SoundUtil(this);
		
	}
	
	public void onPause(){
	    super.onPause();
	    MobclickAgent.onPause(this); 
	}

	public void onResume(){
		super.onResume();
		MobclickAgent.onResume(this); 
	 }
	
	@Override
	public void onPauseGame() {
		super.onPauseGame();
		this.mEngine.stop();
		if(this.mMusic!=null && this.mMusic.isPlaying()){
			this.mMusic.pause();
		}
	}
	
	@Override
	public void onResumeGame() {
		super.onResumeGame();
		this.mEngine.start();
		if(this.isMusic() && this.mMusic!=null){
			this.mMusic.play();
		}
	}
	
	public void onDestroy(){
		 super.onDestroy();
	 }

	
	public int getCameraWidth(){
		return CAMERA_WIDTH;
	}
	
	public int getCameraHeight(){
		return CAMERA_HEIGHT;
	}
	
	/**
	 * 加载器封装
	 * @param pContainer
	 * @param pFilePath
	 * @param pPX
	 * @param pPY
	 * @return
	 */
	public TextureRegion createTR(BitmapTextureAtlas pContainer, String pFilePath, int pPX, int pPY){
		try {
		return BitmapTextureAtlasTextureRegionFactory.createFromAsset(pContainer, this, pFilePath, pPX, pPY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 加载器封装
	 * @param pContainer
	 * @param pFilePath
	 * @param pPX
	 * @param pPY
	 * @return
	 */
	public TiledTextureRegion createTTR(BitmapTextureAtlas pContainer, String pFilePath, int pPX, int pPY, int pColumns, int pRows){
		try {
			return BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(pContainer, this, pFilePath, pPX, pPY, pColumns, pRows);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Music createMusic(String pFilepPath){
		try {
			return MusicFactory.createMusicFromAsset(this.getMusicManager(), this, pFilepPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Sound createSound(String pFilepPath){
		try {
			return SoundFactory.createSoundFromAsset(this.getSoundManager(), this, pFilepPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Font createFont(BitmapTextureAtlas pContainer, String pFilePath, int pSize,boolean pAntiAlias, int pColor){
		try {
		return FontFactory.createFromAsset(pContainer, this, pFilePath, pSize, pAntiAlias, pColor);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 载入资源
	 * @param pTexture
	 */
	public void loadTR(BitmapTextureAtlas pTexture){
		this.mEngine.getTextureManager().loadTexture(pTexture);
	}
	/**
	 * 载入资源
	 * @param pTexture
	 */
	public void loadFont(Font pFont){
		this.mEngine.getFontManager().loadFont(pFont);
	}
	
	
	//设置背景音乐
	public void setMusic(Music mMusic){
		this.mMusic = mMusic;
		this.mMusic.setLooping(true);
	}
	
	//声音控制
	public boolean isMusic(){
		return this.sound.isMusic();
	}
	
	public boolean isSound(){
		return this.sound.isSound();
	}
	
	public void setMusicOn(boolean is){
		this.sound.setMusicOn(is);
		if(is){
			if(this.mMusic!=null && !this.mMusic.isPlaying()){
				this.mMusic.play();
			}
		}else{
			if(this.mMusic!=null && this.mMusic.isPlaying()){
				this.mMusic.pause();
			}
		}
	}
	
	public void setSoundOn(boolean is){
		this.sound.setSoundOn(is);
	}
	
	public void showToast(String msg){
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}
	
}