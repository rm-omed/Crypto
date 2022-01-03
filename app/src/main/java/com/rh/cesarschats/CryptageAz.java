package com.rh.cesarschats;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CryptageAz {
    static char[] text1,text2;
    static char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    static char[] alphabet2 = {'z','y','x','w','v','u','t','s','r','q','p','o','n','m','l','k','j','i','h','g','f','e','d','c','b','a'};

    public static String crypt(String text)  {

        text1=text.toCharArray();
        String cryptedmsg="";
        char ch='a';
        for(int i = 0; i < text1.length; i++){
            for(int j = 0; j < alphabet.length ;j++){
                if(text1[i]==alphabet[j]){
                ch=alphabet2[j];
                }
            }
            cryptedmsg = cryptedmsg+String.valueOf(ch);
        }


        return cryptedmsg;
    }
    public static String decrypt(String text)  {

        text1=text.toCharArray();
        String decryptedmsg="";   char ch='a';
        for(int i = 0; i < text1.length; i++){
            for(int j = 0; j < alphabet2.length ;j++)
                if(text1[i] ==alphabet2[j]){
                    ch=alphabet[j];
                }
            decryptedmsg = decryptedmsg+String.valueOf(ch);
        }

        return decryptedmsg;
    }

}
