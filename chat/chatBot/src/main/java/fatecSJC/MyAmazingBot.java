package fatecSJC;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MyAmazingBot implements LongPollingSingleThreadUpdateConsumer {
    private final TelegramClient telegramClient;

    public MyAmazingBot(String botToken) {
        telegramClient = new OkHttpTelegramClient(botToken);
    }

    Date Tempo = new Date();

    String saudacao = ((Tempo.getHours() >= 18)
            ?"Boa noite!"
            :(Tempo.getHours() >= 12)
                ?"Boa tarde!"
                :"Bom dia!");

    @Override
    public void consume(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String message_text = update.getMessage().getText();

            String[] words = message_text.split(" ");
            List listaPalavras = Arrays.asList(words);

            if (Objects.equals(message_text, "/start")){
                message_text = saudacao + " Eu sou o Ajudante de Livraria! \nComo posso te ajudar?";
            }else if (listaPalavras.contains("piada")){
                message_text = "hahaha";
            }else{
                message_text = "Não compreendi o que você quis dizer.\nPoderia enviar outra mensagem?";
            }

            long chat_id = update.getMessage().getChatId();

            SendMessage message = SendMessage // Create a message object
                    .builder()
                    .chatId(chat_id)
                    .text(message_text)
                    .build();
            try {
                telegramClient.execute(message); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
