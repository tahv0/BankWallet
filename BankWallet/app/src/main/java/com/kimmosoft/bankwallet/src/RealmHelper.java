package com.kimmosoft.bankwallet.src;

import android.content.Context;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by tahv0 on 25-Jan-16.
 */
public class RealmHelper {
    public RealmHelper(Context context) {
        this.realm = Realm.getInstance(context);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    private Realm realm;
    /**
     * An array of sample (dummy) items.
     */
    public RealmResults<Friend> getBankAccounts(){
        RealmQuery<Friend> query = realm.where(Friend.class);
        return query.findAll();
    }

    /**
     * A map of sample (dummy) items, by ID.
     */
    //  public static final Map<String, BankAccountItem> ITEM_MAP = new HashMap<String, BankAccountItem>();

   /* private static final int COUNT = 25;

    {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addFriend("matti näsä", "fi22929292929");
        }
    }*/

    public  void addFriend(String name, String iban,String declaration) {

        realm.beginTransaction();
        Friend friend  = realm.createObject(Friend.class);
        friend.setName(name);
        friend.setId(realm.where(Friend.class).max("id").intValue() + 1);
        RealmList<BankAccount> list = new RealmList<BankAccount>();
        BankAccount account = realm.createObject(BankAccount.class);
        account.setIban(iban);
        account.setId(realm.where(BankAccount.class).max("id").intValue()+1);
        account.setDeclaration(declaration);
        list.add(account);
        friend.setAccounts(list);
        realm.commitTransaction();
        // assert getBankAccounts().size() == 0: "none lol";
        Log.d("Bankaccounts", String.valueOf(getBankAccounts().size()));
    }
    public  Friend getFriend(int id){
        RealmQuery<Friend> query = realm.where(Friend.class);
        query.equalTo("id",id);
        RealmResults<Friend> result1 = query.findAll();
        return result1.first();
    }
}
