package com.yc.NinjaPainter.Entity;

import java.util.HashMap;

import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.scene.Scene;

import android.content.Context;
public abstract class Wall extends Entity{
	
	protected Context context;
	protected Scene mScene;
	protected float x,y;
	static private HashMap<Integer, Wall> wallHash = new HashMap<Integer,Wall>();
	protected boolean isPainterWall = false;
	protected boolean isPainted = false;
	protected boolean isDoor = false;
	protected boolean isOpend = false;
	
	public void init(Context context,Scene mScene,float pX, float pY) {
		this.context = context;
		this.mScene = mScene;
		this.x = pX;
		this.y = pY;
		this.init();
	}
	
	protected void init(){
		this.loadResources();
		this.drawWall();
	}
	
	abstract protected void loadResources();
	abstract protected void drawWall();
	
	//能否通过 
	public boolean check(Player mPlayer){
		return true;
	};
	//通过事件响应
	public abstract int move(Player mPlayer);
	
	
	static public Wall getWall(int pType){
		/*
		Wall wall = wallHash.get(pType);
		if(wall==null){
			try {
				@SuppressWarnings("unchecked")
				Class<Wall> cls = (Class<Wall>) Class.forName("com.yc.NinjaPainter.Entity.Wall_"+pType);
				wall = cls.newInstance();
				wallHash.put(pType, wall);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		*/
		Wall wall = null;
		try {
			@SuppressWarnings("unchecked")
			Class<Wall> cls = (Class<Wall>) Class.forName("com.yc.NinjaPainter.Entity.Wall_"+pType);
			wall = cls.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return wall;
	}
	
	public boolean isPainterWall(){
		return this.isPainterWall;
	}
	
	public boolean isPainted(){
		return this.isPainted;
	}
	
	public boolean isDoor(){
		return this.isDoor;
	}
	
	public boolean isOpened(){
		return this.isOpend;
	}
	
	static public void openDoor(com.yc.NinjaPainter.Entity.Wall_7 wall){
		wall.open();
	}
}