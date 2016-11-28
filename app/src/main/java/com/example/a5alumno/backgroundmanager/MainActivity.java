package com.example.a5alumno.backgroundmanager;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";
    private static final int SLEEP_TIME = 3;

    private ProgressBar mProgressBar;
    private android.os.Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bntThread =(Button) findViewById(R.id.btnStartThread);
        bntThread.setOnClickListener(this);
        Button btnAsyncTasc = (Button) findViewById(R.id.btnStartAsyncTask);
        btnAsyncTasc.setOnClickListener(this);

        this.mProgressBar = (ProgressBar) this.findViewById(R.id.progressBar);
        this.mProgressBar.setProgress(0);
        // If using a 'Handler' to update a thread instead of the 'Activity.runOnUiThread()' method
        this.mHandler = new Handler();

        // The next line reports a runtime error (ANR) unless the operation is performed in a worker thread
        //this.sleepForAWhile(20);
    }

    @Override
    public void onClick(View view) {
        mProgressBar.setProgress(0);

        switch (view.getId()){
            case R.id.btnStartThread:
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        Log.e(MainActivity.TAG, "Thread started");
                        // Acciones a hacer en el UiThread/MainThread van dentro de un runOnUiThread
                        runOnUiThread(new Runnable(){
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Sleeping...", Toast.LENGTH_SHORT).show();
                            }
                        });
                        // Mandamos a "dormir" el  thread
                        sleepForAWhile(MainActivity.SLEEP_TIME);
                        runOnUiThread(new Runnable(){
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Finished", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();
                break;
            case R.id.btnStartAsyncTask:
                new MyAsyncTask(this).execute(MainActivity.SLEEP_TIME);
                break;
        }
    }

    // Método para mandar a "dormir" el thread (simular una acción pesada)
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
        private Context mContext;

        public MyAsyncTask(Context anyContext) {
            this.mContext = anyContext;
        }

        @Override
        protected String doInBackground(Integer... params) {
            for (int idx = 1; idx <= 5; idx++) {
                sleepForAWhile(params[0]);
                publishProgress(idx * 20);
            }
            return "AsyncTask finished";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mProgressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String retString) {
            super.onPostExecute(retString);
            Toast.makeText(this.mContext, retString, Toast.LENGTH_SHORT).show();
        }
    }
}
