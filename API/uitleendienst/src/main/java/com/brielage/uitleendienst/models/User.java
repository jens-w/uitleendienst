package com.brielage.uitleendienst.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable (tableName = "user")
public class User {
    private String id;
    private String username;
    private String password;
    private Rol rol;
    private Persoon persoon;

    public User () {}

    public User (
            String username,
            String password,
            Rol rol,
            Persoon persoon) {
        this.username = username;
        this.password = password;
        this.rol      = rol;
        this.persoon  = persoon;
    }

    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    public String getId () {
        return id;
    }

    @DynamoDBAttribute
    public void setId (String id) {
        this.id = id;
    }

    @DynamoDBAttribute
    public String getUsername () {
        return username;
    }

    @DynamoDBAttribute
    public void setUsername (String username) {
        this.username = username;
    }

    @DynamoDBAttribute
    public String getPassword () {
        return password;
    }

    @DynamoDBAttribute
    public void setPassword (String password) {
        this.password = password;
    }

    @DynamoDBAttribute
    public Rol getRol () {
        return rol;
    }

    @DynamoDBAttribute
    public void setRol (Rol rol) {
        this.rol = rol;
    }

    @DynamoDBAttribute
    public Persoon getPersoon () {
        return persoon;
    }

    @DynamoDBAttribute
    public void setPersoon (Persoon persoon) {
        this.persoon = persoon;
    }

    @Override
    public String toString () {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", rol=" + rol +
                ", persoon=" + persoon +
                '}';
    }
}
