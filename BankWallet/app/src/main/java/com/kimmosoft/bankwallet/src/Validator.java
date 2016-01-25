package com.kimmosoft.bankwallet.src;

import android.util.Log;

import java.math.BigInteger;
import java.util.regex.Pattern;

/**
 * Created by tahv0 on 25-Jan-16.
 */
public class Validator {
    public Boolean validName(String name){
        return Pattern.compile("\\w+").matcher(name).find();
    }
    public Boolean validIBAN(String iban){
        /*
        FI4250001510000023 (välilyönnit poistettu, pituus 18 merkkiä, kuten kuuluukin)
        50001510000023FI42 (maatunnus ja tarkiste siirretty loppuun)
        50001510000023151842 (kirjaimet korvattu numeroilla)
        50001510000023151842 mod 97 = 1


         */
        Boolean retval = false;
        iban = iban.replaceAll("\\s+","");
        if (!Pattern.matches("[a-zA-Z]{2}\\d{16}", iban)) {
            return false;
        }
        String countrycode = iban.substring(0,2);
        String checkcode = iban.substring(2,4);
        iban = iban.substring(4);

        for (char i: countrycode.toCharArray()) {
            int asciinumber = (int) Character.toUpperCase(i);
            iban += String.valueOf(asciinumber - 55);
        }
        iban += checkcode;
        Log.d("IbanInt", iban);
        BigInteger divider = new BigInteger("97");
        BigInteger ibanint = new BigInteger(iban);
        return ibanint.remainder(divider).equals(new BigInteger("1"));


    }
}
