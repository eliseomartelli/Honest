package it.eliseomartelli.honest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.eliseomartelli.honest.objects.Honest;

public class PostActivity extends AppCompatActivity {


    @BindView(R.id.text_input)
    EditText mText;

    @BindView(R.id.age_input)
    EditText mAgeInput;

    @BindView(R.id.sex_input)
    RadioGroup mSexInput;

    @BindView(R.id.fab)
    FloatingActionButton mPost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @OnClick(R.id.fab)
    public void postClick(View v) {

        if (mText.getText().toString().matches("") || mAgeInput.getText().toString().matches("")) {
            Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            Honest honest = new Honest();
            honest.setText(mText.getText().toString());
            honest.setAge(Integer.parseInt(mAgeInput.getText().toString()));

            int checked = mSexInput.getCheckedRadioButtonId();
            honest.setSex((checked == R.id.male) ? Honest.MALE : Honest.FEMALE);

            FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
            final DatabaseReference honestReference = mDatabase.getReference(DatabaseKeys.HONEST);

            honestReference.child(System.currentTimeMillis()+"").setValue(honest);
            finish();
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }
}
