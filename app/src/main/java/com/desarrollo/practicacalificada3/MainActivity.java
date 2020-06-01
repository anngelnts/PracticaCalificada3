package com.desarrollo.practicacalificada3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int CAMERA_PIC_REQUEST = 1;
    private String PATH_IMAGE = "";
    EditText name_edit_text;
    EditText address_edit_text;
    EditText city_edit_text;
    EditText state_edit_text;
    EditText zip_edit_text;
    ImageView image_camera;
    Button save_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name_edit_text = findViewById(R.id.name_edit_text);
        address_edit_text = findViewById(R.id.address_edit_text);
        city_edit_text = findViewById(R.id.city_edit_text);
        state_edit_text = findViewById(R.id.state_edit_text);
        zip_edit_text = findViewById(R.id.zip_edit_text);
        image_camera = findViewById(R.id.image_camera);
        save_button = findViewById(R.id.save_button);

        image_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = name_edit_text.getText().toString();
                String address = address_edit_text.getText().toString();
                String city = city_edit_text.getText().toString();
                String state = state_edit_text.getText().toString();
                String zip = zip_edit_text.getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(MainActivity.this, getResources().getText(R.string.error_string), Toast.LENGTH_SHORT).show();
                }
                else if (address.isEmpty()) {
                    Toast.makeText(MainActivity.this, getResources().getText(R.string.error_string), Toast.LENGTH_SHORT).show();
                }
                else if (city.isEmpty()) {
                    Toast.makeText(MainActivity.this, getResources().getText(R.string.error_string), Toast.LENGTH_SHORT).show();
                }
                else if (state.isEmpty()) {
                    Toast.makeText(MainActivity.this, getResources().getText(R.string.error_string), Toast.LENGTH_SHORT).show();
                }
                else if (zip.isEmpty()) {
                    Toast.makeText(MainActivity.this, getResources().getText(R.string.error_string), Toast.LENGTH_SHORT).show();
                }
                else {
                    SendData(name, address, city, state, zip, PATH_IMAGE);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_PIC_REQUEST){
            if(resultCode == RESULT_OK){
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                PATH_IMAGE = bitmap.toString();
                image_camera = findViewById(R.id.image_camera);
                image_camera.setImageBitmap(bitmap);
            }
        }
    }

    private void SendData(String name, String address, String city, String state, String zip, String image) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra("name", name);
        sendIntent.putExtra("address", address);
        sendIntent.putExtra("city", city);
        sendIntent.putExtra("state", state);
        sendIntent.putExtra("zip", zip);
        sendIntent.putExtra("image", image);
        sendIntent.setType("text/plain");
        if(sendIntent.resolveActivity(getPackageManager()) != null){
            startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.chooser_msn)));
        }
    }
}
