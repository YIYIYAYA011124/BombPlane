package Service;

import java.io.*;
import java.util.Arrays;


public class FileDataHanlder {
	private static final String VOLUMEFILE = "data/Volume";// 音量记录文件
	private static int[] volumes=new int[3];	
	//MasterVolume,BGMVolume,SoundVolume 0-100范围
    private static final int initVolumnPercent=80;
	
	//初始化音量
	public static void initVolumes() {
		 File f = new File(VOLUMEFILE);// 创建记录文件
	        if (!f.exists()) {// 如果文件不存在
	            try {
	                f.createNewFile();// 创建新文件
	                Arrays.fill(volumes, initVolumnPercent);// 数组填充
                    saveVolumes();
	            } catch (IOException e) {}
	            return;// 停止方法
	        }
	        FileInputStream fis = null;
	        InputStreamReader isr = null;
	        BufferedReader br = null;
	        try {
	            fis = new FileInputStream(f);// 文件字节输入流
	            isr = new InputStreamReader(fis);// 字节流转字符流
	            br = new BufferedReader(isr);// 缓冲字符流
	            String value = br.readLine();// 读取一行
	            if (!(value == null || "".equals(value))) {// 如果不为空值
	                String vs[] = value.split(",");// 分割字符串
	                if (vs.length != 3) {// 如果分割结果不等于3
	                    Arrays.fill(volumes,initVolumnPercent);// 数组填充70
	                    saveVolumes();
	                } else {
	                    for (int i=0;i<3;i++) {
	                        // 将记录文件中的值赋给当前分数数组
	                    	 volumes[i]=Integer.parseInt(vs[i]);
	                    }
	                }
	            }
	        } catch (FileNotFoundException e) {
	        } catch (IOException e) {
	        } finally {// 依次关闭流
	            try {
	                br.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            try {
	                isr.close();
	            } catch (IOException e) {
	            }
	            try {
	                fis.close();
	            } catch (IOException e) {
	            }

	        }
	}

	//存储音量值
	public static void saveVolumes() {
		 // 拼接得分数组
        String value = volumes[0] + "," + volumes[1] + "," + volumes[2];
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            fos = new FileOutputStream(VOLUMEFILE);// 文件字节输出流
            osw = new OutputStreamWriter(fos);// 字节流转字符流
            bw = new BufferedWriter(osw);// 缓冲字符流
            bw.write(value);// 写入拼接后的字符串
            bw.flush();// 字符流刷新
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {// 依次关闭流
            try {
                bw.close();
            } catch (IOException e) {
            }
            try {
                osw.close();
            } catch (IOException e) {
            }
            try {
                fos.close();
            } catch (IOException e) {
            }
        }
		
	}
	
	//改变三个音量值
	public static void changeVolulmes(int receiveVolumes[]) {
		for(int i=0;i<3;i++) {
			volumes[i]=receiveVolumes[i];
		}
	}
	
	//获取三个音量值
	public static int[] getVolulmes() {
		return volumes;
	}
	
	//获取音乐音量百分数
	public static float getBGMPercent() {
		float BGMPercent= ((float)(volumes[0]*volumes[1]))/10000;
		return BGMPercent;
	}
	
	//获取音效音量百分数
	public static float getSoundEffectPercent() {
		float BGMPercent= ((float)(volumes[0]*volumes[2]))/10000;
		return BGMPercent;
	}
	
	
	
}
