package com.cardealership.service;

import com.cardealership.dao.DAOUtilities;
import com.cardealership.dao.UserDao;
import com.cardealership.model.AccountType;
import com.cardealership.model.User;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Optional;

public class UserService {
    private UserDao userDao = DAOUtilities.getUserDao();
    private MessageDigest md;

    {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public boolean newUser(String firstName, String lastName, String email, String password){
        // check against the database if the email exists
        try{
            if(!userDao.get(email).isPresent()){ // if we are unable to find a user with the provided email, continue
                return addUser(new User(firstName,lastName,email,password));
            }
            else System.out.println("\t\tEmail already exists.");
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public void convertUser(User user, AccountType accountType){
        user = getUserById(user.getId());
        user.setAccountType(accountType);
        try{
            updateUser(user);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public User logIn(String email, String password){
        Optional<User> result = validate(email,password);
        return result.orElse(null);
    }

    public User getUserById(long id){
        Optional<User> result = getById(id);
        return result.orElse(null);
    }

    public User getUserByEmail(String email){
        Optional<User> result = getByEmail(email);
        return result.orElse(null);
    }


    private Optional<User> getByEmail(String email){
        try{
            Optional<User> result = userDao.get(email);
            if(result.isPresent()) return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private Optional<User> getById(long id){
        try{
            Optional<User> result = userDao.getById(id);
            if(result.isPresent()) return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private boolean addUser(User user){
        try{
            userDao.create(user);
            return true;
        } catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private void updateUser(User user){
        try{
            userDao.update(user);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private Optional<User> validate(String email, String password){
        try{
            Optional<User> result = userDao.get(email);
            if(result.isPresent()){ // a user was found with the email provided
                md.update(password.getBytes());
                password = DatatypeConverter.printHexBinary(md.digest()).toLowerCase();

                if(result.get().getPassword().equals(password)){
                    return result; // return the user if the passwords match
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return Optional.empty(); // return an empty optional if either the account wasn't found or the password was wrong
    }

}
