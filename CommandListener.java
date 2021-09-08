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

        if (d_args[0].toLowerCase().equals("!play") && !game.active) {
            MessageChannel channel = event.getChannel();
            game.start(channel, event.getAuthor());
        } else {
            MessageChannel channel = event.getChannel();
            game.run(channel, event.getAuthor(), d_args);
        }
        /* if (d_args[0].toLowerCase().equals("!ping")) {
            MessageChannel channel = event.getChannel();
            long time = System.currentTimeMillis();
            channel.sendMessage("Pong!").queue(response -> {
                response.editMessageFormat("Pong: %d ms",
                        System.currentTimeMillis() - time).queue();
            });
        } else if (d_args[0].toLowerCase().equals("!draw_board")) {
            MessageChannel channel = event.getChannel();
            String board = "Yolo";
            channel.sendMessage(board).queue();
            channel.sendMessage(event.getAuthor().getName().toString()).queue();

        } */



    }
}
