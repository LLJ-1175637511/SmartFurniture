package com.android.main;

import com.llj.baselib.IOTInterfaceId;

public class MainDataBean {

    @IOTInterfaceId("22683")
    private Float temp;

    @IOTInterfaceId("22684")
    private Float hump;

    @IOTInterfaceId("22686")
    private int people;

    public Float getTemp() {
        return temp;
    }

    public void setTemp(Float temp) {
        this.temp = temp;
    }

    public Float getHump() {
        return hump;
    }

    public void setHump(Float hump) {
        this.hump = hump;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }
}
