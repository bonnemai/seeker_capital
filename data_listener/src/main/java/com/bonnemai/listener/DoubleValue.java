package com.bonnemai.listener;

import org.springframework.data.annotation.Id;

public class DoubleValue {
    @Id
    public String id;

    public int nb;
    public long timestamp;

    public DoubleValue() {}

    public DoubleValue(int nb, long timestamp) {
        this.nb=nb;
        this.timestamp= timestamp;
    }

    @Override
    public String toString() {
        return String.format(
                "Double Value [id=%s, nb='%s', timestamp='%s']",
                id, nb, timestamp);
    }
}
