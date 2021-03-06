package com.kimmosoft.bankwallet;

import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
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
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Add new friend");
        }


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            //back button
            finish();
            return true;
        }
        else if (id == R.id.action_save){
            //save button
            saveButtonActivity(this.findViewById(android.R.id.content));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save, menu);
        return true;
    }
    public void saveButtonActivity (View v){
            //when save-button pressed, check textinput fields and save if fields valid
            EditText nameText = (EditText) findViewById(R.id.friend_name_editText);
            EditText ibanText = (EditText) findViewById(R.id.iban_editText);
            EditText declarationText = (EditText) findViewById(R.id.iban_declaration_editText);
            if (!validator.validName(nameText.getText().toString())){
                Snackbar.make(v, "Not valid name", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return;

            }
            if (!validator.validIBAN(ibanText.getText().toString())){
                Snackbar.make(v, "Not valid IBAN. It must be valid and it has to contain 2 letters and 13-34 digits", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return;

            }
            realmHelper.addFriend(nameText.getText().toString(), ibanText.getText().toString(), declarationText.getText().toString());
            finish(); // Intent complete, new friend added and automatically rollback to previous view.

        }




}
