//Hangman Game - ID2212
Roumpoutsos Nikolaos

A client-server distributed application in Java for a guessing game similar to the "Hangman" game (see a description at Wikipedia.org).
The server chooses a word from a dictionary, and the client (the player) tries to guess the word chosen by the server by suggesting letters 
(one letter at a time) or a whole word.  
The client is given a certain number of attempts (failed attempts) when it may suggest a letter that does not occur in the word. 
If the client suggests a letter that occurs on the word, the server places the letter in all its positions in the word; 
otherwise the counter of allowed failed attempts is decremented. 
