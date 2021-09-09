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
        if (g.Get_Tile_Status(char_pos, num) == g.OCCUPIED) {
            channel.sendMessage("Invalid input: Try again").queue();
        } else {
            g.Update_Tile(1, char_pos, num);
            g.setup_index++;
            return;
        }
    }

    public boolean Parse_User_Selection(String[] input, Grid g, int ship_size) {
        /* Assert valid starting position */
        char_pos = input[0].toLowerCase().charAt(0) - 'a';
        num = Character.getNumericValue(input[0].charAt(1)) - 1;
        if ((char_pos < 0 || char_pos > 9) || (num < 0 || num > 9)) {
            return false;
        }
        /* Check if the valid tile is occupied */
        if (g.Get_Tile_Status(char_pos, num) == g.OCCUPIED) {
            return false;
        }
        String direction = input[1].toLowerCase();
        /* Check if direction is valid. Basic checks are the same for each direction */
        if (direction.equals("right")) {
            /* Is final position out of bounds? */
            if (char_pos + (ship_size - 1) > 9) {
                return false;
            }
            /* Are any of the tiles along the length of the ship occupied? */
            for (int i = ship_size - 1; i >= 1; i--) {
                if (g.Get_Tile_Status(char_pos + i, num) == g.OCCUPIED) {
                    return false;
                }
            }
            /* Else, update all origin tile and other tiles along length of the ship */
            for (int i = 0; i <= ship_size - 1; i++) {
                g.Update_Tile(1, char_pos + i, num);
            }
            return true;

        } else if (direction.equals("left")) {
            if (char_pos - (ship_size - 1) < 0) {
                return false;
            }
            for (int i = ship_size - 1; i >= 1; i--) {
                if (g.Get_Tile_Status(char_pos - i, num) == g.OCCUPIED) {
                    return false;
                }
            }
            for (int i = 0; i <= ship_size - 1; i++) {
                g.Update_Tile(1, char_pos - i, num);
            }
            return true;

        } else if (direction.equals("up")) {
            if (num - (ship_size - 1) < 0) {
                return false;
            }
            for (int i = ship_size - 1; i >= 1; i--) {
                if (g.Get_Tile_Status(char_pos, num - i) == g.OCCUPIED) {
                    return false;
                }
            }
            for (int i = 0; i <= ship_size - 1; i++) {
                g.Update_Tile(1, char_pos, num - i);
            }
            return true;


        } else if (direction.equals("down")) {
            if (num + (ship_size - 1) > 9) {
                return false;
            }
            for (int i = ship_size - 1; i >= 1; i--) {
                if (g.Get_Tile_Status(char_pos, num + i) == g.OCCUPIED) {
                    return false;
                }
            }

            for (int i = 0; i <= ship_size - 1; i++) {
                g.Update_Tile(1, char_pos, num + i);
            }
            return true;
        } else {
            return false;
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
                return "Set your CARRIER's start position and direction (5 tiles)";
            }
            case 2: {
                return "Set your CARRIER's direction (5 tiles)";
            }
            case 3: {
                return "Set your BATTLESHIP's start position and direction (4 tiles)";
            }
            case 4: {
                return "Set your BATTLESHIP's direction (4 tiles)";
            }
            case 5: {
                return "Set your DESTROYER's start position and direction (3 tiles)";
            }
            case 6: {
                return "Set your DESTROYER's direction (3 tiles)";
            }
            case 7: {
                return "Set your SUBMARINE's start position and direction (3 tiles)";
            }
            case 8: {
                return "Set your SUBMARINE's direction (3 tiles)";
            }
            case 9: {
                return "Set your PATROL's start position and direction (2 tiles)";
            }
            case 10: {
                return "Set your PATROL's direction (2 tiles)";
            }

        }
        return " ";
    }

    public void Setup_Grid(MessageChannel channel, User u, String[] input, Grid cur_grid) {
        String output = "";
        if (input.length < 2 && cur_grid.setup_index != 0) {
            channel.sendMessage(Create_Error_String(0)).queue();
            return;
        }
        switch (cur_grid.setup_index) {
            case 0: {
                cur_grid.user = u;
                output = cur_grid.Draw_Board(u);
                channel.sendMessage(output).queue();
                channel.sendMessage(Create_Program_Request(1)).queue();
                cur_grid.setup_index++;
                break;
            }
            case 1: {
                if (Parse_User_Selection(input, cur_grid, 5)) {
                    output = cur_grid.Draw_Board(u);
                    channel.sendMessage(output).queue();
                    cur_grid.setup_index += 2;
                    channel.sendMessage(Create_Program_Request(3)).queue();
                } else {
                    output = cur_grid.Draw_Board(u);
                    channel.sendMessage(output).queue();
                    channel.sendMessage(Create_Error_String(0)).queue();
                    channel.sendMessage(Create_Program_Request(1)).queue();
                }
                break;
            }
            case 2: {
                Parse_Direction(channel, input[0].toLowerCase(), 5, cur_grid);
                output = cur_grid.Draw_Board(u);
                channel.sendMessage(output).queue();
                if (cur_grid.setup_index == 2) {
                    channel.sendMessage(Create_Program_Request(2)).queue();
                } else {
                    channel.sendMessage(Create_Program_Request(3)).queue();
                }
            } break;
            case 3: {
                if (Parse_User_Selection(input, cur_grid, 4)) {
                    output = cur_grid.Draw_Board(u);
                    channel.sendMessage(output).queue();
                    cur_grid.setup_index += 2;
                    channel.sendMessage(Create_Program_Request(5)).queue();
                } else {
                    output = cur_grid.Draw_Board(u);
                    channel.sendMessage(output).queue();
                    channel.sendMessage(Create_Error_String(0)).queue();
                    channel.sendMessage(Create_Program_Request(3)).queue();
                }
            } break;
            case 4: {
                Parse_Direction(channel, input[0], 4, cur_grid);
                output = cur_grid.Draw_Board(u);
                channel.sendMessage(output).queue();
                if (cur_grid.setup_index == 4) {
                    channel.sendMessage(Create_Program_Request(4)).queue();
                } else {
                    channel.sendMessage(Create_Program_Request(5)).queue();
                }
            } break;
            case 5: {
                if (Parse_User_Selection(input, cur_grid, 3)) {
                    output = cur_grid.Draw_Board(u);
                    channel.sendMessage(output).queue();
                    cur_grid.setup_index += 2;
                    channel.sendMessage(Create_Program_Request(7)).queue();
                } else {
                    output = cur_grid.Draw_Board(u);
                    channel.sendMessage(output).queue();
                    channel.sendMessage(Create_Error_String(0)).queue();
                    channel.sendMessage(Create_Program_Request(5)).queue();
                }
            } break;
            case 6: {
                Parse_Direction(channel, input[0], 3, cur_grid);
                output = cur_grid.Draw_Board(u);
                channel.sendMessage(output).queue();
                if (cur_grid.setup_index == 6) {
                    channel.sendMessage(Create_Program_Request(6)).queue();
                } else {
                    channel.sendMessage(Create_Program_Request(7)).queue();
                }

            } break;
            case 7: {
                if (Parse_User_Selection(input, cur_grid, 3)) {
                    output = cur_grid.Draw_Board(u);
                    channel.sendMessage(output).queue();
                    cur_grid.setup_index += 2;
                    channel.sendMessage(Create_Program_Request(9)).queue();
                } else {
                    output = cur_grid.Draw_Board(u);
                    channel.sendMessage(output).queue();
                    channel.sendMessage(Create_Error_String(0)).queue();
                    channel.sendMessage(Create_Program_Request(7)).queue();
                }
            } break;
            case 8: {
                Parse_Direction(channel, input[0], 3, cur_grid);
                output = cur_grid.Draw_Board(u);
                channel.sendMessage(output).queue();
                if (cur_grid.setup_index == 8) {
                    channel.sendMessage(Create_Program_Request(8)).queue();
                } else {
                    channel.sendMessage(Create_Program_Request(9)).queue();
                }

            } break;
            case 9: {
                if (Parse_User_Selection(input, cur_grid, 2)) {
                    output = cur_grid.Draw_Board(u);
                    channel.sendMessage(output).queue();
                    channel.sendMessage(u.getName() + " 's board has been created").queue();
                    if (turn == 2) {
                        channel.sendMessage("Both boards created. Let the game begin").queue();
                        channel.sendMessage("Player One's Turn").queue();
                        channel.sendMessage("Enter a valid Tile").queue();
                        channel.sendMessage(grid_one.Draw_Board(player_one)).queue();
                        channel.sendMessage(grid_two.Draw_Board_Hidden()).queue();
                        start = true;
                        turn = 1;
                    } else {
                        channel.sendMessage("Enter any message to continue").queue();
                        turn++;
                    }
                } else {
                    output = cur_grid.Draw_Board(u);
                    channel.sendMessage(output).queue();
                    channel.sendMessage(Create_Error_String(0)).queue();
                    channel.sendMessage(Create_Program_Request(9)).queue();
                }
            } break;
            case 10: {
                Parse_Direction(channel, input[0], 2, cur_grid);
                output = cur_grid.Draw_Board(u);
                channel.sendMessage(output).queue();
                if (cur_grid.setup_index == 10) {
                    channel.sendMessage(Create_Program_Request(10)).queue();
                } else {
                    channel.sendMessage(u.getName() + " 's board has been created").queue();
                    if (turn == 2) {
                        channel.sendMessage("Both boards created. Let the game begin").queue();
                        channel.sendMessage("Player One's Turn").queue();
                        channel.sendMessage("Enter a valid Tile").queue();
                        channel.sendMessage(grid_one.Draw_Board(player_one)).queue();
                        channel.sendMessage(grid_two.Draw_Board_Hidden()).queue();
                        start = true;
                        turn = 1;
                    } else {
                        turn++;
                    }
                }
            } break;
        }
    }

    public void Run_Turn(MessageChannel channel, int letter, int num, Grid player_grid, Grid enemy_grid) {
        String output = "";
        if (enemy_grid.Get_Tile_Status(letter, num) != enemy_grid.OCCUPIED) {
            enemy_grid.Update_Tile(enemy_grid.CHECKED, letter, num);
        } else {
            enemy_grid.Update_Tile(enemy_grid.DESTROYED, letter, num);
        }
        output = enemy_grid.Draw_Board(enemy_grid.user);
        channel.sendMessage(output).queue();
        output = "";
        output += "Enemy's board\n";
        output += player_grid.Draw_Board_Hidden();
        channel.sendMessage(output).queue();
    }

    public void run(MessageChannel channel, User u, String[] input) {
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
                if (input[0].length() < 2) {
                    channel.sendMessage("Invalid input. Try again").queue();
                    return;
                }
                char_pos = input[0].toLowerCase().charAt(0) - 'a';
                num = Character.getNumericValue(input[0].charAt(1)) - 1;
                if ((char_pos < 0 || char_pos > 9) || (num < 0 || num > 9)) {
                    channel.sendMessage("Invalid input: Try again").queue();
                    return;
                }
                Run_Turn(channel, char_pos, num, grid_one, grid_two);
                turn++;
            } else if (turn == 2) {
                char_pos = input[0].toLowerCase().charAt(0) - 'a';
                num = Character.getNumericValue(input[0].charAt(1)) - 1;
                if ((char_pos < 0 || char_pos > 9) || (num < 0 || num > 9)) {
                    channel.sendMessage("Invalid input: Try again").queue();
                    return;
                }
                if (grid_one.Get_Tile_Status(char_pos, num) != grid_one.OCCUPIED) {
                    grid_one.Update_Tile(grid_two.CHECKED, char_pos, num);
                } else {
                    grid_one.Update_Tile(grid_two.DESTROYED, char_pos, num);
                }
                Run_Turn(channel, char_pos, num, grid_two, grid_one);
                turn = 1;
            }

        }
    }
}
