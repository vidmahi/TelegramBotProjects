import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LeElephantPhotoBot extends TelegramLongPollingBot {


    @Override
    public String getBotUsername() {
        return "the_elephant_photo_bot";
    }

    @Override
    public String getBotToken() {
        return "1819302745:AAGPjv3hYBXYJRZkc3-6YjUv6M_fQtemeQo";
    }

    @Override
    public void onUpdateReceived(Update update) {

       // String message_text = update.getMessage().getText();

        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();
            SendMessage message = new SendMessage(); // Create a message object object
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText(update.getMessage().getText());











       /*     try {
                //sendMessage(message); // Sending our message object to user
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            } else if (update.hasMessage() && update.getMessage().hasPhoto()) {
                // Message contains photo
                // Set variables
               // long chat_id = update.getMessage().getChatId();

                // Array with photo objects with different sizes
                // We will get the biggest photo from that array
                List<PhotoSize> photos = update.getMessage().getPhoto();
                // Know file_id
                String f_id = photos.stream()
                        .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                        .findFirst()
                        .orElse(null).getFileId();
                // Know photo width
                int f_width = photos.stream()
                        .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                        .findFirst()
                        .orElse(null).getWidth();
                // Know photo height
                int f_height = photos.stream()
                        .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                        .findFirst()
                        .orElse(null).getHeight();
                // Set photo caption
                String caption = "file_id: " + f_id + "\nwidth: " + Integer.toString(f_width) + "\nheight: " + Integer.toString(f_height);
                SendPhoto msg = new SendPhoto();



                msg.setChatId(update.getMessage().getChatId().toString());
                msg.setPhoto(f_id);
                msg.setCaption(caption);
                        /*.setChatId(chat_id)
                        .setPhoto(f_id)
                        .setCaption(caption);*/
           /*     try {
                    sendPhoto(msg); // Call method to send the photo with caption
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } */
        }  else if (update.getMessage().getText().equals("/markup")) {
            SendMessage message = new SendMessage(); // Create a message object object
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText("Here is your keyboard!");
                  //  .setChatId(chat_id)
                   // .setText("Here is your keyboard");
            // Create ReplyKeyboardMarkup object
            ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
            // Create the keyboard (list of keyboard rows)
            List<KeyboardRow> keyboard = new ArrayList<>();
            // Create a keyboard row
            KeyboardRow row = new KeyboardRow();
            // Set each button, you can also use KeyboardButton objects if you need something else than text
            row.add("Row 1 Button 1");
            row.add("Row 1 Button 2");
            row.add("Row 1 Button 3");
            // Add the first row to the keyboard
            keyboard.add(row);
            // Create another keyboard row
            row = new KeyboardRow();
            // Set each button for the second line
            row.add("Row 2 Button 1");
            row.add("Row 2 Button 2");
            row.add("Row 2 Button 3");
            // Add the second row to the keyboard
            keyboard.add(row);
            // Set the keyboard to the markup
            keyboardMarkup.setKeyboard(keyboard);
            // Add it to the message
            message.setReplyMarkup(keyboardMarkup);
            try {
                //sendMessage(message); // Sending our message object to user
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    }
}
