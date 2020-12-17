package View;

import java.awt.*;
import javax.swing.*;

import LANBattleMode.LANBMFindEnemyFrame;

import java.awt.event.*;
import Service.Sound;
import WANBattleMode.WANBMFindEnemyFrame;

public class BattleModeChooseFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private Sound sound = new Sound(); 
	private JLayeredPane layeredPane = new JLayeredPane();		//层级面板
	private final Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
	public BattleModeChooseFrame() {
		setBounds((int)screensize.getWidth()/2-200,(int)screensize.getHeight()/2-315, 400, 630);// 设置横纵坐标和宽高
		setTitle("对战模式");// 标题
		sound.playBGMChooseMode();
		setContentPane(layeredPane);
		NetChoose();
		
		ImageIcon icon = new ImageIcon("image/BattleModeChooseFrame/icon.png");	//窗口图标
        setIconImage(icon.getImage());
        
        addWindowListener(new WindowAdapter() {// 添加窗体监听
            public void windowClosing(WindowEvent e) {// 窗体关闭前
            	System.exit(0);
            }
        });
        setResizable(false);
        setVisible(true);
	}
	
	//选择联网方式
	public void NetChoose() {
		layeredPane.removeAll();
		layeredPane.repaint();
		layeredPane.setLayout(null);
		setTitle("对战模式");
		
		JButton BMWANJB=new JButton("远程对战");
		setButton(BMWANJB);
		BMWANJB.setBounds(120,150,160, 60);
		BMWANJB.addActionListener(new ActionListener() { // 为按钮添加鼠标单击事件
			public void actionPerformed(ActionEvent e) {
				BMWANEnterJB();
			}
		});
		layeredPane.add(BMWANJB,new Integer(300)); // 将按钮添加到容器中
		
		JButton BMLANJB=new JButton("面对面对战");
		setButton(BMLANJB);
		BMLANJB.setBounds(120, 250, 160, 60);
		BMLANJB.addActionListener(new ActionListener() { // 为按钮添加鼠标单击事件
			public void actionPerformed(ActionEvent e) {
				BMLANEnterJB();
			}
		});
		layeredPane.add(BMLANJB,new Integer(300)); // 将按钮添加到容器中
		
		JButton BMMachineJB=new JButton("人机对战");
		setButton(BMMachineJB);
		BMMachineJB.setBounds(120, 350, 160, 60);
		BMMachineJB.addActionListener(new ActionListener() { // 为按钮添加鼠标单击事件
			public void actionPerformed(ActionEvent e) {
				String text="<html>相关功能正在开发中，敬请期待！</html>";
				TextDialog txtDl=new TextDialog(BattleModeChooseFrame.this,"提示",text);
				txtDl.setSize(800, 400); 
				txtDl.setLocation((int)screensize.getWidth()/2-400,(int)screensize.getHeight()/2-200);
				txtDl.setVisible(true);
			}
		});
		layeredPane.add(BMMachineJB,new Integer(300)); // 将按钮添加到容器中
		
		JButton BMreturnMainMenuJB=new JButton("主菜单");
		setButton(BMreturnMainMenuJB);
		BMreturnMainMenuJB.setBounds(120, 450, 160, 60);
		BMreturnMainMenuJB.addActionListener(new ActionListener() { // 为按钮添加鼠标单击事件
			public void actionPerformed(ActionEvent e) {
				sound.stopPlay();
				dispose();
				new MainFrame();
			}
		});
		layeredPane.add(BMreturnMainMenuJB,new Integer(300)); // 将按钮添加到容器中
		
		//背景
		ImageIcon backgroundIcon = new ImageIcon("image/BattleModeChooseFrame/background.png");
		backgroundIcon.setImage(backgroundIcon.getImage().getScaledInstance(400,630,Image.SCALE_DEFAULT));
		JLabel backgroundLabel=new JLabel(backgroundIcon);
		backgroundLabel.setBounds(0, 0,400,630);
		backgroundLabel.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(backgroundLabel,new Integer(200));
		
//		ImageIcon icon = new ImageIcon("image/BattleChooseFrame/icon.png");	//窗口图标
//      setIconImage(icon.getImage());
		layeredPane.revalidate();	//重绘
	}
	
	//选择局域网模式加入方式
	public void BMLANEnterJB() {
		layeredPane.removeAll();
		layeredPane.repaint();
		layeredPane.setLayout(null);
		setTitle("面对面对战");
		
		JButton BMCreateRoomJB=new JButton("创建房间");
		setButton(BMCreateRoomJB);
		BMCreateRoomJB.setBounds(120, 150, 160, 60);
		BMCreateRoomJB.addActionListener(new ActionListener() { // 为按钮添加鼠标单击事件
			public void actionPerformed(ActionEvent e) {
				sound.stopPlay();
				dispose();
				new LANBMFindEnemyFrame(true);
			}
		});
		layeredPane.add(BMCreateRoomJB,new Integer(300)); // 将按钮添加到容器中
		
		JButton BMEnterRoomJB=new JButton("加入房间");
		setButton(BMEnterRoomJB);
		BMEnterRoomJB.setBounds(120, 250, 160, 60);
		BMEnterRoomJB.addActionListener(new ActionListener() { // 为按钮添加鼠标单击事件
			public void actionPerformed(ActionEvent e) {
				sound.stopPlay();
				dispose();
				new LANBMFindEnemyFrame(false);
			}
		});
		layeredPane.add(BMEnterRoomJB,new Integer(300)); // 将按钮添加到容器中
		
		JButton BMLastStepJB=new JButton("上一步");
		setButton(BMLastStepJB);
		BMLastStepJB.setBounds(120, 350, 160, 60);
		BMLastStepJB.addActionListener(new ActionListener() { // 为按钮添加鼠标单击事件
			public void actionPerformed(ActionEvent e) {
				NetChoose();
			}
		});
		layeredPane.add(BMLastStepJB,new Integer(300)); // 将按钮添加到容器中
		
		JButton BMreturnMainMenuJB=new JButton("主菜单");
		setButton(BMreturnMainMenuJB);
		BMreturnMainMenuJB.setBounds(120, 450, 160, 60);
		BMreturnMainMenuJB.addActionListener(new ActionListener() { // 为按钮添加鼠标单击事件
			public void actionPerformed(ActionEvent e) {
				sound.stopPlay();
				dispose();
				new MainFrame();
			}
		});
		layeredPane.add(BMreturnMainMenuJB,new Integer(300)); // 将按钮添加到容器中
		
		//背景
		ImageIcon backgroundIcon = new ImageIcon("image/BattleModeChooseFrame/background.png");
		backgroundIcon.setImage(backgroundIcon.getImage().getScaledInstance(400,630,Image.SCALE_DEFAULT));
		JLabel backgroundLabel=new JLabel(backgroundIcon);
		backgroundLabel.setBounds(0, 0,400,630);
		backgroundLabel.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(backgroundLabel,new Integer(200));
		
		layeredPane.revalidate();	//重绘
	}
	
	//选择广域网模式加入方式
		public void BMWANEnterJB() {
			layeredPane.removeAll();
			layeredPane.repaint();
			layeredPane.setLayout(null);
			
			setTitle("远程对战");
			JButton BMCreateRoomJB=new JButton("创建房间");
			setButton(BMCreateRoomJB);
			BMCreateRoomJB.setBounds(120, 150, 160, 60);
			BMCreateRoomJB.addActionListener(new ActionListener() { // 为按钮添加鼠标单击事件
				public void actionPerformed(ActionEvent e) {
					sound.stopPlay();
					dispose();
					new WANBMFindEnemyFrame(true);
				}
			});
			layeredPane.add(BMCreateRoomJB,new Integer(300)); // 将按钮添加到容器中
			
			JButton BMEnterRoomJB=new JButton("加入房间");
			setButton(BMEnterRoomJB);
			BMEnterRoomJB.setBounds(120, 250, 160, 60);
			BMEnterRoomJB.addActionListener(new ActionListener() { // 为按钮添加鼠标单击事件
				public void actionPerformed(ActionEvent e) {
					sound.stopPlay();
					dispose();
					new WANBMFindEnemyFrame(false);
				}
			});
			layeredPane.add(BMEnterRoomJB,new Integer(300)); // 将按钮添加到容器中
			
			JButton BMLastStepJB=new JButton("上一步");
			setButton(BMLastStepJB);
			BMLastStepJB.setBounds(120, 350, 160, 60);
			BMLastStepJB.addActionListener(new ActionListener() { // 为按钮添加鼠标单击事件
				public void actionPerformed(ActionEvent e) {
					NetChoose();
				}
			});
			layeredPane.add(BMLastStepJB,new Integer(300)); // 将按钮添加到容器中
			
			JButton BMreturnMainMenuJB=new JButton("主菜单");
			setButton(BMreturnMainMenuJB);
			BMreturnMainMenuJB.setBounds(120, 450, 160, 60);
			BMreturnMainMenuJB.addActionListener(new ActionListener() { // 为按钮添加鼠标单击事件
				public void actionPerformed(ActionEvent e) {
					sound.stopPlay();
					dispose();
					new MainFrame();
				}
			});
			layeredPane.add(BMreturnMainMenuJB,new Integer(300)); // 将按钮添加到容器中
			
			//背景
			ImageIcon backgroundIcon = new ImageIcon("image/BattleModeChooseFrame/background.png");
			backgroundIcon.setImage(backgroundIcon.getImage().getScaledInstance(400,630,Image.SCALE_DEFAULT));
			JLabel backgroundLabel=new JLabel(backgroundIcon);
			backgroundLabel.setBounds(0, 0,400,630);
			backgroundLabel.setHorizontalAlignment(SwingConstants.CENTER);
			layeredPane.add(backgroundLabel,new Integer(200));
			
			layeredPane.revalidate();	//重绘
		}
	
	//设置按钮
	public void setButton(JButton button) {
		button.setFont(new Font("宋体", Font.BOLD, 18));
		button.setForeground(Color.white);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
	}
	
}
