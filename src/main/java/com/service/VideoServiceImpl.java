package com.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import javafx.application.Platform;
import javafx.scene.media.MediaPlayer;

public class VideoServiceImpl extends UnicastRemoteObject implements VideoServiceInterface {
    private MediaPlayer mediaPlayer;

    public VideoServiceImpl(MediaPlayer mediaPlayer) throws RemoteException {
        this.mediaPlayer = mediaPlayer;
    }

    private void playVideo(){
        Platform.runLater(() -> {
           if(mediaPlayer.getCurrentTime().equals(mediaPlayer.getTotalDuration())) restartVideo();
           else mediaPlayer.play();
        });
    }

    private void stopVideo(){
        Platform.runLater(() -> mediaPlayer.pause());
    }

    private void restartVideo(){
        Platform.runLater(() -> {
            mediaPlayer.seek(mediaPlayer.getStartTime());
            mediaPlayer.play();
        });
    }

    @Override 
    public void control(int ctrl) {
        switch (ctrl) {
            case 1 -> playVideo();
            case 2 -> stopVideo();
            case 3 -> restartVideo();
            default -> System.out.println("Comando inválido.");
        }
    }
}