package com.dailycodebuffer.user.service;

import com.dailycodebuffer.user.VO.Department;
import com.dailycodebuffer.user.VO.ResponseTemplateVO;
import com.dailycodebuffer.user.entity.User;
import com.dailycodebuffer.user.repository.UserRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



@Service
@Slf4j
public class UserService {


    private Logger logger = LoggerFactory.getLogger(UserService.class);


    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;

    }

    public User addUser(User user){
        return userRepository.save(user);
    }

    /*public User getUser(Long id){
        return userRepository.findByUserId(id)
                .orElseThrow(() -> new UserNotFoundException("User by id Not Found")) ;
    }*/


    public ResponseTemplateVO getUserwithDepartment(Long userId) {
        ResponseTemplateVO vo = new ResponseTemplateVO();
        User user = getUser(userId);
         vo.setUser(user);
         vo.setDepartment(getDepartmentPerUser(user.getDepartmentId()));
        return vo;
    }

    @Retry(name = "departmentService", fallbackMethod = "getDefault")
    @CircuitBreaker(name = "departmentService", fallbackMethod = "getDefault")
    public Department getDepartmentPerUser(Long userId){
        logger.info("Inside method2");
        return this.restTemplate.getForEntity("http://DEPARTMENT-SERVICE/department/find/" + userId, Department.class)
                .getBody();
    }

    public User getUser(Long userId){
       return userRepository.findByUserId(userId);
    }

    public Department getDefault(int productId, Throwable throwable){
        return Department.of(0L,"","","");
    }



}
