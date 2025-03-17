package com.pranjal.service.impl;

import com.pranjal.dto.UserDto;
import com.pranjal.enitity.Role;
import com.pranjal.enitity.User;
import com.pranjal.repository.RoleRepository;
import com.pranjal.repository.UserRepository;
import com.pranjal.service.UserService;
import com.pranjal.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private Validation validation;

    @Override
    public Boolean register(UserDto userDto) {
        validation.userValidation(userDto);


        User user = modelMapper.map(userDto, User.class);
        setRole(userDto, user);

        User savedUser = userRepository.save(user);

        return savedUser != null;
    }

        private void setRole(UserDto userDto , User user) {
            List<Integer> reqRoleId = userDto.getRoles().stream().map(role -> role.getId()).toList();
            List<Role> role = roleRepository.findAllById(reqRoleId);
            user.setRoles(role);
    }

}
