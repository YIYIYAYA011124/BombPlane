package Calculate;

import java.util.Scanner;

public class Enemy extends Board{
	/* 类：Enemy 布局数组  / 0:空  1:飞机身  2:飞机头  3:未知  / 初始状态：均为 0 */
	public Enemy() {		  // 构造方法:初始化location数组 全为 0
		super(0);
	}
	
	/*initialEnemy 函数功能：初始化initialEnemy数组 均为 0 */
	public void initialEnemy(){
		initialBoard(0);
	}
	
	/*addPlane 函数功能：输入飞机，在布局数组中添加该飞机*/
	public boolean addPlane(Plane plane) {
		if(checkPlane(plane)) {
			int locationMessages[]=plane.showLocations();
			for(int j=0;j<10;j++)
			{
			location[locationMessages[j]][locationMessages[j+10]]=1;
			}
			location[plane.showHeadLocation()[0]][plane.showHeadLocation()[1]]=2;
			return true;
		}
		else {
			return false;
		}
	}
	
	/*checkPlane 函数功能： 输入飞机，判断布局数组中该放置飞机是否合法 */
	public boolean checkPlane(Plane plane) {
		int i=0;
		if(plane.checkNotOverflow()==false) return false;
		int locationMessages[]=plane.showLocations();
		for(int j=0;j<10;j++)
		{
			if(seeLocation(locationMessages[j],locationMessages[j+10])!=0)
			{
				i++;
			}
		}
		if(i==0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/*addPlanes9Code （开发者专用）函数功能：输入9位信息码，在布局数组中添加3架飞机*/
	public void addPlanes9Code(int code) {
		int rest=code;
		int codeArray[]=new int[9];
		int space;
		for(space=8;space>=0;space--) {
			codeArray[space]=rest/(int)Math.pow(10,space);
			rest=rest%(int)Math.pow(10,space);
		}
		
		addPlane(new Plane(codeArray[8],codeArray[7],codeArray[6]));
		addPlane(new Plane(codeArray[5],codeArray[4],codeArray[3]));
		addPlane(new Plane(codeArray[2],codeArray[1],codeArray[0]));
	}
	
	/*addPlane3Code 函数功能：输入3位信息码，在布局数组中添加这架飞机，返回是否添加成功*/
	public boolean addPlane3Code(int code) {
		int rest=code;
		int codeArray[]=new int[2];
		int space;
		for(space=2;space>=0;space--) {
			codeArray[space]=rest/(int)Math.pow(10,space);
			rest=rest%(int)Math.pow(10,space);
		}
		return addPlane(new Plane(codeArray[2],codeArray[1],codeArray[0]));
	}
	
	/* inputPlane 函数功能：输入行参数、列参数、方向参数，返回Plane */
	public Plane inputPlane(int centerRow,int centerColumn,int direction) {
		Plane plane=new Plane(centerRow,centerColumn,direction);
		return plane;
	}
	
	/* 获得一个不越界的随机飞机*/
	public Plane getRandomPlane() {
		Plane plane;
		do {
			plane=new Plane((int)(Math.random()*8)+1,(int)(Math.random()*8)+1,(int)(Math.random()*4));
		}while(!plane.checkNotOverflow());
		return plane;
	}
	
	/* arrayFill 函数功能：输入读取数量，读取整形数，返回数组*/
	public int[] arrayFill(int num) {
		Scanner sc=new Scanner(System.in);
		int[] message=new int[num];
		for(int i=0;i<num;i++) {
			System.out.println("Input "+(i+1)+ "number");
			message[i]=sc.nextInt();
		}
		sc.close();
		return message;
	}
	
	public static void main(String[] args) {
		Enemy enemy1=new Enemy();
		enemy1.printLocation();
	}
}
