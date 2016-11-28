package com.example.a5alumno.backgroundmanager;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";
    private static final int SLEEP_TIME = 3;

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
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        Log.e(MainActivity.TAG, "Thread started");
                        runOnUiThread(new Runnable(){ // Acciones a hacer en el UiThread/MainThread
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Sleeping...", Toast.LENGTH_LONG).show();
                            }
                        });
                        // Mandamos a "dormir" el  thread
                        sleepForAWhile(MainActivity.SLEEP_TIME);
                    }
                }).start();
                break;
            case R.id.btnStartAsyncTask:
                //new MyAsyncTask().execute();
                break;
        }
    }

    private void sleepForAWhile(int numSeconds){
        long endTime = System.currentTimeMillis() + (numSeconds * 1000);

        while(System.currentTimeMillis() < endTime){
            synchronized (this) {
                try {
                    Log.e(MainActivity.TAG, "Sleeping...");
                    this.wait(endTime - System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class MyAsyncTask extends AsyncTask<Integer, Integer, String> {

        @Override
        protected String doInBackground(Integer... integers) {

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
