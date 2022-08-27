package dev.gniadek.githubrestdemo.utils.exceptions;

public class UsernameNotFoundException extends RuntimeException{
    public UsernameNotFoundException(String username){
        super("Username " + username + " not found.");
    }
}
