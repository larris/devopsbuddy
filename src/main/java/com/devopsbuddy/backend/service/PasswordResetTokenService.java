package com.devopsbuddy.backend.service;

import com.devopsbuddy.backend.persistence.domain.backend.PasswordResetToken;
import com.devopsbuddy.backend.persistence.domain.backend.User;
import com.devopsbuddy.backend.persistence.repositories.PasswordResetRepository;
import com.devopsbuddy.backend.persistence.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by larris on 09/09/16.
 */

@Service
@Transactional(readOnly = true)
public class PasswordResetTokenService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetRepository passwordResetRepository;

    @Value("${token.expiration.length.minutes}")
    private int tokenExpirationInMinutes;

    /** The application logger **/
    private static final Logger LOG = LoggerFactory.getLogger(PasswordResetTokenService.class);

    /**
     * Creates a new Password Reset Token for the user identified by the given email.
     * @param email The email uniquely identifying the user
     * @return a new Password Reset Token for the user identified by the given email or null if none was found
     */

    @Transactional
    public  PasswordResetToken createPasswordResetTokenForEmail(String email){
        PasswordResetToken passwordResetToken = null;
        User user = userRepository.findByEmail(email);

        if(null!=user) {
            String token = UUID.randomUUID().toString();
            LocalDateTime now = LocalDateTime.now(Clock.systemUTC());
            passwordResetToken = new PasswordResetToken(token,user,now,tokenExpirationInMinutes);

            passwordResetToken = passwordResetRepository.save(passwordResetToken);
            LOG.debug("Succesfully created token {} for user {}",token,user.getUsername());
        } else {
            LOG.warn("we couldnt find user for the given email {}",email);
        }

        return passwordResetToken;
    }

    public PasswordResetToken findByToken(String token){
        return passwordResetRepository.findByToken(token);
    }
}

