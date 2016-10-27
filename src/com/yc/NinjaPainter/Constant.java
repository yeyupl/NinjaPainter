package com.yc.NinjaPainter;

public final class Constant {
	public final static int WALL_NOTHING = 0;  //0  空  边缘死亡
	public final static int WALL_ALL = 1;    //1 墙  全墙
	public final static int WALL_LEFT_BOTTOM = 2; //2 左下墙  转向 上右   空白墙作底
	public final static int WALL_RIGHT_BOTTOM = 3; //3 右下墙  转向 上左   空白墙作底
	public final static int WALL_RIGHT_TOP = 4; //4右上墙  转向 下左   空白墙作底
	public final static int WALL_LEFT_TOP = 5; //5 左上墙  转向 下右   空白墙作底
	public final static int WALL_BLANK = 6; //6 空白墙 
	public final static int WALL_DOOR = 7;  //7 门
	public final static int WALL_STAR = 8;  //8 星星   空白墙作底
	public final static int WALL_LADDER = 9;  //9 梯子    停留
	public final static int WALL_RED_PAINTER = 10; //10  红色刷子
	public final static int WALL_GREEN_PAINTER = 11; //11 绿色刷子
	public final static int WALL_BLUE_PAINTER = 12; //12 蓝色刷子
	public final static int WALL_PINK_PAINTER = 13; //13 粉红刷子
	public final static int WALL_RED = 14; //14 红色墙
	public final static int WALL_GREEN = 15; //15 绿色墙
	public final static int WALL_BLUE = 16; //16 蓝色墙
	public final static int WALL_PINK = 17; //17 粉红墙
	public final static int WALL_WIN = 18; //18 窗   边缘死亡
	public final static int WALL_EDGE = 19; //19  边缘墙 边缘死亡
	
	public final static int WALL_DOOR_OPENED = 20; // 7 -> 20  门开
	public final static int WALL_STAR_AWAY = 21; // 8 -> 21 星星消失
	public final static int WALL_RED_PAINTED = 22; // 14 -> 22 红色墙已刷
	public final static int WALL_GREEN_PAINTED = 23; // 15 -> 23 绿色墙已刷
	public final static int WALL_BLUE_PAINTED = 24; // 16 -> 24 蓝色墙已刷
	public final static int WALL_PINK_PAINTED = 25; // 17 -> 25 粉红墙已刷
	
	//经过墙的事件状态
	public final static int DIE = 26;  //死亡 游戏结束
	public final static int FINISH = 27;  //出口 游戏完成 结束
	public final static int STAR_AWAY = 28;  //获得星星，星星消失
	public final static int RED_PAINTER = 29;  //获得红色刷子
	public final static int GREEN_PAINTER = 30;  //获得绿色刷子
	public final static int BLUE_PAINTER = 31;  //获得蓝色刷子
	public final static int PINK_PAINTER = 32;  //获得粉色刷子
	
	public final static int LEFT = 33;  //方向左
	public final static int RIGHT = 34;  //方向各
	public final static int UP = 35;  //方向上
	public final static int DOWN = 36;  //方向下
	
	public final static int STAY = 37;  //遇墙停住
	
	public final static int LADDER = 38;  //遇梯子停住
	
	public final static int NO_PAINTER = 39;  //没有获得刷子
	
	public final static int GOON = 40;  //没有需要处理的特殊事件，游戏继续
	
	public final static int PAINTING = 41;  //刷墙 用于检验墙是否刷完
}
