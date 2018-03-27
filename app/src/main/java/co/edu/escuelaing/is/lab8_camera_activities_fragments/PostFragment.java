package co.edu.escuelaing.is.lab8_camera_activities_fragments;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PostFragment extends Fragment {

    private TextView txtViewMessage;
    private ImageView imgViewPicture;
    private static final String EXTRA_MESSAGE_MESSAGE = "co.edu.escuelaing.is.lab8.MESSAGE";
    private static final String EXTRA_MESSAGE_PICTURE = "co.edu.escuelaing.is.lab8.PICTURE";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.activity_post_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        txtViewMessage = view.findViewById(R.id.txtViewMessage);
        imgViewPicture = view.findViewById(R.id.imgViewPicture);

        Bundle bundle = getArguments();
        String message = bundle.getString(EXTRA_MESSAGE_MESSAGE);
        txtViewMessage.setText(message);

        imgViewPicture.setImageURI(Uri.parse(bundle.getString(EXTRA_MESSAGE_PICTURE)));
    }
}
