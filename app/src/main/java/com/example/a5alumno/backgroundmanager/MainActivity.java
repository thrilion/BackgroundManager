package com.example.a5alumno.backgroundmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bntThread =(Button) findViewById(R.id.btnStartThread);
        bntThread.setOnClickListener(this);
        Button btnAsyncTasc = (Button) findViewById(R.id.btnStartAsyncTask);
        btnAsyncTasc.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnStartThread:
                break;
            case R.id.btnStartAsyncTask:
                break;
        }
    }
}
