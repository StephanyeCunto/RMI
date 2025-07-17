package com.service;

import java.rmi.Naming;
import java.rmi.RemoteException;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class VideoServiceClient {
    public void initService(int ctrl,String rmi) {
        try {
           VideoServiceInterface service = (VideoServiceInterface) Naming.lookup(rmi);
            service.control(ctrl);   

        } catch (RemoteException e) {
            System.err.println("ServiceClient RemoteException: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("ServiceClient exception: " + e.toString());
            e.printStackTrace();
        }
    }
}