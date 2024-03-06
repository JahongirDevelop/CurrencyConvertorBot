package uz.pdp.currencyconvertor.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import uz.pdp.currencyconvertor.model.UserState;
import uz.pdp.currencyconvertor.service.CurrencyService;
import java.util.ArrayList;
import java.util.List;

public class BotService {
    CurrencyService currencyService = new CurrencyService();

    public SendMessage register(String chatId) {
        SendMessage sendMessage = new SendMessage(
                chatId, "Please share your number" );
        sendMessage.setReplyMarkup(shareContact());
        return sendMessage;
    }

    public SendMessage menu(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Menu");
        sendMessage.setReplyMarkup(menuButtons());
        return sendMessage;
    }

    public SendMessage enterAmount(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Enter amount");
        sendMessage.setReplyMarkup(replyKeyboardRemove());
        return sendMessage;
    }

    public SendMessage convertCurrency(String chatId, UserState state, Double amount) {
        String code = "";
        boolean toUZS = true;
        switch (state) {
            case EUR_UZS -> {
                code = "EUR";
                toUZS = true;
            }
            case UZS_EUR -> {
                code = "EUR";
                toUZS = false;
            }
            case USD_UZS -> {
                code = "USD";
                toUZS = true;
            }
            case UZS_USD -> {
                code = "USD";
                toUZS = false;
            }
        }

        String text = currencyService.convert(code, toUZS, amount);
        SendMessage sendMessage = new SendMessage(chatId, text);
        sendMessage.setReplyMarkup(menuButtons());
        return sendMessage;
    }



    public ReplyKeyboardMarkup shareContact() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);

        KeyboardRow row = new KeyboardRow();
        KeyboardButton button = new KeyboardButton("Share number");
        button.setRequestContact(true);
        row.add(button);

        replyKeyboardMarkup.setKeyboard(List.of(row));
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup menuButtons() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        List<KeyboardRow> rows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add("UZS -> USD");
        row.add("UZS -> EUR");
        rows.add(row);

        row = new KeyboardRow();
        row.add("USD -> UZS");
        row.add("EUR -> UZS");
        rows.add(row);

        replyKeyboardMarkup.setKeyboard(rows);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardRemove replyKeyboardRemove() {
        return new ReplyKeyboardRemove(true);

    }



}
