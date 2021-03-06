package com.veselov.chatserver.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.veselov.chatserver.database.dao.MessageDao;
import com.veselov.chatserver.database.dao.UserDao;
import com.veselov.chatserver.database.domain.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController()
@RequestMapping("/api")
public class RestService {

    private static final Gson gson = new GsonBuilder()
            .setDateFormat("dd/mm/yyyy HH:MM:ss:SSS")
            .excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .create();

    @Autowired
    private UserDao userDao;

    @Autowired
    private MessageDao messageDao;

    @RequestMapping("/save")
    public void save() {
        UserEntity user1 = new UserEntity("goga", "test", "test@test.ru");
        userDao.persist(user1);
        UserEntity user2 = new UserEntity("server", "test", "test@test.ru");
        user2.setPhoto("/Users/veselov/Documents/workspace/chat_client/src/img/img.jpg");
        userDao.persist(user2);
        UserEntity user3 = new UserEntity("veselov", "test", "test@test.ru");
//        UserEntity user3 = new UserEntity("test", "test", "test@test.ru");



        userDao.persist(user1);
        userDao.persist(user2);
        userDao.persist(user3);

        List<UserEntity> users = userDao.getAll();

        List<UserEntity> user1Friends = new ArrayList<>();
        user1Friends.add(users.get(1));
        user1Friends.add(users.get(2));
        users.get(0).setFriends(user1Friends);

        userDao.persist(users.get(0));

        UserEntity veselov = userDao.getByName("veselov");
        UserEntity goga = userDao.getByName("goga");
        UserEntity server = userDao.getByName("server");

        ArrayList<UserEntity> list = new ArrayList<>();
        list.add(goga);
        list.add(server);
        veselov.setFriends(list);

        userDao.persist(veselov);
    }

    @RequestMapping(value = "/getUser/{userName}", method = RequestMethod.GET)
    public ResponseEntity<String> getUser(@PathVariable("userName") String name) {
        return new ResponseEntity<>(gson.toJson(userDao.getByName(name)), HttpStatus.OK);
    }

    @RequestMapping(value = "/getFriends/{userName}", method = RequestMethod.GET)
    public ResponseEntity<String> getFriends(@PathVariable("userName") String name) {
        return new ResponseEntity<>(gson.toJson(userDao.getFriends(name)), HttpStatus.OK);
    }

    @RequestMapping(value = "/getMessages",
                    method = RequestMethod.GET,
                    params = { "senderName", "receiverName" })
    public ResponseEntity<String> getMessages(@RequestParam("senderName") String senderName,
                                              @RequestParam("receiverName") String receiverName) {
        return new ResponseEntity<>(gson.toJson(messageDao.getMessages(senderName, receiverName)), HttpStatus.OK);
    }

    @RequestMapping(value = "/login",
            method = RequestMethod.GET,
            params = { "userName" })
    public ResponseEntity<String> login(@RequestParam("userName") String userName) {
        return new ResponseEntity<>(gson.toJson(userDao.getByName(userName.toLowerCase())), HttpStatus.OK);
    }

    @RequestMapping(value = "/signup",
            method = RequestMethod.POST)
    public ResponseEntity<String> signUp(@RequestBody UserEntity body) {
        boolean saved = userDao.save(body);

        if (saved) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/sub/{userName}",
            method = RequestMethod.POST)
    public ResponseEntity<String> subscribe(@PathVariable("userName") String name) {
        userDao.setSubscriber(name);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
