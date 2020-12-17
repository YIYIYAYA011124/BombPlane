package Calculate;

public class Board {             //父类:Board
	protected int[][] location= new int[10][10];
	public Board(int value) {	//构造方法：输入int value 初始化location数组:均为value
		initialBoard(value);
	}
	
	/* initialBoard 函数功能：输入int value 初始化location数组:均为value */
	public void initialBoard(int value) {   
		for(int i=0;i<10;i++)
		{
			for(int j=0;j<10;j++)
			{
				location[i][j]=value;
			}
		}
	}
	
	/*seeLocation 函数功能： 输入行列坐标，返回该位置二维数组值 */
	public int seeLocation(int row,int column) {
		return location[row][column];
	}
	
	/* printLocation 函数功能：打印location数组  */
	public void printLocation(){
		for(int i=0;i<10;i++)
		{
			for(int j=0;j<10;j++)
			{
				System.out.print(location[i][j]+" ");
			}
			System.out.printf("\n");
		}
	}
	
	public static void main(String[] args) {
		
	}
}
