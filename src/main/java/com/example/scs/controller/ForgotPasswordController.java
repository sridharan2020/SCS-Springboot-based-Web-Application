package com.example.scs.controller;

import com.example.scs.config.CustomUserDetailsService;
import com.example.scs.model.User;
import com.example.scs.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import net.bytebuddy.utility.RandomString;

@Controller
public class ForgotPasswordController {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("poornatheja2002@gmail.com", "SCS-IIT(BHU)");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm() {
        return "login/forgot_password_form";
    }

    @PostMapping("/forgot_password")
    public String processForgotPassword(HttpServletRequest request, Model model) throws MessagingException, UnsupportedEncodingException {
        String email = request.getParameter("email");
        String token = RandomString.make(30);

        int flag = customUserDetailsService.updateResetPasswordToken(token, email);

        if(flag == 1)
        {
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
            sendEmail(email, resetPasswordLink);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
        }
        else
        {
            model.addAttribute("message", "Could not find any customer with the email " + email);
        }

        return "login/forgot_password_form";
    }

    public void sendEmail(){

    }


    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        User user = customUserDetailsService.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (user == null) {
            model.addAttribute("message", "Invalid Token! Please use the recent link");
            return "login/forgot_password_form";
        }

        return "login/reset_password_form";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        User user = customUserDetailsService.getByResetPasswordToken(token);
        model.addAttribute("title", "Reset your password");

        if (user == null) {
            model.addAttribute("message", "Invalid Token! Please use the recent link");
            return "login/forgot_password_form";
        } else {
            customUserDetailsService.updatePassword(user, password);
            model.addAttribute("message", "You have successfully changed your password.");
        }

        return "login/login";
    }
}
