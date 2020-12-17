package WANBattleMode;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import Service.Sound;
import Calculate.*;
import View.*;

public class WANBMInputPlanesFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private WANBMFindEnemyFrame messageHandler;
	private JLayeredPane layeredPane = new JLayeredPane();
	private Sound sound = new Sound(); 
	private final Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
	private boolean BattleModeAlreadySent;
	private Enemy myInputEnemy;	//我方飞机布局信息
	private int myInputCode;
	private int InputtingPlane=1;
	private boolean AlreadyChooseCenter=false;
	private int InputtedcenterRow=-1;
	private int InputtedcenterColumn=-1;

	private ImageIcon blueCircle = new ImageIcon("image/buttonImage/blueCircle.png");
	private ImageIcon cyanCircle = new ImageIcon("image/buttonImage/cyanCircle.png");
	private ImageIcon whiteCircle = new ImageIcon("image/buttonImage/whiteCircle.png");
	private ImageIcon whiteHead = new ImageIcon("image/buttonImage/whiteHead.png");
	
	public WANBMInputPlanesFrame(WANBMFindEnemyFrame myfindEnemyFrame) {
		messageHandler=myfindEnemyFrame;
		setLayout(null);
		sound.playBGMInputPlanes();
		setContentPane(layeredPane);
		BattleModeAlreadySent=false;
		
		blueCircle.setImage(blueCircle.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
		cyanCircle.setImage(cyanCircle.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
		whiteCircle.setImage(whiteCircle.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
		whiteHead.setImage(whiteHead.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
		BattleModeInput();
		
		setBounds((int)screensize.getWidth()/2-500,(int)screensize.getHeight()/2-315, 1000, 630);// 设置横纵坐标和宽高
		setTitle("输入飞机");// 标题
		setResizable(false);
        addWindowListener(new WindowAdapter() {// 添加窗体监听
            public void windowClosing(WindowEvent e) {// 窗体关闭前
            	System.exit(0);
            }
        });
        ImageIcon icon = new ImageIcon("image/LANBM/icon.png");	//窗口图标
        setIconImage(icon.getImage());
        setVisible(true);
	}
	
	
	//对战模式 输入九位信息码
	public void BattleModeInput() { 
		layeredPane.removeAll();
		layeredPane.repaint();
		layeredPane.setLayout(null);
		
		myInputEnemy = new Enemy();
		InputtingPlane=1;
		AlreadyChooseCenter=false;
		InputtedcenterRow=-1;
		InputtedcenterColumn=-1;
		myInputCode=0;
		layeredPane.setLayout(null);
		JButton removeAllJB=new JButton("全部删除");
		removeAllJB.setBounds(650,10,150,40);
		setButton(removeAllJB);
		removeAllJB.addActionListener(new ActionListener() { // 为按钮添加鼠标单击事件
			public void actionPerformed(ActionEvent e) {
				if(BattleModeAlreadySent) {
					TextDialog AlreadySentDl=new TextDialog(WANBMInputPlanesFrame.this,"提交信息",
							"<html>你已经提交过了！<br/>请等待对方提交！</html>");
					Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
					AlreadySentDl.setSize(400, 200); 
					AlreadySentDl.setLocation((int)screensize.getWidth()/2-200,(int)screensize.getHeight()/2-100);
					AlreadySentDl.setVisible(true);
				}
				else {
					BattleModeInput();
				}
			}
		});
		layeredPane.add(removeAllJB,new Integer(200));
		
		ImageIcon homeIcon= new ImageIcon("image/MainFrame/home.png");
		homeIcon.setImage(homeIcon.getImage().getScaledInstance(44,40,Image.SCALE_DEFAULT));
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
						scrB[i-1][j-1].addActionListener(new ActionListener() { // 为按钮添加鼠标单击事件
							/**
							static int inputCode;
							static int InputtingPlane=1;
							static boolean AlreadyChooseCenter=false;
							static int InputtedcenterRow=-1;
							static int InputtedcenterColumn=-1;
							*/
							@Override
							public void actionPerformed(ActionEvent e) {
								if(BattleModeAlreadySent==false) {
									if(InputtingPlane<=3) {
										if(myInputEnemy.seeLocation(FiMinus1,FjMinus1)==0) {
											if(AlreadyChooseCenter==false) {
												scrB[FiMinus1][FjMinus1].setIcon(blueCircle);
												InputtedcenterRow=FiMinus1;
												InputtedcenterColumn=FjMinus1;
												AlreadyChooseCenter=true;
											}//选中中心 结束
											else {
												if(InputtedcenterRow==FiMinus1&&InputtedcenterColumn==FjMinus1) {
													scrB[FiMinus1][FjMinus1].setIcon(null);
													InputtedcenterRow=-1;
													InputtedcenterColumn=-1;
													AlreadyChooseCenter=false;
												}//取消中心选中结束
												else if(InputtedcenterRow==FiMinus1+1&&InputtedcenterColumn==FjMinus1) {
													//上0
													Plane plane=new Plane(InputtedcenterRow,InputtedcenterColumn,0);
													if(myInputEnemy.addPlane(plane)) {
														int locationMessages[]=plane.showLocations();
														for(int j=0;j<10;j++) {
															scrB[locationMessages[j]][locationMessages[j+10]].setIcon(whiteCircle);
														}
														scrB[InputtedcenterRow][InputtedcenterColumn].setIcon(cyanCircle);
														scrB[plane.showHeadLocation()[0]][plane.showHeadLocation()[1]].setIcon(whiteHead);
														myInputCode+=(int)(Math.pow(10,InputtingPlane*3-1)*InputtedcenterRow+
																Math.pow(10,InputtingPlane*3-2)*InputtedcenterColumn+Math.pow(10,InputtingPlane*3-3)*0);
														InputtingPlane++;
														InputtedcenterRow=-1;
														InputtedcenterColumn=-1;
														AlreadyChooseCenter=false;
													}
													else {
														String text="飞机输入不合法！";
														TextDialog txtDl=new TextDialog(WANBMInputPlanesFrame.this,"提示",text);
														Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
														txtDl.setSize(200, 150); 
														txtDl.setLocation((int)screensize.getWidth()/2-100,(int)screensize.getHeight()/2-75);
														txtDl.setVisible(true);
													}
												}//上结束
												else if(InputtedcenterRow==FiMinus1-1&&InputtedcenterColumn==FjMinus1) {
													//下1
													Plane plane=new Plane(InputtedcenterRow,InputtedcenterColumn,1);
													if(myInputEnemy.addPlane(plane)) {
														int locationMessages[]=plane.showLocations();
														scrB[InputtedcenterRow][InputtedcenterColumn].setForeground(Color.cyan);
														for(int j=0;j<10;j++) {
															scrB[locationMessages[j]][locationMessages[j+10]].setIcon(whiteCircle);
														}
														scrB[InputtedcenterRow][InputtedcenterColumn].setIcon(cyanCircle);
														scrB[plane.showHeadLocation()[0]][plane.showHeadLocation()[1]].setIcon(whiteHead);
														myInputCode+=(int)(Math.pow(10,InputtingPlane*3-1)*InputtedcenterRow+
																Math.pow(10,InputtingPlane*3-2)*InputtedcenterColumn+Math.pow(10,InputtingPlane*3-3)*1);
														InputtingPlane++;
														InputtedcenterRow=-1;
														InputtedcenterColumn=-1;
														AlreadyChooseCenter=false;
													}
													else {
														String text="飞机输入不合法！";
														TextDialog txtDl=new TextDialog(WANBMInputPlanesFrame.this,"提示",text);
														Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
														txtDl.setSize(200, 150); 
														txtDl.setLocation((int)screensize.getWidth()/2-100,(int)screensize.getHeight()/2-75);
														txtDl.setVisible(true);
													}
												}//下结束
												else if(InputtedcenterRow==FiMinus1&&InputtedcenterColumn==FjMinus1+1) {
													//左2
													Plane plane=new Plane(InputtedcenterRow,InputtedcenterColumn,2);
													if(myInputEnemy.addPlane(plane)) {
														int locationMessages[]=plane.showLocations();
														scrB[InputtedcenterRow][InputtedcenterColumn].setForeground(Color.cyan);
														for(int j=0;j<10;j++) {
															scrB[locationMessages[j]][locationMessages[j+10]].setIcon(whiteCircle);
														}
														scrB[InputtedcenterRow][InputtedcenterColumn].setIcon(cyanCircle);
														scrB[plane.showHeadLocation()[0]][plane.showHeadLocation()[1]].setIcon(whiteHead);
														myInputCode+=(int)(Math.pow(10,InputtingPlane*3-1)*InputtedcenterRow+
																Math.pow(10,InputtingPlane*3-2)*InputtedcenterColumn+Math.pow(10,InputtingPlane*3-3)*2);
														InputtingPlane++;
														InputtedcenterRow=-1;
														InputtedcenterColumn=-1;
														AlreadyChooseCenter=false;
													}
													else {
														String text="飞机输入不合法！";
														TextDialog txtDl=new TextDialog(WANBMInputPlanesFrame.this,"提示",text);
														Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
														txtDl.setSize(200, 150); 
														txtDl.setLocation((int)screensize.getWidth()/2-100,(int)screensize.getHeight()/2-75);
														txtDl.setVisible(true);
													}
												}//左结束
												else if(InputtedcenterRow==FiMinus1&&InputtedcenterColumn==FjMinus1-1) {
													//右3
													Plane plane=new Plane(InputtedcenterRow,InputtedcenterColumn,3);
													if(myInputEnemy.addPlane(plane)) {
														int locationMessages[]=plane.showLocations();
														scrB[InputtedcenterRow][InputtedcenterColumn].setForeground(Color.cyan);
														for(int j=0;j<10;j++) {
															scrB[locationMessages[j]][locationMessages[j+10]].setIcon(whiteCircle);
														}
														scrB[InputtedcenterRow][InputtedcenterColumn].setIcon(cyanCircle);
														scrB[plane.showHeadLocation()[0]][plane.showHeadLocation()[1]].setIcon(whiteHead);
														myInputCode+=(int)(Math.pow(10,InputtingPlane*3-1)*InputtedcenterRow+
																Math.pow(10,InputtingPlane*3-2)*InputtedcenterColumn+Math.pow(10,InputtingPlane*3-3)*3);
														InputtingPlane++;
														InputtedcenterRow=-1;
														InputtedcenterColumn=-1;
														AlreadyChooseCenter=false;
													}
													else {
														String text="飞机输入不合法！";
														TextDialog txtDl=new TextDialog(WANBMInputPlanesFrame.this,"提示",text);
														Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
														txtDl.setSize(200, 150); 
														txtDl.setLocation((int)screensize.getWidth()/2-100,(int)screensize.getHeight()/2-75);
														txtDl.setVisible(true);
													}
												}//右结束
												else {
													scrB[InputtedcenterRow][InputtedcenterColumn].setIcon(null);
													scrB[InputtedcenterRow][InputtedcenterColumn].setForeground(Color.black);
													scrB[FiMinus1][FjMinus1].setIcon(blueCircle);
													InputtedcenterRow=FiMinus1;
													InputtedcenterColumn=FjMinus1;
													AlreadyChooseCenter=true;
												}//更换中心位置 结束
											}//选中非中心 结束
										}//选中位置为空 结束
										else {
											String text="选择失败！";
											TextDialog txtDl=new TextDialog(WANBMInputPlanesFrame.this,"提示",text);
											Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
											txtDl.setSize(200, 150); 
											txtDl.setLocation((int)screensize.getWidth()/2-100,(int)screensize.getHeight()/2-75);
											txtDl.setVisible(true);
										}//选中位置非空 结束
									}//未输入三架飞机 结束
									else {
										String text="<html>已输入三架飞机!<br/>请在确认无误后点击提交！</html>";
										TextDialog txtDl=new TextDialog(WANBMInputPlanesFrame.this,"提示",text);
										Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
										txtDl.setSize(400, 200); 
										txtDl.setLocation((int)screensize.getWidth()/2-200,(int)screensize.getHeight()/2-100);
										txtDl.setVisible(true);
									}//已输入三架飞机 结束
								}//未提交 结束
								else {
									String text="<html>已经提交过飞机布局信息!<br/>请等待对方提交！</html>";
									TextDialog txtDl=new TextDialog(WANBMInputPlanesFrame.this,"提示",text);
									Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
									txtDl.setSize(400, 200); 
									txtDl.setLocation((int)screensize.getWidth()/2-200,(int)screensize.getHeight()/2-100);
									txtDl.setVisible(true);
								}//已提交 结束
							}//actionperformed 结束
						});//actionListener 结束
						scrB[i-1][j-1].setVisible(true);
						layeredPane.add(scrB[i-1][j-1],new Integer(200));
					}//j!=0 结束
				}//i!=0 结束
			}
		}

		JButton HandInJB=new JButton("提交");
		setButton(HandInJB);
		HandInJB.setBounds(830,10,150,40);
		HandInJB.addActionListener(new ActionListener() { // 为按钮添加鼠标单击事件
			public void actionPerformed(ActionEvent e) {
				if(BattleModeAlreadySent==false) {
					if(InputtingPlane>3) {
						messageHandler.sendMessage("1"+String.valueOf(myInputCode));
						BattleModeAlreadySent=true;
						messageHandler.saveMyInputCode(myInputCode);
						if(messageHandler.getCodeReceiveFromEnemy()==-1) {
							TextDialog SentSuccessDl=new TextDialog(WANBMInputPlanesFrame.this,"提交信息",
									"<html>提交成功！<br/>请等待对方提交！</html>");
							Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
							SentSuccessDl.setSize(400, 200); 
							SentSuccessDl.setLocation((int)screensize.getWidth()/2-200,(int)screensize.getHeight()/2-100);
							SentSuccessDl.setVisible(true);
						}
						else {
							dispose();
							soundStopPlay();
							messageHandler.createMyBombPlanesFrame(false);
						}
					}
					else {
						String text="请输入3架飞机后再点击提交！";
						TextDialog txtDl=new TextDialog(WANBMInputPlanesFrame.this,"提交信息",text);
						Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
						txtDl.setSize(400, 200); 
						txtDl.setLocation((int)screensize.getWidth()/2-200,(int)screensize.getHeight()/2-100);
						txtDl.setVisible(true);
					}
				}
				else {
					TextDialog AlreadySentDl=new TextDialog(WANBMInputPlanesFrame.this,"提交信息",
							"<html>你已经提交过了！<br/>请等待对方提交！</html>");
					Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
					AlreadySentDl.setSize(400, 200); 
					AlreadySentDl.setLocation((int)screensize.getWidth()/2-200,(int)screensize.getHeight()/2-100);
					AlreadySentDl.setVisible(true);
				}
			}
		});
		layeredPane.add(HandInJB,new Integer(200)); // 将按钮添加到容器中	
		
		JPanel chatPanel = messageHandler.getChatPanel();
		chatPanel.setLocation(685,394);
		layeredPane.add(chatPanel,new Integer(200)); // 将按钮添加到容器中	
		
		//背景
		ImageIcon backgroundIcon = new ImageIcon("image/LANBM/background.jpg");
		backgroundIcon.setImage(backgroundIcon.getImage().getScaledInstance(1000,630,Image.SCALE_DEFAULT));
		JLabel backgroundLabel=new JLabel(backgroundIcon);
		backgroundLabel.setBounds(0, 0,1000,630);
		backgroundLabel.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(backgroundLabel,new Integer(200));
		
		layeredPane.revalidate();// 容器重新验证所有组件
	}
	
	//是否已经发送9位信息码
	public boolean getBattleModeAlreadySent() {
		return BattleModeAlreadySent;
	}
	
	//停止播放音乐
	public void soundStopPlay() {
		sound.stopPlay();
	}
	
	//设置按钮
	public void setButton(JButton button) {
		button.setFont(new Font("宋体", Font.BOLD, 18));
		button.setForeground(Color.white);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
	}
	
}
