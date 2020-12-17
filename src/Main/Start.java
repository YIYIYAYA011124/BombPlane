package Main;

import Service.FileDataHanlder;
import View.MainFrame;

public class Start {

	public static void main(String[] args) {
		FileDataHanlder.initVolumes();
		new MainFrame();// 创建主窗体
	}
	
}
