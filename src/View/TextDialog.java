package View;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;

import javax.swing.*;

public class TextDialog extends JDialog {  
	private static final long serialVersionUID = 1L;
	public TextDialog(JFrame frame,String title,String text){
		super(frame, title, true);
		Container container = getContentPane(); // 创建一个容器
		JLabel jl=new JLabel(text);
		jl.setFont(new Font("微软雅黑",0,18));
		jl.setHorizontalAlignment(SwingConstants.CENTER);
		container.add(jl); // 在容器中添加标签
		container.setBackground (Color.white);
		setResizable(false);
	}

}

