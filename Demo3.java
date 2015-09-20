package pm;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;

public class Demo3 {
	Demo2[] cs=new Demo2[12];
	public static final int UP=-1;
	public static final int DOWN=1;
	public static final int LEFT=-2;
	public static final int RIGHT=2;
	
	int direction;
	
	
	//构造中给cs赋值
	public Demo3(){
		for(int i=0;i<cs.length;i++){
			cs[i]=new Demo2(i,0,Color.red);
		}
		direction=DOWN;
	}
	
	//画蛇
	public void draw(Graphics g){
		for(int i=0;i<cs.length;i++){
			Demo2 c=cs[i];
			g.fill3DRect(c.x*Demo1.CELL_SIZE, c.y*Demo1.CELL_SIZE, Demo1.CELL_SIZE,Demo1.CELL_SIZE,true);
			
		}
	}
	//判断食物坐标与蛇坐标是否重合
	public boolean contains(int x,int y){
		for(int i=0;i<cs.length;i++){
			Demo2 c=cs[i];
			if(c.x==x&&c.y==y){
				return true;
			}
		}
		return false;
	}
	//根据运动的方向创建新的头节点
	public  Demo2 createHead(int dir){
		Demo2 head=cs[0];
		int x=head.x;
		int y=head.y;
		switch(dir){
		case UP:y--;break;
		case DOWN:y++;break;
		case LEFT:x--;break;
		case RIGHT:x++;break;
		}
		return new Demo2(x,y,head.col);
	}
	//封装蛇爬行
	public void creep(){
		for(int i=cs.length-1;i>=1;i--){
			cs[i]=cs[i-1];
		}
		cs[0]=createHead(direction);
	}
	
	//根据默认爬行方向 判断蛇是否发生碰撞
	public boolean hit(){
		//判断是否撞到墙壁
		Demo2 head=createHead(direction);
		//根据运动方向得到头节点对象  并得到该格子对象的x y坐标
		int x=head.x;
		int y=head.y;
		if(x<0||x>Demo1.COLS||y<0||y>Demo1.ROWS){
			//四个临界值
			return true;
		}
		//判断其是否咬到自身
		//蛇头的坐标与蛇身每个格子坐标相互比较
		for(int i=1;i<cs.length;i++){
			Demo2 c=cs[i];
			if(x==c.x&&y==c.y){
				return true;
			}
		}
		return false;
	}
	public boolean eat(Demo2 food){
		//得到头节点
		Demo2 head=createHead(direction);
		//根据头节点坐标与food的坐标比较
		boolean eat=head.x==food.x && head.y==food.y;
		//如果重合 表示吃到食物 蛇身变长
		if(eat){
			cs=Arrays.copyOf(cs, cs.length+1);
		}
		//如果没有吃到 继续爬行
		for(int i=cs.length-1;i>=1;i--){
			cs[i]=cs[i-1];
			
		}
		cs[0]=head;
		return eat;
	}
	//根据指定的方向让蛇爬行 并判断是否发生碰撞
	public boolean hit(int direction){
		if(this.direction+direction==0){//判断方向与当前方向是否一致
			return false;
		}
		this.direction=direction;
		return hit();
	}
	
	
	
	//根据指定的方向让蛇爬行 并判断是否吃到食物
	public boolean eat(int direction,Demo2 food){
		if(this.direction+direction==0){//判断方向与当前方向是否一致
			return false;
		}
		this.direction=direction;
		return eat(food);
	}
	
	
	
}	
