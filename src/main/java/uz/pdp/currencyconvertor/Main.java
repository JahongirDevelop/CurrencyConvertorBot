package uz.pdp.currencyconvertor;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import uz.pdp.currencyconvertor.bot.CurrencyConvertorBot;

public class Main {
    public static void main(String[] args){
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new CurrencyConvertorBot());
            System.out.println("In progress..");

        } catch (TelegramApiException e) {
            throw new RuntimeException(e);

        }


    }
}