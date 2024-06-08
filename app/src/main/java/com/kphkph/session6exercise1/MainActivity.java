package com.kphkph.session6exercise1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText et1,et2,et3,et4,etEmail;
    CheckBox cb1,cb2,cb3,cb4;
    Button btnResetValues,btnSendEmail;
    TextView tvMessage;

    boolean xAllSelected;
    boolean xSomeSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setViews();
        setListeners();
    }

    private void setListeners(){
        //checkBoxesListeners
        checkIfAllItemsAreChecked(cb1);
        checkIfAllItemsAreChecked(cb2);
        checkIfAllItemsAreChecked(cb3);
        checkIfAllItemsAreChecked(cb4);
        //resetValuesListener
        btnResetValues.setOnClickListener(v -> {
            resetValues();
        });
        //sendEmailListener
        btnSendEmail.setOnClickListener(v -> {
            sendEmail();
        });
        //emailEditTextListener
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnSendEmail.setEnabled(isValidEmail(s));
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    @SuppressLint("SetTextI18n")
    private void checkIfAllItemsAreChecked(CheckBox checkBox){
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            //checkAllAreSelected
            if(cb1.isChecked() && cb2.isChecked() && cb3.isChecked() && cb4.isChecked()){
                tvMessage.setText("Congratulations!");
                xAllSelected = true;
            }
            else{
                tvMessage.setText("");
                xAllSelected = false;
            }
            //checkSomeAreSelected
            xSomeSelected = cb1.isChecked() || cb2.isChecked() || cb3.isChecked() || cb4.isChecked();
        });
    }

    private void setViews(){
        //setEditTexts
        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        et3 = (EditText) findViewById(R.id.et3);
        et4 = (EditText) findViewById(R.id.et4);
        etEmail = (EditText) findViewById(R.id.etEmail);
        //setCheckBoxes
        cb1 = (CheckBox) findViewById(R.id.cb1);
        cb2 = (CheckBox) findViewById(R.id.cb2);
        cb3 = (CheckBox) findViewById(R.id.cb3);
        cb4 = (CheckBox) findViewById(R.id.cb4);
        //setButtons
        btnResetValues = (Button) findViewById(R.id.btnReset);
        btnSendEmail = (Button) findViewById(R.id.btnSendEmail);
        btnSendEmail.setEnabled(false);
        //setTextViews
        tvMessage = (TextView) findViewById(R.id.tvMessage);
    }

    private void resetValues(){
        cb1.setChecked(false);
        cb2.setChecked(false);
        cb3.setChecked(false);
        cb4.setChecked(false);
    }

    private void sendEmail(){
        if(!xSomeSelected){
            Toast.makeText(this,"Please complete at least one task to share your achievements!",Toast.LENGTH_LONG).show();
        }
        else{
            String mailTo = etEmail.getText().toString();

            StringBuilder content = new StringBuilder();

            if(cb1.isChecked()){
                content.append(et1.getText());
                content.append("\n");
            }
            if(cb2.isChecked()){
                content.append(et2.getText());
                content.append("\n");
            }
            if(cb3.isChecked()){
                content.append(et3.getText());
                content.append("\n");
            }
            if(cb4.isChecked()){
                content.append(et4.getText());
                content.append("\n");
            }


            Intent intent = new Intent(Intent.ACTION_SEND);

            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{mailTo});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Here Check My Completed Tasks");
            intent.putExtra(Intent.EXTRA_TEXT, content.toString());

            intent.setType("message/rfc822");

            startActivity(Intent.createChooser(intent, "Choose an Email client :"));
        }

    }

}