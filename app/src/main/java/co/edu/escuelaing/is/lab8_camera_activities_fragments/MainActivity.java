package co.edu.escuelaing.is.lab8_camera_activities_fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edtMessage;
    private Button btnPhoto,btnSave;
    private ImageView imgPhoto;
    private Uri selectedImageUri;
    private String[] photosCaptured = {"Camera", "Internal Storage"};
    private DialogInterface.OnClickListener onClickImageControl;
    private static final int SELECT_FILE_CAMERA = 0;
    private static final int SELECT_FILE_INTERNAL = 1;
    public static final String EXTRA_MESSAGE_MESSAGE = "co.edu.escuelaing.is.lab8.MESSAGE";
    public static final String EXTRA_MESSAGE_PICTURE = "co.edu.escuelaing.is.lab8.PICTURE";

    @NonNull
    public static Dialog createSingleChoiceAlertDialog(@NonNull Context context,
                                                       @Nullable String title,
                                                       @NonNull CharSequence[] items,
                                                       @NonNull DialogInterface.OnClickListener optionSelectedListener,
                                                       @Nullable DialogInterface.OnClickListener cancelListener)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setItems(items,optionSelectedListener);
        if(cancelListener != null){
            builder.setNegativeButton(R.string.Cancel,cancelListener);
        }
        return builder.create();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtMessage = findViewById(R.id.edtMessage);
        btnPhoto = findViewById(R.id.btnPhoto);
        btnSave = findViewById(R.id.btnSave);

        imgPhoto = findViewById(R.id.imgPhoto);

        onClickImageControl = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent;
                if(photosCaptured[i].equals("Camera")){
                    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,SELECT_FILE_CAMERA);
                }else{
                    intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,"Select picture from internal storage"),SELECT_FILE_INTERNAL);
                }
            }
        };

        btnSave.setOnClickListener(this);
        btnPhoto.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == Activity.RESULT_OK){
            selectedImageUri = data.getData();
            imgPhoto.setImageURI(selectedImageUri);
            /*
            switch(requestCode){
                case SELECT_FILE_CAMERA:
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    imgPhoto.setImageBitmap(bitmap);
                    break;
                case SELECT_FILE_INTERNAL:
                    Uri selectedImageUri = data.getData();
                    imgPhoto.setImageURI(selectedImageUri);
                    break;
            }*/

        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == btnPhoto.getId()){
            createSingleChoiceAlertDialog(this,"Select picture captured method",photosCaptured,onClickImageControl,null).show();
        }else if(view.getId() == btnSave.getId()){
            System.out.println(imgPhoto.getDrawable());
            if(edtMessage.getText().toString().length()==0 && imgPhoto.getDrawable() == null){
                System.out.println("Entro1");
                Toast.makeText(this,"Please enter either a message or select an image",Toast.LENGTH_SHORT).show();
            }else{
                if(edtMessage.getText().toString().length()>10){
                    System.out.println("Entro2");
                    edtMessage.setError("The text field should have a lenght longer than 50 characters");
                }else{
                    System.out.println("Entro3");
                    Intent intent = new Intent(this,PostActivity.class);

                    intent.putExtra(EXTRA_MESSAGE_PICTURE,selectedImageUri.toString());
                    intent.putExtra(EXTRA_MESSAGE_MESSAGE,edtMessage.getText().toString());

                    startActivity(intent);
                }
            }

        }
    }
}
