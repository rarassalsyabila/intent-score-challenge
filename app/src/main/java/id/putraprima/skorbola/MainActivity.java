package id.putraprima.skorbola;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public static final String HOME_KEY = "home";
    public static final String AVAHOME_KEY = "imhome";
    public static final String AWAY_KEY = "away";
    public static final String AVAAWAY_KEY = "imaway";

    private EditText HomeInput;
    private EditText AwayInput;
    private ImageView AvahomeImage;
    private ImageView AvaawayImage;
    private Bitmap imhome;
    private Bitmap imaway;

    private static final String TAG = MainActivity.class.getCanonicalName();
    private static final int GALLERY_REQUEST_CODE = 1;
    private static final int GALLERY_REQUEST_CODE1 = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HomeInput = findViewById(R.id.home_team);
        AwayInput = findViewById(R.id.away_team);
        AvahomeImage = findViewById(R.id.home_logo);
        AvaawayImage = findViewById(R.id.away_logo);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }

        if (requestCode == GALLERY_REQUEST_CODE) {
            if (data != null) {
                try {
                    Uri imageUri = data.getData();
                    imhome = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    AvahomeImage.setImageBitmap(imhome);
                } catch (IOException e) {
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
        else if (requestCode == GALLERY_REQUEST_CODE1){
            if (data != null) {
                try {
                    Uri imageUri = data.getData();
                    imaway = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    AvaawayImage.setImageBitmap(imaway);
                } catch (IOException e) {
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }

    public void clickNext(View view) {
        String home = HomeInput.getText().toString();
        String away = AwayInput.getText().toString();

        if(home.length() == 0){
            HomeInput.setError("Fill the team name (HOME)!");
        }
        else if (away.length() == 0){
            AwayInput.setError("Fill the team name (AWAY)!");
        }
        else if (imhome == null){
            Toast.makeText(this, "Choose the logo first(HOME)!", Toast.LENGTH_SHORT).show();
        }
        else if (imaway == null){
            Toast.makeText(this, "Choose the logo first(AWAY)!", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent = new Intent(this, MatchActivity.class);
            AvahomeImage.buildDrawingCache();
            AvaawayImage.buildDrawingCache();
            imhome = AvahomeImage.getDrawingCache();
            imaway = AvaawayImage.getDrawingCache();
            Bundle extras = new Bundle();
            extras.putParcelable(AVAHOME_KEY, imhome);
            extras.putParcelable(AVAAWAY_KEY, imaway);
            intent.putExtras(extras);
            intent.putExtra(HOME_KEY, home);
            intent.putExtra(AWAY_KEY, away);
            startActivity(intent);
        }
    }

    public void changeAway(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQUEST_CODE1);
    }

    public void changeHome(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }
}
