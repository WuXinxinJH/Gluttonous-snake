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
 * ̰����
 * ����
 * ��С(450,480)
 * ���
 * ��С(350,350)
 * ���Ͻ�����(50,50)
 * ����Ϊ��ɫ
 * ����������ĸ���ɫ�ı���
 */
public class Demo1 extends JPanel{
	public static final int COLS=35;//����
	public static final int ROWS=35;//����
	public static final int CELL_SIZE=10;//���Ӵ�С
	private static final long serialVersionUID=1L;
	
	Demo3 d3;
	Demo2 food;
	public Demo1(){
		d3=new Demo3();//�����ߵĶ���
		food=createfood();//����ʳ�﷽������
	}
	
	private Demo2 createfood() {
		// ����һ��ʳ�����
		Random r=new Random();
		int x,y;
		do{
			x=r.nextInt(COLS);
			y=r.nextInt(ROWS);
		}while(d3.contains(x, y));
		//������Ҫ֪���ж�x,y������û�����������ϣ���Ҫ֪���ߵ�12�����ӵ�����
		//ͨ����װcontains�����Ƚ�
		d3.contains(x, y);
		
		return new Demo2(x,y,Color.orange);
	}
	
	//�߿�ʼ����
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
		
		this.requestFocus();//��ý���
		//ͨ�����̿����ߵ��˶�
		this.addKeyListener(new KeyAdapter() {
			//���������keyAdapter�Ѿ���д��keyListener�еķ���
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

	//��װһ������ �ж������й������Ƿ�����ײ
	public void creepHit(){
		if(d3.hit()){
			d3=new Demo3();
			food=new Demo1().createfood();
			
		}else if(d3.eat(food))
			food=createfood();
	}
	//��װһ������ ��������� �Ƿ�����ײ���Ե�����
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
		frame.setTitle("�Ի�����");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);//ȡ������Ĭ�ϲ���
		frame.setVisible(true);
		
		Demo1 panel=new Demo1();
		panel.setSize(350,350);
		panel.setLocation(50,50);
		frame.add(panel);
		panel.go();
	}
}
