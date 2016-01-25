package com.kimmosoft.bankwallet.src;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by tahv0 on 25-Jan-16.
 */
public class BankAccount extends RealmObject {
    @PrimaryKey
    private int id;
    @Required
    private String iban;
    private String declaration;

    public void setId(int id){this.id = id;};
    public void setIban(String iban){this.iban = iban;};
    public int getId(){return this.id;};
    public String getIban(){return this.iban;};
    public String getDeclaration(){return this.declaration;};
    public void setDeclaration(String declaration){this.declaration = declaration;};

}
