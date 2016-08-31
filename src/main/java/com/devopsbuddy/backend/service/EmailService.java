package com.devopsbuddy.backend.service;

import com.devopsbuddy.web.domain.frontend.FeedbackPojo;
import org.springframework.mail.SimpleMailMessage;

/**
 * Created by larris on 31/08/16.
 */
public interface EmailService {

    /**
     * Sends an email with the content in the feedbackPojo
     * @param feedbackPojo
     */
    public void sendFeedbackEmail(FeedbackPojo feedbackPojo);

    /**
     * Sends an email with the content of the Simple Mail Message Object
     * @param message
     */
    public void sendGenericEmaiMessage(SimpleMailMessage message);

}
