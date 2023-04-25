package com.nyu.taha.kafkaTest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KData {
    private String myKey;
    private String myValue;
}