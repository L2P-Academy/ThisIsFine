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
	
	private final String musicFileUrl="";
	private final String sfxFileUrl="";
	private final String buttonFileUrl="";
	public Clip musicClip;
	public Clip sfxClip;
	public Clip buttonClip;
	
	public SoundController(){
		musicClip=createClip(musicFileUrl);
		sfxClip=createClip(sfxFileUrl);
		buttonClip=createClip(buttonFileUrl);
	}
	
	public void playMusic(Clip clip) {
		musicClip.start();
	}
	
	public void stopMusic(Clip clip) {
		musicClip.stop();
	}
	
	public void playSfx(Clip clip) {
		sfxClip.start();
	}
	
	public void playButtonClip(Clip clip) {
		buttonClip.start();
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
