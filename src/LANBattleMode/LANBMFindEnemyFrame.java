package LANBattleMode;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import Service.Sound;
import View.BattleModeChooseFrame;
import View.MainFrame;
import View.TextDialog;

import java.io.*;
import java.net.*;

public class LANBMFindEnemyFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private BufferedReader reader; // 创建BufferedReader对象
	private PrintWriter writer; // 创建BufferedWriter对象
	private ServerSocket server; // 创建ServerSocket对象
	private Socket socket; // 创建Socket对象socket
	private Sound sound = new Sound(); 
	private final int socketNumber=20118;
	private JLayeredPane layeredPane = new JLayeredPane();;
	private JLabel RoomMessageJL;
	private JButton RoomJB;
	private ActionListener actionListener=null;
	private LANBMInputPlanesFrame myInPutPlanesFrame;
	private LANBMBombPlanesFrame myBombPlanesFrame;
	private LANBMFinishFrame myFinishFrame;
	private int codeReceiveFromEnemy;
	private int myInputCode;
	private int myLANBMStep=0;
	private boolean isCancelConnect=false;	//是我方主动取消连接
	private ChatPanel chatPanel = new ChatPanel();
	
	
	private final Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
	public LANBMFindEnemyFrame(boolean IsServer) {
		setLayout(null);
		sound.playBGMFindEnemy();
		myLANBMStep=0;
		isCancelConnect=false;
		setContentPane(layeredPane);
		
		JButton BMCRreturnMainMenuJB=new JButton("主菜单");
		BMCRreturnMainMenuJB.setFont(new Font("宋体", Font.BOLD, 18));
		BMCRreturnMainMenuJB.setForeground(Color.white);
		BMCRreturnMainMenuJB.setContentAreaFilled(false);
		BMCRreturnMainMenuJB.setFocusPainted(false);
		BMCRreturnMainMenuJB.setBounds(690, 500, 160, 60);
		BMCRreturnMainMenuJB.addActionListener(new ActionListener() { // 为按钮添加鼠标单击事件
			public void actionPerformed(ActionEvent e) {
				sound.stopPlay();
				stopConnect();
				dispose();
				new MainFrame();
			}
		});
		layeredPane.add(BMCRreturnMainMenuJB,new Integer(300)); // 将按钮添加到容器中

		JButton BMCRLastStepJB=new JButton("上一步");
		BMCRLastStepJB.setFont(new Font("宋体", Font.BOLD, 18));
		BMCRLastStepJB.setForeground(Color.white);
		BMCRLastStepJB.setContentAreaFilled(false);
		BMCRLastStepJB.setFocusPainted(false);
		BMCRLastStepJB.setBounds(150, 500, 160, 60);
		BMCRLastStepJB.addActionListener(new ActionListener() { // 为按钮添加鼠标单击事件
			public void actionPerformed(ActionEvent e) {
				sound.stopPlay();
				stopConnect();
				dispose();
				new BattleModeChooseFrame().BMLANEnterJB();
			}
		});
		layeredPane.add(BMCRLastStepJB,new Integer(300));
		
		RoomMessageJL=new JLabel("");
		RoomMessageJL.setHorizontalAlignment(SwingConstants.CENTER);
		RoomMessageJL.setForeground(Color.white);
		RoomMessageJL.setFont(new Font("宋体", Font.BOLD, 23));
		RoomMessageJL.setBounds(100, 50, 800, 400);
		layeredPane.add(RoomMessageJL,new Integer(300));
		
		RoomJB=new JButton("");
		RoomJB.setFont(new Font("宋体", Font.BOLD, 18));
		RoomJB.setForeground(Color.white);
		RoomJB.setContentAreaFilled(false);
		RoomJB.setFocusPainted(false);
		RoomJB.setBounds(420, 500, 160, 60);
		layeredPane.add(RoomJB,new Integer(300));

		//背景
		ImageIcon backgroundIcon = new ImageIcon("image/LANBM/background.jpg");
		backgroundIcon.setImage(backgroundIcon.getImage().getScaledInstance(1000,630,Image.SCALE_DEFAULT));
		JLabel backgroundLabel=new JLabel(backgroundIcon);
		backgroundLabel.setBounds(0, 0,1000,630);
		backgroundLabel.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(backgroundLabel,new Integer(200));
		
		//飞机
		ImageIcon planeIcon = new ImageIcon("image/LANBM/plane.png");
		planeIcon.setImage(planeIcon.getImage().getScaledInstance(128,30,Image.SCALE_DEFAULT));
		JLabel planeLabel=new JLabel(planeIcon);
		planeLabel.setBounds(0,0,128,30);
		planeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		layeredPane.add(planeLabel,new Integer(400));
		
		Runnable planeThread = () -> {
			int step=0;
			while(true) {
				planeLabel.setBounds(step,20,128,30);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				step++;
				if(step>1000) {
					step=0;
				}
			}
	    };
	    Thread planeLine = new Thread(planeThread);
	    planeLine.start();
		
		if(IsServer) createRoom();
		else enterRoom();
		
		ImageIcon icon = new ImageIcon("image/LANBM/icon.png");	//窗口图标
        setIconImage(icon.getImage());
		setBounds((int)screensize.getWidth()/2-500,(int)screensize.getHeight()/2-315, 1000, 630);// 设置横纵坐标和宽高
		setTitle("对战模式");// 标题
		setResizable(false);
        addWindowListener(new WindowAdapter() {// 添加窗体监听
            public void windowClosing(WindowEvent e) {// 窗体关闭前
            	System.exit(0);
            }
        });
        setVisible(true);
		
	}
	
	class ChatPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private String chatMessages="";
		private JLabel jl = new JLabel();
		private JScrollPane sp;
		
		public ChatPanel() {
	        setLayout(null);
	        setSize(300,200);
	        setBackground(Color.white);
	        
	        jl.setFont(new Font("宋体", Font.BOLD, 18));
			//jl.setSize(300,160);
			jl.setHorizontalAlignment(SwingConstants.LEFT);
			jl.setVerticalAlignment(SwingConstants.BOTTOM);
			
	        sp=new JScrollPane(jl);
			sp.setForeground(Color.black);
			sp.setBackground(Color.white);
			sp.getVerticalScrollBar().setUnitIncrement(25);	//设置灵敏度
			sp.setBounds(0,0,300,160);
			sp.setOpaque(false);
			sp.getViewport().setOpaque(false);
			add(sp); // 在容器中添加标签
	        
	        JTextField chatJT=new JTextField("",20);
	        chatJT.setFont(new Font("宋体", Font.BOLD, 18));
	        chatJT.setForeground(Color.black);
	        chatJT.setBackground(Color.white);
	        chatJT.setBounds(0, 160, 200, 40);
	        chatJT.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String messageToSend=chatJT.getText();
					chatMessages+="我方:";
					chatMessages+=messageToSend;
					chatMessages+="<br/>";
					jl.setText("<html>"+chatMessages+"<br/></html>");
					sendMessage("9"+messageToSend);
					chatJT.setText("");
					sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
					chatJT.requestFocus();
				}
			});
	        add(chatJT);
	        
	        JButton HandInJB=new JButton("发送");
	        HandInJB.setFont(new Font("宋体", Font.BOLD, 18));
	        HandInJB.setForeground(Color.black);
	        HandInJB.setBackground(Color.white);
	        HandInJB.setFocusPainted(false);
			HandInJB.setBounds(200,160,100,40);
			HandInJB.addActionListener(new ActionListener() { // 为按钮添加鼠标单击事件
				public void actionPerformed(ActionEvent e) {
					String messageToSend=chatJT.getText();
					chatMessages+="我方:";
					chatMessages+=messageToSend;
					chatMessages+="<br/>";
					jl.setText("<html>"+chatMessages+"<br/></html>");
					sendMessage("9"+messageToSend);
					chatJT.setText("");
					sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
					chatJT.requestFocus();
				}
			});
			add(HandInJB);
		}
        
		public void receiveMessage(String message) {
			System.out.println(message);
			chatMessages+="对方:";
			chatMessages+=message;
			chatMessages+="<br/>";
			jl.setText("<html>"+chatMessages+"<br/></html>");
			sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
		}
	}
	
	//获得ChatPanel
	public JPanel getChatPanel() {
		return chatPanel;
	}
	
	//接收信息的线程
	class ReaderThread extends Thread{
		@Override
		public void run(){
			reader=null;
	        try{  
	            while(true){  
	                //读取客户端数据    
	            	reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
	            	receiveMessageHandle(reader.readLine());
	            	Thread.sleep(100);
	            }  
	        }catch(Exception e){  
	        	if(isCancelConnect==false) {
	        		TextDialog txtDl=new TextDialog(LANBMFindEnemyFrame.this,"提示","<html>对手已掉线！<br/>即将返回主菜单...</html>");
	        		txtDl.setSize(600, 300); 
	        		txtDl.setLocation((int)screensize.getWidth()/2-300,(int)screensize.getHeight()/2-150);
	        		txtDl.addWindowListener(new WindowAdapter() {// 添加窗体监听
	        			public void windowClosing(WindowEvent e) {// 窗体关闭前
	        				switch(myLANBMStep) {
		            		case 0:
		            			sound.stopPlay();
		            			dispose();
		            			break;
		            		case 1:
		            			myInPutPlanesFrame.soundStopPlay();
		            			myInPutPlanesFrame.dispose();
		            			break;
		            		case 2:
		            			myBombPlanesFrame.soundStopPlay();
		            			myBombPlanesFrame.dispose();
		            			break;
		            		case 3:
		            			myFinishFrame.soundStopPlay();
		            			myFinishFrame.dispose();
		            			break;
	        				}
	        				stopConnect();
	        				new MainFrame();
	        			}
	        		});
	        		txtDl.setVisible(true);
	        	}
	        }
		}
	}
	
	//发送信息的方法
	public void sendMessage(String message) {
		writer.println(message);
	}
	
	//创建房间
	public void createRoom() {
		RoomMessageJL.setText("创建房间中...");
		
		InetAddress ip;
		try { // try语句块捕捉可能出现的异常
			server = new ServerSocket(socketNumber); // 实例化Socket对象
			ip = InetAddress.getLocalHost(); // 实例化对象
			String localip = ip.getHostAddress(); // 获取本IP地址
			RoomMessageJL.setText("<html>房间创建成功! 正在等待对手连接......<br/>房间号:"+localip+
					"<br/>把房间号告诉你的好友吧！</html>");
			
			removeActionListener();
			RoomJB.setText("开始游戏");
			actionListener=new ActionListener() { // 为按钮添加鼠标单击事件
				@Override
				public void actionPerformed(ActionEvent e) {
					String text="正在等待对手连接！";
					TextDialog txtDl=new TextDialog(LANBMFindEnemyFrame.this,"提示",text);
					txtDl.setSize(600, 300); 
					txtDl.setLocation((int)screensize.getWidth()/2-300,(int)screensize.getHeight()/2-150);
					txtDl.setVisible(true);
				}
			};
			RoomJB.addActionListener(actionListener);
			
			class serverAcceptThread implements Runnable{   //接收对手连接的线程
			    @Override  
			    public void run() {
			    	try {
						socket=server.accept();
						writer=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
			            new Thread(new ReaderThread()).start(); 
			            
			            removeActionListener();
			            actionListener=new ActionListener() { // 为按钮添加鼠标单击事件
							@Override
							public void actionPerformed(ActionEvent e) {
								sendMessage("0");
								dispose();
								sound.stopPlay();
								createMyInPutPlanesFrame();
							}
						};
						RoomJB.addActionListener(actionListener);
			            
						RoomMessageJL.setText("<html>对手已连接！<br/>立刻开始游戏吧！</html>");
			    	} catch (IOException e2) {} 
			    }
			}
			new Thread(new serverAcceptThread()).start();
			
		}
		catch (Exception e) {	//获取ip地址或者实例socket出现异常
			RoomMessageJL.setText("房间创建失败！请检查你的网络连接!");
			stopConnect();
			RoomJB.setText("再次创建");
			
			removeActionListener();
            actionListener=new ActionListener() { // 为按钮添加鼠标单击事件
				@Override
				public void actionPerformed(ActionEvent e) {
					createRoom();
				}
			};
			RoomJB.addActionListener(actionListener);
		}
	}
	
	//加入房间
	public void enterRoom() {
		RoomMessageJL.setText("请输入房间号:");
		removeActionListener();
		
		JTextField EnterRoomJT=new JTextField("",20);
		RoomJB.setText("加入房间");
		
		class ClientEnterRoomThread implements Runnable{   //连接房间的线程
		    @Override  
		    public void run() {
		    	RoomMessageJL.setText("<html>正在连接中...<br/>请稍候</html>");
		    	try { // 捕捉异常
		    		socket=new Socket(EnterRoomJT.getText(),socketNumber); // 实例化Socket对象
		    		writer=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
		    		new Thread(new ReaderThread()).start(); 
		    		RoomMessageJL.setText("<html>成功加入房间！<br/>等待房主开始游戏...</html>");
		    		
		    		if(actionListener!=null) EnterRoomJT.removeActionListener(actionListener);
		    		removeActionListener();
		    		actionListener=new ActionListener() { // 为按钮添加鼠标单击事件
		    			@Override
		    			public void actionPerformed(ActionEvent e) {
		    				String text="正在等待房主开始游戏！";
							TextDialog txtDl=new TextDialog(LANBMFindEnemyFrame.this,"提示",text);
							txtDl.setSize(600, 300); 
							txtDl.setLocation((int)screensize.getWidth()/2-300,(int)screensize.getHeight()/2-150);
							txtDl.setVisible(true);
		    			}
		    		};
		    		RoomJB.addActionListener(actionListener);
		    		EnterRoomJT.addActionListener(actionListener);
		    	} catch (Exception e1) {	//加入房间失败
		    		stopConnect();
		    		RoomMessageJL.setText("<html>加入房间失败！<br/>请检查你的网络或房间号是否正确！</html>");
		    	}
		    }
		}
		
		EnterRoomJT.setFont(new Font("微软雅黑",0,18));
		EnterRoomJT.setBounds(300, 300, 400, 40);
		EnterRoomJT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new ClientEnterRoomThread()).start();
			}
		});
		layeredPane.add(EnterRoomJT,new Integer(300));
		EnterRoomJT.requestFocus();
		
		
		removeActionListener();
		actionListener=new ActionListener() { // 为按钮添加鼠标单击事件
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new ClientEnterRoomThread()).start();
			}
		};
		RoomJB.addActionListener(actionListener);
	}
	
	//停止连接
	public void stopConnect() {
		try {
			if(server!=null) {
				server.close();
			}
			if (socket!= null) {
				socket.close(); // 关闭套接字
			}
			isCancelConnect=true;
		} catch (IOException e2) {}
	}
	
	//移除RoomJB上的ActionLister
	public void removeActionListener() {
		if(actionListener!=null) {
			RoomJB.removeActionListener(actionListener);
			actionListener=null;
		}
	}
	
	//获取对方发送的信息码
	public int getCodeReceiveFromEnemy() {
		return codeReceiveFromEnemy;
	}
	
	//存储自己输入的信息码
	public void saveMyInputCode(int myInputCode) {
		this.myInputCode=myInputCode;
	}
	
	//创建myInPutPlanesFrame
	public void createMyInPutPlanesFrame() {
		codeReceiveFromEnemy=-1;
		myLANBMStep=1;
		myInPutPlanesFrame=new LANBMInputPlanesFrame(LANBMFindEnemyFrame.this);
	}
	
	//创建myBombPlanesFrame
	public void createMyBombPlanesFrame(boolean isFirst) {
		myLANBMStep=2;
		myBombPlanesFrame=new LANBMBombPlanesFrame(LANBMFindEnemyFrame.this,myInputCode,codeReceiveFromEnemy,isFirst);
	}
	
	//创建myFinishFrame
	public void createMyFinishFrame(boolean isWinner) {
		myLANBMStep=3;
		myFinishFrame=new LANBMFinishFrame(LANBMFindEnemyFrame.this,myInputCode,codeReceiveFromEnemy,isWinner);
	}
	
	public void receiveMessageHandle(String readerMessage){
		/*messageType 读取信息类型: 
		 * 0:服务器开始游戏
		 * 1:任意端发出的布局9位信息码
		 * 2:任意端发出的坐标
		 * 3:任意端发出的继续游戏（准备）
		 * 4:任意端发出的取消游戏（取消准备）
		 * 5:任意端发出的游戏结束 发送端胜利
		 * 9:聊天
		**/
		System.out.println("接收信息"+readerMessage);
		char messageType=readerMessage.charAt(0);
		String messageBody=readerMessage.substring(1);
		switch(messageType) {
			case '0':
				System.out.println("0");
				dispose();
				sound.stopPlay();
				createMyInPutPlanesFrame();
				break;
			case '1':
				if(codeReceiveFromEnemy==-1) {	
					codeReceiveFromEnemy=Integer.parseInt(messageBody);
					if(myInPutPlanesFrame.getBattleModeAlreadySent()==true) {
						myInPutPlanesFrame.dispose();
						myInPutPlanesFrame.soundStopPlay();
						createMyBombPlanesFrame(true);
					}
				}
				break;
			case '2':
				int p,q,r;
				r=Integer.parseInt(messageBody);
				p=r/10;
				q=r%10;
				myBombPlanesFrame.handleReceiveLocation(p,q);
				break;
			case '3':
				myFinishFrame.enemyReadyChange(true);
				if(myFinishFrame.getMyReadyState()==true) {
					myFinishFrame.dispose();
					myFinishFrame.soundStopPlay();
					createMyInPutPlanesFrame();
				}
				
				break;
			case '4':
				myFinishFrame.enemyReadyChange(false);
				break;
			case '5':
				myBombPlanesFrame.dispose();
				myBombPlanesFrame.soundStopPlay();
				createMyFinishFrame(false);
				break;
			case '9':
				chatPanel.receiveMessage(messageBody);
				break;
		}
	}
	
	
}
