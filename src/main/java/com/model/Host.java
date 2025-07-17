package com.model;

import lombok.*;

@Getter
@AllArgsConstructor
public class Host {
    private String host, service;
    private int port;
}
