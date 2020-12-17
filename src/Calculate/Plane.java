package Calculate;

public class Plane {
//类：飞机
	private int centerRow;		//Plane类的成员变量：飞机中心的行参数
	private int centerColumn;	//Plane类的成员变量：飞机中心的列参数
	private int direction;		//Plane类的成员变量：飞机朝向     （ 0 上, 1 下, 2 左, 3 右 ）
	public Plane(int centerRow,int centerColumn,int direction){
		this.centerRow=centerRow;		
		this.centerColumn=centerColumn;	
		this.direction=direction;
	}
	
	/* showLocations 函数功能: 以数组形式返回Plane中10个点坐标locations[a],locations[a+10] */
	public int[] showLocations(){
		int[] locations=new int[20];
		switch(direction) {
		case 0:
			 locations[10]=locations[13]=locations[16]=locations[18]=centerColumn;
			 locations[12]=locations[17]=centerColumn-1;
			 locations[11]=centerColumn-2;
			 locations[14]=locations[19]=centerColumn+1;
			 locations[15]=centerColumn+2;
			 locations[1]=locations[2]=locations[3]=locations[4]=locations[5]=centerRow;
			 locations[0]=centerRow-1;
			 locations[6]=centerRow+1;
			 locations[7]=locations[8]=locations[9]=centerRow+2;
			 break;
		case 1:
			 locations[10]=locations[13]=locations[16]=locations[18]=centerColumn;
			 locations[12]=locations[17]=centerColumn-1;
			 locations[11]=centerColumn-2;
			 locations[14]=locations[19]=centerColumn+1;
			 locations[15]=centerColumn+2;
			 locations[1]=locations[2]=locations[3]=locations[4]=locations[5]=centerRow;
			 locations[0]=centerRow+1;
			 locations[6]=centerRow-1;
			 locations[7]=locations[8]=locations[9]=centerRow-2;
			 break;
		case 2:
			 locations[0]=locations[3]=locations[6]=locations[8]=centerRow;
			 locations[2]=locations[7]=centerRow-1;
			 locations[1]=centerRow-2;
			 locations[4]=locations[9]=centerRow+1;
			 locations[5]=centerRow+2;
			 locations[11]=locations[12]=locations[13]=locations[14]=locations[15]=centerColumn;
			 locations[10]=centerColumn-1;
			 locations[16]=centerColumn+1;
			 locations[17]=locations[18]=locations[19]=centerColumn+2;
			 break;
		case 3:
			 locations[0]=locations[3]=locations[6]=locations[8]=centerRow;
			 locations[2]=locations[7]=centerRow-1;
			 locations[1]=centerRow-2;
			 locations[4]=locations[9]=centerRow+1;
			 locations[5]=centerRow+2;
			 locations[11]=locations[12]=locations[13]=locations[14]=locations[15]=centerColumn;
			 locations[10]=centerColumn+1;
			 locations[16]=centerColumn-1;
			 locations[17]=locations[18]=locations[19]=centerColumn-2;
			 break;
		}
		return locations;
	}
	
	/* showHeadLocation 函数功能: 返回Plane飞机头坐标head[0][1] */
	public int[] showHeadLocation() {
		int[] head=new int[2];
		switch(direction) {
		case 0:
			head[0]=centerRow-1;
			head[1]=centerColumn;
			break;
		case 1:
			head[0]=centerRow+1;
			head[1]=centerColumn;
			break;
		case 2:
			head[0]=centerRow;
			head[1]=centerColumn-1;
			break;
		case 3:
			head[0]=centerRow;
			head[1]=centerColumn+1;
			break;
		}
		return head;
	}
	
	/* checkNotOverflow 函数功能：检查飞机位置是否越界，如果越界，返回false */
	public boolean checkNotOverflow(){
		int i=0;
		if (centerRow<1) i++;
		if (centerRow>8) i++;
		if (centerColumn<1) i++;
		if (centerColumn>8) i++;
		if (direction!=0 && direction!=1 && direction!=2 && direction!=3) i++;
		if (centerRow==1 && direction!=0) i++;
		if (centerRow==8 && direction!=1) i++;
		if (centerColumn==1 && direction!=2) i++;
		if (centerColumn==8 && direction!=3) i++;
		if (i==0) return true;
		else return false;
	}
	
	/* printPlaneCode 打印飞机信息码 */
	public void printPlaneCode() {
		System.out.println("飞机信息: "+centerRow+" "+centerColumn+" "+direction);
	}
	
	public static void main(String[] args) {
		
	}

}