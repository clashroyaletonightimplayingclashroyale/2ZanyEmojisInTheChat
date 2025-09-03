package Wordle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class WordleBot {

    public static void main(String[] args) throws FileNotFoundException {
        Set<String> words = readFile();
        Scanner console = new Scanner(System.in);

        // keep going until they get it correct
        for (int i = 0; i < 6 && words.size() > 0; i++) {
            // print the available words
            printWords(words);

            // get the word the user types and the hint world returned
            String guess = getGuess(words, console);
            String hint = getHint(console);
            // "GGGGG" means solution was found
            if (hint.equals("GGGGG")) {
                break;
            } else {
                words = prune(words, guess, hint);
                // otherwise we prune words that don't match the guess
            }
        }
    }

    /**
     * Prints all the available words that the user can type and pick from (10per
     * line)
     * 
     * @param words
     */
    public static void printWords(Set<String> words) {
        int count = 0;
        for (String word : words) {
            System.out.print(word + " ");
            count++;
            if (count >= 10) {
                count = 0;
                System.out.println();
            }

        }
        System.out.println();
    }

    /**
     * Gets guess from user
     * 
     * @param words   available words
     * @param console scanner to console
     * @return word the user picked to type in
     */
    public static String getGuess(Set<String> words, Scanner console) {
        boolean validGuess = false;
        System.out.print("Your word: ");
        String word = console.next().toLowerCase();
        while (!words.contains(word)) {
            System.out.println("Invalid word, pick another");
            word = console.next();
        }
        return "";
    }

    /**
     * Retrusn the hint from the user (5 characters)
     * B = black (wrong letter)
     * y = yellow (wrong position, but correct letter)
     * G = green (correct position)
     * 
     * @param console
     * @return
     */
    public static String getHint(Scanner console) {
        Boolean validHint = false;
        System.out.print("Your hint  (YBYGG)");
        String hint = console.next();
        while (!validHint) {
            if (hint.length() == 5) {
                validHint = true;
                for (int i = 0; i < 5; i++) {
                    char c = hint.charAt(i);
                    if (c != 'B' && c != 'Y' && c != 'G') {
                        validHint = false;
                    }
                }
            }
            if (!validHint) {
                System.out.print("Invalid hint");
                hint = console.next();
            }
        }
        return hint;
    }

    /**
     * Prunes the list to remove any words invalidated by the hint
     * 
     * @param words
     * @param guess
     * @param hint
     * @return the new word set
     */
    public static Set<String> prune(Set<String> words, String guess, String hint) {
        Set<String> pruneList = new TreeSet<String>();

        for (String word : words) {
            if (shouldKeep(word.toCharArray(), guess.toCharArray(), hint.toCharArray())) {
                pruneList.add(word);
            }
        }

        return pruneList;
    }

    public static boolean shouldKeep(char[] word, char[] guess, char[] hint) {
        // handle green
        for (int i = 0; i < 5; i++) {
            if (hint[i] == 'G') {
                if (word[i] != guess[i]) {
                    return false;
                } else {
                    word[i] = '*';
                }
            }
        }
        // reform the word with the * characters
        String wordString = new String(word);

        // handles black and yellow
        for (int i = 0; i < 5; i++) {
            if (hint[i] == 'Y') {
                if (word[i] != guess[i]) {
                    return false;
                } else if (!wordString.contains("" + guess[i])) {
                    return false;
                }
            } else if (hint[i] == 'B') {
                if (wordString.contains("" + guess[i])) {
                    return false;
                }
            }
        }

        return true;

    }

    /**
     * reads the wordle file to a tree set
     */
    public static Set<String> readFile() throws FileNotFoundException {
        // file path
        File f = new File("Unit5\\Wordle\\wordle_words.txt");
        Scanner s = new Scanner(f);

        // tree set so its sorted alphabetically
        Set<String> words = new TreeSet<String>();
        while (s.hasNext()) {
            words.add(s.next().toLowerCase());
        }

        s.close();
        return words;
    }
}
