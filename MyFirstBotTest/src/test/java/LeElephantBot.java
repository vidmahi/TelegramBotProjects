import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.toIntExact;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import java.time.LocalDate;
import java.util.Locale;

import org.bson.Document;


public class LeElephantBot extends TelegramLongPollingBot {

    private ArrayList<Members> memberList;

    public LeElephantBot() {
        memberList = new ArrayList<Members>();
    }

    public void add(Members member) {
        memberList.add(member);
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < memberList.size(); i++) {
            result += (i + 1) + ". " + memberList.get(i);
        }
        //  System.out.println(result);
        return result;
    }


    @Override
    public String getBotUsername() {

        return "the_elephant_bot";
    }

    @Override
    public String getBotToken() {
        return "1862589889:AAHLjDC86Ieh_s_AF0cotcoLSowSSrftd6k";
    }


    private String check(String first_name, String last_name, int user_id, String username) {
        MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&directConnection=true&ssl=false");
        MongoClient mongoClient = new MongoClient(connectionString);
        MongoDatabase database = mongoClient.getDatabase("TelegramBot");
        MongoCollection<Document> collection = database.getCollection("users");
        MongoCollection<Document> collectionTwo = database.getCollection("jeopardy_test");
        long found = collection.count(Document.parse("{id : " + Integer.toString(user_id) + "}"));
        long foundTwo = collectionTwo.count(Document.parse("{id : " + Integer.toString(user_id) + "}"));
        if (found == 0) {
            Document doc = new Document("first_name", first_name)
                    .append("last_name", last_name)
                    .append("id", user_id)
                    .append("username", username)
                    .append("user_status", "New");
            collection.insertOne(doc);
            mongoClient.close();
            System.out.println("User not exists in database. Written.");
            return "no_exists";
        } else {
            System.out.println("User exists in database.");
            mongoClient.close();
            return "exists";
        }


    }


    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text

        SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields

        System.out.println(update.getMessage().getText());

        System.out.println(memberList);
        System.out.println(memberList.size());
        for(int i = 0; i < memberList.size(); i++)
        {
            System.out.println(memberList.get(i).getName() + " " + memberList.get(i).getId());
        }





        if (update.hasMessage() && update.getMessage().hasText()) {

            //******* This is Echo Bot Start ********

            // message.setText(update.getMessage().getText());

            //******* This is Echo Bot End ********

            //******* This is Reverse Bot Start ********
            /*String reverse = "";
            String str = (update.getMessage()).getText();

            for (int i = str.length() - 1; i >= 0; i--) {
                reverse = reverse + str.charAt(i);
            }
            System.out.println(reverse);
            message.setText(reverse);*/

            //******* This is Reverse Bot End ********

            //******* Send Yes/No Keyboard Start ********

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        // Create a keyboard row
        KeyboardRow row = new KeyboardRow();
        // Set each button, you can also use KeyboardButton objects if you need something else than text
        row.add("Yes");
        row.add("No");
        keyboard.add(row);

            //******* Send Yes/No Keyboard End ********

        message.setChatId(update.getMessage().getChatId().toString());



            //******* Mongo Connection Start ********

        MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&directConnection=true&ssl=false");
        MongoClient mongoClient = new MongoClient(connectionString);
        MongoDatabase database = mongoClient.getDatabase("TelegramBot");
        MongoCollection<Document> collection = database.getCollection("users");

            //******* Mongo Connection End ********

        /*long found = collection.count(Document.parse("{id : " + Integer.toString(Integer.parseInt(message.getChatId())) + "}"));

        int chatTest = Integer.parseInt(message.getChatId());
        String old_user = "Old";
        String new_user = "New";*/

            int chatTest = Integer.parseInt(message.getChatId());

            //******* Checking if user exists Start ********

        if(update.getMessage().getText().equalsIgnoreCase("Hello"))
        {

            long found1 = collection.count(Document.parse("{id : " + Integer.toString(chatTest) + "}"));
            System.out.println(found1);
            if (found1==0) {
                message.setText("Welcome to the elephant chat bot! Thank you for registering yourself! Do you want to take a short poll?");
                keyboardMarkup.setKeyboard(keyboard);
                message.setReplyMarkup(keyboardMarkup);
            }
            else
            {
                message.setText("Welcome back. You are already registered. Enjoy the bot! The valid commands are /jeopardy, /watson, and /translate");
            }
        }

            //******* Checking if user exists End ********

            //******* Poll Start ********

            if(update.getMessage().getText().charAt(2) == '-')
            {
                message.setText("Thank you! Your birthday is on " + update.getMessage().getText().substring(0) + "! Please enter the command /color to move on to the next question, and enter your favorite color.");
                String vDb = update.getMessage().getText().substring(0);
                System.out.println(message.getChatId());
                int tcId = Integer.parseInt(message.getChatId());
                collection.updateOne(Filters.eq("id", tcId), Updates.set("birthday", vDb));
                Document myDoc2 = collection.find(Filters.eq("id", tcId)).first();
                System.out.println(myDoc2.toJson());

                LocalDate myObj = LocalDate.now(); // Create a date object
                System.out.println(myObj);

                if(myObj.toString().substring(5).equals(vDb))
                {
                    message.setText("Happy Birthday!");
                }
            }

            if (update.getMessage().getText().equals("Yes"))
            {
                message.setText("You selected yes. Let's begin! Please enter the command /birthday");

            }
            else if (update.getMessage().getText().equals("No"))
            {
                message.setText("You selected no. Thank you, have a nice day!");
            }

            if(update.getMessage().getText().equals("/birthday"))
            {
                message.setText("Thank you! Please enter your birthday in the format '(month - date)'. An example is '04-20'.");
            }

            if(update.getMessage().getText().equals("/color"))
            {
                message.setText("Thank you! Please click your favorite color!");
                ReplyKeyboardMarkup keyboardMarkupColor = new ReplyKeyboardMarkup();
                List<KeyboardRow> keyboardColor = new ArrayList<>();
                // Create a keyboard row
                KeyboardRow rowColor = new KeyboardRow();
                // Set each button, you can also use KeyboardButton objects if you need something else than text
                rowColor.add("Green");
                rowColor.add("Blue");
                keyboardColor.add(rowColor);
                keyboardMarkupColor.setKeyboard(keyboardColor);
                message.setReplyMarkup(keyboardMarkupColor);
            }

            if (update.getMessage().getText().equals("Green"))
            {
                message.setText("You like the color green! So do I! Thank you for answering these questions. Have a great day! The valid commands are /translate, /watson, and /jeopardy");
                String cDb = update.getMessage().getText();
                System.out.println(message.getChatId());
                int cId = Integer.parseInt(message.getChatId());
                collection.updateOne(Filters.eq("id", cId), Updates.set("color", cDb));
            }
            else if (update.getMessage().getText().equals("Blue"))
            {
                message.setText("You like the color blue! So do I! Thank you for answering these questions. Have a great day!");
                String cDb = update.getMessage().getText();
                System.out.println(message.getChatId());
                int cId = Integer.parseInt(message.getChatId());
                collection.updateOne(Filters.eq("id", cId), Updates.set("color", cDb));
            }

            //******* Poll End ********

            //******* Translate Start ********

            if(update.getMessage().getText().equals("/translate"))
            {
                message.setText("Please type in 'Translate (something)' to see a translation of your chosen word or phrase in different languages. And example is 'Translate Hello World'");
            }

            if(update.getMessage().getText().contains("Translate"))
            {
                String trans = update.getMessage().getText().substring(10);
                Translate t = new Translate();
                try {
                    String finalt = t.Post(trans);
                    System.out.println("finalt" + finalt);
                    //System.out.println(finalt.substring(finalt.indexOf("text"+5),finalt.indexOf(",")));
                    message.setText(finalt.substring((finalt.indexOf("text"))+7,finalt.indexOf(",")-1));
                    //message.setText(finalt.substring(26,finalt.indexOf(",")));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            //******* Translate End ********

            //******* Jeopardy Start ********

            if(update.getMessage().getText().equals("/jeopardy"))
            {

                String data = null;
                int testId = 0;

                try {
                    ProcessBuilder pb = new ProcessBuilder("python","C:\\Users\\ssist\\Documents\\Vidmahi\\ss1.py").inheritIO();
                    Process p = pb.start();
                    p.waitFor();
                    BufferedReader bfr = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String line = "";
                    while ((line = bfr.readLine()) != null) {
                        System.out.println(line);
                    }
                    data = new String(Files.readAllBytes(Paths.get("C:\\Users\\ssist\\Documents\\Vidmahi\\output.txt")));
                    if(update.getMessage().getText().contains("/jeopardy")) {


                        String questionSubstring = data.substring((data.indexOf("question")) + 11, data.indexOf("value") - 3);
                        String answerSubstring = data.substring((data.indexOf("answer"))+10,data.indexOf("round")-4);


                        message.setText(questionSubstring);

                        testId = Integer.parseInt(message.getChatId());
                        collection.updateOne(Filters.eq("id", testId), Updates.set("question", questionSubstring));
                       collection.updateOne(Filters.eq("id", testId), Updates.set("answer", answerSubstring));

              }


                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }


              }

            String data = null;
            try {
                data = new String(Files.readAllBytes(Paths.get("C:\\Users\\ssist\\Documents\\Vidmahi\\output.txt")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String answerSubstringTest = data.substring((data.indexOf("answer"))+10,data.indexOf("round")-4).toLowerCase();
            System.out.println(answerSubstringTest);
            if((update.getMessage().getText()).toLowerCase().equals(answerSubstringTest))
            {
                System.out.println("Comparision check");
                message.setText("Correct!");
            }

            //******* Jeopardy End ********


            // message.setText(update.getMessage().getText());

            System.out.println(message.getChatId());




            String user_first_name = update.getMessage().getChat().getFirstName();
            String user_last_name = update.getMessage().getChat().getLastName();
            String user_username = update.getMessage().getChat().getUserName();
            long user_id = update.getMessage().getChat().getId();
          /*  String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();*/


            try {
                execute(message); // Call method to send the message
                check(user_first_name, user_last_name, toIntExact(user_id), user_username);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }


    }
}






