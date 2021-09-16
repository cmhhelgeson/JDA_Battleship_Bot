import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
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

    public static boolean debug = false;
    static String prefix = "!";

    private static ShardManager shard_manager;

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


        DefaultShardManagerBuilder builder =
                DefaultShardManagerBuilder.create(token, gateway_intents);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.playing("@Navy for info!"));
        builder.addEventListeners(new CommandListener());
        builder.disableCache(disabled_flags);
        shard_manager = builder.build();
        /* JDA jda = JDABuilder.createLight(token, gateway_intents).
                addEventListeners(new CommandListener())
                .setActivity(Activity.playing("Navy Ship")).
                disableCache(disabled_flags).build(); */

        /* Fiddle with Console Thread later */

        Thread ConsoleThread = new Thread(() -> {
            Scanner scan = new Scanner(System.in);
            while (scan.hasNextLine()) {
                Process_Command(scan.nextLine());
            }
        });
        ConsoleThread.setDaemon(true);
        ConsoleThread.setName("Console Thread");
        ConsoleThread.start();
    }

    private static void Process_Command(String cmd) {
        if (cmd.equals("debug")) {
            debug = !debug;
            String response = debug ? "on" : "off";
            System.out.println("[INFO] Debug mode has been turned" + response);
        }
    }
}
