package uz.pdp.currencyconvertor.bot;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import uz.pdp.currencyconvertor.model.User;
import uz.pdp.currencyconvertor.model.UserState;
import uz.pdp.currencyconvertor.service.UserService;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CurrencyConvertorBot extends TelegramLongPollingBot {
    Logger logger = Logger.getLogger("Loggerjon");

    BotService botService = new BotService();
    UserService userService = new UserService();
    @Override
    public String getBotUsername() {
        return "YourBotUsername"; // Update with your bot's username

    }

    @Override
    public String getBotToken() {
        return System.getenv("BOT_TOKEN"); // Use environment variable for token

    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String text = message.getText();
        Long chatId = message.getChatId();

        User currentUser = userService.getByChatId(chatId);
        UserState userState = UserState.START;

        if (currentUser != null) {
            userState = currentUser.getState();
            switch (userState) {
                case REGISTERED, MENU -> {
                    switch (text) {
                        case "EUR -> UZS" -> {
                            userService.updateState(chatId, UserState.EUR_UZS);
                            userState = UserState.EUR_UZS;
                        }
                        case "USD -> UZS" -> {
                            userService.updateState(chatId, UserState.USD_UZS);
                            userState = UserState.USD_UZS;
                        }
                        case "UZS -> USD" -> {
                            userService.updateState(chatId, UserState.UZS_USD);
                            userState = UserState.UZS_USD;
                        }
                        case "UZS -> EUR" -> {
                            userService.updateState(chatId, UserState.UZS_EUR);
                            userState = UserState.UZS_EUR;
                        }
                    }
                }
                case EUR_UZS, USD_UZS, UZS_USD, UZS_EUR -> {
                    try {
                        Double amount = Double.valueOf(text);
                        execute(botService.convertCurrency(chatId.toString(), userState, amount));
                        userService.updateState(chatId, UserState.MENU);

                    } catch (NumberFormatException e) {
                        execute(new SendMessage(chatId.toString(), "please enter valid number"));
                    }
                    return;
                }
            }
        } else if (message.hasContact()) {
                Contact contact = message.getContact();
                User user = User.builder()
                        .chatId(chatId)
                        .firstName(contact.getFirstName())
                        .lastName(contact.getLastName())
                        .phoneNumber(contact.getPhoneNumber())
                        .state(UserState.REGISTERED)
                        .build();
                userService.add(user);
                userState = UserState.REGISTERED;
            }

            switch (userState) {
                case START -> {
                    execute(botService.register(chatId.toString()));
                }
                case REGISTERED, MENU -> {
                    execute(botService.menu(chatId.toString()));
                }
                case EUR_UZS, USD_UZS, UZS_EUR, UZS_USD -> {
                    execute(botService.enterAmount(chatId.toString()));
                }
            }


        Chat chat = message.getChat();
        String userPhoneNumber = message.getContact().getPhoneNumber();
        /*System.out.println("chat.getFirstName() = " + chat.getFirstName());
        System.out.println("chat.getUserName() = " + chat.getUserName());
        System.out.println("chat.getId() = " + chat.getId());
        System.out.println("userPhoneNumber = " + userPhoneNumber);*/

        logger.log(Level.INFO,chat.getFirstName()+" entered , message : " + text);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(chatId.toString());
        execute(sendMessage);

    }

}
