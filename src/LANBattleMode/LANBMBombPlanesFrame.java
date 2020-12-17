package LANBattleMode;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import Service.Sound;
import Calculate.*;
import View.*;

public class LANBMBombPlanesFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private LANBMFindEnemyFrame messageHandler;
	private JLayeredPane layeredPane = new JLayeredPane();
	private Sound sound = new Sound(); 
	private final Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
	private final int myInputCode;
	private final int codeReceiveFromEnemy;
	private boolean IsMyRound;
	private JLabel BMRoundJL;
	private Enemy enemy = new Enemy();		//敌方飞机布局信息
	private Enemy myInputEnemy= new Enemy();		//我方飞机布局信息
	private Screen screen = new Screen();
	private int step=1;
	private int bombedPlane=0;
	private JLabel[][] LMJL=new JLabel[10][10];
	
	private int[][] scrBStatus = new int[10][10];
	private ImageIcon[] circles;
	private ImageIcon[] heads;
	private ImageIcon[] crosses;
	private ImageIcon smallWhiteCircle;
	private ImageIcon smallWhiteHead;
	private ImageIcon smallRedCircle;
	private ImageIcon smallRedHead;
	private ImageIcon smallRedCross;
	
	public LANBMBombPlanesFrame(LANBMFindEnemyFrame myfindEnemyFrame,int InputCode,int codeReceive,boolean isFirst) {
		messageHandler=myfindEnemyFrame;
		IsMyRound=isFirst;
		setLayout(null);
		sound.playBGMBombPlanes();
		
		setContentPane(layeredPane);
		myInputCode=InputCode;
		codeReceiveFromEnemy=codeReceive;
		
		step=1;
		bombedPlane=0;
		enemy.addPlanes9Code(codeReceiveFromEnemy);
		myInputEnemy.addPlanes9Code(myInputCode);
		
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
			crosses[i].setImage(crosses[i].getImage().getScaledInstance(18,18,Image.SCALE_DEFAULT));
		}
		
		ImageIcon homeIcon= new ImageIcon("image/MainFrame/home.png");
		homeIcon.setImage(homeIcon.getImage().getScaledInstance(44,40,Image.SCALE_DEFAULT));
		smallWhiteCircle= new ImageIcon("image/buttonImage/whiteCircle.png");
		smallWhiteCircle.setImage(smallWhiteCircle.getImage().getScaledInstance(15,15,Image.SCALE_DEFAULT));
		smallWhiteHead= new ImageIcon("image/buttonImage/whiteHead.png");
		smallWhiteHead.setImage(smallWhiteHead.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
		smallRedCircle= new ImageIcon("image/buttonImage/redCircle.png");
		smallRedCircle.setImage(smallRedCircle.getImage().getScaledInstance(15,15,Image.SCALE_DEFAULT));
		smallRedHead= new ImageIcon("image/buttonImage/redHead.png");
		smallRedHead.setImage(smallRedHead.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
		smallRedCross= new ImageIcon("image/buttonImage/redCross.png");
		smallRedCross.setImage(smallRedCross.getImage().getScaledInstance(10,10,Image.SCALE_DEFAULT));
		
		JButton returnMainMenu=new JButton(homeIcon);
		returnMainMenu.setBounds(0,0,60,40);
		setButton(returnMainMenu);
		returnMainMenu.setHorizontalAlignment(SwingConstants.CENTER);
		returnMainMenu.setVisible(true);
		returnMainMenu.addActionListener(new ActionListener() { // 为按钮添加鼠标单击事件
			public void actionPerformed(ActionEvent e) {
				dispose();
				soundStopPlay();
				messageHandler.stopConnect();
				new MainFrame();
			}
		});
		layeredPane.add(returnMainMenu,new Integer(300));
		
		
		JLabel StepJL=new JLabel("当前是第  1  步");
		StepJL.setBounds(700,0,150,30);
		StepJL.setHorizontalAlignment(SwingConstants.CENTER);
		StepJL.setFont(new Font("微软雅黑",0,20));
		StepJL.setForeground(Color.white);
		StepJL.setVisible(true);
		layeredPane.add(StepJL,new Integer(300));
		
		
		JLabel BombedPlaneJL=new JLabel("已摧毁敌方飞机  0  架");
		BombedPlaneJL.setBounds(710,30,200,30);
		BombedPlaneJL.setHorizontalAlignment(SwingConstants.CENTER);
		BombedPlaneJL.setFont(new Font("微软雅黑",0,20));
		BombedPlaneJL.setForeground(Color.white);
		BombedPlaneJL.setVisible(true);
		layeredPane.add(BombedPlaneJL,new Integer(300));
		
		
		if(IsMyRound) {
			BMRoundJL=new JLabel("我方回合");
			BMRoundJL.setForeground(Color.cyan);
		}
		else {
			BMRoundJL=new JLabel("敌方回合");
			BMRoundJL.setForeground(Color.red);
		}
		BMRoundJL.setHorizontalAlignment(SwingConstants.CENTER);
		BMRoundJL.setBounds(880,0,100,30);
		BMRoundJL.setFont(new Font("微软雅黑",0,20));
		BMRoundJL.setVisible(true);
		layeredPane.add(BMRoundJL,new Integer(300));
		
		
		JButton scrB[][]=new JButton[10][10];
		JLabel rowNumberJL[]=new JLabel[10];
		JLabel columbLetterJL[]=new JLabel[10];
		String numbers[]= {"1","2","3","4","5","6","7","8","9","10"};
		String letters[]= {"A","B","C","D","E","F","G","H","I","J"};
		
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
										if(IsMyRound) {
											IsMyRound=false;
											BMRoundJL.setText("敌方回合");
											BMRoundJL.setForeground(Color.red);
											
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
											if(bombedPlane>=3) {
												messageHandler.sendMessage("5");
											}
											else {
												messageHandler.sendMessage("2"+FiMinus1+FjMinus1);
											}
											BombedPlaneJL.setText("已摧毁敌方飞机  "+bombedPlane+"  架");
											BombMessageJDialog bmjd=new BombMessageJDialog(LANBMBombPlanesFrame.this,locationMessage,step,bombedPlane,repeat);
											Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
											bmjd.setSize(400, 250); 
											bmjd.setLocation((int)screensize.getWidth()/2-200,(int)screensize.getHeight()/2-150);
											bmjd.setVisible(true);
											step++;
											StepJL.setText("当前是第  "+step+"  步");
											if(bombedPlane>=3) 
											{
												dispose();
												soundStopPlay();
												messageHandler.createMyFinishFrame(true);
											}
										} 
										else {
											TextDialog txtDl=new TextDialog(LANBMBombPlanesFrame.this,"提示","现在是对手的回合！");
											Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
											txtDl.setSize(600, 300); 
											txtDl.setLocation((int)screensize.getWidth()/2-300,(int)screensize.getHeight()/2-150);
											txtDl.setVisible(true);
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
		
		
		for(int p=0;p<10;p++) {
			for(int q=0;q<10;q++) {
				switch(myInputEnemy.seeLocation(p,q)) {
					case 0:
						LMJL[p][q]=new JLabel();
						break;
					case 1:
						LMJL[p][q]=new JLabel(smallWhiteCircle);
						break;
					case 2:
						LMJL[p][q]=new JLabel(smallWhiteHead);
						break;
				}
				LMJL[p][q].setBounds(600+33*q,60+33*p,33,33);
				LMJL[p][q].setHorizontalAlignment(SwingConstants.CENTER);
				LMJL[p][q].setVisible(true);
				layeredPane.add(LMJL[p][q],new Integer(300));	
			}
		}
	
		JPanel chatPanel = messageHandler.getChatPanel();
		chatPanel.setLocation(685,394);
		layeredPane.add(chatPanel,new Integer(200)); // 将按钮添加到容器中	
		
		//背景
		ImageIcon backgroundIcon = new ImageIcon("image/MainFrame/background.jpg");
		backgroundIcon.setImage(backgroundIcon.getImage().getScaledInstance(1000,630,Image.SCALE_DEFAULT));
		JLabel backgroundLabel=new JLabel(backgroundIcon);
		backgroundLabel.setBounds(0, 0,1000,630);
		backgroundLabel.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(backgroundLabel,new Integer(200));
		
		setBounds((int)screensize.getWidth()/2-500,(int)screensize.getHeight()/2-315, 1000, 630);// 设置横纵坐标和宽高
		setTitle("炸飞机！");// 标题
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
	
	//停止播放音乐
	public void soundStopPlay() {
		sound.stopPlay();
	}
	
	//接受对方炸飞机坐标，更新回合
	public void handleReceiveLocation(int p,int q) {
		switch(myInputEnemy.seeLocation(p, q)) {
		case 0:
			LMJL[p][q].setIcon(smallRedCross);
			break;
		case 1:
			LMJL[p][q].setIcon(smallRedCircle);
			break;
		case 2:
			LMJL[p][q].setIcon(smallRedHead);
			break;
		}
		IsMyRound=true;
		BMRoundJL.setText("我方回合");
		BMRoundJL.setForeground(Color.cyan);
	}
	
	//设置按钮
	public void setButton(JButton button) {
		button.setFont(new Font("宋体", Font.BOLD, 18));
		button.setForeground(Color.white);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
	}
}
