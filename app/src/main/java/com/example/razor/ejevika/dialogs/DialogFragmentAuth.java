package com.example.razor.ejevika.dialogs;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.razor.ejevika.R;

/**
 * Created by Администратор on 10.02.2016.
 */
public class DialogFragmentAuth extends DialogFragment implements View.OnClickListener {

    public TextView userName;
    public TextView password;
    public Button btnSend;

    public DialogFragmentAuth() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.auth_dialog_fragment, container);
        Dialog fragment = getDialog();
        fragment.setTitle("Auth");

        userName = (TextView) v.findViewById(R.id.user_name);
        password = (TextView) v.findViewById(R.id.user_password);
        btnSend = (Button) v.findViewById(R.id.btn_send);
        btnSend.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send:
                Toast.makeText(getActivity(),"send",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
