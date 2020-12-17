package View;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

import Service.Sound;

public class BombMessageJDialog extends JDialog { // 创建新类继承JDialog类
	private static final long serialVersionUID = 1L;
	private Sound sound=new Sound();
	public BombMessageJDialog(JFrame frame,int locationMessage,int step,int bombedPlane,boolean Repeat) {
		// 实例化一个JDialog类对象，指定对话框的父窗体、窗体标题和类型
		super(frame, "提示", true);
		addWindowListener(new WindowAdapter() {// 添加窗体监听
            public void windowClosing(WindowEvent e) {// 窗体关闭前
                sound.stopPlay();
            }
        });
		Container container = getContentPane(); // 创建一个容器
		container.setLayout(new BorderLayout());
		String resultText=new String();
		String[] BombResults= {"你什么都没有炸到！","你对一架飞机造成了损伤！","你摧毁了一架飞机"};
		resultText=BombResults[locationMessage];
		String tipText=new String();
		tipText=getTipText(locationMessage,Repeat);
		
		if(bombedPlane==3) {
			resultText="成功摧毁全部飞机！";
			tipText=("<html>共"+step+"步！<br/><br/></html>");
		}
		
		switch(locationMessage) {	
		case 0:
				sound.playBOMB0();
				break;
		case 1:
				sound.playBOMB1();
				break;
		case 2:
				sound.playBOMB2();
				break;
		default:resultText="未知错误！";
				break;
		}
		JLabel resultLabel=new JLabel(resultText);
		JLabel tipLabel=new JLabel(tipText);
		resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
		tipLabel.setHorizontalAlignment(SwingConstants.CENTER);
		resultLabel.setFont(new Font("微软雅黑",0,25));
		tipLabel.setFont(new Font("微软雅黑",0,18));
		container.add(BorderLayout.CENTER,resultLabel); // 在容器中添加标签
		container.add(BorderLayout.SOUTH,tipLabel); // 在容器中添加标签
		setResizable(false);
	}	
	
	public String getTipText(int locationMessage,boolean Repeat) {
		String tipText="<html>tips:<br/>";
		String Bomb0Tips[]={"炸了个寂寞...","事实证明，幸运也是实力的一部分","多尝试从中部开始炸，机会更大！"
				,"别泄气！万一下次就中了呢！","听说油炸空气是米其林餐厅的菜品~","问题不大！",
				"请珍惜资源，这边不推荐地毯式轰炸~"};
		String Bomb1Tips[]={"已经看到成功的希望了!","尝试根据机身推理机头的位置！","一五一三记在心，歼灭敌机易如反掌！",
				"想象一下飞机的形状，<br/>说不定下次就是精准命中！"};
		String Bomb2Tips[]={"正中靶心！","Good Job！","干的漂亮！"};
		String Bomb0RepeatTips[]={"这个坐标和你什么仇什么怨...","你再炸这里也不会创造奇迹的...","莫自闭，方法总比困难多~"};
		String Bomb1RepeatTips[]={"为什么只炸机身无法摧毁飞机？<br/>这是个好问题...","一位统计学家说过，"
				+ "加强战机时<br/>应该着重修理弹痕少的部位，<br/>这可能是一个哲学问题...<br/>(18年全国二卷作文)"};
		String Bomb2RepeatTips[]={"对一个飞机头轰炸三次<br/>是不会获得胜利的!","你是要把飞机分解成分子级别吗？"};
		
		
		if(Repeat==false) {
			if(locationMessage==0) tipText+=Bomb0Tips[(int)(Math.random()*7)];
			if(locationMessage==1) tipText+=Bomb1Tips[(int)(Math.random()*4)];
			if(locationMessage==2) tipText+=Bomb2Tips[(int)(Math.random()*3)];
		}
		else {
			if(locationMessage==0) tipText+=Bomb0RepeatTips[(int)(Math.random()*3)];
			if(locationMessage==1) tipText+=Bomb1RepeatTips[(int)(Math.random()*2)];
			if(locationMessage==2) tipText+=Bomb2RepeatTips[(int)(Math.random()*2)];
		}
			
		
		tipText+="<br/><br/></html>";
		return tipText;
	}
}
