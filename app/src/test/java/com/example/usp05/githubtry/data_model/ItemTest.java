package com.example.usp05.githubtry.data_model;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by nathan on 4/28/18.
 */
public class ItemTest {
    
    Item item1 = new Item();
    Item item2 = new Item();

    final String username = "usernameTest";
    final String name = "itemNameTest";
    final String location = "locationTest";
    final String type = "typeTest";
    final String date_purchased = "01/23/4567";
    final String date_expired = "08/09/5678";
    final String notes = "Test long notes string: Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus sit amet orci massa. In hac habitasse platea dictumst. Fusce cursus finibus mi vel posuere. Quisque elit nisl, finibus vel ullamcorper non, eleifend tincidunt orci. Quisque maximus felis dictum, consequat mi id, placerat sem. Morbi fermentum vulputate feugiat. Aliquam convallis nec erat vel bibendum. Vivamus vulputate erat massa, vitae laoreet eros condimentum in. Duis tempor sollicitudin augue id volutpat. Ut et cursus lorem, eu condimentum metus. In porttitor vitae justo nec dictum. Nunc aliquet elit urna, id tristique est scelerisque aliquam. Proin non dolor leo. Integer varius aliquam tellus, at dapibus arcu facilisis a. Morbi pellentesque condimentum sodales. Proin tempus convallis consectetur. Quisque blandit, mi at ultricies sodales, libero mi interdum sapien, porta varius magna leo vitae ex. Donec sagittis tellus nec convallis venenatis. Aenean vulputate nisl sed tellus venenatis, in ultricies sem lobortis. Mauris euismod sodales lobortis. Sed elementum nibh vel enim hendrerit vehicula. Sed auctor mauris in augue ullamcorper, eu eleifend velit ultricies. ";
    final int quantity = 1234;
    final float average_price = 5678;
    final Date expirationDate = Calendar.getInstance().getTime();
    final Date purchaseDate = Calendar.getInstance().getTime();

    
    @Before
    public void setUp() throws Exception {
        item1.setUsername(username);
        item1.setName(name);
        item1.setLocation(location);
        item1.setType(type);
        item1.setDate_purchased(date_purchased);
        item1.setDate_expired(date_expired);
        item1.setNotes(notes);
        item1.setQuantity(quantity);
        item1.setAverage_price(average_price);
        item1.setExpirationDate(expirationDate);
        item1.setPurchaseDate(purchaseDate);
    }

    @Test
    public void getUsername() throws Exception {
        assertEquals("Wanted "+username+", got "+item1.getUsername(), username, item1.getUsername());
    }

    @Test
    public void setUsername() throws Exception {
        String newUsername = "newUsernameTest";
        item1.setUsername(newUsername);
        assertEquals(newUsername, item1.getUsername());
    }

    @Test
    public void getName() throws Exception {
        assertEquals(name, item1.getName());
    }

    @Ignore
    public void setName() throws Exception {
    }

    @Test
    public void getLocation() throws Exception {
        assertEquals(location, item1.getLocation());
    }

    @Ignore
    public void setLocation() throws Exception {
    }

    @Test
    public void getType() throws Exception {
        assertEquals(type, item1.getType());
    }

    @Ignore
    public void setType() throws Exception {
    }

    @Test
    public void getDate_purchased() throws Exception {
        assertEquals(date_purchased, item1.getDate_purchased());
        assertNotEquals(date_purchased, item1.getDate_expired());
    }

    @Ignore
    public void setDate_purchased() throws Exception {
    }

    @Test
    public void getDate_expired() throws Exception {
        assertEquals(date_expired, item1.getDate_expired());
    }

    @Ignore
    public void setDate_expired() throws Exception {
    }

    @Test
    public void getNotes() throws Exception {
        assertEquals(notes, item1.getNotes());
    }

    @Ignore
    public void setNotes() throws Exception {
    }

    @Test
    public void getQuantity() throws Exception {
        assertEquals(quantity, item1.getQuantity());
    }

    @Ignore
    public void setQuantity() throws Exception {
    }

    @Test
    public void getAverage_price() throws Exception {
        assertEquals(average_price, item1.getAverage_price(), 0);
    }

    @Ignore
    public void setAverage_price() throws Exception {
    }

    @Test
    public void getExpirationDate() throws Exception {
        assertEquals(expirationDate, item1.getExpirationDate());
    }

    @Ignore
    public void setExpirationDate() throws Exception {
    }

    @Test
    public void getPurchaseDate() throws Exception {
        assertEquals(purchaseDate, item1.getPurchaseDate());
    }

    @Ignore
    public void setPurchaseDate() throws Exception {
    }

    @Test
    public void equals() throws Exception {
        item2.setUsername(username);
        item2.setName(name);
        item2.setType(type);
        assertTrue(item1.equals(item2));
    }

    @Test
    public void test_hashCode() throws Exception {
        item2.setUsername(username);
        item2.setName(name);
        item2.setType(type);
        assertEquals(item1.hashCode(), item2.hashCode());
    }

}