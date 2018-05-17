package com.example.somasur.exam1somasur;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView mTripletTextView;
    private Triple mTriple;
    private PythagoreanGenerator mPythogoreanGenerator = new PythagoreanGenerator();
    private TextView mView;
    private TextView mCorrectCount;
    private TextView mIncorrectCount;
    private int correct;
    private int incorrect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchDialog();
            }
        });
    }

    public void modifyCorrectCount(String myTriple) {
        correct++;
        mCorrectCount = findViewById(R.id.correct_count);
        String correctString = correct + "";
        mCorrectCount.setText(correctString);
        mView = findViewById(R.id.triples);
        mView.setText(getString(R.string.triple_part_text) + myTriple);
    }

    public void modifyIncorrectCount(String myTriple) {
        incorrect++;
        mIncorrectCount = findViewById(R.id.incorrect_count);
        String correctString = incorrect + "";
        mIncorrectCount.setText(correctString);
        mView = findViewById(R.id.triples);
        mView.setText(getString(R.string.triple_part_text) + myTriple);
    }

    public void launchDialog() {
        AlertDialog.Builder myBuilder = new AlertDialog.Builder(this);
        myBuilder.setTitle(R.string.dialog_title);
        View view = getLayoutInflater().inflate(R.layout.dialog_add, null, false);
        mTripletTextView = view.findViewById(R.id.triples_in_dialog);
        mTriple = mPythogoreanGenerator.generatePotentialTriple();
        mTripletTextView.setText(mTriple.toString());
        myBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(mPythogoreanGenerator.isPotentialATrueTriple()) {
                    modifyCorrectCount(mTriple.toString());
                } else {
                    String x = mPythogoreanGenerator.getTrueTriple().toString();
                    modifyIncorrectCount(mPythogoreanGenerator.getTrueTriple().toString());
                }
            }

        });
        myBuilder.setView(view);
        myBuilder.setNegativeButton(android.R.string.cancel, null);
        myBuilder.create().show();

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
        if (id == R.id.item_delete) {
            mCorrectCount = findViewById(R.id.correct_count);
            final int pres = correct;
            final int nex = incorrect;
            mIncorrectCount = findViewById(R.id.incorrect_count);
            mCorrectCount.setText("0");
            mIncorrectCount.setText("0");
            Snackbar snackbar = Snackbar.make(findViewById(R.id.relative_layout), R.string.reset_scores, Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCorrectCount.setText(pres+"");
                    mIncorrectCount.setText(nex+"");
                }
            });
            correct = 0;
            incorrect = 0;
            snackbar.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
