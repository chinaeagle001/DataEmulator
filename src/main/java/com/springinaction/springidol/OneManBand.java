package com.springinaction.springidol;

import java.util.Map;

/**
 * Created by xuezhangying on 3/13/16.
 */
public class OneManBand implements Performer {
    public OneManBand() {}

    public void perform() throws PerformanceException {
        for (String key : instruments.keySet()) {
            System.out.println(key + ":");
            Instrument instrument = instruments.get(key);
            instrument.play();
        }
    }

    private Map<String, Instrument> instruments;

    public void setInstruments(Map<String, Instrument> instruments) {
        this.instruments = instruments;
    }
}
