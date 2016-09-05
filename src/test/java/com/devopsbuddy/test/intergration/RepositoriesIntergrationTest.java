package com.devopsbuddy.test.intergration;

import com.devopsbuddy.DevopsbuddyApplication;
import com.devopsbuddy.backend.persistence.domain.backend.Plan;
import com.devopsbuddy.backend.persistence.domain.backend.Role;
import com.devopsbuddy.backend.persistence.domain.backend.User;
import com.devopsbuddy.backend.persistence.domain.backend.UserRole;
import com.devopsbuddy.backend.persistence.repositories.PlanRepository;
import com.devopsbuddy.backend.persistence.repositories.RoleRepository;
import com.devopsbuddy.backend.persistence.repositories.UserRepository;
import com.devopsbuddy.enums.PlansEnum;
import com.devopsbuddy.enums.RolesEnum;
import com.devopsbuddy.utils.UserUtils;
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




    @Before
    public void  init(){
        Assert.assertNotNull(planRepository);
        Assert.assertNotNull(roleRepository);
        Assert.assertNotNull(userRepository);
    }

    @Test
    public  void testCreateNewPlan() throws Exception{
        Plan basicPlan = createBasicPlan(PlansEnum.BASIC);
        planRepository.save(basicPlan);
        Plan retrievedPlan =planRepository.findOne(PlansEnum.BASIC.getId());
        Assert.assertNotNull(retrievedPlan);
    }

    @Test
    public void testCreateNewRole() throws Exception{
        Role userRole = createBasicRole(RolesEnum.BASIC);
        roleRepository.save(userRole);

        Role retrievedRole = roleRepository.findOne(RolesEnum.BASIC.getId());
        Assert.assertNotNull(retrievedRole);

    }

    @Test
    public void createNewUser(){
        //Create and save plan record
        Plan basicPlan = createBasicPlan(PlansEnum.BASIC);
        planRepository.save(basicPlan);

        /** Create User instance and se the Plan (basicPlan) saced entity as Foreign Key */
        User basicUser  = UserUtils.createBasicUser();
        basicUser.setPlan(basicPlan);

        /** Sets a collection  in the User entity . We add the UserRole object
         * set with the User and Role  object we just created
         */
        Role basicRole = createBasicRole(RolesEnum.BASIC);
        Set<UserRole> userRoles =  new HashSet<>();
        UserRole userRole = new UserRole(basicUser,basicRole);
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

    private Role createBasicRole(RolesEnum rolesEnum) {
        return new Role(rolesEnum);
    }


    // private methods

    private Plan createBasicPlan(PlansEnum plansEnum){
        return  new Plan(plansEnum);
    }



}
