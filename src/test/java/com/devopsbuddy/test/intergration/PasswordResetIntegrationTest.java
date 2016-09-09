package com.devopsbuddy.test.intergration;

import com.devopsbuddy.DevopsbuddyApplication;
import com.devopsbuddy.backend.persistence.domain.backend.PasswordResetToken;
import com.devopsbuddy.backend.persistence.domain.backend.User;
import com.devopsbuddy.backend.persistence.repositories.PasswordResetRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by larris on 09/09/16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DevopsbuddyApplication.class)
public class PasswordResetIntegrationTest extends AbstractIntegrationTest {

    @Value("${token.expiration.length.minutes}")
    private int expirationTimeInMinutes;

    @Autowired
    private PasswordResetRepository passwordResetRepository;

    @Rule
    public TestName testName = new TestName();

    @Before
    public void init() {
        Assert.assertFalse(expirationTimeInMinutes == 0);
    }

    @Test
    public void testTokenExpirationLength() throws Exception {

        User user = createUser(testName);
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getId());

        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());
        String token = UUID.randomUUID().toString();

        LocalDateTime expectedTime = now.plusMinutes(expirationTimeInMinutes);


        PasswordResetToken passwordResetToken = createPasswordResetToken(token, user, now);

        LocalDateTime actualTime = passwordResetToken.getExpiryDate();
        Assert.assertNotNull(actualTime);
        Assert.assertEquals(expectedTime, actualTime);

    }

    @Test
    public void testFindTokenByTokenValue() throws Exception{
        User user = createUser(testName);
        String token = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());

        createPasswordResetToken(token, user, now);

        PasswordResetToken retrievedPasswordResetToken = passwordResetRepository.findByToken(token);
        Assert.assertNotNull(retrievedPasswordResetToken);
        Assert.assertNotNull(retrievedPasswordResetToken.getId());
        Assert.assertNotNull(retrievedPasswordResetToken.getUser());

    }

    @Test
    public  void testDeleteToken() throws Exception{
        User user =createUser(testName);
        String token = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());

        PasswordResetToken passwordResetToken = createPasswordResetToken(token, user, now);
        long tokenId = passwordResetToken.getId();
        passwordResetRepository.delete(tokenId);

        PasswordResetToken souldNotExistToken = passwordResetRepository.findOne(tokenId);
        Assert.assertNull(souldNotExistToken);
    }

    @Test
    public void testCascadeDeleteFromuserEntity() throws  Exception{
        User user =createUser(testName);
        String token = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());

        PasswordResetToken passwordResetToken = createPasswordResetToken(token, user, now);
        passwordResetToken.getId();

        userRepository.delete(user.getId());

        Set<PasswordResetToken> souldBeEmpty = passwordResetRepository.findAllByUserId(user.getId());
        Assert.assertTrue(souldBeEmpty.isEmpty());
    }

    @Test
    public void testMultipleTokensAreReturnedWehnQueryingByUserId() throws Exception{
        User user =createUser(testName);
        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());
        String token1 = UUID.randomUUID().toString();
        String token2 = UUID.randomUUID().toString();
        String token3 = UUID.randomUUID().toString();

        Set<PasswordResetToken> tokens = new HashSet<>();
        tokens.add(createPasswordResetToken(token1, user, now));
        tokens.add(createPasswordResetToken(token2, user, now));
        tokens.add(createPasswordResetToken(token3, user, now));

        passwordResetRepository.save(tokens);

        User findUser = userRepository.findOne(user.getId());

        Set<PasswordResetToken> actualTokens = passwordResetRepository.findAllByUserId(findUser.getId());
        Assert.assertTrue(actualTokens.size() == tokens.size());

        List<String> tokensAsList = tokens.stream().map(prt ->prt.getToken()).collect(Collectors.toList());
        List<String> actualTokensAsList = actualTokens.stream().map(prt ->prt.getToken()).collect(Collectors.toList());
        Assert.assertEquals(tokensAsList,actualTokensAsList);

    }

    private  PasswordResetToken createPasswordResetToken(String token,User user,LocalDateTime now){
        PasswordResetToken passwordResetToken = new PasswordResetToken(token,user,now,expirationTimeInMinutes);
        passwordResetRepository.save(passwordResetToken);
        Assert.assertNotNull(passwordResetToken.getId());

        return passwordResetToken;
    }

}
