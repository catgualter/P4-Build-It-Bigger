package com.anagualter;

import java.util.Random;

public class JokesLibrary {
    private String [] jokeList;


    public JokesLibrary(){

        jokeList = new String[]{
                "A friend of mine tried to annoy me with bird puns, but I soon realized that toucan play at that game."
                ,"I wondered why the baseball was getting bigger. Then it hit me."
                ,"Did you hear about the guy whose whole left side was cut off? He's all right now."
                ,"I'd tell you a chemistry joke but I know I wouldn't get a reaction."
                ,"I'm reading a book about anti-gravity. It's impossible to put down."
                ,"I wasn't originally going to get a brain transplant, but then I changed my mind."
                ,"I used to be a banker but I lost interest."
                ,"My friend's bakery burned down last night. Now his business is toast."
                ,"Have you ever tried to eat a clock? It's very time consuming."
        };
    }

    public String getJoke(){

        return jokeList[new Random().nextInt(jokeList.length)];
    }

}
