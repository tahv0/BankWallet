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
import com.kimmosoft.bankwallet.src.Validator;

import java.math.BigInteger;
import java.util.regex.Pattern;

public class FriendAddNew extends AppCompatActivity {
    private Button button;
    private RealmHelper realmHelper;
    private Validator validator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_add_new);
        ActionBar actionBar = getSupportActionBar();
        this.realmHelper = new RealmHelper(getApplicationContext());
        this.validator = new Validator();
        validator.validIBAN("FI4250001510000023");
        if (actionBar != null){
            //actionBar.setTitle(realmHelper.getFriend(friendtId).getName() + "'s accounts");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Add new friend");
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
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public class saveButtonActivity implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            EditText nameText = (EditText) findViewById(R.id.friend_name_editText);
            EditText ibanText = (EditText) findViewById(R.id.iban_editText);
            EditText declarationText = (EditText) findViewById(R.id.iban_declaration_editText);

            if (!validator.validName(nameText.getText().toString())){
                Snackbar.make(v, "Not valid name", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return;

            }
            if (!validator.validIBAN(ibanText.getText().toString())){
                Snackbar.make(v, "Not valid IBAN. It must be valid iban and it contains 2 letters and 16 digits", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return;
            }
            realmHelper.addFriend(nameText.getText().toString(), ibanText.getText().toString(), declarationText.getText().toString());

        }


    }

}
