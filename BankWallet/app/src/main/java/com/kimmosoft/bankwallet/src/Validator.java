package com.kimmosoft.bankwallet.src;

import android.util.Log;

import com.kimmosoft.bankwallet.R;

import java.math.BigInteger;
import java.util.regex.Pattern;

/**
 * Created by tahv0 on 25-Jan-16.
 */
public class Validator {
    public Boolean validName(String name){
        //regex that checks if name contains [a-zA-Z_]
        return Pattern.compile("\\w+").matcher(name).find();
    }
    public Boolean validIBAN(String iban){
        /*
       1. Check that the total IBAN length is correct as per the country. If not, the IBAN is invalid
       2. Move the four initial characters to the end of the string
       3. Replace each letter in the string with two digits, thereby expanding the string, where A = 10, B = 11, ..., Z = 35
       4. Interpret the string as a decimal integer and compute the remainder of that number on division by 97


         */
        Boolean retval = false;
        iban = iban.replaceAll("\\s+","");
        //Shortest iban is in Norway and longest possible is 34 chars.
        if (!Pattern.matches("[a-zA-Z]{2}\\d{13,32}", iban)) {
            return false;
        }
        String countrycode = iban.substring(0,2);
        String checkcode = iban.substring(2,4);
        iban = iban.substring(4);

        for (char i: countrycode.toCharArray()) {
            int asciinumber = (int) Character.toUpperCase(i);
            //A is 10 B is 11 etc.. Z is 35
            iban += String.valueOf(asciinumber - 55);
        }
        iban += checkcode;
        BigInteger divider = new BigInteger("97");
        BigInteger ibanint = new BigInteger(iban);
        return ibanint.remainder(divider).equals(new BigInteger("1"));


    }
}
