package View;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import Calculate.*;
import Service.Sound;
public class TeachingModeFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private int step;
	private int bombedPlane;
	private Sound sound = new Sound(); 
	private JLayeredPane layeredPane = new JLayeredPane();
	private int[][] scrBStatus = new int[10][10];
	private ImageIcon[] circles;
	private ImageIcon[] heads;
	private ImageIcon[] crosses;
	
	public TeachingModeFrame() {
		Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((int)screensize.getWidth()/2-500,(int)screensize.getHeight()/2-315, 1000, 630);// 设置横纵坐标和宽高
		setTitle("教学模式");// 标题
		
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
		
		setContentPane(layeredPane);
        sound.playBGMBombPlanes();
        addWindowListener(new WindowAdapter() {// 添加窗体监听
            public void windowClosing(WindowEvent e) {// 窗体关闭前
            	System.exit(0);
            }
        }); 
        ImageIcon icon = new ImageIcon("image/MainFrame/icon.png");	//窗口图标
        setIconImage(icon.getImage());
        setResizable(false);
        setVisible(true);
        TeachingMode(1);
	}
	
	public void TeachingMode(int difficulty) {
		layeredPane.removeAll();
		layeredPane.repaint();
		layeredPane.setLayout(null);
		
		Enemy enemy = new Enemy();
		Screen screen = new Screen();
		step=1;
		bombedPlane=0;
		int Planes9Codes[]= {471721221,222752272,743273223,630362221,722473221
		,633370221,851283212,783821212,861712212,443712212,430150221,642723412};
		if(difficulty<5) {
			int TMPlanes9Code=Planes9Codes[(int)(Math.random()*3)+3*difficulty-3];
			enemy.addPlanes9Code(TMPlanes9Code);
		}
		else {
			Plane plane;
			for(int rest=3;rest>0;rest--) {
				plane=enemy.getRandomPlane();
				if(!enemy.addPlane(plane)) {
					rest++;
				}
			}
		}
		
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
		
			
		JButton TMHelpJb=new JButton("帮助");
		TMHelpJb.setBounds(700,440,150,40);
		setButton(TMHelpJb);
		TMHelpJb.setVisible(true);
		TMHelpJb.addActionListener(new ActionListener() { // 为按钮添加鼠标单击事件
			public void actionPerformed(ActionEvent e) {
				String HelpJBTextArray[]= {"地图中共有三架飞机，飞机所占格数自头到尾均为1513，<br/>飞机头被击中即视为该架飞机被摧毁。" + 
"<br/>你的任务是通过选择与推理，用最少的步数摧毁所有飞机。<br/>点击地图上的坐标按钮以进行轰炸。",
				"<br/><br/>飞机可以朝着上下左右四个方向。",
				"<br/><br/>飞机的头部可能会偏向角落，这会使飞机更不易被摧毁。<br/>尝试摧毁敌机的同时，也要注意揣摩敌人的心理，"
		+ "<br/>利用他们的惯性思维来保护自己的飞机和摧毁他们的飞机。",
				"<br/><br/>飞机虽然不能重叠，但是它们可以相邻。<br/>" + 
				"<br/><br/>通过位置和方向的调整使飞机紧靠在一起，<br/>将加大推测飞机头位置的难度<br/>如果推理受阻，请不要忘了这种可能性。",
				"<br/><br/>胜利就在眼前！"
				};
				String HelpJBText=new String();
				HelpJBText="<html>";
				for(int i=0;i<difficulty;i++) {
					HelpJBText+=HelpJBTextArray[i];
				}
				HelpJBText+="</html>";
				TextDialog txtDl=new TextDialog(TeachingModeFrame.this,"帮助",HelpJBText);
				Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
				int Dlheight=300+50*difficulty;
				txtDl.setSize(600, Dlheight); 
				txtDl.setLocation((int)screensize.getWidth()/2-300,((int)screensize.getHeight()-Dlheight)/2);
				txtDl.setVisible(true);
			}
		});
		layeredPane.add(TMHelpJb,new Integer(200));
		
		JLabel LevelJL=new JLabel("教学模式第 "+difficulty+" 关");
		LevelJL.setBounds(695,45,250,40);
		LevelJL.setFont(new Font("宋体", Font.BOLD, 25));
		LevelJL.setForeground(Color.white);
		LevelJL.setVisible(true);
		layeredPane.add(LevelJL,new Integer(200));
		
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
		
		ImageIcon backgroundIcon = new ImageIcon("image/MainFrame/background.jpg");
		backgroundIcon.setImage(backgroundIcon.getImage().getScaledInstance(1000,630,Image.SCALE_DEFAULT));
		JLabel backgroundLabel=new JLabel(backgroundIcon);
		backgroundLabel.setBounds(0, 0,1000,630);
		backgroundLabel.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(backgroundLabel,new Integer(100));
		
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
												if(locationMessage==2) bombedPlane++;
												scrBStatus[FiMinus1][FjMinus1]=50+bombedPlane+1;
												scrB[FiMinus1][FjMinus1].setIcon(heads[bombedPlane+1]);
												break;
										}
									}
									BombedPlaneJL.setText("已摧毁敌方飞机  "+bombedPlane+"  架");
									BombMessageJDialog bmjd=new BombMessageJDialog(TeachingModeFrame.this,locationMessage,step,bombedPlane,repeat);
									Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
									bmjd.setSize(500, 250); 
									bmjd.setLocation((int)screensize.getWidth()/2-250,(int)screensize.getHeight()/2-150);
									bmjd.addWindowListener(new WindowAdapter() {// 添加窗体监听
										public void windowClosing(WindowEvent e) {// 窗体关闭前
											if(bombedPlane>=3) {
												if(difficulty<5) TeachingMode(difficulty+1);
												else {
													String FRtext="<html>好样的！你通过了所有关卡，<br/>取得了最终胜利！恭喜完成教学模式，"
															+"<br/>这就去其他模式里体会一下BombPlane<br/>的更多乐趣吧！\r\n" + 
															"<br/>即将为您返回主菜单！</html>";
													TextDialog finishReturnDialog=new TextDialog(TeachingModeFrame.this,"提示",FRtext);
													finishReturnDialog.setSize(500, 300); 
													finishReturnDialog.setLocation((int)screensize.getWidth()/2-250,(int)screensize.getHeight()/2-150);
													finishReturnDialog.addWindowListener(new WindowAdapter() {// 添加窗体监听
											            public void windowClosing(WindowEvent e) {// 窗体关闭前
											            	sound.stopPlay();
											            	dispose();
															new MainFrame();
											            }
											        });
													finishReturnDialog.setVisible(true);
												}
											}
										}
									});	
									bmjd.setVisible(true);
									step++;
									StepJL.setText("当前是第  "+step+"  步");
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
							@Override
							public void mouseClicked(MouseEvent e) {}
							@Override
							public void mousePressed(MouseEvent e) {}
						});
						
						scrB[i-1][j-1].setVisible(true);
						layeredPane.add(scrB[i-1][j-1],new Integer(200));
					}
				}
			}
		}
						
		String TMtext[]= {"<html>欢迎来到教学模式第 1 关！<br/>教学模式共有五关，难度依次递增，"
+ "<br/>地图中共有三架飞机，飞机所占格数自头到尾均为1513，<br/>飞机头被击中即视为该架飞机被摧毁。" + 
"<br/>你的任务是通过选择与推理，用最少的步数摧毁所有飞机！<br/>如有忘记，请参考右下方的[帮助]，随着你的通关，" + 
"<br/>[帮助]也会更加丰富，你也会掌握更多技巧。<br/>先尝试点击坐标上的位置吧！<br/>那么，战斗正式开始！</html>" ,
				"<html>太棒了！你完成了第一关！<br/>恭喜进入第二关，我们提升了一点难度。<br/>第一关地图中的飞机"
+ "都是同向的。<br/>在这一关，飞机可以朝着上下左右四个方向。<br/>相信这对于你来说并不是问题！</html>",
				"<html>恭喜你完成第二关！<br/>第二关中飞机的方向更加的多样化，使飞机的布局有了更多的新可能。<br/>"
+ "在之后的关卡中，你会发现异向的更多妙处。<br/>第三关中飞机的头部会偏向角落，这会使飞机更不易被摧毁。" + 
"<br/>尝试摧毁敌机的同时，也要注意揣摩敌人的心理，<br/>利用他们的惯性思维来保护自己的飞机和摧毁他们的飞机<br/>"
+ "好了，让我们进入第三关吧！</html>" ,
				"<html>干得漂亮！你完成了第三关！<br/>飞机虽然不能重叠，但是它们可以相邻。<br/>"
+ "通过位置和方向的调整使飞机紧靠在一起，<br/>将加大推测飞机头位置的难度<br/>如果推理受阻，请不要忘了这种可能性！"
+ "<br/>希望你能闯过第四关！</html>" ,
				"<html>GOOD JOB！你完成了第四关！<br/>第五关，飞机将随机生成。<br/>摧毁所有飞机，取得最终胜利！"+
"<br/>成功只有一步之遥了！</html>"
		};
		TextDialog txtDl=new TextDialog(TeachingModeFrame.this,"教学模式提示",TMtext[difficulty-1]);
		Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
		txtDl.setSize(650, 350); 
		txtDl.setLocation((int)screensize.getWidth()/2-325,(int)screensize.getHeight()/2-175);
		txtDl.setVisible(true);
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
