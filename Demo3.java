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
	
	
	//�����и�cs��ֵ
	public Demo3(){
		for(int i=0;i<cs.length;i++){
			cs[i]=new Demo2(i,0,Color.red);
		}
		direction=DOWN;
	}
	
	//����
	public void draw(Graphics g){
		for(int i=0;i<cs.length;i++){
			Demo2 c=cs[i];
			g.fill3DRect(c.x*Demo1.CELL_SIZE, c.y*Demo1.CELL_SIZE, Demo1.CELL_SIZE,Demo1.CELL_SIZE,true);
			
		}
	}
	//�ж�ʳ���������������Ƿ��غ�
	public boolean contains(int x,int y){
		for(int i=0;i<cs.length;i++){
			Demo2 c=cs[i];
			if(c.x==x&&c.y==y){
				return true;
			}
		}
		return false;
	}
	//�����˶��ķ��򴴽��µ�ͷ�ڵ�
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
	//��װ������
	public void creep(){
		for(int i=cs.length-1;i>=1;i--){
			cs[i]=cs[i-1];
		}
		cs[0]=createHead(direction);
	}
	
	//����Ĭ�����з��� �ж����Ƿ�����ײ
	public boolean hit(){
		//�ж��Ƿ�ײ��ǽ��
		Demo2 head=createHead(direction);
		//�����˶�����õ�ͷ�ڵ����  ���õ��ø��Ӷ����x y����
		int x=head.x;
		int y=head.y;
		if(x<0||x>Demo1.COLS||y<0||y>Demo1.ROWS){
			//�ĸ��ٽ�ֵ
			return true;
		}
		//�ж����Ƿ�ҧ������
		//��ͷ������������ÿ�����������໥�Ƚ�
		for(int i=1;i<cs.length;i++){
			Demo2 c=cs[i];
			if(x==c.x&&y==c.y){
				return true;
			}
		}
		return false;
	}
	public boolean eat(Demo2 food){
		//�õ�ͷ�ڵ�
		Demo2 head=createHead(direction);
		//����ͷ�ڵ�������food������Ƚ�
		boolean eat=head.x==food.x && head.y==food.y;
		//����غ� ��ʾ�Ե�ʳ�� ����䳤
		if(eat){
			cs=Arrays.copyOf(cs, cs.length+1);
		}
		//���û�гԵ� ��������
		for(int i=cs.length-1;i>=1;i--){
			cs[i]=cs[i-1];
			
		}
		cs[0]=head;
		return eat;
	}
	//����ָ���ķ����������� ���ж��Ƿ�����ײ
	public boolean hit(int direction){
		if(this.direction+direction==0){//�жϷ����뵱ǰ�����Ƿ�һ��
			return false;
		}
		this.direction=direction;
		return hit();
	}
	
	
	
	//����ָ���ķ����������� ���ж��Ƿ�Ե�ʳ��
	public boolean eat(int direction,Demo2 food){
		if(this.direction+direction==0){//�жϷ����뵱ǰ�����Ƿ�һ��
			return false;
		}
		this.direction=direction;
		return eat(food);
	}
	
	
	
}	
