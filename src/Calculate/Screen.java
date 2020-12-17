package Calculate;

public class Screen extends Board {
	/* 类：Screen 显示数组  / 0:空  1:伤  2:死  3:未知 / 初始状态：均为 3 */
	public Screen() {         // 构造方法:初始化location数组 全为 3
		super(3);
	}
	
	/* initialScreen 函数功能：初始化initialLocation数组 均为 3 */
	public void initialScreen(){
		initialBoard(3);
	}
	
	/* changeScreen 函数功能: 输入行坐标，列坐标，将某值赋给显示数组该位置 */
	public void changeScreen(int row,int column,int value) {
		location[row][column]=value;
	}
	
	public static void main(String[] args) {
		Screen screen1=new Screen();
		screen1.printLocation();
	}
}
