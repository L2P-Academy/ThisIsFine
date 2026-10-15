package controller;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

//author:SenseiFarm

public class SoundController {
	private final String urlPrefix="/sfx/";//"Haeng halt n /sfx/ an, wie in der MenuView. Layer 8 Fehler" - Christph Bauch, 15.10.2026 
	private final String urlEnd=".wav";
	private final String alarmUrl="alarm";
	private final String attentionUrl="attention";
	private final String extinguishedUrl="extinguished";
	private final String fireExtinguisherUrl="fireExtinguisher";
	private final String footStepsUrl="footSteps";
	private final String gameOverUrl="gameOver";
	private final String inGame_bgmUrl="inGame_bgm";
	private final String mouseClickUrl="mouseClick";
	private final String qteFailUrl="qteFail";
	private final String qteSuccees_Url="qteSucceed_";
	private final String talking_Url="talking_";
	private final String taskCompletedUrl="taskCompleted";
	private final String terminalLockOutUrl="terminalLockOut";
	private final String timerUrl="timer";
	private final String typingUrl="typing";
	
	public Clip titleClip;
	public Clip bgmClip;
	public Clip sfxClip;
	public Clip successClip;
	public Clip failClip;
	public Clip typingClip;
	public Clip buttonClip;
	
	public SoundController(){
		titleClip=createClip(inGame_bgmUrl);
		bgmClip=createClip(inGame_bgmUrl);
		//sfxClip=createClip(null);
		failClip=createClip(qteFailUrl);
		typingClip=createClip(typingUrl);
		buttonClip=createClip(mouseClickUrl);
	}
	
	public void playMusic(Clip clip) {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		clip.setFramePosition(0);
		clip.start();
	}
	
	public void stopMusic(Clip clip) {
		clip.stop();
	}
	
	public void playSfx(Clip clip) {
		clip.setFramePosition(0);
		clip.start();
	}
	
	public void stopSfx(Clip clip) {
		clip.stop();
	}
	
	public void playRandomSuccessClip() {
		int i=(int)Math.random()*5+1;
		successClip=createClip(qteSuccees_Url+i);
		successClip.start();
	}
	
	public void playButtonClip(Clip clip) {
		clip.setFramePosition(0);
		clip.start();
	}
	
	public void close() {
		if(titleClip.isOpen()) {
			titleClip.close();
		}
		if(bgmClip.isOpen()) {
			bgmClip.close();
		}
		//if(sfxClip.isOpen()) {
		//	sfxClip.close();
		//}
		if(buttonClip.isOpen()) {
		buttonClip.close();
		}
	}
	
	private Clip createClip(String fileUrl) {
		try{
			InputStream fileStream=new BufferedInputStream(getClass().getResourceAsStream(urlPrefix+fileUrl+urlEnd));
			AudioInputStream aStream=AudioSystem.getAudioInputStream(fileStream);
			Clip c =AudioSystem.getClip();
			c.open(aStream);
			return c;
		}catch(FileNotFoundException e) {// TODO Schönere Fehlermeldungen
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		return null;
	}
}
