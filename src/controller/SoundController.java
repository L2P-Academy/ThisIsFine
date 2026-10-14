package controller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

//author:SenseiFarm

public class SoundController {
	private final String alarmUrl="alarm.wav";
	private final String attentionUrl="attention.wav";
	private final String extinguishedUrl="extinguished.wav";
	private final String fireExtinguisherUrl="fireExtinguisher.wav";
	private final String footStepsUrl="footSteps.wav";
	private final String gameOverUrl="gameOver.wav";
	private final String inGame_bgmUrl="inGame_bgm.wav";
	private final String mouseClickUrl="mouseClick.wav";
	private final String qteFailUrl="qteFail.wav";
	private final String qteSuccees_1Url="qteSucceed_1.wav";
	private final String talking_1Url="talking_1.wav";
	private final String taskCompletedUrl="taskCompleted.wav";
	private final String terminalLockOutUrl="terminalLockOut.wav";
	private final String timerUrl="timer.wav";
	private final String typingUrl="typing.wav";
	
	
	public Clip musicClip;
	public Clip sfxClip;
	public Clip buttonClip;
	
	public SoundController(){
		musicClip=createClip(inGame_bgmUrl);
		sfxClip=createClip(null);
		buttonClip=createClip(mouseClickUrl);
		
	}
	
	public void playMusic(Clip clip) {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		clip.start();
	}
	
	public void stopMusic(Clip clip) {
		clip.stop();
	}
	
	public void playSfx(Clip clip) {
		clip.start();
	}
	
	public void playButtonClip(Clip clip) {
		clip.start();
	}
	
	public void close() {
		musicClip.close();
		sfxClip.close();
		buttonClip.close();
	}
	
	private Clip createClip(String fileUrl) {
		try{
			FileInputStream fileStream=(FileInputStream)SoundController.class.getResourceAsStream(fileUrl);//TODO Sound URL einfügen
			AudioInputStream aStream=new AudioInputStream(fileStream, AudioSystem.getAudioFileFormat(fileStream).getFormat(),0);
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
