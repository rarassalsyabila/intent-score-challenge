package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MatchActivity extends AppCompatActivity {
    private TextView Home;
    private TextView Away;
    private ImageView AvahomeImage;
    private ImageView AvaawayImage;
    private TextView scoreHome;
    private TextView scoreAway;
    private  TextView homeScoreName;
    private  TextView awayScoreName;
    private Integer score1;
    private Integer score2;
    String returnStringHome;
    String returnStringAway;
    String homeTeam, awayTeam;
    String result, message, scorerName;

    public static final String HASIL_KEY = "hasil";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        Home = findViewById(R.id.txt_home);
        Away = findViewById(R.id.txt_away);
        AvahomeImage = findViewById(R.id.home_logo);
        AvaawayImage = findViewById(R.id.away_logo);
        scoreHome = findViewById(R.id.score_home);
        scoreAway = findViewById(R.id.score_away);
        homeScoreName = findViewById(R.id.resultNameHome);
        awayScoreName = findViewById(R.id.resultNameAway);

        score1 = 0;
        score2 = 0;
        scoreHome.setText("0");
        scoreAway.setText("0");

        Bundle extrras = getIntent().getExtras();

        homeTeam = extrras.getString("inputHome");
        awayTeam = extrras.getString("inputAway");

        if (extrras != null){
            String hn = getIntent().getExtras().getString("home");
            String an = getIntent().getExtras().getString("away");
            Bundle extra = getIntent().getExtras();
            Bitmap bmp = extra.getParcelable("imhome");
            Bitmap bmp1 = extra.getParcelable("imaway");

            Home.setText(hn);
            Away.setText(an);
            AvahomeImage.setImageBitmap(bmp);
            AvaawayImage.setImageBitmap(bmp1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the SecondActivity with an OK result
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                returnStringHome = data.getStringExtra("scorerName");

                String pencetakHome = returnStringHome;
                String pencetakBaruHome = homeScoreName.getText().toString();
                homeScoreName.setText(String.valueOf(pencetakBaruHome + " \n "+pencetakHome));

            }
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                returnStringAway = data.getStringExtra("scorerName");

                String pencetakAway = returnStringAway;
                String pencetakBaruAway = awayScoreName.getText().toString();
                awayScoreName.setText(String.valueOf(pencetakBaruAway + " \n " + pencetakAway));
            }
        }
    }

    public void plusHome(View view) {
        score1++;
        scoreHome.setText(String.valueOf(score1));
        Intent intent = new Intent(this, ScorerActivity.class);
        startActivityForResult(intent, 1);
    }

    public void plusAway(View view) {
        score2++;
        scoreAway.setText(String.valueOf(score2));
        Intent intent = new Intent(this, ScorerActivity.class);
        startActivityForResult(intent, 2);
    }

    public void cekHasil(View view) {
        String hasil;
        if(score1 > score2){
            hasil = Home.getText().toString();
            scorerName = homeScoreName.getText().toString();
        }
        else if(score2 > score1){
            hasil = Away.getText().toString();
            scorerName = awayScoreName.getText().toString();
        }
        else {
            hasil = "Draw";
        }
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(HASIL_KEY, hasil);
        intent.putExtra("scorer", scorerName);
        startActivity(intent);
    }
}
