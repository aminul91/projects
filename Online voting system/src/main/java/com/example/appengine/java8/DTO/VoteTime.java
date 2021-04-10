package com.example.appengine.java8.DTO;

import com.google.appengine.api.datastore.Key;
import java.io.Serializable;
import java.util.Date;

public class VoteTime implements Serializable {
    private Key key;
    private Date startdate;
    private Date enddate;
    private Long id;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    @Override
    public String toString(){
        return "VoteTime{" +
                "id='" + id + '\'' +
                "key=" + key +
                "startDate"+ startdate +'\'' +
                "endDate"+ enddate + '\'' +
                '}';
    }
}
