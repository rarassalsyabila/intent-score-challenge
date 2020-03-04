package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ScorerActivity extends AppCompatActivity {
    private EditText scorerName;
    String nameScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorer);
        scorerName = findViewById(R.id.editText);
    }

    public void Next(View view) {
        nameScore = scorerName.getText().toString();
        if (nameScore.equals("")){
            Toast.makeText(this, "Isi dulu", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent();
            intent.putExtra("scorerName", nameScore);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
