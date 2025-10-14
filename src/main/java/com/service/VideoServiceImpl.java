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

    private void increaseVolume(double increaseVolume){
        Platform.runLater(() -> {
            double newVolume = Math.min(1.0, mediaPlayer.getVolume() + increaseVolume);
                        System.out.println("Aumentar volume: "+increaseVolume);

            mediaPlayer.setVolume(newVolume);
        });
    }

    private void DecreaseVolume(double decreaseVolume){
        Platform.runLater(() -> {
            double newVolume = Math.min(1.0, mediaPlayer.getVolume() - decreaseVolume);
            System.out.println("Diminuir volume: "+decreaseVolume);

            mediaPlayer.setVolume(newVolume);
        });
    }

    @Override 
    public void control(int ctrl, double newVolume) {
        switch (ctrl) {
            case 1 -> playVideo();
            case 2 -> stopVideo();
            case 3 -> restartVideo();
            case 4 -> increaseVolume(newVolume);
            case 5 -> DecreaseVolume(newVolume);
            default -> System.out.println("Comando inválido.");
        }
    }
}