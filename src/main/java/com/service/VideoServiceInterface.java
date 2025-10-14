package com.service;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VideoServiceInterface extends Remote {
    void control(int ctrl, double newVolume) throws RemoteException;
}