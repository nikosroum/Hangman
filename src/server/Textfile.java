/**
 *
 * @author Natalia Paratsikidou
 * @author Nikolaos Roumpoutsos
 * 
 */

package server;
import java.io.*;
import java.util.Random;

public class Textfile {
   
    //countlines from the dictionary file 
    public int countlines(String filename) throws IOException {
        LineNumberReader reader  = new LineNumberReader(new FileReader(filename));
        int cnt = 0;
        String lineRead = "";
        while ((lineRead = reader.readLine()) != null) {}
        cnt = reader.getLineNumber(); 
        reader.close();
        return cnt;
    }
    
    //Method to choose a random word from dictionary
    public String chooseword(String file,int count){
        String chosen = new String();
        /* Setting the seed */
	Random randomGenerator = new Random();
        /* Generating random number */
	int randomWord = randomGenerator.nextInt(count);
        try {
        BufferedReader in = new BufferedReader(new FileReader(file));
        int cnt=0;
        
        while((cnt<randomWord) && (chosen = in.readLine()) != null ){ //skip other lines till the random chosen
            cnt ++;
        }
        
        in.close();
        } catch (IOException e) {
        }
        return chosen;
    }
    
    //Create a dashed word which contains equal number of dashes as the letters of the word picked from dictionary
     public String dashme(String word){
        /* Setting dashes */
	char positions[] = new char[word.length()];
            for (int i = 0; i < word.length(); i++) {
                positions[i] = '-';
            }
            String dashes = new String(positions);
        return dashes;
    }
     
}
