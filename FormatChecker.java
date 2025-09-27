package Lab04_Varozza_Evan;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.zip.DataFormatException;

public class FormatChecker {
    public static void main(String[] args) throws FileNotFoundException {
        String arg7 = "invalid7.dat";
        int columns, rows;
        if(args.length == 0) {
            System.err.println("No file names provided. Please provide at least one file name as a command-line argument.");
            return;
        }
        for (String arg : args) {
            try {
                System.out.println();
                //New file and scanner using provided argument string.
                File dataFile = new File(arg);
                Scanner scanner = new Scanner(dataFile);
                try {
                    String firstLine = scanner.nextLine();
                    //split the string at each space
                    String[] tokens = firstLine.trim().split("\\s+");
                    //If there are more than 2 tokens, that is an invalid format.
                    if (tokens.length > 2) {
                        scanner.close();
                        throw new InputMismatchException();
                    }
                    rows = Integer.parseInt(tokens[0]);
                    columns = Integer.parseInt(tokens[1]);
                    //Check for valid number of rows
                    for(int i = 0; i < rows; i++) {
                            scanner.nextLine();
                    }
                    //Blank enter keystrokes after the specified number of rows count as having a next line, so if the row has anything in it other than blank, throw an exception.
                    if(scanner.hasNextLine() && !scanner.nextLine().equals("")) {
                        scanner.close();
                        throw new DataFormatException("The number of rows specified does not match the actual number of rows in the file.");
                    }
                    //Reset the scanner to the beginning of the file to check each entry.
                    Scanner lineScanner = new Scanner(dataFile);
                    // Skip the first line already processed
                    lineScanner.nextLine(); 
                    for (int i = 0; i < rows; i++) {
                        String line = lineScanner.nextLine();
                        String[] lineTokens = line.trim().split("\\s+");
                        for (String token : lineTokens) {
                            //Input must be an integer, therefore if it is not, throw an exception.
                            if(token.equals("X")) {
                                lineScanner.close();
                                throw new DataFormatException("Entry 'X' found in row " + (i + 1) + ". Entries must be integers.");
                            }
                        }
                        //if there is an extra token in the line where there should be a certain number of columns, throw an exception.
                        if (lineTokens.length != columns) {
                            lineScanner.close();
                            throw new DataFormatException("Row " + (i + 1) + " does not contain the correct number of columns.");
                        }
                    }
                    lineScanner.close();
                    //If everything has passed, the file is valid.
                    System.out.println(arg + "\nVALID");
                } catch (InputMismatchException e) {
                    System.err.println(arg + "\n" + e + ": First two entries must be integers representing rows and columns.\nINVALID");
                } catch (NumberFormatException e) {
                    System.err.println(arg + "\n" + e + ": First line must contain only two integers. \nINVALID");
                } catch (DataFormatException e) {
                    System.err.println(arg + "\n" + e + "\nINVALID");
                }
                scanner.close(); // Close the scanner after reading
            } catch (FileNotFoundException e) {
                System.err.println(arg +"\n" + e + ": The system cannot find the file specified.\nINVALID");
            } catch(IllegalArgumentException e) {
                System.err.println(e);
                break; // Exit the loop if no file names are provided
            }
        }
    }
}
