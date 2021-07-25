import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/*public class Main {

    public static void main(String[] args) throws TelegramApiException {


        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

        try{
            telegramBotsApi.registerBot(new MyFirstBot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }
}*/

public class MyFirstBot{
    public static void main(String[] args) {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

    }
}
