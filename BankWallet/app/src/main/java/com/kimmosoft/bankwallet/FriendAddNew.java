package com.kimmosoft.bankwallet;

import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kimmosoft.bankwallet.src.RealmHelper;

import java.math.BigInteger;
import java.util.regex.Pattern;

public class FriendAddNew extends AppCompatActivity {
    private Button button;
    private RealmHelper realmHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_add_new);
        ActionBar actionBar = getSupportActionBar();
        this.realmHelper = new RealmHelper(getApplicationContext());
        validIBAN("FI4250001510000023");
        if (actionBar != null){
            //actionBar.setTitle(realmHelper.getFriend(friendtId).getName() + "'s accounts");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        button = ( Button) findViewById(R.id.firend_new_save_button);
        button.setOnClickListener(new saveButtonActivity());

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
           NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public class saveButtonActivity implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            EditText nameText = (EditText) findViewById(R.id.friend_name_editText);
            EditText ibanText = (EditText) findViewById(R.id.iban_editText);
            EditText declarationText = (EditText) findViewById(R.id.Iban_declaration_editText);
            if (!validName(nameText.getText().toString())){
                Snackbar.make(v, "Not valid name", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return;

            }
            if (!validIBAN(ibanText.getText().toString())){
                Snackbar.make(v, "Not valid IBAN. It must be valid iban and it contains 2 letters and 16 digits", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return;
            }
            realmHelper.addFriend(nameText.getText().toString(),ibanText.getText().toString(),declarationText.getText().toString());

        }


    }
    Boolean validName(String name){
        return name.matches("\\w+");
    }
    Boolean validIBAN(String iban){
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
        Log.d("IbanInt",iban);
        BigInteger divider = new BigInteger("97");
        BigInteger ibanint = new BigInteger(iban);
        return ibanint.remainder(divider).equals(new BigInteger("1"));


    }
}
