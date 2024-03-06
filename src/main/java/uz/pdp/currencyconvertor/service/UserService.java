package uz.pdp.currencyconvertor.service;

import uz.pdp.currencyconvertor.model.User;
import uz.pdp.currencyconvertor.model.UserState;

import java.util.ArrayList;
import java.util.Objects;

import static uz.pdp.currencyconvertor.utils.Utils.userRepository;

public class UserService {


    public User getByChatId(Long chatId) {
        for (User user : userRepository.usersReadFromFile()) {
            if(Objects.equals(user.getChatId(), chatId)) {
                return user;
            }
        }
        return null;
    }

    public void add(User user) {
        ArrayList<User> users = userRepository.usersReadFromFile();
        users.add(user);
        userRepository.usersWriteToFile(users);
    }

    public void updateState(Long chatId, UserState userState) {
        ArrayList<User> users = userRepository.usersReadFromFile();
        for (User user : users) {
            if(Objects.equals(user.getChatId(), chatId)) {
                user.setState(userState);

                break;
            }
        }
        userRepository.usersWriteToFile(users);
    }
}
