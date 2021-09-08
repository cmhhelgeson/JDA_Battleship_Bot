import net.dv8tion.jda.api.entities.User;

import java.util.Locale;

public class Grid {
    /* Board Dimensions */
    final int BOARD_WIDTH = 10;
    final int BOARD_HEIGHT = 10;
    final int BORDER = 1;
    /* Carrier Dimensions */
    final int CARRIER_SIZE = 5;
    final int BATTLESHIP_SIZE = 4;
    final int DESTROYER_SIZE = 3;
    final int SUB_SIZE = 3;
    final int PATROL_SIZE = 2;
    /* Tile types */
    final int EMPTY = 0;
    final int OCCUPIED = 1;
    final int DESTROYED = 2;
    final int CHECKED = 3;
    /* Tile sprites */
    final String empty_sprite = ":blue_square:";
    final String occupied_sprite = ":white_circle:";
    final String destroyed_sprite = ":x:";
    final String checked_sprite = ":regional_indicator_x:";

    Tile[][] grid;
    String player_emote;
    int setup_index = 0;

    public Grid() {
        this.player_emote = ":flushed:";
        //According to the Internet, java arrays are row first, column second?
        //The number of rows is determined by the height of the board
        grid = new Tile[BOARD_WIDTH][BOARD_HEIGHT];
        //Should probably switch index values of these for loops
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                grid[j][i] = new Tile(EMPTY);
            }
        }
        setup_index = 0;
        /*for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                grid[i][j].Set_Status(0);
                grid[i][j].Set_Sprite(":blue_square:");
            }
        } */
    }

    public void Update_Tile(int status, int column, int row) {
        grid[column][row].tile_status = status;
    }

    public int Get_Tile_Status(int column, int row) {
        return grid[column][row].tile_status;
    }

    public String Draw_Board(User user) {
        String output = "";
        /* Draw letters. */
        output += user.getName() + "'s Board\n";
        output += "||\n";
        for (int i = 0; i < 10; i++) {
            String temp = ":regional_indicator_";
            char letter = 'a';
            letter += i;
            temp += letter + ":";
            output += temp;
        }
        output += "\n";
        /* Draws Board Tiles */
        for (int i = 0; i < BOARD_HEIGHT; i++) {

            for (int j = 0; j < BOARD_WIDTH; j++) {
                output += grid[j][i].toString();
            }
            output += "\n";

        }
        output += "||\n";
        return output;
    }

    public String Draw_Board_Hidden() {
        String output = "";
        /* Draw letters. */
        output += "||\n";
        for (int i = 0; i < 10; i++) {
            String temp = ":regional_indicator_";
            char letter = 'a';
            letter += i;
            temp += letter + ":";
            output += temp;
        }
        output += "\n";
        /* Draws Board Tiles */
        for (int i = 0; i < BOARD_HEIGHT; i++) {

            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (grid[j][i].tile_status != OCCUPIED) {
                    output += grid[j][i].toString();
                } else {
                    output += empty_sprite;
                }
            }
            output += "\n";

        }
        output += "||\n";
        return output;
    }


    /* public Grid(String emote) {
        this.player_emote = emote;
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                tiles[i][j] = new Tile(0, ":blue_square:");
            }
        }
    }



    public void Update_Grid(String instruction) {
        instruction.substring(0, 2);
        instruction = instruction.toUpperCase();
        int letter_column = instruction.charAt(0) - 41;
        if (letter_column < 0 || letter_column > 9) {
            //do something
        }
        int number_row = instruction.charAt(1) - '0';
        if (number_row < 0 || number_row > 9) {
            //do something
        }
        if (tiles[number_row][letter_column].tile_status == EMPTY) {
            tiles[number_row][letter_column].Set_Status(CHECKED);
            tiles[number_row][letter_column].Set_Sprite(checked_sprite);
        } else if (tiles[number_row][letter_column].tile_status == OCCUPIED) {
            tiles[number_row][letter_column].Set_Status(DESTROYED);
            tiles[number_row][letter_column].Set_Sprite(destroyed_sprite);
        }
    }

    public void Place_Ship(Player p) {

    }

    public String Draw_Grid() {
        String output = "This is where the board will be drawn\n";
        for (int i = 0; i < 10; i++) {
            String temp = ":regional_indicator_";
            char letter = 'a';
            letter += i;
            temp += letter + ":";
            output += temp;
        }
        output+= "\n";
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                output += tiles[i][j].sprite;
            }
            output += "\n";
        }
        return output;
    } */






}
