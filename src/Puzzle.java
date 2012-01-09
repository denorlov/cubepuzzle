import java.util.Arrays;

public class Puzzle {
    enum Edge {
        TOP, LEFT, BOTTOM, RIGTH, FRONT, BACK
    };
    
    private static final int[][] combinations = new int[][] {
        {1,2,3,4, 5,6},
        {4,1,2,3, 5,6},
        {3,4,1,2, 5,6},
        {2,3,4,1, 5,6},            

        {4,3,2,1, 6,5},
        {1,4,3,2, 6,5},
        {2,1,4,3, 6,5},
        {3,2,1,4, 6,5},

        
        {2,5,4,6, 1,3},
        {6,2,5,4, 1,3},
        {4,6,2,5, 1,3},
        {5,4,6,2, 1,3},

        {2,6,4,5, 1,3},
        {5,2,6,4, 1,3},
        {4,5,2,6, 1,3},
        {6,4,5,2, 1,3},            


        {1,5,3,6, 2,4},
        {6,1,5,3, 2,4},
        {3,6,1,5, 2,4},
        {5,3,6,1, 2,4},

        {6,3,5,1, 4,2},
        {1,6,3,5, 4,2},
        {5,1,6,3, 4,2},
        {3,5,1,6, 4,2},
    };
    
    private static final int[][] cubes = new int[][] {
        {1,3,0,2, 2,0},
        {2,1,2,2, 3,0},
        {2,0,3,3, 1,1},
        {3,1,0,0, 1,2},
    };

    public static void main(String[] args) {
        int[] positionToCombination = new int[] {0, 0, 0, 0};
        combine(positionToCombination, 0);
    }

    private static void combine(int[] positionToCombination, int positionToCombine) {
        if(positionToCombine >= cubes.length) {
            if(check(positionToCombination)) {
                System.out.println("combination: " + Arrays.toString(positionToCombination));

                for(int combinationIdx : positionToCombination) {
                    int[] combination = combinations[combinationIdx];
                    System.out.print(Arrays.toString(combination) + " ->");

                    for(int edge : combination) {
                        System.out.print(" " + Edge.values()[edge-1]);
                    }

                    System.out.println();
                }
            }

            return;
        }

        //todo:rewrite as cycle
        for(int combination = 0; combination < combinations.length; combination++) {
            int[] newPositionToCombination = positionToCombination.clone();
            newPositionToCombination[positionToCombine] = combination;

            combine(newPositionToCombination, positionToCombine + 1);
        }
    }

    private static int[] valueCounts = new int[] {0, 0, 0, 0, 0, 0};
    
    private static boolean check(int[] positionToCombination) {
        for(int edge = 0 ; edge < Edge.values().length - 2; edge++) {
            Arrays.fill(valueCounts, 0);

            for(int cube = 0; cube < cubes.length; cube++) {
                int cubeEdgeValue = value(cube, edge, positionToCombination[cube]);
                int valueCount = valueCounts[cubeEdgeValue] + 1;
                if(valueCount > 1) {
                    return false;
                }
                valueCounts[cubeEdgeValue] = valueCount;
            }
        }
        
        return true;
    }
    
    private static int value(int cube, int edge, int combination) {
        int cubeEdge = combinations[combination][edge];
        return cubes[cube][cubeEdge - 1];
    }
}