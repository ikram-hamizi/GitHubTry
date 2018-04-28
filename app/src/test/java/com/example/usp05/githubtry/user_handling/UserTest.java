package com.example.usp05.githubtry.user_handling;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by nathan on 4/28/18.
 */
public class UserTest {

    User user1 = new User();
    User user2;

    final String username = "testUsername";
    final String password = "testPassword";
    final String question1 = "testQuestion1";
    final String question2 = "testQuestion2";
    final String question3 = "testQuestion3";

    @Before
    public void setUp() throws Exception {
        user1.setUsername(username);
        user1.setPassword(password);
        user1.setPassword2(password);
        user1.setSecQuestion1(question1);
        user1.setSecQuestion2(question2);
        user1.setSecQuestion3(question3);

        user2 = new User(username,null,
                null,null,
                null,null);
    }

    @Test
    public void getUsername() throws Exception {
        assertEquals(username, user1.getUsername());
    }

    @Test
    public void getPassword() throws Exception {
        assertEquals(password, user1.getPassword());
    }

    @Test
    public void getPassword2() throws Exception {
        assertEquals(password, user1.getPassword());
    }

    @Test
    public void getSecQuestion1() throws Exception {
        assertEquals(question1, user1.getSecQuestion1());
    }

    @Test
    public void getSecQuestion2() throws Exception {
        assertEquals(question2, user1.getSecQuestion2());
    }

    @Test
    public void getSecQuestion3() throws Exception {
        assertEquals(question3, user1.getSecQuestion3());
    }

    @Test
    public void equals() throws Exception {
        assertTrue(user1.equals(user2));
    }

    @Test
    public void test_hashCode() throws Exception {
        assertEquals(user1.hashCode(), user2.hashCode());
    }

}