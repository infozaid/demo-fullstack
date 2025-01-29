package com.example.demo.user;

import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.util.CustomEncoderPass;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class UserService {


    private final UserDao userDao;
    private final UserDtoMapper userDtoMapper;
    private final CustomEncoderPass customEncoderPass;

    public UserService(@Qualifier("jpa") UserDao userDao, UserDtoMapper userDtoMapper, CustomEncoderPass customEncoderPass) {
        this.userDao = userDao;
        this.userDtoMapper = userDtoMapper;
        this.customEncoderPass = customEncoderPass;
    }

    public UserDTO getUser(Integer id){
       return userDao.selectUserById(id)
                .map(userDtoMapper)
                .orElseThrow(()->new ResourceNotFoundException("Customer with id [%s] not found".formatted(id)));
    }

    public void addUser(UserRegistrationRequest userRegistrationRequest){

        String email = userRegistrationRequest.email();

        if(userDao.existsUserWithEmail(email)){
            throw new DuplicateResourceException("email already taken");
        }

        User user = new User(userRegistrationRequest.name(),
                userRegistrationRequest.email(),
                customEncoderPass.encode(userRegistrationRequest.password()),
                userRegistrationRequest.role());

        userDao.insertUser(user);
    }




}
