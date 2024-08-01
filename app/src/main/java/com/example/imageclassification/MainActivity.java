package com.example.imageclassification;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    // ---- Tambahkan contoh kodingan disini ----
    ImageView imageView;
    Uri imageuri;
    Button buclassify;
    TextView classitext;
    Button searchButton;

    TFLiteHelper tfLiteHelper;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.image);
        buclassify = (Button) findViewById(R.id.classify);
        classitext = (TextView) findViewById(R.id.classifytext);
        searchButton = findViewById(R.id.searchButton);

        tfLiteHelper = new TFLiteHelper(this);
        tfLiteHelper.init();

        imageView.setOnClickListener(selectImageListener);
        buclassify.setOnClickListener(classifyImageListtener);

        ActionBar actionBar = getSupportActionBar();


        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);

        TextView textView = new TextView(this);
        textView.setText(R.string.app_name);
        textView.setTextColor(getResources().getColor(android.R.color.white));
        textView.setTextSize(18);


        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER);

        actionBar.setCustomView(textView, params);
        actionBar.setDisplayShowCustomEnabled(true);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 12 && resultCode == RESULT_OK && data != null) {
            imageuri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageuri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    View.OnClickListener selectImageListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String SELECT_TYPE = "image/*";
            String SELECT_PICTURE = "Select Picture";

            Intent intent = new Intent();
            intent.setType(SELECT_TYPE);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, SELECT_PICTURE), 12);
        }
    };

    View.OnClickListener classifyImageListtener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (bitmap != null) {
                tfLiteHelper.classifyImage(bitmap);
                setLabel(tfLiteHelper.showresult());
            }
        }
    };

    void setLabel(String label) {
        classitext.setText(label);
    }
    // ----------------------------------------------------

    public void searchOnGoogle(View view) {
        String searchText = classitext.getText().toString();

        // Pastikan teks yang dicari tidak kosong
        if (!searchText.isEmpty()) {
            // Buka browser dengan pencarian Google
            Uri searchUri = Uri.parse("https://www.google.com/search?q=" + Uri.encode(searchText));
            Intent searchIntent = new Intent(Intent.ACTION_VIEW, searchUri);
            startActivity(searchIntent);
        }
    }
}