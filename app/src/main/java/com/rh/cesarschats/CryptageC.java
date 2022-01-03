package com.rh.cesarschats;

public class CryptageC {
    static char[] alphabet1 = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    public static String crypt(int n, String sIn)  {


        char[] charSIn = sIn.toCharArray();
        char[] charSOut = new char[charSIn.length];
        int pos1, pos2;
        for(int i = 0; i < charSIn.length; i++)   {
            pos1 = posChar(charSIn[i], alphabet1);
            pos2 = newPos(pos1, n);
            if(pos2 == -1) charSOut[i] = ' ';
            else charSOut[i] = alphabet1[pos2];
        }
        return new String(charSOut);
    }

    public static String decrypt(int n, String sIn)  {


        char[] charSIn = sIn.toCharArray();
        char[] charSOut = new char[charSIn.length];
        int pos1, pos2;
        for(int i = 0; i < charSIn.length; i++) {
            pos1 = posChar(charSIn[i], alphabet1);
            pos2 = newPos(pos1, -n);
            if(pos2 == -1) charSOut[i] = ' '; // si -1, c'est que ce n'est pas une lettre, on met un espace à la place
            else charSOut[i] = alphabet1[pos2];
        }
        return new String(charSOut);
    }


    private static int posChar(char c, char[] tab)  {
        for(int i = 0; i < tab.length; i++)   {
            if(tab[i] == c) return i;
        }   return -1;
    }

    // Donne la nouvelle position dans l'alphabet en fonction de n
    private static int newPos(int pos,int n)  {
        int pos2 = pos;
        if(pos <= -1) { // -1 signifie que le caractere n'a pas été trouvé dans l'alphabet (caractere spécial, chiffre, espace, etc.)
            pos2 = -1;
        } else {
            int i = 0;
            while(i < abs(n)) {
                if(n < 0) {
                    if(pos2 - 1 == -1) pos2 = 25;
                    else pos2--;
                } else {
                    if(pos2 + 1 >= 25) pos2 = 0;
                    else pos2++;
                }
                i++;
            }
        }
        return pos2;
    }

    // Valeur absolue de a
    public static int abs(int a)  {
        if(a >= 0) return a;
        else return -a;
    }
}