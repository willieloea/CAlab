/**
 * The CA1 class allows you to create any 1-dimensional Cellular Automaton.
 * This means that you can set 
 *     the size of the 1-dimensional grid, 
 *     the neighbourhood of the cells,
 *     the states of the cells, and
 *     the rule of the automaton.
 * The grid or state of the CA is represented by a int array.
 * This is because the states of the cells are represented by ints.
 * 
 * @author Willie Loftie-Eaton
 */

public class CA1
{
    private int[] neighbourhood; // cells which determine the state in next time step
               // neighbourhood[0] = position of 1-st cell taken into account of rule
               // neighbourhood[1] = posiiton of 2-nd cell taken into account of rule
               //                 ---
               // neighbourhood[n] = posiiton of n-th cell taken into account of rule
    private int nrOfStates;      // the number of unique states a cell can have
    private int[] rule;          // rule in base-(nr_of_states)
               // rule[0] = 1-st digit in base-k number
               // rule[1] = 2-nd digit in base-k number
               //        ---
               // rule[r] = r-th digit in base-k number
    private int[] state;         // 1-Dimensional array of cells with states that
                                 // make up the state of the Cellular Automaton

    /**
     * Initlalizes a one dimensional cellular automaton, 
     * with the rule given as an array of states.
     * 
     * @param neighbourhood the positions, relative to the updated cell, of the cells
     *                      that will determine how a cell will be updated
     * @param nrOfStates the number of states a cell can have
     * @param rule the rule according to which the cells are updated
     * @param state the inital states of all the cells of the cellular automaton
     */
    public CA1(int[] neighbourhood, int nrOfStates, int[] rule, int[] state)
    {
        this.neighbourhood = neighbourhood;
        this.nrOfStates = nrOfStates;
        this.rule = rule;
        this.state = state;
    }

    /**
     * Initlalizes a one dimensional cellular automaton, with the rule given
     * as integer, which will be converted to an array of states.
     * 
     * @param neighbourhood the positions, relative to the updated cell, of the cells
     *                      that will determine how a cell will be updated
     * @param nrOfStates the number of states a cell can have
     * @param rule the rule according to which the cells are updated
     * @param state the inital states of all the cells of the cellular automaton
     */
    public CA1(int[] neighbourhood, int nrOfStates, int rule, int[] state)
    {
        this.setNeighbourhood(neighbourhood);
        this.setNrOfStates(nrOfStates);
        int[] ruleArr = this.decimalToRuleArr(rule);
        this.setRule(ruleArr);
        this.setState(state);
    }

    /**
     * Set the neighbourhood of the cellular automaton
     * neighbourhood[i] = plus-minus x = plus-minus x cells from the focus cell
     * plus = to the right
     * minus = to the left
     * 
     * @param neighbourhood the neighbourhood of the cellular automaton
     */
    public void setNeighbourhood(int[] neighbourhood)
    {
        this.neighbourhood = neighbourhood;
    }

    /**
     * Set the number of states of the cellular automaton
     * 
     * @param nrOfStates the number of states of the cellular automaton
     */
    public void setNrOfStates(int nrOfStates)
    {
        this.nrOfStates = nrOfStates;
    }

    /**
     * Set the rule of the cellular automaton
     * 
     * @param rule the rule of the cellular automaton
     */
    public void setRule(int[] rule)
    {
        this.rule = rule;
    }

    /**
     * Set the current state of the cellular automaton
     * 
     * @param state the current state of the cellular automaton
     */
    public void setState(int[] state)
    {
        this.state = state;
    }

    /**
     * Get the neighbourhood of the cellular automaton
     * 
     * @return the neighbourhood of the cellular automaton
     */
    public int[] getNeighbourhood()
    {
        return this.neighbourhood;
    }

    /**
     * Get the number of states of the cellular automaton
     * 
     * @return the number of states of the cellular automaton
     */
    public int getNumberOfStates()
    {
        return nrOfStates;
    }

    /**
     * Get the rule of the cellular automaton
     * 
     * @return the rule of the cellular automaton
     */
    public int[] getRule()
    {
        return this.rule;
    }

    /**
     * Get the current state of the cellular automaton
     * 
     * @return the current state of the cellular automaton
     */
    public int[] getState()
    {
        return this.state;
    }

    /**
     * Print the states of all the cells
     */
    public void printState()
    {
        for (int cell: state) {
            System.out.print(cell);
        }
    }

    /**
     * Print the states of all the cells, with every state represented
     * with the corresponding character in the printChars array
     * 
     * @param printChars the characters which should represent the states
     */
    public void printState(char[] printChars)
    {
        for (int cell: state) {
            System.out.print(printChars[cell]);
        }
    }

    /**
     * Converter from base-10 to base-(number of states), where the digits are stored in
     * an array of integers (the size of the number of states, to the power of the size
     * of the neighbourhood)
     * 
     * @param decimal the base 10 number to be converted
     * @return the decimal number in base-(number of states)
     */
    public int[] decimalToRuleArr(int decimal)
    {
        int nhSize = neighbourhood.length; // number of cells in the neighbourhood

        /* See that user is trying to convert a number that can be represented by a rule */
        if (decimal > (int) Math.pow(nhSize, Math.pow(nhSize, nrOfStates))) {
            // k^(n^n) = number of rules that can exist
            System.err.println("Your states and neighbourhood cannot represent " + decimal);
            System.exit(0);
        }

        /* Convert from decimal number to integer array representing the rule */
        int[] baseN = decimalToBaseN(decimal, nrOfStates); // array of states/numbers that represent the decimal number
        int baseNIndex = baseN.length - 1;          // index of this array, used to point from its end to its beginning
        int r = (int) Math.pow(nrOfStates, nhSize); // number of characters the base k number 
                                                    // used to represent the decimal number
                                                    // i.e. the number of characters in a rule, hence 'r'
        int[] ruleArr = new int[r--]; // rule array of states/numbers that represent the decimal number
        while (baseNIndex != -1) {
            // copy states of baseN array to rule array (ruleArr)
            ruleArr[r--] = baseN[baseNIndex--];
        }

        return ruleArr;
    }
    
    /**
     * Converter from base-10 to base-n, where the digits are stored in an 
     * array of integers (the size of the number of digits needed to represent 
     * the decimal number in base-n)
     * 
     * @param decimal the base 10 number to be converted
     * @param n the base to which the decimal number is converted
     * @return the decimal number in base-(number of states)
     */
    public static int[] decimalToBaseN(int decimal, int n)
    {   
        int digitCount = 1;                  // number of digits in base n required to represent the decimal number
        while ((int) Math.pow(n, digitCount) <= decimal) {
            digitCount++;
        }

        int[] baseN = new int[digitCount--]; // array of states/numbers that represent the decimal number in base n

        for (int i = 0; i < baseN.length; i++) {
            baseN[i] = decimal/((int) Math.pow(n, digitCount));
            decimal = decimal%((int) Math.pow(n, digitCount));
            digitCount--;
        }

        return baseN;
    }

    /**
     * 
     * @param nh
     * @param k
     * @return
     */
    public static int nhToDecimal(int[] nh, int nrOfStates)
    {
        // nh = states in the neighbourhood
        // nrOfStates = number of possible states a cell can have

        int decimal = 0;

        for (int i = 0; i < nh.length; i++) {
            decimal = decimal + (nh[i]*((int) Math.pow(nrOfStates, nh.length - 1 - i)));
        }

        return decimal;
    }

    /**
     * Print the rule of the cellular automaton in the base of its states
     */
    public void printRuleInStateBase()
    {
        for (int cell: rule) {
            System.out.print(cell);
        }
    }

    /**
     * Update the state of the cellular automaton
     */
    public void updateState()
    {
        int[] newState = new int[getState().length];    // the state after one time step

        // apply the rule to all cells in the current state
        int stateSize = getState().length;      // the size of the state array
        int nhSize = getNeighbourhood().length; // the size of the neighbourhood
        int[] currNhStates = new int[nhSize];   // the states of the cells of the current neighbourhood
        for (int i = 0; i < stateSize; i++) {
            // get neighbourhood cells in state
            for (int nhCell = 0; nhCell < nhSize; nhCell++) {
                int nhCellState = (i+neighbourhood[nhCell])%stateSize;
                // give the correct positive number if it is negative at the moment
                if (nhCellState < 0) {
                    nhCellState = stateSize + nhCellState;
                }
                currNhStates[nhCell] = state[nhCellState];
            }

            // find resulting cell state of current neighborhood state
            newState[i] = rule[nhToDecimal(currNhStates, nrOfStates)];
        }

        this.setState(newState);
    }

    /*
    public static void main(String[] args)
    {
        char[] printChars = {' ', '#', '@'};

        int[] neighbourhood = {-1, 0, 1};
        int nrOfStates = 3;
        int nrOfRules = (int) Math.pow(nrOfStates, Math.pow(nrOfStates, neighbourhood.length));
        int[] state= new int[145];
        state[72] = 1;
        state[73] = 2;
        // state[73] = 1;
        state[74] = 1;
        for (int rule = 0; rule < nrOfRules; rule++) {
            System.out.println("Rule: " + rule);
            CA1 test = new CA1(neighbourhood, nrOfStates, rule, state);
            for (int gen = 0; gen < 20; gen++) {
                test.printState(printChars);
                System.out.println();
                test.updateState();
            }
            System.out.println();
        }
    }
    */
    
    public static void main(String[] args)
    {
        int[] neighbourhood = {-1, 0, 1};
        int nrOfStates = 2;
        int[] ruleA = {0, 1, 1, 1, 1, 0, 0, 0};
        int[] state= new int[145];
        state[73] = 1;
     
        CA1 rule_30 = new CA1(neighbourhood, nrOfStates, ruleA, state);
        char[] printChars = {' ', '#'};
        for (int generation = 0; generation < 73; generation++) {
            rule_30.printState(printChars);
            System.out.println();
            rule_30.updateState();
        }
    }
}