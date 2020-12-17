package View;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import Calculate.*;
import Service.Sound;

public class MachineModeFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private int step;
	private int bombedPlane;
	private Sound sound = new Sound(); 
	private Enemy enemy;
	private long startTime;
	private JLayeredPane layeredPane = new JLayeredPane();
	private int[][] scrBStatus = new int[10][10];
	private ImageIcon[] circles;
	private ImageIcon[] heads;
	private ImageIcon[] crosses;
	
	public MachineModeFrame() {
		setLayout(null);
		Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
		
		setContentPane(layeredPane);
		machineModeBombPlanes();
		
		circles = new ImageIcon[5];
		heads = new ImageIcon[5];
		crosses = new ImageIcon[5];
		String [] colors = {"white","blue","red","yellow","green"};
		for(int i=0;i<5;i++) {
			heads[i]= new ImageIcon("image/buttonImage/"+colors[i]+"Head.png");
			heads[i].setImage(heads[i].getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT));
			circles[i]= new ImageIcon("image/buttonImage/"+colors[i]+"Circle.png");
			circles[i].setImage(circles[i].getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
			crosses[i]= new ImageIcon("image/buttonImage/"+colors[i]+"Cross.png");
			crosses[i].setImage(crosses[i].getImage().getScaledInstance(15,15,Image.SCALE_DEFAULT));
		}
		
		setBounds((int)screensize.getWidth()/2-500,(int)screensize.getHeight()/2-315, 1000, 630);// 设置横纵坐标和宽高
		setTitle("人机模式");// 标题
		setResizable(false);
        addWindowListener(new WindowAdapter() {// 添加窗体监听
            public void windowClosing(WindowEvent e) {// 窗体关闭前
            	System.exit(0);
            }
        });
        ImageIcon icon = new ImageIcon("image/MainFrame/icon.png");	//窗口图标
        setIconImage(icon.getImage());
        setVisible(true);
	}
	
	
	public void machineModeBombPlanes() {
		layeredPane.removeAll();
		layeredPane.repaint();
		layeredPane.setLayout(null);
		
		Screen screen = new Screen();
		sound.stopPlay();
		sound.playBGMBombPlanes();
		
		step=1;
		bombedPlane=0;
		
		enemy=new Enemy();
		Plane plane;
		for(int rest=3;rest>0;rest--) {
			plane=enemy.getRandomPlane();
			if(!enemy.addPlane(plane)) {
				rest++;
			}
		}
		layeredPane.setLayout(null);
		
		JButton returnMainMenu=new JButton("主菜单");
		returnMainMenu.setBounds(700,510,150,40);
		setButton(returnMainMenu);
		returnMainMenu.setVisible(true);
		returnMainMenu.addActionListener(new ActionListener() { // 为按钮添加鼠标单击事件
			public void actionPerformed(ActionEvent e) {
				sound.stopPlay();
				dispose();
				new MainFrame();
			}
		});
		layeredPane.add(returnMainMenu,new Integer(200));
		
		JLabel StepJL=new JLabel("当前是第  1  步");
		StepJL.setBounds(700,100,250,40);
		StepJL.setFont(new Font("宋体", Font.BOLD, 25));
		StepJL.setForeground(Color.white);
		StepJL.setVisible(true);
		layeredPane.add(StepJL,new Integer(200));
		
		
		JLabel BombedPlaneJL=new JLabel("已摧毁敌方飞机  0  架");
		BombedPlaneJL.setBounds(670,160,300,40);
		BombedPlaneJL.setFont(new Font("宋体", Font.BOLD, 25));
		BombedPlaneJL.setForeground(Color.white);
		BombedPlaneJL.setVisible(true);
		layeredPane.add(BombedPlaneJL,new Integer(200));
		
		JButton scrB[][]=new JButton[10][10];
		JLabel rowNumberJL[]=new JLabel[10];
		JLabel columbLetterJL[]=new JLabel[10];
		String numbers[]= {"1","2","3","4","5","6","7","8","9","10"};
		String letters[]= {"A","B","C","D","E","F","G","H","I","J"};
		
		for(int i=0;i<10;i++) {
			for(int j=0;j<10;j++) {
				scrBStatus[i][j]=0;	
			}
		}
		
		for(int i=0;i<=10;i++) {
			for(int j=0;j<=10;j++) {
				if(i==0){
					if(j!=0) {
						rowNumberJL[j-1]=new JLabel(numbers[j-1]);
						rowNumberJL[j-1].setFont(new Font("宋体", Font.BOLD, 18));
						rowNumberJL[j-1].setForeground(Color.white);
						rowNumberJL[j-1].setBounds(27+50*j, 20+50*i, 50, 50);
						rowNumberJL[j-1].setVisible(true);
						layeredPane.add(rowNumberJL[j-1],new Integer(200));
					}
				}
				else {
					if(j==0) {
						columbLetterJL[i-1]=new JLabel(letters[i-1]);
						columbLetterJL[i-1].setFont(new Font("宋体", Font.BOLD, 18));
						columbLetterJL[i-1].setForeground(Color.white);
						columbLetterJL[i-1].setBounds(30+50*j, 10+50*i, 50, 50);
						columbLetterJL[i-1].setVisible(true);
						layeredPane.add(columbLetterJL[i-1],new Integer(200));
					}
					else {
						scrB[i-1][j-1]=new JButton();
						setButton(scrB[i-1][j-1]);
						scrB[i-1][j-1].setBounds(10+50*j, 10+50*i, 50, 50);
						final int FiMinus1=i-1;
						final int FjMinus1=j-1;
						scrB[i-1][j-1].addMouseListener(new MouseListener() { // 为按钮添加鼠标单击事件
							@Override
							public void mouseClicked(MouseEvent e) {}

							@Override
							public void mousePressed(MouseEvent e) {}

							@Override
							public void mouseReleased(MouseEvent e) {
								//0:空  1:标记飞机身 2:标记飞机头 3:标记空 4:已轰炸飞机身 5:已轰炸飞机头 6:已轰炸空
								//0:白 1:蓝 2:红 3:黄 4:绿
								switch(e.getButton()) {
									case 1:
										int locationMessage=enemy.seeLocation(FiMinus1,FjMinus1);
										boolean repeat=false;
										if(screen.seeLocation(FiMinus1, FjMinus1)!=3) repeat=true;
										else {
											screen.changeScreen(FiMinus1,FjMinus1,locationMessage);
											switch (locationMessage) {
												case 0:
													scrBStatus[FiMinus1][FjMinus1]=60;
													scrB[FiMinus1][FjMinus1].setIcon(crosses[0]);
													break;
												case 1:
													scrBStatus[FiMinus1][FjMinus1]=40;
													scrB[FiMinus1][FjMinus1].setIcon(circles[0]);
													break;
												case 2:
													bombedPlane++;
													scrBStatus[FiMinus1][FjMinus1]=50+bombedPlane+1;
													scrB[FiMinus1][FjMinus1].setIcon(heads[bombedPlane+1]);
													break;
											}
										}
										if(bombedPlane<3) {
											BombedPlaneJL.setText("已摧毁敌方飞机  "+bombedPlane+"  架");
											BombMessageJDialog bmjd=new BombMessageJDialog(MachineModeFrame.this,locationMessage,step,bombedPlane,repeat);
											Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
											bmjd.setSize(400, 250); 
											bmjd.setLocation((int)screensize.getWidth()/2-200,(int)screensize.getHeight()/2-150);
											bmjd.setVisible(true);
											step++;
											StepJL.setText("当前是第  "+step+"  步");
										}
										else {
											machineModeFinish();
										}
										break;
									case 2:
										switch(scrBStatus[FiMinus1][FjMinus1]) {
										case 0:
											scrBStatus[FiMinus1][FjMinus1]=2;
											scrB[FiMinus1][FjMinus1].setIcon(heads[1]);
											break;
										case 1:
											scrBStatus[FiMinus1][FjMinus1]=2;
											scrB[FiMinus1][FjMinus1].setIcon(heads[1]);
											break;
										case 2:
											scrBStatus[FiMinus1][FjMinus1]=3;
											scrB[FiMinus1][FjMinus1].setIcon(crosses[1]);
											break;
										case 3:
											scrBStatus[FiMinus1][FjMinus1]=0;
											scrB[FiMinus1][FjMinus1].setIcon(null);
											break;
										}
										break;
									case 3:
										int guessColor=bombedPlane-1;
										if(guessColor<0) guessColor=0;
										guessColor+=2;
										System.out.println("guessColor:"+guessColor);
										switch(scrBStatus[FiMinus1][FjMinus1]) {
										case 0:
											scrBStatus[FiMinus1][FjMinus1]=1;
											scrB[FiMinus1][FjMinus1].setIcon(circles[1]);
											break;
										case 1:
											scrBStatus[FiMinus1][FjMinus1]=0;
											scrB[FiMinus1][FjMinus1].setIcon(null);
											break;
										case 2:
											scrBStatus[FiMinus1][FjMinus1]=1;
											scrB[FiMinus1][FjMinus1].setIcon(circles[1]);
											break;
										case 3:
											scrBStatus[FiMinus1][FjMinus1]=1;
											scrB[FiMinus1][FjMinus1].setIcon(circles[1]);
											break;
										case 40:
											scrBStatus[FiMinus1][FjMinus1]=40+guessColor;
											scrB[FiMinus1][FjMinus1].setIcon(circles[guessColor]);
											break;
										case 42:
											if(guessColor==3) {
												scrBStatus[FiMinus1][FjMinus1]=40;
												scrB[FiMinus1][FjMinus1].setIcon(circles[0]);
											}
											else {
												scrBStatus[FiMinus1][FjMinus1]=43;
												scrB[FiMinus1][FjMinus1].setIcon(circles[3]);
											}
											break;
										case 43:
											if(guessColor==4) {
												scrBStatus[FiMinus1][FjMinus1]=40;
												scrB[FiMinus1][FjMinus1].setIcon(circles[0]);
											}
											else {
												scrBStatus[FiMinus1][FjMinus1]=44;
												scrB[FiMinus1][FjMinus1].setIcon(circles[4]);
											}
											break;
										case 44:
											if(guessColor==2) {
												scrBStatus[FiMinus1][FjMinus1]=40;
												scrB[FiMinus1][FjMinus1].setIcon(circles[0]);
											}
											else {
												scrBStatus[FiMinus1][FjMinus1]=42;
												scrB[FiMinus1][FjMinus1].setIcon(circles[2]);
											}
											break;
										}
										break;
								}
							}
							@Override
							public void mouseEntered(MouseEvent e) {}
							@Override
							public void mouseExited(MouseEvent e) {} 
						});
						scrB[i-1][j-1].setVisible(true);
						layeredPane.add(scrB[i-1][j-1],new Integer(200));
					}
				}
			}
		}
		startTime=System.currentTimeMillis();
		
		//背景
		ImageIcon backgroundIcon = new ImageIcon("image/MainFrame/background.jpg");
		backgroundIcon.setImage(backgroundIcon.getImage().getScaledInstance(1000,630,Image.SCALE_DEFAULT));
		JLabel backgroundLabel=new JLabel(backgroundIcon);
		backgroundLabel.setBounds(0, 0,1000,630);
		backgroundLabel.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(backgroundLabel,new Integer(200));
		
		layeredPane.revalidate();// 容器重新验证所有组件
	}
	
	public void machineModeFinish() {
		layeredPane.removeAll();
		layeredPane.repaint();
		layeredPane.setLayout(null);
		
		long usedTime=(System.currentTimeMillis()-startTime)/1000;
		sound.stopPlay();
		sound.playBGMBombFinish();
		JLabel BMFEJL[][]=new JLabel[10][10];
		JLabel rowNumberJL[]=new JLabel[10];
		JLabel columbLetterJL[]=new JLabel[10];
		String numbers[]= {"1","2","3","4","5","6","7","8","9","10"};
		String letters[]= {"A","B","C","D","E","F","G","H","I","J"};
		Icon messages[]= {null,circles[0],heads[0]};
		
		for(int i=0;i<=10;i++) {
			for(int j=0;j<=10;j++) {
				if(i==0){
					if(j!=0) {
						rowNumberJL[j-1]=new JLabel(numbers[j-1]);
						rowNumberJL[j-1].setFont(new Font("宋体", Font.BOLD, 18));
						rowNumberJL[j-1].setForeground(Color.white);
						rowNumberJL[j-1].setBounds(10+50*j, 10+50*i, 50, 50);
						rowNumberJL[j-1].setHorizontalAlignment(SwingConstants.CENTER);
						layeredPane.add(rowNumberJL[j-1],new Integer(200));
					}
				}
				else {
					if(j==0) {
						columbLetterJL[i-1]=new JLabel(letters[i-1]);
						columbLetterJL[i-1].setFont(new Font("宋体", Font.BOLD, 18));
						columbLetterJL[i-1].setForeground(Color.white);
						columbLetterJL[i-1].setBounds(10+50*j, 10+50*i, 50, 50);
						columbLetterJL[i-1].setHorizontalAlignment(SwingConstants.CENTER);
						layeredPane.add(columbLetterJL[i-1],new Integer(200));
					}
					else {
						BMFEJL[i-1][j-1]=new JLabel(messages[enemy.seeLocation(i-1,j-1)]);
						BMFEJL[i-1][j-1].setFont(new Font("宋体", Font.BOLD, 18));
						BMFEJL[i-1][j-1].setForeground(Color.white);
						BMFEJL[i-1][j-1].setBounds(10+50*j, 10+50*i, 50, 50);
						BMFEJL[i-1][j-1].setVisible(true);
						layeredPane.add(BMFEJL[i-1][j-1],new Integer(200));
					}
				}
			}
		}
		

		JLabel TotalStepJL=new JLabel("共  "+step+"  步");
		TotalStepJL.setBounds(700,100,250,40);
		TotalStepJL.setFont(new Font("宋体", Font.BOLD, 25));
		TotalStepJL.setForeground(Color.white);
		TotalStepJL.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(TotalStepJL,new Integer(200));
		
		
		int usedHours=(int)usedTime/3600;
		usedHours%=24;
		usedTime%=3600;
		int usedMinutes=(int)usedTime/60;
		usedTime%=60;
		int usedSeconds=(int)usedTime;

		JLabel TotalTimeJL=new JLabel(String.format("共用时 %02d:%02d:%02d",usedHours,usedMinutes,usedSeconds));
		TotalTimeJL.setBounds(700,160,250,40);
		TotalTimeJL.setFont(new Font("宋体", Font.BOLD, 25));
		TotalTimeJL.setForeground(Color.white);
		TotalTimeJL.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(TotalTimeJL,new Integer(200));
		
		
		JButton returnMainMenu=new JButton("主菜单");
		returnMainMenu.setBounds(700,510,150,40);
		setButton(returnMainMenu);
		returnMainMenu.addActionListener(new ActionListener() { // 为按钮添加鼠标单击事件
			public void actionPerformed(ActionEvent e) {
				sound.stopPlay();
				dispose();
				new MainFrame();
			}
		});
		layeredPane.add(returnMainMenu,new Integer(200));
		
		
		JButton machineModeAgain=new JButton("再来一局");
		machineModeAgain.setBounds(700,440,150,40);
		setButton(machineModeAgain);
		machineModeAgain.addActionListener(new ActionListener() { // 为按钮添加鼠标单击事件
			public void actionPerformed(ActionEvent e) {
				machineModeBombPlanes();
			}
		});
		layeredPane.add(machineModeAgain,new Integer(200));

		//背景
		ImageIcon backgroundIcon = new ImageIcon("image/MainFrame/background.jpg");
		backgroundIcon.setImage(backgroundIcon.getImage().getScaledInstance(1000,630,Image.SCALE_DEFAULT));
		JLabel backgroundLabel=new JLabel(backgroundIcon);
		backgroundLabel.setBounds(0, 0,1000,630);
		backgroundLabel.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(backgroundLabel,new Integer(200));
		
		layeredPane.revalidate();// 容器重新验证所有组件
	}
	
	//设置按钮
	public void setButton(JButton button) {
		button.setFont(new Font("宋体", Font.BOLD, 18));
		button.setForeground(Color.white);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
	}
	
}
	