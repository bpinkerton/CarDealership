package com.cardealership.service;

import com.cardealership.dao.DAOUtilities;
import com.cardealership.dao.UserDao;
import com.cardealership.model.AccountType;
import com.cardealership.model.User;

import java.util.Optional;

public class UserService {
    private UserDao userDao = DAOUtilities.getUserDao();

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

    public boolean addUser(User user){
        try{
            userDao.create(user);
            return true;
        } catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public void updateUser(User user){
        try{
            userDao.update(user);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void convertUser(User user, AccountType accountType){
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


    private Optional<User> validate(String email, String password){
        try{
            Optional<User> result = userDao.get(email);
            if(result.isPresent()){ // a user was found with the email provided
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
