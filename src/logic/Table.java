package logic;

import java.util.ArrayList;

public class Table {

    public enum moveDirection {
        UP,
        RIGHT,
        DOWN,
        LEFT
    }

    private ArrayList<Integer> table;
    private int depth;

    public Table(ArrayList<Integer> inputTable, int depth) throws InvalidArrayException {
        if(this.isValid(inputTable)) {
            this.table = (ArrayList<Integer>) inputTable.clone();
            this.depth = depth;
        } else {
            throw new InvalidArrayException("The input array does not contain the required numbers");
        }

    }

    public boolean isValid(ArrayList<Integer> input) {
        if(input.size() == 9 && input.contains(0) && input.contains(1) && input.contains(2) && input.contains(3) && input.contains(4) && input.contains(5) && input.contains(6) && input.contains(7) && input.contains(8)) {
            return true;
        } else {
            return false;
        }
    }

    public int getSpaceIndex() throws InvalidArrayException {
        for(int i = 0; i < 9; i++) {
            if(this.table.get(i) == 0) {
                return i;
            }
        }
        throw new InvalidArrayException("There is no space (0) in the input array");
    }

    public ArrayList<moveDirection> getPossibleDirections(moveDirection lastDirection) throws InvalidArrayException {
        ArrayList<moveDirection> output = new ArrayList<>();
        int spaceIndex = getSpaceIndex();

        if(spaceIndex % 3 == 1) {
            if(lastDirection != moveDirection.RIGHT) {
                output.add(moveDirection.LEFT);
            }
            if(lastDirection != moveDirection.LEFT) {
                output.add(moveDirection.RIGHT);
            }
        } else {
            if(spaceIndex % 3 == 0) {
                output.add(moveDirection.LEFT);
            } else {
                if (spaceIndex % 3 == 2) {
                    output.add(moveDirection.RIGHT);
                } else {
                    throw new InvalidArrayException("The input array does not contain the required numbers");
                }
            }
        }

        if(spaceIndex <= 5 && lastDirection != moveDirection.DOWN) {
            output.add(moveDirection.UP);
        }

        if(spaceIndex >= 3 && lastDirection != moveDirection.UP) {
            output.add(moveDirection.DOWN);
        }

        return output;
    }

    public void move(moveDirection direction) throws InvalidArrayException {
        int spaceIndex = getSpaceIndex();
        switch(direction) {
            case UP:
                table.set(spaceIndex, this.table.get(spaceIndex + 3));
                table.set(spaceIndex+3, 0);
                break;
            case RIGHT:
                table.set(spaceIndex, this.table.get(spaceIndex - 1));
                table.set(spaceIndex-1, 0);
                break;
            case DOWN:
                table.set(spaceIndex, this.table.get(spaceIndex - 3));
                table.set(spaceIndex-3, 0);
                break;
            case LEFT:
                table.set(spaceIndex, this.table.get(spaceIndex + 1));
                table.set(spaceIndex+1, 0);
                break;
        }
        this.depth++;
    }

    public Table cloneTable() throws InvalidArrayException {
        return new Table(this.table, this.depth);
    }

    public ArrayList<Integer> getTable() {
        return table;
    }

    public int getDepth() {
        return depth;
    }

    public boolean isEqualTo(Table inputTable) {
        for(int i = 0; i < inputTable.getTable().size(); i++) {
            if(!this.table.get(i).equals(inputTable.getTable().get(i))) {
                return false;
            }
        }
        return true;
    }

}
