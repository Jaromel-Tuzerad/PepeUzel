package logic;

import java.util.ArrayList;

public class LogicResources {

    public static ArrayList<Table> getStepsToSolution(Table table) throws InvalidArrayException {
        ArrayList<Table> path = new ArrayList<>();
        path.addAll(recursiveSolve(table, null));
        return path;
    }

    public static boolean checkSolution(Table table) {
        ArrayList<Integer> solution = new ArrayList<>();
        for(int i = 1; i <= 8; i++) {
            solution.add(i);
        }
        solution.add(0);
        ArrayList<Integer> inputArray = table.getTable();
        return inputArray.equals(solution);
    }

    public static ArrayList<Table> recursiveSolve(Table table, Table.moveDirection lastDirection) throws InvalidArrayException {
        ArrayList<Table> output = new ArrayList<>();
        output.add(table);
        // If the solution is the last table in the input path, returns an empty arrayList
        if(checkSolution(table)) {
            return output;
        } else if(table.getDepth() >= 31) {
            return new ArrayList<>();
        } else {
            // Goes through the possible directions
            for(Table.moveDirection direction : table.getPossibleDirections(lastDirection)) {
                // Creates a new table with the same numbers as the current one
                Table newTable = table.cloneTable();
                // Moves the new table in the current direction
                newTable.move(direction);
                // Tries to find a solution for the new table
                ArrayList<Table> pathToBeAdded = recursiveSolve(newTable, direction);
                if(pathToBeAdded.size() > 0) {
                    // Goes through the pathToBeAdded, if it finds a table that's the same as the current one,
                    // returns an empty arrayList
                    for(Table t : pathToBeAdded) {
                        if(t.isEqualTo(table)) {
                            return new ArrayList<>();
                        }
                    }
                    if(checkSolution(pathToBeAdded.get(pathToBeAdded.size()-1))) {
                        output.addAll(pathToBeAdded);
                        return output;
                    } else {
                        return new ArrayList<>();
                    }
                }
            }
            return new ArrayList<>();
        }
    }
}
