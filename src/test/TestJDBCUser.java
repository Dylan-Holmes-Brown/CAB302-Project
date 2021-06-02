package test;

import common.User;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import server.JDBCUserDataSource;

import javax.swing.*;

//TODO: Test exceptions
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestJDBCUser {

    private static JDBCUserDataSource jdbcUserDataSource;
    private final String name = "Blah";
    String expectedUser = "Blah, 1234, Member, Apple";

    @BeforeEach
    public void ConstructUserTable() {
        jdbcUserDataSource = new JDBCUserDataSource();
    }

    @AfterAll
    public static void TestUserClose() {
        jdbcUserDataSource.close();
    }

    @Test
    @Order(1)
    public void TestAddUser() {
        User user = new User(name, "1234", "Member", "Apple");
        jdbcUserDataSource.addUser(user);
        User u = jdbcUserDataSource.getUser(name);
        assertEquals(name, u.getUsername());
        assertEquals("1234", u.getPassword());
        assertEquals("Member", u.getAccountType());
        assertEquals("Apple", u.getOrganisationalUnit());
    }

    @Test
    @Order(2)
    public void TestGetUser() {
        User u = jdbcUserDataSource.getUser(name);
        assertEquals(expectedUser, (String.format("%s, %s, %s, %s", u.getUsername(), u.getPassword(), u.getAccountType(), u.getOrganisationalUnit())));
    }

    @Test
    @Order(3)
    public void TestUserSize() {
        int size = jdbcUserDataSource.getUserSize();
        assertEquals(1, size);
    }

    @Test
    @Order(4)
    public void TestUsernameSet() {
        DefaultListModel listModel = new DefaultListModel();
        for (String user : jdbcUserDataSource.UsernameSet()){
            listModel.addElement(user);
        }
        assertTrue(listModel.contains(name));
    }

    @Test
    @Order(5)
    public void TestDeleteUser() {
        DefaultListModel listModel = new DefaultListModel();
        DefaultListModel listModel2 = new DefaultListModel();
        for (String user : jdbcUserDataSource.UsernameSet()){
            listModel.addElement(user);
        }
        if (listModel.contains(name)) {
            jdbcUserDataSource.deleteUser(name);
            for (String user : jdbcUserDataSource.UsernameSet()){
                listModel2.addElement(user);
            }
            assertFalse(listModel2.contains(name));
        }
        else {
            fail(String.format("User '%s' could not be found in the table and therefore could not be deleted", name));
        }
    }
}
