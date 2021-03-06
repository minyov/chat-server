package com.veselov.chatserver.database.domain;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "C_USER")
public class UserEntity extends AbstractEntity {

    @Expose
    @Column(name = "C_NAME", nullable = false)
    private String name;

    @Column(name = "C_PASSWORD", nullable = false)
    private String password;

    @Expose
    @Column(name = "C_EMAIL", nullable = false)
    private String email;

    @Expose
    @Column(name = "C_PHOTO")
    private String photo;

    @Expose
    @Column(name = "C_SUBSCRIBER")
    private Boolean subscriber;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "T_FRIENDS",
            joinColumns = @JoinColumn(name = "C_PERSON_ID", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "C_FRIEND_ID", nullable = false)
    )
    private List<UserEntity> friends = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "T_FRIENDS",
            joinColumns = @JoinColumn(name = "C_FRIEND_ID", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "C_PERSON_ID", nullable = false)
    )
    private List<UserEntity> friendOf = new ArrayList<>();

    public UserEntity() {
    }

    public UserEntity(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.subscriber = false;
    }

    public UserEntity(UUID id, String name, String password, String email) {
        super(id);
        this.name = name;
        this.password = password;
        this.email = email;
        this.subscriber = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<UserEntity> getFriends() {
        return friends;
    }

    public void setFriends(List<UserEntity> friends) {
        this.friends = friends;
    }

    public List<UserEntity> getFriendOf() {
        return friendOf;
    }

    public void setFriendOf(List<UserEntity> friendOf) {
        this.friendOf = friendOf;
    }

    public Boolean getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Boolean subscriber) {
        this.subscriber = subscriber;
    }
}
