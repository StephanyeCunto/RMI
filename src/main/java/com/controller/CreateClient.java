package com.controller;

import java.rmi.*;
import java.util.ArrayList;

import com.model.Host;
import com.service.VideoServiceClient;

import javafx.fxml.FXML;
import javafx.scene.layout.*;
import javafx.scene.control.*;

public class CreateClient {
    @FXML private TextField hostField, portField;
    @FXML private VBox listHost;

    private ArrayList<Host> hosts = new ArrayList<>();

    @FXML 
    public void createHost(){
        Host host = new Host(hostField.getText().trim(), "null", Integer.parseInt(portField.getText().trim()));
        if(!existHost(host)) hosts.add(host);
        printHost();
    }

    private boolean existHost(Host host){
        for(int i = 0; i < hosts.size(); i++){
            if(hosts.get(i).getHost().equals(host.getHost()) && hosts.get(i).getPort() == host.getPort()) return true;
        }

        return false;
    }

    private void printHost(){
        if(listHost.getChildren()!=null) listHost.getChildren().clear();

        for(Host host : hosts){
            try{
                String[] objetos = Naming.list("rmi://"+host.getHost()+":"+host.getPort()+"/");
                initHostList(objetos);
            }catch (Exception e) {
                System.err.println("Erro ao listar objetos: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void initHostList(String[] objetos){
        Label hostName = new Label(String.join("\n",objetos));
        hostName.getStyleClass().add("field-title");

        Button btnStart = new Button("Start");
        btnStart.setOnAction(e -> initService(1,String.join("\n",objetos))); 
        btnStart.getStyleClass().add("hero-button");

        Button btnStop = new Button("Stop");
        btnStop.setOnAction(e -> initService(2,String.join("\n",objetos))); 
        btnStop.getStyleClass().add("hero-button");

        Button btnRestart = new Button("Restart");
        btnRestart.setOnAction(e -> initService(3,String.join("\n",objetos))); 
        btnRestart.getStyleClass().add("hero-button");

        HBox hboxBtn = new HBox(20);
        hboxBtn.getChildren().addAll(btnStart, btnStop, btnRestart);

        VBox vbox = new VBox(30);
        vbox.getStyleClass().add("modern-card");
        vbox.getChildren().addAll(hostName, hboxBtn);

        listHost.getChildren().add(vbox);
    }

    private void initService(int control, String objetos){
        VideoServiceClient cliente = new VideoServiceClient();
        cliente.initService(control,objetos);
    }
}
