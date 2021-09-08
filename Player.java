public class Player {
    int row = 0;
    int column = 0;
    Grid current_grid;

    public Player(int x, int y, Grid _grid) {
        this.row = x;
        this.column = y;
        this.current_grid = _grid;
    }

    public int GetRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean moveUp() {
        if (row == 0) {
            return false;
        } else {
            row -= 1;
        } return true;
    }

    public boolean moveLeft() {
        if (column == 0) {
            return false;
        } else {
            column -= 1;
        }
        return true;
    }

    public boolean moveRight() {
        if (column == 9) {
            return false;
        } else {
            column += 1;
        } return true;
    }
}
