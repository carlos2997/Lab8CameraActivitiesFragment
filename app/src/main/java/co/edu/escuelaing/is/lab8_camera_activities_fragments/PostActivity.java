package co.edu.escuelaing.is.lab8_camera_activities_fragments;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

public class PostActivity extends AppCompatActivity implements Serializable{

    private TextView txtViewMessage;
    private ImageView imgViewPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        txtViewMessage = findViewById(R.id.txtViewMessage);
        imgViewPicture = findViewById(R.id.imgViewPicture);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE_MESSAGE);
        txtViewMessage.setText(message);

        imgViewPicture.setImageURI(Uri.parse(intent.getExtras().getString(MainActivity.EXTRA_MESSAGE_PICTURE)));

    }


}
