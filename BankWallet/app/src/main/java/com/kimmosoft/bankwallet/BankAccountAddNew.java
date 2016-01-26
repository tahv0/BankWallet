package com.kimmosoft.bankwallet;

import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kimmosoft.bankwallet.src.BankAccount;
import com.kimmosoft.bankwallet.src.RealmHelper;
import com.kimmosoft.bankwallet.src.Validator;

public class BankAccountAddNew extends AppCompatActivity {
    private RealmHelper realmHelper;
    private Validator validator;
    private int friendid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_account_add_new);
        ActionBar actionBar = getSupportActionBar();
        this.realmHelper = new RealmHelper(getApplicationContext());
        if (getIntent().getExtras().containsKey("friendid")){
            //This activity is called from FriendListActivity in BankAccount to add new accounts and edit old ones (2 different usages).
            // So thats why if-else statements
            friendid = getIntent().getExtras().getInt("friendid");
            actionBar.setTitle("Add new account for "+ realmHelper.getFriend(friendid).getName());
        }
        else{
            EditText ibanText = (EditText) findViewById(R.id.iban_editText);
            EditText declarationText = (EditText) findViewById(R.id.iban_declaration_editText);
            BankAccount account = realmHelper.getAccount(getIntent().getExtras().getInt("accountid"));
            ibanText.setText( account.getIban());
            declarationText.setText(account.getDeclaration());
            actionBar.setTitle("Edit account");
        }
        this.validator = new Validator();

        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);

        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save, menu);
        return true;
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
    public void saveButtonActivity (View v){

            EditText ibanText = (EditText) findViewById(R.id.iban_editText);
            EditText declarationText = (EditText) findViewById(R.id.iban_declaration_editText);

            if (!validator.validIBAN(ibanText.getText().toString())){
                //help snackbar-text if user writes unvalid iban
                Snackbar.make(v, "Not valid IBAN. It must be valid and it has to contain 2 letters and 13-34 digits", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return;
            }
            if (getIntent().getExtras().containsKey("friendid")) {
                //add new account
                realmHelper.addAccount(friendid, ibanText.getText().toString(), declarationText.getText().toString());
            }
            else{
                //edit old already saved account
                realmHelper.editAccount(getIntent().getExtras().getInt("accountid"),ibanText.getText().toString(),declarationText.getText().toString());
            }
            finish(); //Operation succeeded, so rollback from Intent to previous view.




    }
}
