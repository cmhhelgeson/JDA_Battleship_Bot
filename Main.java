import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.cache.CacheFlag;


import javax.security.auth.login.LoginException;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main extends ListenerAdapter {

    private static boolean debug = false;
    static String prefix = "!";

    public static void main(String[] args) throws LoginException, InterruptedException {
        String token = null;
        try {
            File token_file = Paths.get("Token.txt").toFile();
            /* String tf = "./resources/Token.txt"
            File token_file = tf.toFile();
            File token_file = tf.toFile(); */
            if (!token_file.exists()) {
                System.out.print("Enter token: ");
                Scanner scan = new Scanner(System.in);
                token = scan.nextLine();
                System.out.println();
                if (!token_file.createNewFile()) {
                    System.out.println("Could not create new file");
                    scan.close();
                    return;
                }
                Files.write(token_file.toPath(), token.getBytes());
                scan.close();
            }
            token = new String(Files.readAllBytes(token_file.toPath()));

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (token == null) {
            return;
        }

        /* Enables or disables events for the bot to keep track of
         * Settings: track messages, emojis, message reactions
         * TODO: Future, voice commands, put lists in function
         */
        List<GatewayIntent> gateway_intents = new ArrayList<>
                (Arrays.asList(GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.GUILD_EMOJIS,
                        GatewayIntent.GUILD_MESSAGE_REACTIONS));

        /* Disable cache flags for memory savings
        * CLIENT_STATUS: is client online
        * ACTIVITY: Current activity of user
        * */
        List<CacheFlag> disabled_flags = new ArrayList<>(
                Arrays.asList(CacheFlag.CLIENT_STATUS,
                        CacheFlag.ACTIVITY,
                        CacheFlag.MEMBER_OVERRIDES,
                        CacheFlag.VOICE_STATE)
        );

        JDA jda = JDABuilder.createLight(token, gateway_intents).
                addEventListeners(new CommandListener())
                .setActivity(Activity.playing("Navy Ship")).
                disableCache(disabled_flags).build();
        /* Fiddle with Console Thread later */
    }

   /* @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        User user = event.getUser();
        if (user.isBot()) {
            return;
        }
        //A guild is a server, not sure why I need it
        //TODO: GET Guild

        //Gets the reaction
        MessageReaction reaction = event.getReaction();
        TextChannel channel = event.getChannel();
        //gets the message from the channel's id
        //Do the nonsense polymars does

        switch(event.getReactionEmote().toString()) {
            case "RE:U+1f346":
            case "Re:U+1f609":
                channel.sendMessage("Go!").queue(response -> {
                    response.editMessageFormat("Go to horny jail %s!", user.getName()).queue();
                });
                break;
            case ":hey_bby:":
                channel.sendMessage("Hello there handsome!").queue();
                break;
            default:
                break;
        }
    } */

    //TODO: PASS IN GRID OF TILES AND DRAW BASED ON TILE STATUS */

    /* String draw_board() {
        String output = "This is where the board will be drawn\n";
        for (int i = 0; i < 10; i++) {
            String temp = ":regional_indicator_";
            char letter = 'a';
            letter += i;
            temp += letter + ":";
            output += temp;
        }
        output+= "\n";
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < 10; i++) {
                output += ":blue_square:";
            }
            output += "\n";
        }

        return output;
    } */


    /* @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Message msg = event.getMessage();
        if (msg.getContentRaw().equals("!play")) {
            MessageChannel channel = event.getChannel();
            channel.sendMessage("Welcome to Battleship!");
        }
        if (msg.getContentRaw().equals("!ping")) {
            MessageChannel channel = event.getChannel();
            long time = System.currentTimeMillis();
            channel.sendMessage("Pong!").queue(response -> {
                response.editMessageFormat("Pong: %d ms",
                        System.currentTimeMillis() - time).queue();
            });
        } else if (msg.getContentRaw().equals("!draw_board")) {
            MessageChannel channel = event.getChannel();
            String board = draw_board();
            channel.sendMessage(board).queue();

        }



    } */


}
