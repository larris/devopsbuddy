package com.devopsbuddy.test.intergration;

import com.devopsbuddy.DevopsbuddyApplication;
import com.devopsbuddy.backend.persistence.domain.backend.Plan;
import com.devopsbuddy.backend.persistence.domain.backend.Role;
import com.devopsbuddy.backend.persistence.domain.backend.User;
import com.devopsbuddy.backend.persistence.domain.backend.UserRole;
import com.devopsbuddy.backend.persistence.repositories.PlanRepository;
import com.devopsbuddy.backend.persistence.repositories.RoleRepository;
import com.devopsbuddy.backend.persistence.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by larris on 05/09/16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DevopsbuddyApplication.class)
public class RepositoriesIntergrationTest {

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    private  static  final  int BASIC_PLAN_ID = 1;
    private  static  final  int BASIC_ROLE_ID = 1;

    @Before
    public void  init(){
        Assert.assertNotNull(planRepository);
        Assert.assertNotNull(roleRepository);
        Assert.assertNotNull(userRepository);
    }

    @Test
    public  void testCreateNewPlan() throws Exception{
        Plan basicPlan = createBasicPlan();
        planRepository.save(basicPlan);
        Plan retrievedPlan =planRepository.findOne(BASIC_PLAN_ID);
        Assert.assertNotNull(retrievedPlan);
    }

    @Test
    public void testCreateNewRole() throws Exception{
        Role userRole = createBasicRole();
        roleRepository.save(userRole);

        Role retrievedRole = roleRepository.findOne(BASIC_ROLE_ID);
        Assert.assertNotNull(retrievedRole);

    }

    @Test
    public void createNewUser(){
        //Create and save plan record
        Plan basicPlan = createBasicPlan();
        planRepository.save(basicPlan);

        /** Create User instance and se the Plan (basicPlan) saced entity as Foreign Key */
        User basicUser  = createBasicUser();
        basicUser.setPlan(basicPlan);

        /** Sets a collection  in the User entity . We add the UserRole object
         * set with the User and Role  object we just created
         */
        Role basicRole = createBasicRole();
        Set<UserRole> userRoles =  new HashSet<>();
        UserRole userRole = new UserRole();
        userRole.setUser(basicUser);
        userRole.setRole(basicRole);
        userRoles.add(userRole);

        /**
         * IMPORTANT
         * To add values to a JPA Entity
         * ALWAYS use the geter method first and all the objects afterwards
         */

        basicUser.getUserRoles().addAll(userRoles);

        /**
         * Saves the other side of the User to Roles relationship
         * by persisting all Roles in the UserRoles collection
         */
        for (UserRole ur: userRoles){
            roleRepository.save(ur.getRole());
        }

        /**
         * Saves the user and the run the findOne method to retrieve the user
         * if all relationships contain data the repositories work correctly
         */

        basicUser =userRepository.save(basicUser);
        User newlyCreatedUser = userRepository.findOne(basicUser.getId());
        Assert.assertNotNull(newlyCreatedUser);
        Assert.assertTrue(newlyCreatedUser.getId() !=0);
        Assert.assertNotNull(newlyCreatedUser.getPlan());
        Assert.assertNotNull(newlyCreatedUser.getPlan().getId());
        Set<UserRole> newlyCreatedUserUserRoles = newlyCreatedUser.getUserRoles();

        for (UserRole ur : newlyCreatedUserUserRoles){
            Assert.assertNotNull(ur.getRole());
            Assert.assertNotNull(ur.getRole().getId());
        }



    }

    private Role createBasicRole() {
        Role role = new Role();
        role.setId(BASIC_ROLE_ID);
        role.setName("ROLE_USER");
        return  role;
    }


    // private methods

    private Plan createBasicPlan(){
        Plan plan = new Plan();
        plan.setId(BASIC_PLAN_ID);
        plan.setName("Basic");
        return plan;
    }


    private User createBasicUser(){
        User user = new User();
        user.setUsername("basicUser");
        user.setPassword("secret");
        user.setEmail("me@example.com");
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
