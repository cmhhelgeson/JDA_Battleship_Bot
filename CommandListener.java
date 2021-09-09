import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;



public class CommandListener extends ListenerAdapter {

    //Use hashmaps to associate users with separate game instances
    //HashMap<User, Game> games = new HashMap<User, Game>();
    Game game = new Game();
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        //Bot will not respond to messages it has sent itself
        if (event.getAuthor().isBot()) {
            return;
        }
        //We separate the discord message into an array of strings, which each value
        //separated by spaces
        String[] d_args = event.getMessage().getContentRaw().split("\\s+");
        MessageChannel channel = event.getChannel();
        if (d_args[0].toLowerCase().equals("!play") && !game.active) {
            game.start(channel, event.getAuthor());
        } else if (d_args[0].toLowerCase().equals("!quit")) {
            channel.sendMessage("Goodbye!").queue();
            event.getJDA().shutdown();
            System.exit(0);
        } else {
            game.run(channel, event.getAuthor(), d_args);
        }
    }
}
