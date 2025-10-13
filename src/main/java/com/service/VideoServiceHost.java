package com.service;

import java.rmi.Naming;

import com.model.Host;

import javafx.fxml.FXML;
import javafx.scene.media.MediaPlayer;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class VideoServiceHost {
    private Host host;
    
    @FXML private MediaPlayer mediaPlayer;

    public void startHost() {
        try {
            System.setProperty("java.rmi.serve.hostname", host.getHost());

            VideoServiceInterface serviceVideo = new VideoServiceImpl(mediaPlayer);

            String rmi = ("rmi://" + host.getHost() + ":" + host.getPort() + "/" + host.getService());
            Naming.rebind(rmi, serviceVideo);

        } catch (Exception e) {
            System.err.println("ServiceHost exception: " + e.toString());
            e.printStackTrace();
        }
    }
}