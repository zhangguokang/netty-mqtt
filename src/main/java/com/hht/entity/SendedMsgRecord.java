package com.hht.entity;

public class SendedMsgRecord {
    private Integer id;

    private String clientid;

    private Integer sendmsgid;

    private Integer sendtimes;

    private String sendclientid;
    
    

    public SendedMsgRecord(String clientid, Integer sendmsgid, Integer sendtimes, String sendclientid) {
        super();
        this.clientid = clientid;
        this.sendmsgid = sendmsgid;
        this.sendtimes = sendtimes;
        this.sendclientid = sendclientid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid == null ? null : clientid.trim();
    }

    public Integer getSendmsgid() {
        return sendmsgid;
    }

    public void setSendmsgid(Integer sendmsgid) {
        this.sendmsgid = sendmsgid;
    }

    public Integer getSendtimes() {
        return sendtimes;
    }

    public void setSendtimes(Integer sendtimes) {
        this.sendtimes = sendtimes;
    }

    public String getSendclientid() {
        return sendclientid;
    }

    public void setSendclientid(String sendclientid) {
        this.sendclientid = sendclientid == null ? null : sendclientid.trim();
    }
}