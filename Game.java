import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

public class Game {
    boolean active = false;
    User player_one = null;
    User player_two = null;
    int turn = 0;
    boolean start = false;

    Grid grid_one = null;
    Grid grid_two = null;
    int char_pos = -1;
    int num = -1;

   /* public void New_Game(MessageChannel channel) {
        if (!active) {
            channel.sendMessage("Welcome to Battleship!").queue();
            channel.sendMessage("Enter your usernames below: ").queue();
        }
    } */

    public void start(MessageChannel channel, User u) {
        if (active) {
            channel.sendMessage("Game already initialized").queue();
        }
        if (player_one == null) {
            player_one = u;
            String s = u.getName() + " has entered the game!";
            channel.sendMessage(s).queue();
        } else if (player_two == null) {
            active = true;
            player_two = u;
            turn = 1;
            String s = u.getName() + " has entered the game!";
            channel.sendMessage(s).queue();
            grid_one = new Grid();
            grid_two = new Grid();
            channel.sendMessage("Let the game begin! Enter any message to start.").queue();
        }
    }

    public void Parse_Position(MessageChannel channel, String input, Grid g) {
        char_pos = input.charAt(0) - 'a';
        num = Character.getNumericValue(input.charAt(1)) - 1;
        if ((char_pos < 0 || char_pos > 9) || (num < 0 || num > 9)) {
            channel.sendMessage("Invalid input: Try again").queue();
            return;
        }
        if (g.Get_Tile_Status(char_pos, num) != g.OCCUPIED) {
            g.Update_Tile(1, char_pos, num);
            g.setup_index++;
        } else {
            channel.sendMessage("Invalid input: Try again").queue();
            return;
        }
    }

    public void Parse_Direction(MessageChannel channel, String input, int ship_size, Grid g) {
        if (input.equals("right")) {
            if (char_pos + (ship_size - 1) > 9) {
                channel.sendMessage(Create_Error_String(0)).queue();
                return;
            } else {
                for (int i = 1; i <= ship_size - 1; i++) {
                    g.Update_Tile(1, char_pos + i, num);
                }
                g.setup_index++;
            }
        } else if (input.equals("left")) {
            if (char_pos - (ship_size - 1) < 0) {
                channel.sendMessage(Create_Error_String(0)).queue();
            } else {
                for (int i = 1; i <= ship_size - 1; i++) {
                    g.Update_Tile(1, char_pos - i, num);
                }
                g.setup_index++;
            }

        } else if (input.equals("up")) {
            if (num - (ship_size - 1) < 0) {
                channel.sendMessage(Create_Error_String(0)).queue();
            } else {
                for (int i = 1; i <= ship_size - 1; i++) {
                    g.Update_Tile(1, char_pos, num - i);
                }
                g.setup_index++;
            }
        } else if (input.equals("down")) {
            if (num + (ship_size - 1) > 9) {
                channel.sendMessage(Create_Error_String(0)).queue();
            } else {
                for (int i = 1; i <= ship_size - 1; i++) {
                    g.Update_Tile(1, char_pos, num + i);
                }
                g.setup_index++;
            }
        } else {
            channel.sendMessage("Invalid Input. Try again").queue();
        }
    }

    public String Create_Error_String(int error) {
        switch(error) {
            case 0: {
                return "Invalid Input. Try Again.";
            }
            default: {
                return "Undefined error";
            }
        }
    }

    public String Create_Program_Request(int request) {
        switch (request) {
            case 1: {
                return "Set your CARRIER's start position (5 tiles)";
            }
            case 2: {
                return "Set your CARRIER's direction (5 tiles)";
            }
            case 3: {
                return "Set your BATTLESHIP's start position (4 tiles)";
            }
            case 4: {
                return "Set your BATTLESHIP's direction (4 tiles)";
            }
            case 5: {
                return "Set your DESTROYER's start position (3 tiles)";
            }
            case 6: {
                return "Set your DESTROYER's direction (3 tiles)";
            }
            case 7: {
                return "Set your SUBMARINE's start position (3 tiles)";
            }
            case 8: {
                return "Set your SUBMARINE's direction (3 tiles)";
            }
            case 9: {
                return "Set your PATROL's start position (2 tiles)";
            }
            case 10: {
                return "Set your PATROL's direction (2 tiles)";
            }

        }
        return " ";
    }

    public void Setup_Grid(MessageChannel channel, User u, String input, Grid cur_grid) {
        String output = "";
        switch (cur_grid.setup_index) {
            case 0: {
                output = cur_grid.Draw_Board(u);
                channel.sendMessage(output).queue();
                channel.sendMessage(Create_Program_Request(1)).queue();
                cur_grid.setup_index++;
                break;
            }
            case 1: {
                Parse_Position(channel, input, cur_grid);
                output = cur_grid.Draw_Board(u);
                channel.sendMessage(output).queue();
                if (cur_grid.setup_index == 1) {
                    channel.sendMessage(Create_Program_Request(1)).queue();
                } else {
                    channel.sendMessage(Create_Program_Request(2)).queue();
                }

                break;
            }
            case 2: {
                Parse_Direction(channel, input, 5, cur_grid);
                output = cur_grid.Draw_Board(u);
                channel.sendMessage(output).queue();
                if (cur_grid.setup_index == 2) {
                    channel.sendMessage(Create_Program_Request(2)).queue();
                } else {
                    channel.sendMessage(Create_Program_Request(3)).queue();
                }
            } break;
            case 3: {
                Parse_Position(channel, input, cur_grid);
                output = cur_grid.Draw_Board(u);
                channel.sendMessage(output).queue();
                if (cur_grid.setup_index == 3) {
                    channel.sendMessage(Create_Program_Request(3)).queue();
                } else {
                    channel.sendMessage(Create_Program_Request(4)).queue();
                }

            } break;
            case 4: {
                Parse_Direction(channel, input, 4, cur_grid);
                output = cur_grid.Draw_Board(u);
                channel.sendMessage(output).queue();
                if (cur_grid.setup_index == 4) {
                    channel.sendMessage(Create_Program_Request(4)).queue();
                } else {
                    channel.sendMessage(Create_Program_Request(5)).queue();
                }
            } break;
            case 5: {
                Parse_Position(channel, input, cur_grid);
                output = cur_grid.Draw_Board(u);
                channel.sendMessage(output).queue();
                if (cur_grid.setup_index == 5) {
                    channel.sendMessage(Create_Program_Request(5)).queue();
                } else {
                    channel.sendMessage(Create_Program_Request(6)).queue();
                }
            } break;
            case 6: {
                Parse_Direction(channel, input, 3, cur_grid);
                output = cur_grid.Draw_Board(u);
                channel.sendMessage(output).queue();
                if (cur_grid.setup_index == 6) {
                    channel.sendMessage(Create_Program_Request(6)).queue();
                } else {
                    channel.sendMessage(Create_Program_Request(7)).queue();
                }

            } break;
            case 7: {
                Parse_Position(channel, input, cur_grid);
                output = cur_grid.Draw_Board(u);
                channel.sendMessage(output).queue();
                if (cur_grid.setup_index == 7) {
                    channel.sendMessage(Create_Program_Request(7)).queue();
                } else {
                    channel.sendMessage(Create_Program_Request(8)).queue();
                }
            } break;
            case 8: {
                Parse_Direction(channel, input, 3, cur_grid);
                output = cur_grid.Draw_Board(u);
                channel.sendMessage(output).queue();
                if (cur_grid.setup_index == 8) {
                    channel.sendMessage(Create_Program_Request(8)).queue();
                } else {
                    channel.sendMessage(Create_Program_Request(9)).queue();
                }

            } break;
            case 9: {
                Parse_Position(channel, input, cur_grid);
                output = cur_grid.Draw_Board(u);
                channel.sendMessage(output).queue();
                if (cur_grid.setup_index == 9) {
                    channel.sendMessage(Create_Program_Request(9)).queue();
                } else {
                    channel.sendMessage(Create_Program_Request(10)).queue();
                }

            } break;
            case 10: {
                Parse_Direction(channel, input, 2, cur_grid);
                output = cur_grid.Draw_Board(u);
                channel.sendMessage(output).queue();
                if (cur_grid.setup_index == 10) {
                    channel.sendMessage(Create_Program_Request(10)).queue();
                } else {
                    channel.sendMessage(u.getName() + " 's board has been created").queue();
                    if (turn == 2) {
                        channel.sendMessage("Both boards created. Let the game begin").queue();
                        start = true;
                        turn = 1;
                    } else {
                        turn++;
                    }

                }
            } break;
        }
    }

    public void run(MessageChannel channel, User u, String input) {
        String output = "";
        //Users setup their boards
        if (!start) {
            if (turn == 1) {
                Setup_Grid(channel, u, input, grid_one);
            } else if (turn == 2) {
                Setup_Grid(channel, u, input, grid_two);
            } else if (turn == 3){
                start = true;
                turn = 1;
            }
        } else {
            if (turn == 1) {
                output = "";
                output = grid_one.Draw_Board(u);
                channel.sendMessage(output).queue();
                output = "";
                output += "Enemy's board\n";
                output += grid_two.Draw_Board_Hidden();
                channel.sendMessage(output).queue();
                char_pos = input.charAt(0) - 'a';
                num = Character.getNumericValue(input.charAt(1)) - 1;
                if ((char_pos < 0 || char_pos > 9) || (num < 0 || num > 9)) {
                    channel.sendMessage("Invalid input: Try again").queue();
                    return;
                }
                if (grid_two.Get_Tile_Status(char_pos, num) != grid_two.OCCUPIED) {
                    grid_two.Update_Tile(grid_two.CHECKED, char_pos, num);
                } else {
                    grid_two.Update_Tile(grid_two.DESTROYED, char_pos, num);
                }
                turn++;
            } else if (turn == 2) {
                output = "";
                output = grid_two.Draw_Board(u);
                channel.sendMessage(output).queue();
                output = "";
                output += "Enemy's board\n";
                output += grid_one.Draw_Board_Hidden();
                channel.sendMessage(output).queue();
                char_pos = input.charAt(0) - 'a';
                num = Character.getNumericValue(input.charAt(1)) - 1;
                if ((char_pos < 0 || char_pos > 9) || (num < 0 || num > 9)) {
                    channel.sendMessage("Invalid input: Try again").queue();
                    return;
                }
                if (grid_one.Get_Tile_Status(char_pos, num) != grid_one.OCCUPIED) {
                    grid_one.Update_Tile(grid_two.CHECKED, char_pos, num);
                } else {
                    grid_one.Update_Tile(grid_two.DESTROYED, char_pos, num);
                }
                channel.sendMessage(output).queue();
                turn = 1;
            }

        }
    }
}
