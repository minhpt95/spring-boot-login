package com.catdev.project.service.impl;

import com.catdev.project.service.BotService;
import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ReplyKeyboardRemove;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Service
@Log4j2
public class BotServiceImpl implements BotService {
    private final TelegramBot telegramBot;

    public BotServiceImpl(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;

        telegramBot.setUpdatesListener(updates -> {
            updates.forEach(this::doAction);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }


    private void SendMessageFromBot(Integer chatID, String message){
        SendMessageFromBot(chatID,message,null,null);
    }

    private void SendMessageFromBot(Integer chatID, String message, ReplyKeyboardMarkup replyKeyboardMarkup){
        SendMessageFromBot(chatID,message,replyKeyboardMarkup,null);
    }

    private void SendMessageFromBot(Integer chatID, String message, ReplyKeyboardMarkup replyKeyboardMarkup, ReplyKeyboardRemove replyKeyboardRemove){
        SendMessage sendMessage = new SendMessage(chatID,message);

        if(Objects.nonNull(replyKeyboardMarkup)){
            sendMessage.replyMarkup(replyKeyboardMarkup);
        }

        if(Objects.nonNull(replyKeyboardRemove)){
            sendMessage.replyMarkup(replyKeyboardRemove);
        }

        telegramBot.execute(sendMessage, new Callback<SendMessage, SendResponse>() {
            @Override
            public void onResponse(SendMessage request, SendResponse response) {
                log.info("onResponse -> request : {}, response : {}",request,response);
            }

            @Override
            public void onFailure(SendMessage request, IOException e) {
                log.error("onResponse -> request : {}",request,e);
            }
        });
    }

    private void doAction(Update update){

    }


}
