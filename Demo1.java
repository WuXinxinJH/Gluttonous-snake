package pm;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * 贪吃蛇
 * 窗口
 * 大小(450,480)
 * 面板
 * 大小(350,350)
 * 左上角坐标(50,50)
 * 背景为黑色
 * 面板四周有四个红色的边线
 */
public class Demo1 extends JPanel{
	public static final int COLS=35;//行数
	public static final int ROWS=35;//列数
	public static final int CELL_SIZE=10;//格子大小
	private static final long serialVersionUID=1L;
	
	Demo3 d3;
	Demo2 food;
	public Demo1(){
		d3=new Demo3();//创建蛇的对象
		food=createfood();//调用食物方法对象
	}
	
	private Demo2 createfood() {
		// 返回一个食物对象
		Random r=new Random();
		int x,y;
		do{
			x=r.nextInt(COLS);
			y=r.nextInt(ROWS);
		}while(d3.contains(x, y));
		//现在需要知道判断x,y坐标有没有落在蛇身上，需要知道蛇的12个格子的坐标
		//通过封装contains方法比较
		d3.contains(x, y);
		
		return new Demo2(x,y,Color.orange);
	}
	
	//蛇开始爬行
	public void go(){
		Timer t=new Timer();
		t.schedule(new TimerTask(){

			@Override
			public void run() {
				//d3.creep();
				creepHit();
				repaint();
			}
			
		},0,100);
		
		this.requestFocus();//获得焦点
		//通过键盘控制蛇的运动
		this.addKeyListener(new KeyAdapter() {
			//子类抽象类keyAdapter已经重写了keyListener中的方法
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				switch(e.getKeyCode()){
				case KeyEvent.VK_UP:creepHit(Demo3.UP);break;
				case KeyEvent.VK_DOWN:creepHit(Demo3.DOWN);break;
				case KeyEvent.VK_LEFT:creepHit(Demo3.LEFT);break;
				case KeyEvent.VK_RIGHT:creepHit(Demo3.RIGHT);break;
				}
			}
		});
		
	}

	//封装一个方法 判断其爬行过程中是否发生碰撞
	public void creepHit(){
		if(d3.hit()){
			d3=new Demo3();
			food=new Demo1().createfood();
			
		}else if(d3.eat(food))
			food=createfood();
	}
	//封装一个方法 给定方向后 是否发生碰撞及吃到事物
	public void creepHit(int dir){
		if(d3.hit(dir)){
			d3=new Demo3();
			food=new Demo1().createfood();
		}else if(d3.eat(food))
			food=createfood();
		}
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		
		g.setColor(Color.black);
		g.fillRect(0,0,this.getWidth(),this.getHeight());
		g.setColor(Color.red);
		g.drawRect(0, 0, getWidth()-1,getHeight()-1);
		//g.fill3DRect(25, 250, 12, 12, false);
		d3.draw(g);
		
		g.setColor(food.col);
		g.fill3DRect(food.x*CELL_SIZE, food.y*CELL_SIZE,CELL_SIZE, CELL_SIZE, true);
		
		
	}
	
	public static void main(String[] args) {
		JFrame frame=new JFrame();
		frame.setSize(450, 480);
		frame.setTitle("吃货世界");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);//取消窗口默认布局
		frame.setVisible(true);
		
		Demo1 panel=new Demo1();
		panel.setSize(350,350);
		panel.setLocation(50,50);
		frame.add(panel);
		panel.go();
	}
}
