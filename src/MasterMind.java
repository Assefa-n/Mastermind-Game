
/********************************************************************
 * Programmer:    Naga Assefa
 * Class:  CS40S
 *
 * Assignment: Mastermind Game
 *
 * Description: This is a program made to simulate the game mastermind to use the ablities of the 2D array.
 ***********************************************************************/

// import java libraries here as needed

        import java.util.Random;
        import java.text.DecimalFormat;
        import java.io.*;
        import java.util.Scanner;
        import java.awt.*;
        import javax.swing.*;

public class MasterMind {  // begin class

    public static void main(String[] args)throws IOException {  // begin main

        // ********* declaration of constants **********
        final int DIFF = 1;          // the diffrence for for-loops
        final char WIN = 'W';        // when the player guesses the right number in the right position or wins the game
        final char LOST = 'L';       // when the player guesses the wrong number or runs out of tries
        final char ALMOST = 'A';     // when the player guesses the right number int the wrong position
        final int FIRST = 0;        // First value in array
        final int RESTART = 0;      // initializeintizing values
        final int COUNTER = 1;      // the counter/increases in a value
        // ********** declaration of variables **********

        String strin;        // string data input from keyboard
        String strout;        // processed info string to be output
        String bannerOut;        // string to print banner to message dialogs

        String prompt;        // prompt for use in input dialogs

        String delim = "[ , ]+";    // delimiter string for splitting input string
        String[] tokens = null;     // string array for gathering input

        // The value of the row and table of the game
            int ball  = 5;         // the amount of coloured ball
            int colm = 11;         // the amount of chances the player has
            String values = "";     // the values of the ball and the number of tries a person gets

        // Game Board
            String nl = System.lineSeparator(); // new line character for file writing

        // the solution numbers
            int[] solution = new int [ball];    // solution to the game
            Random rand = new Random();         // The random variable for the solution of the table

        // gameplay variables
            Scanner scan  = new Scanner(System.in);
            String guess = "";
            char stop;                              // the exit function for the while loop running the game
            char [] tries = new char[ball];                           // The exit function for Winning the game
            int Al = 0;                         // checking if the number gussed is in the solution but in the wrong spot
        // stats
            int numGus = 0;                 // The number of gusses it to took to get the right answer
            int highScore = 0;              // The high score for the amount of tries it took to guess the solution
            double avg= 0;      // the average of all the guesses taken by the player
            int count= 0;                   // Counter for the amount of games played
            int sum = 0;                    // sum of the numbers gussesd for the average
            int winsInRow = 0;              // the number of gusses correct the player gusses\
            int SuWnInR= 0 ;                // sustains the maximum wins in a row
        // ***** create objects *******

            //ConsoleReader console = new ConsoleReader(System.in);
            //DecimalFormat df1 = new DecimalFormat("##.###");
            Scanner scanner = new Scanner(System.in);
            //BufferedReader fin = new BufferedReader(new FileReader("demo1Data.txt"));
            //PrintWriter fout = new PrintWriter(new BufferedWriter(new FileWriter("outFile.txt")));

        // declare and initialize a 2D array
            int[][] master = new int [colm][ball];// the game board
            char[] repeats = new char [ball];      // finds if there are any numbers in the solution from the guess
        // ********** Print output Banner **********
        
        printBanner();      // call printBanner method
        //fileBanner(fout);       // call fileBanner method
        bannerOut = windowBanner();

        // ************************ get input **********************
           //Setting the column and the number of balls
              System.out.println("Set the Chances and the number of balls\ntype the column,then the # of balls\n Eg. 11,4");
              values = scanner.next();
              tokens = values.split(delim);
              colm = Integer.parseInt(tokens[0]);
              ball = Integer.parseInt(tokens[1]);
              // initialize the values that require the colm and ball
                    solution = new int [ball];      // solution to the game
                    rand = new Random();            // The random variable for the solution of the table
                    tries = new char[ball];         // The exit function for Winning the game
                    repeats = new char [ball];      // finds if there are any numbers in the solution from the guess
                    master = new int [colm][ball];  // the game board
                    int []skip = new int [ball];      // skips the
        // ************************ processing ***************************

         // Prompt
            prompt = nl+"To play guess a four digit code between 1-"+ ball+ nl;
            prompt+=  "If you don't want to play press n" + nl;
            System.out.println(prompt);
        // print table
            PrintTable(master);
        // control variable
            guess = scan.next();
            stop = guess.charAt(0);

            while(Character.toLowerCase(stop) != 'n'){
                // initialize variables
                   solution = getSolution(solution,rand);    // The solution to the game
                   LoadTable(master, RESTART);

                for (int i = FIRST; i< colm; i++){
                    numGus = i+DIFF;   // the number of guesses it took to win

                    // load the variables and check if the player needs to try again
                       checkGuess(master,solution, tokens,guess, delim, tries, WIN, LOST, i);

                    // get Result of what the user got right and wrong and almost got
                        getResult(master[i], solution,repeats,ALMOST, WIN, LOST);

                    // print table
                        PrintTable(master);

                    // Determining if the player won or lost
                        tries = getResult(tries);

                        if (tries[FIRST] == WIN) {
                            i = colm;
                            System.out.println(nl+"          "+"Winner, Winner Chicken Dinner");
                            System.out.println("Guesses: "+ numGus);
                        }// end if
                        else if(tries[FIRST] != WIN && i!= colm-DIFF){
                            System.out.println("try again");
                            guess = scan.next();
                            stop = guess.charAt(0);
                            } //end else
                        else{
                        i= colm;
                            System.out.format("%s %s %10s",nl,nl, "You Lost"+nl);
                            System.out.print("Solution: ");
                            PrintArray(solution);
                        }// end else ... // loosing statment
                }// end for

                // wins in a row
                if(tries[FIRST] == WIN){
                    winsInRow ++;
                }// end if
                else {
                    winsInRow = RESTART;
                }// end else
                    if(SuWnInR < winsInRow){
                        SuWnInR = winsInRow;
                    }// end if
                // average
                    count++;
                    sum+=numGus;
                // high score
                    highScore = GameStats(highScore, numGus);
                    numGus = 0;
                // update control variable
                    System.out.println(prompt);
                    guess = scan.next();
                    stop = guess.charAt(0);
          }// end while
          // stats
            // Wins in Row
                System.out.println("Wins in Row: "+ SuWnInR);
            // High Score
                System.out.println("High Score: " + highScore);
            // averege gusses
            avg = (double)sum/count;
            System.out.println("Average: "+ avg);

        // ************************ print output ****************************
          System.out.println("end of prossesing");
        // ***** close streams *****
        //fin.close();                // close input buffer stream
        //fout.close();               // close output stream

    }  // end main
    
    //************************************** static methods static methods*********************************************

    public static int GameStats(int S, int scaler){
        if(S <= scaler){
            S = scaler;
        }// end if
       return S;
    }// end gameStates()
    public static  void getResult(int[]M, int[]S,char[]check, char Almost, char Win, char Lost){
        final int skip1 = -12928;
        final int skip2 = -28948;
        int[] tempGuss = new int[M.length];
        int[] tempSol = new int[S.length];

            tempGuss = LoadTempArray(M,tempGuss);
            tempSol = LoadTempArray(S,tempSol);
            for(int i = 0; i< S.length; i++) {
                if (tempGuss[i] == tempSol[i]) {
                    check[i] = Win;
                    tempGuss[i] = skip1;
                    tempSol[i] = skip2;
                }// end if
                else{
                    check[i] = Lost;
                }// end else
            }// end for
            for(int i = 0; i< S.length; i++) {

                for (int j = 0; j < S.length; j++) {
                        if (tempGuss[i] == tempSol[j]) {
                            check[j] = Almost;
                            tempGuss[i] = skip1;
                            tempSol[j] = skip2;
                            j = S.length;

                        }// end if
                    }// end for

            }// end for

        for(int r = 0; r< check.length; r++){
            if(check[r] == Win){
                System.out.println("Right");

            }// end if
            else if(check[r] == Almost){
                System.out.println("Almost");
            }// end if
            else if(check[r] == Lost){
                System.out.println("LOST");
            }// end if

        }// end for

    }// end getResult

    public static char[] getResult(char[] check){

        for (int i = 0; i < check.length; i++) {
            if (check[i] == 'L' ) {
                for (int j = 0; j < check.length; j++) {
                    check[j] = 'L';
                }// end for
            }// end if
        }// end for

        return check;
    }// end getResult
    public static int[] getSolution(int[] solution, Random rand){

        for (int i = 0; i <solution.length; i++){
            int ran = rand.nextInt(solution.length) + 1;
            solution[i] = ran;

        }// end for
        return solution;
     }// end solution();
    public static int[] LoadTempArray(int [] Og, int [] copy){
        for(int i = 0; i<Og.length; i++){
            copy[i] = Og[i];
        }// end for loop
        return copy;
    }// end loadTempArray()

    // load table with random numbers
    public static void LoadTable(int[][] t , int r){
        for(int i = 0; i< t.length; i++){
            for(int j = 0; j< t[i].length; j++){
                t[i][j] = r;
            }// end for loop
        } // end for loop

    }// load Table (tandom numbets)

    public static void checkGuess(int[][] Mast , int[]S, String[] in, String guss,String divider,  char[] tries,char WIN,char LOST, int n) {

        final int RESTART = 0;      // initializeintizing values in 2D arrays
            for (int j = 0; j < Mast[n].length; j++) {
                in = guss.split(divider);
                Mast[n][j] = Integer.parseInt(in[j]);
                if (Mast[n][j] == S[j]) {
                    tries[j] = WIN;
                }// end if
                else {
                    tries[j] = LOST;
                } // end else
            }// end for loop

        getResult(tries);

    }// end Loadtable

    public static void PrintTable(int[][] t) {
        for(int i = 0; i < t.length; i++) {
            for(int j = 0; j < t[i].length;j++) {
                System.out.print(t[i][j] + ", ");
            }// end for

            System.out.println();
        }// end for

    }// end PrintTable
      public static void PrintArray(int[] p ){
          //print Array
          String nl = System.lineSeparator(); // new line character for file writing
          int Dif = 1;  // the diffrence for length of array
          for(int j = 0; j<p.length; j++){
                  if(j!=p.length-Dif) {
                      System.out.print(p[j] + ", ");
                  }// end if
                  else{
                      System.out.print(p[j]+nl);
                  }// end else
              }// end for loop

    } // end PrintTable
    /***********************************************************
     *   Purpose: Create a banner string that can be used to
     *           print the banner to a message dialog or the console
     *           window
     *   Interface:  no parameters
     *   Returns:    no return
     * **************************************************************/
    public static String windowBanner(){
        String bannerOut = "";

        bannerOut = "*******************************************\n";
        bannerOut += "Name:        Naga Assefa\n";
        bannerOut += "Class:        CS400S\n";
        bannerOut += "Assignment:    Mastermind Game\n";
        bannerOut += "*******************************************\n\n";

        return bannerOut;
    } // end bannerOut

    /***********************************************************
     *   Purpose: print a banner to the console window
     *   Interface:  no parameters
     *   Returns:    no return
     * **************************************************************/
    public static void printBanner(){
        System.out.println("*******************************************");
        System.out.println("Name:         Naga Assefa");
        System.out.println("Class:        CS40S");
        System.out.println("Assignment:    mastermind");
        System.out.println("*******************************************");
    } // end print banner

    /***********************************************************
     *   Purpose: prints a banner to the output file
     *   Interface:  print writer fout
     *   Returns:    no return
     * **************************************************************/
    public static void fileBanner(PrintWriter fout){
        fout.println("*******************************************");
        fout.println("Name:        Naga");
        fout.println("Class:        CS40S");
        fout.println("Assignment:    Mastermind");
        fout.println("*******************************************");
    } // end bannerOut

}  // end class