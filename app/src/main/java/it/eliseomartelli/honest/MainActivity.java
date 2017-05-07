package it.eliseomartelli.honest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.eliseomartelli.honest.objects.Honest;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_recyclerview)
    RecyclerView mMainRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Instantiating Firebase
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference honestReference = mDatabase.getReference(DatabaseKeys.HONEST);
        final Query honest = honestReference.orderByKey().limitToLast(100);
        honest.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Honest honest = postSnapshot.getValue(Honest.class);
                    Log.d("honest", honest.getText());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(getClass().getName(), databaseError.getMessage());
            }
        });

        mMainRecyclerView.setHasFixedSize(false);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mMainRecyclerView.setLayoutManager(mLayoutManager);

        RecyclerView.Adapter mAdapter = new FirebaseRecyclerAdapter<Honest, HonestHolder>(Honest.class, R.layout.honest_layout_element, HonestHolder.class, honest) {
            @Override
            protected void populateViewHolder(HonestHolder viewHolder, Honest model, int position) {
                viewHolder.mAgeText.setText(String.format(getString(R.string.years_old), model.getAge()+""));
                viewHolder.mSexColor.setBackgroundColor((model.getSex() == Honest.MALE) ? getResources().getColor(R.color.man) : getResources().getColor(R.color.girl));
                viewHolder.mText.setText(model.getText());
            }
        };
        mMainRecyclerView.setAdapter(mAdapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Open create activity
                Intent intent = new Intent(MainActivity.this, PostActivity.class);
                int width = view.getMeasuredWidth(), height = view.getMeasuredHeight();
                ActivityOptionsCompat options = ActivityOptionsCompat.makeClipRevealAnimation(view, 0, 0, width, height);
                startActivity(intent, options.toBundle());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    static class HonestHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.sex_color)
        LinearLayout mSexColor;

        @BindView(R.id.age_text)
        TextView mAgeText;

        @BindView(R.id.text)
        TextView mText;

        public HonestHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
