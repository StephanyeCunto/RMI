package com.controller;

import com.service.*;
import com.model.Host;

import javafx.fxml.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.media.*;

import java.io.File;
import java.rmi.registry.LocateRegistry;
import java.util.ResourceBundle;
import java.net.URL;


public class CreateHost implements Initializable{
    @FXML private TextField hostField, serviceField, portField;
    @FXML private VBox vboxContainer;
    @FXML private MediaView mediaView;
    @FXML private ComboBox<String> videoComboBox;
    String videoFileAdress = "src/main/resources/video.mp4";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadComboBox();
    }

    @FXML
    public void createHost(){
        try{
            Host host = new Host(hostField.getText().trim(), serviceField.getText().trim(), Integer.parseInt(portField.getText().trim()));

            initHost(host);
        } catch (NumberFormatException e) {
            System.err.println("Erro:"+ "A porta deve ser um número válido.");
        } catch (Exception e) {
            System.err.println("Erro:"+ "Erro ao criar host: " + e.getMessage());
        }
    }

    private void loadComboBox(){  
        File folder = new File("src/main/resources");
        File[] files = folder.listFiles();
        if (files == null){
            videoComboBox.setDisable(true);
            return;
        }

        for (File file : files) videoComboBox.getItems().add(file.getName());

        if (!videoComboBox.getItems().isEmpty()) {
            videoComboBox.getSelectionModel().selectFirst();
            videoFileAdress = folder + "/" + videoComboBox.getValue();
        } 

        videoComboBox.valueProperty().addListener((obs, oldVal, newVal) -> videoFileAdress = folder + "/" + videoComboBox.getValue());
    }

    private void initHost(Host host){
        try {
            LocateRegistry.createRegistry(host.getPort());
            System.out.println("RMI Registry criado na porta: " + host.getPort());

            initViewVideo();
            initServiceVideo(host);
        } catch (Exception e) {
            System.err.println("Registry já estava rodando na porta " + host.getPort());
        }
    }

    private void initServiceVideo(Host host){
        MediaPlayer mediaPlayer = mediaView.getMediaPlayer();
        VideoServiceHost serviceVideo = new VideoServiceHost(host, mediaPlayer);
        serviceVideo.startHost();
    }

    private void initViewVideo(){
        vboxContainer.getChildren().clear();
        File videoFile = new File(videoFileAdress);

        String videoUri = videoFile.toURI().toString();
        Media media = new Media(videoUri);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaView = new MediaView(mediaPlayer);

        mediaView.setFitWidth(700);
        mediaView.setFitHeight(400);
        mediaView.setPreserveRatio(true);

        vboxContainer.getChildren().add(mediaView);
    }
}
