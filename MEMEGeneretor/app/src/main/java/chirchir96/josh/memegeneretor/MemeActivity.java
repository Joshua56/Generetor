package chirchir96.josh.memegeneretor;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class MemeActivity extends AppCompatActivity {

    public static final int MY_PERMISSION_REQUEST = 1;
    public static final int RESULT_LOAD_IMAGE=2;

    Button save,load,share, go;
    EditText editText1, editText;
    ImageView imageView;
    TextView textView1, textView;
    String currentImage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meme);

        int clicked= getIntent().getIntExtra("clicked", R.drawable.image1);
        ImageView imgView=findViewById(R.id.imgClicked);
        imgView.setImageResource(clicked);

        if (ActivityCompat.checkSelfPermission(MemeActivity.
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MemeActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);


        }else {
            ActivityCompat.requestPermissions(MemeActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);

        }


        imageView =  findViewById(R.id.imgClicked);

        textView = findViewById(R.id.select_one);
        textView1 = findViewById(R.id.select_two);

        editText= findViewById(R.id.top_caption);
        editText1= findViewById(R.id.bottom_caption);



        save = findViewById(R.id.save);
        load = findViewById(R.id.load);
        share = findViewById(R.id.share);
        go = findViewById(R.id.go);



        save.setEnabled(false);
        share.setEnabled(false);


        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,RESULT_LOAD_IMAGE);



            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                View context = findViewById(R.id. lay);
                Bitmap bitmap = getScreenShot(context);
                currentImage = "meme"+ System.currentTimeMillis() + ".png";
                store(bitmap, currentImage);
                share.setEnabled(true);



            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shareImage(currentImage);



            }


        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textView.setText(editText.getText().toString());
                textView1.setText(editText1.getText().toString());

                editText.setText("");
                editText1.setText("");

            }
        });
    }

    public static Bitmap getScreenShot(View view){

        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public void store(Bitmap bitmap, String fileName){

        String dirPath= Environment.getExternalStorageDirectory().getAbsolutePath()+ "/MEME";
        File dir = new File(dirPath);
        if (!dir.exists()){
            dir.mkdir();

        }

        File file = new File(dirPath, fileName);
        try {

            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG,100, fos);

            fos.flush();
            fos.flush();
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            Toast.makeText(this, "Error in saving ", Toast.LENGTH_SHORT).show();
        }
    }



    private void shareImage(String fileName){

        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/MEME";
        Uri uri = Uri.fromFile(new File(dirPath, fileName));
        Intent intent= new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");


        intent.putExtra(Intent.EXTRA_SUBJECT,"");
        intent.putExtra(Intent.EXTRA_TEXT,"");
        intent.putExtra(Intent.EXTRA_STREAM,"uri");

        try { startActivity(intent.createChooser(intent, "share via"));

        }catch (ActivityNotFoundException e){
            Toast.makeText(this,"No sharing app found",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==RESULT_LOAD_IMAGE && requestCode == RESULT_OK && null != data ){
            Uri selectedImage = data.getData();
            String[] filePathColumn= {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn,null,null);
            cursor.moveToFirst();
            int columnIndex= cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            save.setEnabled(true);


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSION_REQUEST:{

                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                }

                if (ContextCompat.checkSelfPermission(MemeActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){


                }else {
                    Toast.makeText(this,"No permission granted",Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;

            }
        }
    }
    }

