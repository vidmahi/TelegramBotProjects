/*import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class LeElephantBot extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return "the_elephant_bot";
    }

    @Override
    public String getBotToken() {
        return "1862589889:AAE-Bn0W6NfsjtkNYlFyWANe6I-uFCZJbTU";
    }

    @Override
    public void onUpdateReceived(Update update) {
        //System.out.println(update.getMessage().getText());
        //System.out.println(update.getMessage().getFrom().getFirstName() );


        String command=update.getMessage().getText();

        SendMessage message = new SendMessage();

        if(command.equals("/myname")){
            System.out.println(update.getMessage().getFrom().getFirstName());
            message.setText(update.getMessage().getFrom().getFirstName());
        }
        if (command.equals("/mylastname")){
                System.out.println(update.getMessage().getFrom().getLastName());
                message.setText(update.getMessage().getFrom().getLastName());
        }
        if(command.equals("/myfullname")){
            System.out.println(update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getLastName());
            message.setText(update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getLastName());
        }

        message.setChatId(String.valueOf(update.getMessage().getChatId()));

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            SendMessage m = new SendMessage() // Create a message object object
                    .setChatId(String.valueOf((chat_id)))
                    .setText(message_text);
                    //.setText(update.getMessage().getText());
            try {
                execute(m); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}*/

//import org.telegram.telegrambots.api.methods.send.SendMessage;
//import org.telegram.telegrambots.api.objects.Update;
import com.mongodb.DBCursor;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
//import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
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



import org.bson.Document;
import org.json.JSONObject;
import org.telegram.telegrambots.meta.generics.TelegramBot;

public class LeElephantBot extends TelegramLongPollingBot {

    private ArrayList<Members> memberList;

    public LeElephantBot()
    {
        memberList = new ArrayList<Members>();
    }

    public void add(Members member)
    {
        memberList.add(member);
    }

    public String toString()
    {
        String result = "";
        for (int i = 0; i < memberList.size(); i++) {
            result += (i+1) +". " + memberList.get(i);
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
        return "1862589889:AAE-Bn0W6NfsjtkNYlFyWANe6I-uFCZJbTU";
    }


    private String check(String first_name, String last_name, int user_id, String username) {
        MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&directConnection=true&ssl=false");
        MongoClient mongoClient = new MongoClient(connectionString);
        MongoDatabase database = mongoClient.getDatabase("TelegramBot");
        MongoCollection<Document> collection = database.getCollection("users");
        long found = collection.count(Document.parse("{id : " + Integer.toString(user_id) + "}"));
        if (found == 0) {
            Document doc = new Document("first_name", first_name)
                    .append("last_name", last_name)
                    .append("id", user_id)
                    .append("username", username);
                  //  .append("birthday", birthday);
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

        System.out.println(update.getMessage().getText());

        System.out.println(memberList);
        System.out.println(memberList.size());
        for(int i = 0; i < memberList.size(); i++)
        {
            System.out.println(memberList.get(i).getName() + " " + memberList.get(i).getId());
        }

        if (update.hasMessage() && update.getMessage().hasText()) {


            String reverse = "";
            String str = (update.getMessage()).getText();
           // System.out.println(str);
            for (int i = str.length() - 1; i >= 0; i--) {
                reverse = reverse + str.charAt(i);
            }
           // System.out.println(reverse);

            ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
            List<KeyboardRow> keyboard = new ArrayList<>();
            // Create a keyboard row
            KeyboardRow row = new KeyboardRow();
            // Set each button, you can also use KeyboardButton objects if you need something else than text
            row.add("Yes");
            row.add("No");
            keyboard.add(row);


           // MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&directConnection=true&ssl=false");


            //keyboard=[['Yes', 'No']], one_time_keyboard=True
           SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
            message.setChatId(update.getMessage().getChatId().toString());

            message.setText(update.getMessage().getText());

           // if((update.getMessage().getText())!=null)

            //IDEA!! Instead of typing "I am ok with answering a question about..." type in the actual bday instead, since that is being captured

            MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&directConnection=true&ssl=false");
            MongoClient mongoClient = new MongoClient(connectionString);
            MongoDatabase database = mongoClient.getDatabase("TelegramBot");
            MongoCollection<Document> collection = database.getCollection("users");
            long found = collection.count(Document.parse("{id : " + Integer.toString(Integer.parseInt(message.getChatId())) + "}"));
            if (found == 0) {
               System.out.println("test");
            } else {
                //System.out.println("User exists in database.");
                message.setText("Welcome back!");
            }


            if(update.getMessage().getText().equals("Hello"))
            {
                message.setText("Welcome to the elephant chat bot! Thank you for registering yourself! Do you want to take a short poll?");
                    keyboardMarkup.setKeyboard(keyboard);
                // Add it to the message
                //  message.setText("Hello");
                  message.setReplyMarkup(keyboardMarkup);
            }
            else
            {
                message.setText("Welcome to the elephant chat bot! Thank you for coming! To register, please type in 'Hello'");
            }

         /*   if(update.getMessage().getText().equals("I am ok with answering a question about my birthday"))
            {
               // message.setText("Thank you! Please enter your birthday in the format 'My birthday is on (month / date)'");
            }*/

           // if(update.getMessage().getText().contains("My birthday is on"))
            if(update.getMessage().getText().charAt(2) == '-')
            {
              //  message.setText("Thank you! Your birthday is on " + update.getMessage().getText().substring(18) + "! Please enter the command /color to move on to the next question.");
                  message.setText("Thank you! Your birthday is on " + update.getMessage().getText().substring(0) + "! Please enter the command /color to move on to the next question, and enter your favorite color.");
               // String vDb = update.getMessage().getText().substring(18);
                String vDb = update.getMessage().getText().substring(0);
                System.out.println(message.getChatId());
                int tcId = Integer.parseInt(message.getChatId());
                collection.updateOne(Filters.eq("id", tcId), Updates.set("birthday", vDb));
                //collection.find(Filters.eq("id", message.getChatId()));
               // String tcId = message.getChatId();

                //System.out.println(collection.find(eq("id", tcId)));
              // Document myDoc = collection.find(Filters.eq("first_name", "Vidhu")).first();
                Document myDoc2 = collection.find(Filters.eq("id", tcId)).first();
                System.out.println(myDoc2.toJson());
                //String vDb = update.getMessage().getText().substring(0);

                LocalDate myObj = LocalDate.now(); // Create a date object
                System.out.println(myObj);

                if(myObj.toString().substring(5).equals(vDb))
                {
                    message.setText("Happy Birthday!");
                }
            }

          /*  if(update.getMessage().getText().equals("I am ok with answering a question about my favorite color"))
            {
               // message.setText("Thank you! Please enter your favorite color in the format 'My favorite color is (color)'");
            }*/

          /*  if(update.getMessage().getText().contains("My favorite color is"))
            {
                message.setText("Thank you! Your favorite color is " + update.getMessage().getText().substring(21) + "! Please enter the command /food to move on to the next question.");
                String cDb = update.getMessage().getText().substring(21);
                System.out.println(message.getChatId());
                int cId = Integer.parseInt(message.getChatId());
                collection.updateOne(Filters.eq("id", cId), Updates.set("color", cDb));
                //collection.find(Filters.eq("id", message.getChatId()));
                // String tcId = message.getChatId();

                //System.out.println(collection.find(eq("id", tcId)));
                // Document myDoc = collection.find(Filters.eq("first_name", "Vidhu")).first();
                Document myDoc1 = collection.find(Filters.eq("id", cId)).first();
                System.out.println(myDoc1.toJson());
            } */

          /*  if(update.getMessage().getText().equals("I am ok with answering a question about my favorite food"))
            {
                //message.setText("Thank you! Please enter your favorite food in the format 'My favorite food is (food)'");
            }*/

           /* if(update.getMessage().getText().contains("My favorite food is"))
            {
                message.setText("Thank you! Your favorite food is " + update.getMessage().getText().substring(20) + "! Thank you so much for answering these questions! Please feel free to explore the bot now!");
                String fDb = update.getMessage().getText().substring(20);
                System.out.println(message.getChatId());
                int fId = Integer.parseInt(message.getChatId());
                collection.updateOne(Filters.eq("id", fId), Updates.set("food", fDb));
                //collection.find(Filters.eq("id", message.getChatId()));
                // String tcId = message.getChatId();

                //System.out.println(collection.find(eq("id", tcId)));
                // Document myDoc = collection.find(Filters.eq("first_name", "Vidhu")).first();
                Document myDoc3 = collection.find(Filters.eq("id", fId)).first();
                System.out.println(myDoc3.toJson());
            }
*/

           //  message.setText("Welcome to the elephant chat bot! Thank you for registering yourself! Do you want to take a short poll?");

        //    keyboardMarkup.setKeyboard(keyboard);
            // Add it to the message
          //  message.setText("Hello");
          //  message.setReplyMarkup(keyboardMarkup);


         /*   if(update.getMessage().getText().charAt(0) == '/')
            {
                if(update.getMessage().getText().equals("/birthday"))
                {
                        message.setText("Please enter your birthday");
                        String bday = update.getMessage().getText();
                        message.setText("Your birthday is on " + bday + "! Please enter the command /color");
                }
                if(update.getMessage().getText().equals("/color"))
                {
                    message.setText("Please enter your favorite color");
                }
                if(update.getMessage().getText().equals("/food"))
                {
                    message.setText("Please enter your favorite food");
                }
            }
            else if(update.getMessage().getText().charAt(0) != '/')
            {
                message.setText(update.getMessage().getText());
            } */

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
              //  message.setText("Thank you! Please enter your birthday in the format 'My birthday is on (month / date)'. An example is '04/20'.");
                message.setText("Thank you! Please enter your birthday in the format '(month - date)'. An example is '04-20'.");
                //message.setText("Before all of these questions, we will ask you if you are ok with answering them for privacy reasons. Are you ok with answering a question about your birthday? If so, please type 'I am ok with answering a question about my birthday'");
             /*   String user_birthday = update.getMessage().getText();
                MongoClient mongoClient = new MongoClient(connectionString);
                MongoDatabase database = mongoClient.getDatabase("TelegramBot");
                MongoCollection<Document> collection = database.getCollection("users");
                long user_id = update.getMessage().getChat().getId();
                long found = collection.count(Document.parse("{id : " + Integer.toString((int) user_id) + "}"));
                if (found == 0) {
                    Document doc = new Document("first_name", first_name)
                            .append("last_name", last_name)
                            .append("id", user_id)
                            .append("username", username)
                            .append("birthday", user_birthday);
                } */
                /*else
                {
                    db.users.doc( [
                            {
                                    $addFields: {"birthday": user_birthday }

                            }
                            ])
                }*/

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
                //message.setText("Are you ok with answering a question about your favorite color? If so, please type 'I am ok with answering a question about my favorite color'");
            }

            if (update.getMessage().getText().equals("Green"))
            {
                message.setText("You like the color green! So do I! Thank you for answering these questions. Have a great day!");
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



          /*  if(update.getMessage().getText().equals("/food"))
            {
                message.setText("Thank you! Please enter your favorite food in the format 'My favorite food is (food)'");
               // message.setText("Are you ok with answering a question about your favorite food? If so, please type 'I am ok with answering a question about my favorite food'");
            }*/



            //System.out.println(message.getText());

          //  String bDayText = "";

            // System.out.println(str);
           /* for (int x = 0; x < txt.length(); x++) {
                bDayText = bDayText + str.charAt(x);
            }
            // System.out.println(reverse); */
          /*  String bDayText = "";
            String ans = update.getMessage().getText();
            //if (update.getMessage().getText().equals("Yes")) {
            if (ans.equals("Yes")) {
                message.setText("You selected yes. Let's begin! When is your birthday?");
                bDayText = (update.getMessage()).getText();
                //System.out.println(bDayText);
                System.out.println(update.getMessage().getText());
                /*if (bDayText == "123")
                {
                    System.out.println("in the loop");
                    message.setText("Cool! Your birthday is on 123"  + "! Would you like to continue?");
                }

            }
            else if (ans.equals("No")) {
                message.setText("You selected no. Thank you, have a nice day!");
            }

            if (bDayText == "123")
            {
                System.out.println("in the loop");
                message.setText("Cool! Your birthday is on 123"  + "! Would you like to continue?");
            } */











            // message.setText(update.getMessage().getText());
          //  message.setText(reverse);
            System.out.println(message.getChatId());

            /*for(int j = 0; j < memberList.size(); j++)
            {


                if(message.getChatId().equals(memberList.get(j).getId()))
                {
                    message.setText("Hi " + memberList.get(j).getName());
                }
            }*/

        /*    if (update.getMessage().getText().equalsIgnoreCase("hello")) {
                for (int j = 0; j < memberList.size(); j++) {
                    if (message.getChatId().equals(memberList.get(j).getId())) {
                        message.setText("Hello to you too, " + memberList.get(j).getName());
                    }
                }
            }
            else
            {
                message.setText("Have a nice day!");
            }
*/


         /*   String id = new String();
            id = message.getChatId();

            ArrayList<String> chatId = new ArrayList<String>();
            chatId.add("Pandu - 1825052544");
            chatId.add("Amma - 1825777884");
            System.out.println(id);


            if(id == "1825052544")
            {
                message.setText("Hi Pandu!");
            }
            else if(id == "1825777884")
            {
                message.setText("Hi Amma!");
            }

            for(int i = 0; i < chatId.size(); i++)
            {
                System.out.println(chatId.get(i));


                if(chatId.get(i).contains(("Pandu - 1825052544")))
                {

                    System.out.println("In pandu loop");
                    message.setText("Hi Pandu!");
                    break;
                }

                if(chatId.get(i).contains(("Amma - 1825777884")))
                {
                    message.setText("Hi Amma!");
                    break;
                }

                break;

                if(chatId.get(i).contains(id))
                {
                    int j = chatId.get(i).indexOf(" ");
                    message.setText("Hi " + chatId.get(i).substring(0,j));
                }

            }*/
            String user_first_name = update.getMessage().getChat().getFirstName();
            String user_last_name = update.getMessage().getChat().getLastName();
            String user_username = update.getMessage().getChat().getUserName();
            long user_id = update.getMessage().getChat().getId();
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();


            try {
                execute(message); // Call method to send the message
                check(user_first_name, user_last_name, toIntExact(user_id), user_username);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }


    }
    }





