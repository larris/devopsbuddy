package com.devopsbuddy.utils;

import com.devopsbuddy.backend.persistence.domain.backend.User;

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
}
