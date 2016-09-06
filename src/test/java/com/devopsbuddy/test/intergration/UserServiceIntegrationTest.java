package com.devopsbuddy.test.intergration;

import com.devopsbuddy.DevopsbuddyApplication;
import com.devopsbuddy.backend.persistence.domain.backend.Role;
import com.devopsbuddy.backend.persistence.domain.backend.User;
import com.devopsbuddy.backend.persistence.domain.backend.UserRole;
import com.devopsbuddy.backend.service.UserService;
import com.devopsbuddy.enums.PlansEnum;
import com.devopsbuddy.enums.RolesEnum;
import com.devopsbuddy.utils.UserUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by larris on 06/09/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DevopsbuddyApplication.class)
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Rule public TestName testName = new TestName();


    @Test
    public void testCreateNewUser() throws  Exception{

        String usename = testName.getMethodName();
        String email = testName.getMethodName() + "@gmail.com";

        Set<UserRole> userRoles =new HashSet<>();
        User basicUser = UserUtils.createBasicUser(usename,email);
        userRoles.add(new UserRole(basicUser,new Role(RolesEnum.BASIC)));

        User user = userService.createUser(basicUser, PlansEnum.BASIC,userRoles);
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getId());
    }
}
