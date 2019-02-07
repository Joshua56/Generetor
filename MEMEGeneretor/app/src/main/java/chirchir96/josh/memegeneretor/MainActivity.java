package chirchir96.josh.memegeneretor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {


    Button btnOne;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridView = findViewById(R.id.grid_view);

        // Instance of chirchir96.josh.memegeneretor.ImageAdapter Class

        gridView.setAdapter(new ImageAdapter(this));


    }
}
