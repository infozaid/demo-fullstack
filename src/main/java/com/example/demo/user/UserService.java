package com.example.demo.user;

import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.user.role.ERole;
import com.example.demo.user.role.Role;
import com.example.demo.user.role.RoleDAO;
import com.example.demo.util.CustomEncoderPass;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserService {


    private final UserDao userDao;
    private final RoleDAO roleDAO;
    private final UserDtoMapper userDtoMapper;
    private final CustomEncoderPass customEncoderPass;

    public UserService(@Qualifier("jpa") UserDao userDao, @Qualifier("role_jpa") RoleDAO roleDAO,UserDtoMapper userDtoMapper, CustomEncoderPass customEncoderPass) {
        this.userDao = userDao;
        this.roleDAO = roleDAO;
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
        Map<String, Role> roleMap = roleDAO.findALlRoles();

        if(userDao.existsUserWithEmail(email)){
            throw new DuplicateResourceException("email already taken");
        }
        Set<Role> userRoles = new HashSet<>();
        if(userRegistrationRequest.roles()==null || userRegistrationRequest.roles().isEmpty()){
            userRoles.add(roleMap.get("role_user"));
        }else{
            userRoles = userRegistrationRequest.roles()
                    .stream()
                    .map(roleName -> {
                        Role role = roleMap.get(roleName.toLowerCase());
                        if (role == null) {
                            throw new ResourceNotFoundException("Error: Role '" + roleName + "' not found.");
                        }
                        return role; // Use existing role from DB, not new Role()
                    })
                    .collect(Collectors.toSet());
        }

        User user = new User(userRegistrationRequest.name(),
                userRegistrationRequest.email(),
                customEncoderPass.encode(userRegistrationRequest.password()));
        user.setRoles(userRoles);
        userDao.insertUser(user);
    }




}
