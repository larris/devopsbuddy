package com.devopsbuddy.test.unit;

import com.devopsbuddy.backend.persistence.domain.backend.User;
import com.devopsbuddy.utils.UserUtils;
import com.devopsbuddy.web.controllers.ForgotMyPasswordController;
import com.devopsbuddy.web.domain.frontend.BasicAccountPayload;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.UUID;

/**
 * Created by larris on 12/09/16.
 */
public class UserUtilsUnitTest {

    private MockHttpServletRequest mockHttpServletRequest;
    //Library that creates dummy user account details
    private PodamFactory podamFactory;

    @Before
    public void init(){
        mockHttpServletRequest = new MockHttpServletRequest();
        podamFactory =  new PodamFactoryImpl();
    }

    @Test
    public void testPasswordResetEmailUrlConstruction() throws Exception{
        mockHttpServletRequest.setServerPort(8080); //defaul is 80
        String token = UUID.randomUUID().toString();
        long userId = 123456;

        String expectedUrl = "http://localhost:8080" + ForgotMyPasswordController.CHANGE_PASSWORD_PATH + "?id="+ userId +"&token="+ token;

        String actualUrl = UserUtils.createPasswordResetUrl(mockHttpServletRequest, userId,token);

        Assert.assertEquals(expectedUrl,actualUrl);
    }

    @Test
    public void mapWebUserToDomailUser(){

        BasicAccountPayload webUser = podamFactory.manufacturePojoWithFullData(BasicAccountPayload.class);
        webUser.setEmail("me@example.com");

        User user = UserUtils.fromWebUserToDomainUser(webUser);
        Assert.assertNotNull(user);

        Assert.assertEquals(webUser.getUserName(),user.getUsername());
        Assert.assertEquals(webUser.getPassword(),user.getPassword());
        Assert.assertEquals(webUser.getFirstName(),user.getFirstName());
        Assert.assertEquals(webUser.getLastName(),user.getLastName());
        Assert.assertEquals(webUser.getEmail(),user.getEmail());
        Assert.assertEquals(webUser.getPhoneNumber(),user.getPhoneNumber());
        Assert.assertEquals(webUser.getCountry(),user.getCountry());
        Assert.assertEquals(webUser.getDescription(),user.getDescription());




    }
}
