import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MyFirstBot extends TelegramLongPollingBot {


    @Override
    public String getBotUsername() {
        return "the_elephant_bot";
    }

    @Override
    public String getBotToken() {
        return "1862589889:AAEBn0W6NfsjtkNYlFyWANe6IuFCZJbTU";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();

       /*     if(message.hasText()) {
                String text = message.getText();

                if (text.equals("/start")) {
                    System.out.println("The Message Came!!!");

                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText("Hello World!!");
                    sendMessage.setParseMode(ParseMode.MARKDOWN);
                    sendMessage.setChatId(String.valueOf(message.getChatId()));
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }*/
        }

    }
}
