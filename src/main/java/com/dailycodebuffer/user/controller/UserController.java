package com.dailycodebuffer.user.controller;

import com.dailycodebuffer.user.VO.ResponseTemplateVO;
import com.dailycodebuffer.user.entity.User;

import com.dailycodebuffer.user.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user){
        LOG.info("Inside userService");
        User newuser = userService.addUser(user);
        return new ResponseEntity<>(newuser, HttpStatus.CREATED);
    }

    @GetMapping("/find/{userId}")
    public ResponseTemplateVO getUserwithDepartment(@PathVariable Long userId){
            log.info("Sending Order Details with Order Id {} to Notification Service",userId);
            return userService.getUserwithDepartment(userId);
    }





}
