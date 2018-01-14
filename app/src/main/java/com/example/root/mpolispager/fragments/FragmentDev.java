package com.example.root.mpolispager.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.root.mpolispager.R;

/**
 * Created by root on 6.1.18.
 */

public class FragmentDev extends Fragment {

    View v;
    ImageView img;
    Button send;
    EditText name, subject, email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_dev, container, false);

        return v;
    }

    @Override
    public void onResume() {
        initV();
        super.onResume();
    }

    private void initV(){
        send = (Button) v.findViewById(R.id.dev_send);
        name = (EditText) v.findViewById(R.id.dev_et_name);
        email = (EditText) v.findViewById(R.id.dev_et_email);
        subject = (EditText) v.findViewById(R.id.dev_et_subject);
        img = (ImageView) v.findViewById(R.id.dev_img);
        Glide.with(img.getContext()).load(R.drawable.info_1).into(img);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSend()) send();
            }
        });

    }

    private boolean isSend() {
        if(email.getText().toString().contains("@") && email.getText().toString().length()>1
                && name.getText().toString().length()>1 &&
                subject.getText().toString().length()>1) return true;
        else {

            Toast.makeText(getActivity()," Требуется правильно заполнить форму...",Toast.LENGTH_SHORT).show();
            return false;
        }


    }

    private void send(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        String[] TO = {"vteme@anika-cs.by"};
        String[] FROM = {email.getText().toString()};
        intent.setData(Uri.parse("mailto:"));
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, TO);
        intent.putExtra(Intent.EXTRA_EMAIL, FROM);
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        intent.putExtra(Intent.EXTRA_TEXT, "Имя: "+name.getText().toString()+"\n\n"+subject.getText().toString());
        startActivity(Intent.createChooser(intent, "Send Email"));
    }
}
