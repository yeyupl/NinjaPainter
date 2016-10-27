package com.yc.NinjaPainter.Data;

public class LevelPos{
	//关卡位置
	private final static int[][] posXY = new int[][]{
		{77,220},
		{132,220},
		{187,220},
		{77,270},
		{132,270},
		{187,270},
		{77,320},
		{132,320},
		{187,320},
		{132,370},
		
		{277,220},
		{332,220},
		{387,220},
		{277,270},
		{332,270},
		{387,270},
		{277,320},
		{332,320},
		{387,320},
		{332,370},
		
		{477,220},
		{532,220},
		{587,220},
		{477,270},
		{532,270},
		{587,270},
		{477,320},
		{532,320},
		{587,320},
		{532,370}
	};	
	
	public static int[] getPos(int index){
		return posXY[index];
	}
}