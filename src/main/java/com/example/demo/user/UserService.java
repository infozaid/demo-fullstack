package com.example.demo.user;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.util.CustomEncoderPass;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserDao userDao;
    private final UserDtoMapper userDtoMapper;
    private final CustomEncoderPass customEncoderPass;

    public UserDTO getUser(Integer id){
       return userDao.selectUserById(id)
                .map(userDtoMapper)
                .orElseThrow(()->new ResourceNotFoundException("Customer with id [%s] not found".formatted(id)));
    }


}
