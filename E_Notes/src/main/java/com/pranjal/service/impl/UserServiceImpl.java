package com.pranjal.service.impl;

import com.pranjal.dto.EmailRequest;
import com.pranjal.dto.PasswordChangeRequest;
import com.pranjal.dto.PswdResetRequest;
import com.pranjal.enitity.User;
import com.pranjal.exception.ResourceNotFoundException;
import com.pranjal.repository.UserRepository;
import com.pranjal.service.UserService;
import com.pranjal.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;


    @Override
    public void changePassword(PasswordChangeRequest passwordChangeRequest) {
        User loggedInUser = CommonUtil.getLoggedInUser();

        if (!passwordEncoder.matches(passwordChangeRequest.getOldPassword(), loggedInUser.getPassword())) {
            throw new IllegalArgumentException("Old password does not match");
        }
        loggedInUser.setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
        userRepository.save(loggedInUser);

    }

    @Override
    public void sendEmailPasswordReset(String email, HttpServletRequest httpServletRequest) throws  Exception {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        // Generate unique Password reset token
        String passwordResetToken = UUID.randomUUID().toString();
        user.getStatus().setPasswordResetToken(passwordResetToken);
        User updatedUser =  userRepository.save(user);

        String url = CommonUtil.getUrl(httpServletRequest);
        sendEmailRequest(updatedUser, url);
    }

    private void sendEmailRequest(User user, String url) throws Exception {
        String resetPasswordLink = url + "/api/v1/home/verify-paswd-link?uid=" + user.getId() + "&code=" + user.getStatus().getPasswordResetToken();
        String message = String.format(
                "<head>" +
                        "<style>" +
                        "body { font-family: Arial, sans-serif; line-height: 1.6; }" +
                        ".container { max-width: 600px; margin: auto; padding: 20px; background-color: #f9f9f9; border-radius: 5px; }" +
                        ".button { background-color: #007bff; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; display: inline-block; }" +
                        "</style>" +
                        "</head>" +
                        "<body>" +
                        "<div class='container'>" +
                        "<h2>Reset Your Password, %s</h2>" +
                        "<p>Dear %s,</p>" +
                        "<p>We received a request to reset your password for your <b>E Notes</b> account.</p>" +
                        "<p>If you did not request a password reset, please ignore this email. If you did, click the button below to reset your password:</p>" +
                        "<p><a href='%s' class='button'>Reset Password</a></p>" +
                        "<p>If the button does not work, please copy and paste the following link into your browser:</p>" +
                        "<p>%s</p>" +
                        "<p>If you have any questions, feel free to contact our support team.</p>" +
                        "<p>Best Regards,</p>" +
                        "<p><b>Pranjal Kumar Shukla</b><br>E Notes Team</p>" +
                        "</div>" +
                        "</body>",
                user.getFirstName(), user.getFirstName(), resetPasswordLink, resetPasswordLink);
        EmailRequest emailRequest = EmailRequest.builder()
                .to(user.getEmail())
                .title("Reset Your Password")
                .subject("Reset Your E Notes Password")
                .message(message)
                .build();

        emailService.send(emailRequest);
    }


    @Override
public void verifyPaswdResetLink(Integer uid, String code) throws Exception {

        User user =  userRepository.findById(uid).orElseThrow(()-> new ResourceNotFoundException("Invalid User"));
        verifyPasswordRestLink(user.getStatus().getPasswordResetToken(), code);
    }

    @Override
    public void resetPassword(PswdResetRequest pswdResetRequest) throws Exception {
        User user =  userRepository.findById(pswdResetRequest.getUid()).orElseThrow(()-> new ResourceNotFoundException("Invalid User"));
        String encodePassword = passwordEncoder.encode(pswdResetRequest.getNewPassword());
        user.setPassword(encodePassword);
        user.getStatus().setPasswordResetToken(null);
        userRepository.save(user);

    }

    private void verifyPasswordRestLink(String existToken, String reqToken) {

        if (StringUtils.hasText(reqToken)) { // request Token not null

            // password already reset
            if (!StringUtils.hasText(existToken)) {
                throw new IllegalArgumentException("Token is empty | Already Password reset");
            }
            // user req token changes
            if (!existToken.equals(reqToken)) {
                throw new IllegalArgumentException("Token not match");
            }
        }
        else {
        throw new IllegalArgumentException("Invalid url");
        }
    }



}
