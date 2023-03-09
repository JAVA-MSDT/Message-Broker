package com.grpc.repository;

import com.grpc.config.H2DatabaseConnection;
import com.grpc.modal.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserRepository {

    private static final Logger LOGGER = Logger.getLogger(UserRepository.class.getName());
    private static final String SELECT_USER_BY_USERNAME = "SELECT * FROM user WHERE username = ?";

    public User getUserDetails(String username) {
        User user = new User();
        try {
            Connection connection = H2DatabaseConnection.getConnectionToDatabase();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_USERNAME);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            user = getUserFromResultSet(resultSet).isEmpty() ? user : getUserFromResultSet(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error Connecting to DB");
            throw new RuntimeException(e);
        }

        return user;
    }

    private List<User> getUserFromResultSet(ResultSet resultSet) {
        List<User> users = new ArrayList<>();
        User user = new User();
        try {
            while (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setName(resultSet.getString("name"));
                user.setAge(resultSet.getInt("age"));
                user.setGender(resultSet.getString("gender"));
                users.add(user);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error Extracting User from Result Set");
            throw new RuntimeException(e);
        }
        return users;
    }
}
