package ch.hsr.mge.learnit.presentation;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import ch.hsr.mge.learnit.Application;
import ch.hsr.mge.learnit.R;
import ch.hsr.mge.learnit.domain.CardSet;

public class WellDoneActivity extends AppCompatActivity {
    private int index;
    private CardSet set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_well_done);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);


        TextView statistik = (TextView) findViewById(R.id.statistik);
        Bundle extras = getIntent().getExtras();
        index = extras.getInt("CARDSET_POSITION");
        Application app = (Application) getApplication();
        set = app.getCardSets().get(index);
        String cardString = set.getSize() == 1? "card":"cards";
        statistik.setText("You now have " + set.amountCorrectCards() + " of "+ set.getSize() +" correct " + cardString);
    }
}
