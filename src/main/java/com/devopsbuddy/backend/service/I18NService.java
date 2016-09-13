package com.devopsbuddy.backend.service;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Created by larris on 29/08/16.
 */

@Service
public class I18NService {

    /** The application logger **/
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(I18NService.class);

    @Autowired
    private MessageSource messageSource;

    public  String getMessage(String messageId){
        LOG.info("Returning i18n text messageid {}",messageId);
        Locale locale = LocaleContextHolder.getLocale();
        return getMessage(messageId,locale);
    }

    public  String getMessage(String messageId,Locale locale) {
        return messageSource.getMessage(messageId,null,locale);
    }
}
