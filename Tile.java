public class Tile {
    final int EMPTY = 0;
    final int OCCUPIED = 1;
    final int DESTROYED = 2;
    final int CHECKED = 3;
    boolean selected = false;
    final String empty_sprite = ":blue_square:";
    final String occupied_sprite = ":white_circle:";
    final String destroyed_sprite = ":x:";
    final String checked_sprite = ":regional_indicator_x:";

    int tile_status = 0;

    public Tile(int _status) {
        this.tile_status = _status;
    }

    public void Set_Status(int _status) {
        this.tile_status = _status;
    }

    /* public Tile(String _sprite) {
        this.tile_status = 0;
        this.sprite = _sprite;
    }

    public Tile(int status, String _sprite) {
        this.tile_status = status;
        this.sprite = _sprite;
    }

    public void Set_Status(int status) {
        this.tile_status = status;
    }

    public void Set_Sprite(String _sprite) {
        this.sprite = _sprite;
    }

    public String toString() {
        String temp = "";
        switch(this.tile_status) {
            case 0: {
                temp = ":blue_square:";
            } break;
            case 1: {
                temp = ":black_circle:";
            } break;
            case 2: {
                temp = ":x:";
            } break;
            case 3: {
                temp = ":regional_indicator_x:";
            } break;

        }
        return temp;
    } */

    public String toString() {
        String result = "";
        if (tile_status == EMPTY) {
            result = empty_sprite;
        }
        if (tile_status == OCCUPIED) {
            result = occupied_sprite;
        }
        if (tile_status == DESTROYED) {
            result = destroyed_sprite;
        }
        if (tile_status == CHECKED) {
            result = checked_sprite;
        }
        return result;
    }

}
