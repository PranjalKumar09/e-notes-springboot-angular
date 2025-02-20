package com.pranjal.service.impl;

import com.pranjal.config.security.CustomUserDetails;
import com.pranjal.dto.EmailRequest;
import com.pranjal.dto.LoginRequest;
import com.pranjal.dto.LoginResponse;
import com.pranjal.dto.UserDto;
import com.pranjal.enitity.AccountStatus;
import com.pranjal.enitity.Role;
import com.pranjal.enitity.User;
import com.pranjal.repository.RoleRepository;
import com.pranjal.repository.UserRepository;
import com.pranjal.service.JwtService;
import com.pranjal.service.UserService;
import com.pranjal.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.UUID;


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
    @Autowired
    private EmailService emailService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    @Override
    public Boolean register(UserDto userDto, String url) throws Exception {
        validation.userValidation(userDto);
        User user = modelMapper.map(userDto, User.class);

        setRole(userDto, user);

        AccountStatus status = AccountStatus.builder()
                .isActive(false)
                .verificationCode(UUID.randomUUID().toString())
                .build();
        user.setStatus(status);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User savedUser = userRepository.save(user);

        if (!ObjectUtils.isEmpty(savedUser)) {
//            sendEmail(user,url);
            return true;
        }

            return false;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        Authentication authentication =   authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            if (authentication.isAuthenticated()) {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            String token = jwtService.generateToken(customUserDetails.getUser());
            UserDto userDto = modelMapper.map(customUserDetails.getUser(), UserDto.class);

            return LoginResponse.builder()
                    .token(token)
                    .user(userDto)
                    .build();
        }
        return null;

    }

    private void sendEmail(User savedUser, String url) throws Exception {
        String verificationLink = url + "/api/v1/home/verify?id=" + savedUser.getId() + "&vc=" + savedUser.getStatus().getVerificationCode();
        String message = String.format(
                "<!DOCTYPE html>" +
                        "<html>" +
                        "<head>" +
                        "<style>" +
                        "body { font-family: Arial, sans-serif; line-height: 1.6; }" +
                        ".container { max-width: 600px; margin: auto; padding: 20px; background-color: #f9f9f9; border-radius: 5px; }" +
                        ".button { background-color: #007bff; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; display: inline-block; }" +
                        "</style>" +
                        "</head>" +
                        "<body>" +
                        "<div class='container'>" +
                        "<h2>Welcome to E Notes, %s!</h2>" +
                        "<p>Dear %s,</p>" +
                        "<p>Thank you for registering on <b>E Notes</b>. We’re excited to have you on board!</p>" +
                        "<p>Please verify your email address by clicking the button below:</p>" +
                        "<p><a href='%s' class='button'>Verify Account</a></p>" +
                        "<p>If you didn't create an account, you can ignore this email.</p>" +
                        "<p>Best Regards,</p>" +
                        "<p><b>Pranjal Kumar Shukla</b><br>E Notes Team</p>" +
                        "</div>" +
                        "</body>" +
                        "</html>",
                savedUser.getFirstName(), savedUser.getFirstName(), verificationLink);

        EmailRequest emailRequest = EmailRequest.builder()
                .to(savedUser.getEmail())
                .title("Account Creating Confirmation")
                .subject("Account Created Success")
                .message(message)
                .build();
        emailService.send(emailRequest);
    }

    private void setRole(UserDto userDto , User user) {
            List<Integer> reqRoleId = userDto.getRoles().stream().map(role -> role.getId()).toList();
            List<Role> role = roleRepository.findAllById(reqRoleId);
            user.setRoles(role);
    }

}
