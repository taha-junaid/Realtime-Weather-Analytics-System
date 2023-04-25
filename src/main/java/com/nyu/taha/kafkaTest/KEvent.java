package com.nyu.taha.kafkaTest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KEvent {
    private String message;
    private String status;
    private KData data;
}