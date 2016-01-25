package com.kimmosoft.bankwallet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.kimmosoft.bankwallet.src.Friend;
import com.kimmosoft.bankwallet.src.RealmHelper;

import java.util.List;

import io.realm.Realm;

/**
 * An activity representing a list of Friends. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link FriendDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class FriendListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    RealmHelper realmHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.friendlist_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        realmHelper = new RealmHelper(getApplicationContext());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent = new Intent(getApplicationContext(),FriendAddNew.class);
                startActivity(intent);
            }
        });

        View recyclerView = findViewById(R.id.friend_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.friend_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        for (int i = 1; i <= 15; i++) {
            realmHelper.addFriend("matti näsä", "fi22929292929","tili",i);
            realmHelper.addFriend("irmeli näsä", "fi22929292929","tili",i);
        }
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(realmHelper.getBankAccounts()));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Friend> mValues;

        public SimpleItemRecyclerViewAdapter(List<Friend> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.friend_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).getName());
            holder.mContentView.setText(String.valueOf(( mValues.get(position).getAccounts().size()) + " IBAN(s)"));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        Context context = v.getContext();
                        Intent intent = new Intent(context, AccountListActivity.class);
                        intent.putExtra("id", holder.mItem.getId());

                        context.startActivity(intent);

                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public Friend mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
