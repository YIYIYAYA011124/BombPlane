
package Service;

import java.io.FileNotFoundException;

//音效类
public class Sound {
    final String DIR = "music/";
    final String BOMB0= "bombnothing.wav";
    final String BOMB1= "bombbody.wav";
    final String BOMB2= "bombhead.wav";
    final String BGMMenu= "bgmMenu.wav";
    final String BGMChooseMode= "bgmChooseMode.wav";
    final String BGMFindEnemy= "bgmFindEnemy.wav";
    final String BGMInputPlanes= "bgmInputPlanes.wav";
    final String BGMBombPlanes= "bgmBombPlanes.wav";
    final String BGMBombFinish= "bgmBombFinish.wav";
    
    MusicPlayer player;
    
    //播放bombnothing
    public void playBOMB0() {
        play(DIR+BOMB0,FileDataHanlder.getSoundEffectPercent(),false);
    }
    
    //播放bombbody
    public void playBOMB1() {
        play(DIR+BOMB1,FileDataHanlder.getSoundEffectPercent(),false);
    }
    
    //播放bombhead
    public void playBOMB2() {
        play(DIR+BOMB2,FileDataHanlder.getSoundEffectPercent(),false);
    }
    
    //播放bgm
    public void playBGMMenu() {
        play(DIR+BGMMenu,FileDataHanlder.getBGMPercent(),true);
    }
    
    //播放bgm
    public void playBGMFindEnemy() {
        play(DIR+BGMFindEnemy,FileDataHanlder.getBGMPercent(),true);
    }
    
    //播放bgm
    public void playBGMChooseMode() {
        play(DIR+BGMChooseMode,FileDataHanlder.getBGMPercent(),true);
    }
    
    //播放bgm
    public void playBGMInputPlanes() {
        play(DIR+BGMInputPlanes,FileDataHanlder.getBGMPercent(),true);
    }
    
    //播放bgm
    public void playBGMBombPlanes() {
        play(DIR+BGMBombPlanes,FileDataHanlder.getBGMPercent(),true);
    }
    
    //播放bgm
    public void playBGMBombFinish() {
        play(DIR+BGMBombFinish,FileDataHanlder.getBGMPercent(),true);
    }
    
    //临时更改音量
    public void temporaryChangeVolumn(float volumePercent) {
    	player.temporaryChangeVolumn(volumePercent);
    }
    
    
    
    /**
     * 播放
     * 
     * @param file
     *            音乐文件完整名称
     * @param circulate
     *            是否循环播放
     */
    private void play(String file,float volumn,boolean circulate) {
        try {
            // 创建播放器
            player = new MusicPlayer(file,volumn,circulate);
            player.play();// 播放器开始播放
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void stopPlay() {
    	if(player!=null) player.stop();
    }
    
}
