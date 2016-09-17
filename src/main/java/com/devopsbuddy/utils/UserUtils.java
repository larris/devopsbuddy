package com.devopsbuddy.utils;

import com.devopsbuddy.backend.persistence.domain.backend.User;
import com.devopsbuddy.web.controllers.ForgotMyPasswordController;
import com.devopsbuddy.web.domain.frontend.BasicAccountPayload;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by larris on 06/09/16.
 */
public class UserUtils {

    /**
     * non instatiable class
     */

    private   UserUtils(){
        throw  new  AssertionError("Non instantiable");
    }

    /**
     * Create a user with a basic attributes set
     */

    public  static User createBasicUser(String username,String email){
        User user = new User();
        user.setUsername(username);
        user.setPassword("secret");
        user.setEmail(email);
        user.setFirstName("fistName");
        user.setLastName("lastName");
        user.setPhoneNumber("123456789123");
        user.setCountry("GB");
        user.setEnabled(true);
        user.setDescription("A basic user");
        user.setProfileImageUrl("https://blbla.images.com/basicuser");

        return user;

    }


    public static String createPasswordResetUrl(HttpServletRequest request, long userId, String token) {

        String passwordResetUrl =
                request.getScheme() +
                        "://" +
                        request.getServerName() +
                        ":" +
                        request.getServerPort() +
                        request.getContextPath() +
                        ForgotMyPasswordController.CHANGE_PASSWORD_PATH +
                        "?id=" +
                        userId +
                        "&token=" +
                        token;

        return passwordResetUrl;
    }

    public static <T extends BasicAccountPayload> User fromWebUserToDomainUser(T fronendPayload) {
       User user = new User();
        user.setUsername(fronendPayload.getUserName());
        user.setPassword(fronendPayload.getPassword());
        user.setFirstName(fronendPayload.getFirstName());
        user.setLastName(fronendPayload.getLastName());
        user.setEmail(fronendPayload.getEmail());
        user.setPhoneNumber(fronendPayload.getPhoneNumber());
        user.setCountry(fronendPayload.getCountry());
        user.setEnabled(true);
        user.setDescription(fronendPayload.getDescription());

        return  user;

    }
}
