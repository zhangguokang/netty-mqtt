package com.hht.entity;

public class MsgRep {
    private Integer id;

    private Integer messageid;

    private String topname;

    private String sendiden;

    private byte[] content;

    public MsgRep(Integer messageid, String topname, String sendiden, byte[] content) {
        super();
        this.messageid = messageid;
        this.topname = topname;
        this.sendiden = sendiden;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMessageid() {
        return messageid;
    }

    public void setMessageid(Integer messageid) {
        this.messageid = messageid;
    }

    public String getTopname() {
        return topname;
    }

    public void setTopname(String topname) {
        this.topname = topname == null ? null : topname.trim();
    }

    public String getSendiden() {
        return sendiden;
    }

    public void setSendiden(String sendiden) {
        this.sendiden = sendiden == null ? null : sendiden.trim();
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}