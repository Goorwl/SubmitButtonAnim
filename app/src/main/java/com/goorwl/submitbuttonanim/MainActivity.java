package com.goorwl.submitbuttonanim;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SubmitButtonView buttonView = findViewById(R.id.btn_submit);

        buttonView.setOnViewClickListener(new SubmitButtonView.OnViewClickListener() {
            @Override
            public void animStart() {
                Toast.makeText(MainActivity.this, "点击了控件", Toast.LENGTH_SHORT).show();
                buttonView.startAnim();
            }

            @Override
            public void animEnd() {

            }
        });
    }
}
