package com.minyov.chatserver.database.domain;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "T_MESSAGE")
public class Message extends AbstractEntity {

    @Expose
    @JoinColumn(name = "C_SENDER", nullable = false)
    @ManyToOne
    private UserEntity sender;

    @Expose
    @JoinColumn(name = "C_RECIEVER", nullable = false)
    @ManyToOne
    private UserEntity reciever;

    @Expose
    @Column(name = "C_TEXT", nullable = false)
    private String text;

    @Expose
    @Column(name = "C_DATE_TIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Expose
    @Column(name = "C_READ", nullable = false)
    private Boolean read;

    public Message(UserEntity sender, UserEntity reciever, String text, Date date, Boolean read) {
        this.sender = sender;
        this.reciever = reciever;
        this.text = text;
        this.date = date;
        this.read = read;
    }

    public Message(UUID id, UserEntity sender, UserEntity reciever, String text, Date date, Boolean read) {
        super(id);
        this.sender = sender;
        this.reciever = reciever;
        this.text = text;
        this.date = date;
        this.read = read;
    }

    public UserEntity getSender() {
        return sender;
    }

    public void setSender(UserEntity sender) {
        this.sender = sender;
    }

    public UserEntity getReciever() {
        return reciever;
    }

    public void setReciever(UserEntity reciever) {
        this.reciever = reciever;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }
}
