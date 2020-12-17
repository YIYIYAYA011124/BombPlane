package View;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.event.*;

import Service.FileDataHanlder;
import Service.Sound;


public class MainFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private Sound sound = new Sound(); 
	private JLayeredPane layeredPane = new JLayeredPane();		//层级面板
	private boolean continueMove=true; 
	
	public MainFrame() {
		setLayout(null);
		Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
		setContentPane(layeredPane);
	    sound.playBGMMenu();
		
		
		//更新日志按钮
		JButton blUpdate=new JButton("更新日志");
		setButton(blUpdate);
		blUpdate.setBounds(10, 520, 110, 30);
		blUpdate.addActionListener(new ActionListener() { // 为按钮添加鼠标单击事件
			public void actionPerformed(ActionEvent e) {
				String text="<html><br/><br/><br/>"
						+ "当前版本:V1.8.0&#12288Version:V1.8.0<br/><br/>"
						+ "V1.8.0:远程联机开放 2020.10.20<br/>"
						+ "V1.7.0:聊天系统开放,界面重新排布,炸飞机界面添加标记功能 2020.10.19<br/>"
						+ "V1.6.0:设置功能开放,增加音量调节功能和数据存取功能 2020.8.7<br/>"
						+ "V1.5.0:对战模式界面加载bug修复,对战模式模块扩充,人机模式更新,<br/>"
						+ "&#12288&#12288&#12288对战模式增加判断敌方是否掉线功能,底层代码优化,<br/>"
						+ "&#12288&#12288&#12288BGM代码扩充并换源, 更新日志界面重做 2020.8.6<br/>"
						+ "V1.4.0:对战模式开放（局域网），退出按钮开放 2020.8.3<br/>"
						+ "V1.3.2:教学模式地图更新 2020.7.27<br/>"
						+ "V1.3.1:教学模式代码优化 2020.7.27<br/>"
						+"V1.3.0:教学模式内容填充 2020.7.27<br/>"
						+ "V1.2.0:对战模式部分开放，优化了文本 2020.7.26"
						+ "<br/>V1.1.0:教学模式开放，修复了结算时步数错误的bug，"
						+ "<br/>&#12288&#12288&#12288修复了方向参数错误的bug，优化了已炸飞机数计算逻辑，"
						+ "<br/>&#12288&#12288&#12288将联系我们版块进行了修改 2020.7.24"
						+ "<br/>V1.0.3:添加人机模式随机生成飞机功能 2020.7.22<br/>"
						+ "V1.0.2:修复了方向参数上的bug 2020.7.22<br/>V1.0.1:对文本进行了修复 2020.7.14<br/>"
						+ "V1.0.0:初代版本发布 2020.7.14<br/>"
						
						+"<br/><br/>预期更新前瞻 :<br/>"
						+ "(这些更新已经安排上了!优先度从上到下依次降低)<br/>"
						+"炸飞机模块回合计时功能<br/>对战模式远程联机开放<br/>"
						+ "对战模式人机对战开放<br/>创意模式开放<br/>"
						
						+ "<br/><br/>开发者信息:本游戏由BombPlane项目组进行开发、维护与更新。<br/>"
						+ "QQ:1731019653&#12288Tel:18646393118<br/>"
						+ "感谢您体验BombPlane！祝您玩的愉快！<br/><br/><br/><br/></html>";
				JDialog UpdateJD=new JDialog(MainFrame.this,"更新日志",true);
				Container UDcontainer=UpdateJD.getContentPane();
				UDcontainer.setBackground (Color.white);
				JLabel jl=new JLabel(text);
				jl.setFont(new Font("微软雅黑",0,18));
				jl.setHorizontalAlignment(SwingConstants.CENTER);
				
				JScrollPane sp=new JScrollPane(jl);
				sp.getVerticalScrollBar().setUnitIncrement(25);	//设置灵敏度
				sp.setOpaque(false);
				sp.getViewport().setOpaque(false);
				UDcontainer.add(sp); // 在容器中添加标签
				
				UpdateJD.setSize(800, 600); 
				UpdateJD.setLocation((int)screensize.getWidth()/2-400,(int)screensize.getHeight()/2-300);
				UpdateJD.setVisible(true);
			}
		});
		layeredPane.add(blUpdate,new Integer(300));
		
		//帮助按钮
		JButton blHelp=new JButton("帮助");
		setButton(blHelp);
		blHelp.setBounds(225, 520, 110, 30);
		blHelp.addActionListener(new ActionListener() { // 为按钮添加鼠标单击事件
			public void actionPerformed(ActionEvent e) {
				String text="<html>1.欢迎来到BombPlane的世界！本游戏由BombPlane项目组进行制作、维护与更新。<br/>"+
		"2.游戏含有音效，请注意周围环境与音量。<br/>3.BombPlane的目标只有一个，那就是摧毁敌方的三架飞机！<br/>"
		+ "4.你可以对地图坐标进行轰炸以尝试摧毁敌机。<br/>5.只有当你轰炸飞机头时，才视为摧毁敌方飞机。<br/>"
		+ "6.请尝试利用已知信息推理敌方飞机头位置。<br/>7.如果你还不是很懂游戏玩法，可以尝试教学模式。"
		+ "<br/>8.?表示未知，空格表示无飞机，O表示机身，X表示机头。<br/>9.如果有任何创意或者bug反馈，请联系开发者: QQ 1731019653"
		+ "&#12288Tel 18646393118<br/>10.感谢您体验BombPlane！祝您玩的愉快！</html>";
				TextDialog txtDl=new TextDialog(MainFrame.this,"帮助",text);
				txtDl.setSize(800, 400); 
				txtDl.setLocation((int)screensize.getWidth()/2-400,(int)screensize.getHeight()/2-200);
				txtDl.setVisible(true);
			}
		});
		layeredPane.add(blHelp,new Integer(300));
				
		//开放人员按钮
		JButton blContactUs=new JButton("开发人员");
		setButton(blContactUs);
		blContactUs.setBounds(440, 520, 110, 30);
		blContactUs.addActionListener(new ActionListener() { // 为按钮添加鼠标单击事件
			public void actionPerformed(ActionEvent e) {
				String text="<html>BombPlane(炸飞机)游戏由BombPlane项目组<br/>进行开发、维护与更新。<br/>"
+ "BombPlane项目组成立于哈工大（深圳）大一立项任务<br/>BombPlane项目组成员:<br/>项目导师 汤步洲副教授<br/>组长 董天泽"
+ "<br/>组员 黄光前<br/>组员 邱成<br/>组员 易舒齐<br/><br/>联系我们:<br/>QQ:1731019653&#12288Tel:18646393118<br/><br/>"
+ "测试者信息:<br/>宁作平<br/>感谢测试者对游戏开发的大力支持！</html>";
				TextDialog txtDl=new TextDialog(MainFrame.this,"开发人员",text);
				txtDl.setSize(600, 600); 
				txtDl.setLocation((int)screensize.getWidth()/2-300,(int)screensize.getHeight()/2-300);
				txtDl.setVisible(true);
				}
			});
		layeredPane.add(blContactUs,new Integer(300));
			
		//设置按钮              
		JButton blSetting=new JButton("设置");
		setButton(blSetting);
		blSetting.setBounds(655, 520, 110, 30);
		blSetting.addActionListener(new ActionListener() { // 为按钮添加鼠标单击事件
			public void actionPerformed(ActionEvent e) {
				JDialog SettingJD=new JDialog(MainFrame.this,"设置",true);
				Container SettingJDcontainer=SettingJD.getContentPane();
				SettingJDcontainer.setBackground (Color.white);
				SettingJD.setLayout(null);
				
				String text="<html>总音量<br/><br/>&#12288音乐<br/><br/>&#12288音效</html>";
				JLabel jl=new JLabel(text);
				jl.setFont(new Font("微软雅黑",0,20));
				jl.setHorizontalAlignment(SwingConstants.CENTER);
				jl.setBounds(22,15,75,150);
				SettingJDcontainer.add(jl);
				
				int[] volumns=FileDataHanlder.getVolulmes();
				
				JSlider MasterVolumnJS=new JSlider(0,100,volumns[0]);
				MasterVolumnJS.setMajorTickSpacing(20); // 设置主刻度间隔
				MasterVolumnJS.setMinorTickSpacing(5); // 设置次刻度间隔
				MasterVolumnJS.setPaintTicks(true);  // 绘制 刻度 和 标签
				MasterVolumnJS.setPaintLabels(true);
				//MasterVolumnJS.setForeground(Color.white);
				MasterVolumnJS.setOpaque(false);
				MasterVolumnJS.setBounds(100, 20, 300, 50);
				MasterVolumnJS.addChangeListener(new ChangeListener() {
			            @Override
			            public void stateChanged(ChangeEvent e) {
			            	volumns[0]=MasterVolumnJS.getValue();
			            	sound.temporaryChangeVolumn(((float)volumns[0]*volumns[1])/10000);
			            }
			    });
				SettingJDcontainer.add(MasterVolumnJS);
		        
				JSlider BGMVolumnJS=new JSlider(0,100,volumns[1]);
				BGMVolumnJS.setMajorTickSpacing(20); // 设置主刻度间隔
				BGMVolumnJS.setMinorTickSpacing(5); // 设置次刻度间隔
				BGMVolumnJS.setPaintTicks(true);  // 绘制 刻度 和 标签
				BGMVolumnJS.setPaintLabels(true);
				//BGMVolumnJS.setForeground(Color.white);
				BGMVolumnJS.setOpaque(false);
				BGMVolumnJS.setBounds(100, 76, 300, 50);
				BGMVolumnJS.addChangeListener(new ChangeListener() {
		            @Override
		            public void stateChanged(ChangeEvent e) {
		            	volumns[1]=BGMVolumnJS.getValue();
		            	sound.temporaryChangeVolumn(((float)volumns[0]*volumns[1])/10000);
		            }
				});
				SettingJDcontainer.add(BGMVolumnJS);
				
				JSlider SoundEffectVolumnJS=new JSlider(0,100,volumns[2]);
				SoundEffectVolumnJS.setMajorTickSpacing(20); // 设置主刻度间隔
				SoundEffectVolumnJS.setMinorTickSpacing(5); // 设置次刻度间隔
				SoundEffectVolumnJS.setPaintTicks(true);  // 绘制 刻度 和 标签
				SoundEffectVolumnJS.setPaintLabels(true);
				//SoundEffectVolumnJS.setForeground(Color.white);
				SoundEffectVolumnJS.setOpaque(false);
				SoundEffectVolumnJS.setBounds(100, 135, 300, 50);
				SoundEffectVolumnJS.addChangeListener(new ChangeListener() {
		            @Override
		            public void stateChanged(ChangeEvent e) {
		            	volumns[2]=BGMVolumnJS.getValue();
		            }
				});
				SettingJDcontainer.add(SoundEffectVolumnJS);
				
				SettingJD.addWindowListener(new WindowAdapter() {// 添加窗体监听
		            public void windowClosing(WindowEvent e) {// 窗体关闭前
		            	FileDataHanlder.changeVolulmes(volumns);
		            	FileDataHanlder.saveVolumes();
		            }
		        });
				SettingJD.setSize(480, 230); 
				SettingJD.setLocation((int)screensize.getWidth()/2-240,(int)screensize.getHeight()/2-115);
				SettingJD.setVisible(true);
			}
		});
		layeredPane.add(blSetting,new Integer(300));
				
		//退出按钮               
		JButton blExit=new JButton("退出");
		setButton(blExit);
		blExit.setBounds(870, 520, 110, 30);
		blExit.addActionListener(new ActionListener() { // 为按钮添加鼠标单击事件
			public void actionPerformed(ActionEvent e) {
				whetherExit();
			}
		});
		layeredPane.add(blExit,new Integer(300));
				
		//对战模式按钮     
		JButton blBattleMode=new JButton("对战模式");
		setButton(blBattleMode);
		blBattleMode.setBounds(750, 27, 160, 60);
		blBattleMode.addActionListener(new ActionListener() { // 为按钮添加鼠标单击事件
			public void actionPerformed(ActionEvent e) {
				sound.stopPlay();
				dispose();
				continueMove=false;
				new BattleModeChooseFrame();
			}
		});
		layeredPane.add(blBattleMode,new Integer(300));
						
		//人机模式按钮       
		JButton blMachineMode=new JButton("人机模式");
		setButton(blMachineMode);
		blMachineMode.setBounds(750, 157, 160, 60);
		blMachineMode.addActionListener(new ActionListener() { // 为按钮添加鼠标单击事件
			public void actionPerformed(ActionEvent e) {
				sound.stopPlay();
				dispose();
				continueMove=false;
				new MachineModeFrame();
			}
		});
		layeredPane.add(blMachineMode,new Integer(300));
					
		//创意模式按钮         TODO
		JButton blCreativityMode=new JButton("创意模式");
		setButton(blCreativityMode);
		blCreativityMode.setBounds(750, 287, 160, 60);
		blCreativityMode.addActionListener(new ActionListener() { // 为按钮添加鼠标单击事件
			public void actionPerformed(ActionEvent e) {
				String text="<html>相关功能正在开发中，敬请期待！</html>";
				TextDialog txtDl=new TextDialog(MainFrame.this,"创意模式",text);
				txtDl.setSize(800, 400); 
				txtDl.setLocation((int)screensize.getWidth()/2-400,(int)screensize.getHeight()/2-200);
				txtDl.setVisible(true);
			}
		});
		layeredPane.add(blCreativityMode,new Integer(300));
		
		//教学模式按钮         
		JButton blTeachingMode=new JButton("教学模式");
		setButton(blTeachingMode);
		blTeachingMode.setBounds(750, 417, 160, 60);
		blTeachingMode.addActionListener(new ActionListener() { // 为按钮添加鼠标单击事件
			public void actionPerformed(ActionEvent e) {
				sound.stopPlay();
				dispose();
				continueMove=false;
				new TeachingModeFrame();
			}
		});
		layeredPane.add(blTeachingMode,new Integer(300));
				
		//开发者标签
		ImageIcon teamIcon = new ImageIcon("image/MainFrame/Team.png");
		teamIcon.setImage(teamIcon.getImage().getScaledInstance(300,50,Image.SCALE_DEFAULT));
		JLabel jlDeveloper = new JLabel(teamIcon);
		jlDeveloper.setBounds(50,25,300,50);
		jlDeveloper.setHorizontalAlignment(SwingConstants.CENTER);
		
		layeredPane.add(jlDeveloper,new Integer(300));
		
		//背景
		ImageIcon backgroundIcon = new ImageIcon("image/MainFrame/longbackground.jpg");
		backgroundIcon.setImage(backgroundIcon.getImage().getScaledInstance(2000,630,Image.SCALE_DEFAULT));
		JLabel backgroundLabel=new JLabel(backgroundIcon);
		backgroundLabel.setBounds(0, 0, 2000, 630);
		backgroundLabel.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(backgroundLabel,new Integer(200));
		
		//layeredPane.revalidate();	//重绘
		Runnable backgroundThread = () -> {
			int step=1000;
			while(continueMove) {
				backgroundLabel.setBounds(step-1000, 0, 2000, 630);
				try {
					Thread.sleep(50);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				step--;
				if(step<0) {
					step=1000;
				}
			}
	    };
	    Thread backgroundLine = new Thread(backgroundThread);
	    backgroundLine.start();
	    
		ImageIcon icon = new ImageIcon("image/MainFrame/icon.png");	//窗口图标
        setIconImage(icon.getImage());
		setBounds((int)screensize.getWidth()/2-500,(int)screensize.getHeight()/2-315, 1000, 630);// 设置横纵坐标和宽高
		setTitle("BombPlane");// 标题
        addWindowListener(new WindowAdapter() {// 添加窗体监听
            public void windowClosing(WindowEvent e) {// 窗体关闭前
            	System.exit(0);
            }
        });
        setResizable(false);
        setVisible(true);
	}
	
	//设置按钮
	public void setButton(JButton button) {
		button.setFont(new Font("宋体", Font.BOLD, 18));
		button.setForeground(Color.white);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
	}
	
	//询问是否退出游戏
	public void whetherExit() {
		JDialog exitDialog = new JDialog(this,"提示",true);
		exitDialog.setLayout(null);
		exitDialog.setSize(300, 180);
		exitDialog.setLocationRelativeTo(null);
		Container container = exitDialog.getContentPane(); // 创建一个容器
		
		JLabel exitLabel=new JLabel("是否退出游戏?");
		exitLabel.setFont(new Font("宋体", Font.BOLD, 20));
		exitLabel.setForeground(Color.BLACK);
		exitLabel.setHorizontalAlignment(SwingConstants.CENTER);
		exitLabel.setBounds(45,20,200,40);
		container.add(exitLabel); // 在容器中添加标签
		
		JButton enterButton = new JButton("确定");
		enterButton.setFont(new Font("宋体", Font.BOLD, 18));
		enterButton.setForeground(Color.BLACK);
		enterButton.setContentAreaFilled(false);
		enterButton.setFocusPainted(false);
		enterButton.setBounds(30, 100, 80, 30);
		enterButton.addActionListener(new ActionListener() { // 为按钮添加鼠标单击事件
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		container.add(enterButton);
		
		JButton cancelButton = new JButton("取消");
		cancelButton.setFont(new Font("宋体", Font.BOLD, 18));
		cancelButton.setForeground(Color.BLACK);
		cancelButton.setContentAreaFilled(false);
		cancelButton.setFocusPainted(false);
		cancelButton.setBounds(180, 100, 80, 30);
		cancelButton.addActionListener(new ActionListener() { // 为按钮添加鼠标单击事件
			@Override
			public void actionPerformed(ActionEvent e) {
				exitDialog.dispose();
			}
		});
		container.add(cancelButton);
		
		container.setBackground (Color.white);
		exitDialog.setResizable(false);
		exitDialog.setVisible(true);
	}
	
}