package com.yc.NinjaPainter.Entity;

import com.yc.NinjaPainter.Constant;

public class Wall_0 extends Wall{
	@Override
	 protected void loadResources(){
		 
	 };
	 
	 @Override
	 protected void drawWall(){
		 
	 };
	 
	 @Override
	public int move(Player mPlayer){
		 return Constant.DIE;
	 }

}