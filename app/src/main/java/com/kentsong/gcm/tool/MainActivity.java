package com.kentsong.gcm.tool;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.kentsong.gcm.tool.utils.BundleHelper;

import org.joda.time.LocalDateTime;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

public class MainActivity extends Activity {

    private final String TAG = "MainActivity";
    private EditText edit_senderId;
    private TextView tv_pushToken;
    private TextView tv_log;
    private Button btn_getPushToken;
    private Button btn_mailTo;
    private Button btn_clean;
    private String pushID;
    private String senderID;
    private ProgressDialog progress;
    private MyReceiver myReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        init();
    }

    private void init() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //註冊廣播接收器
        myReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.test");
        this.registerReceiver(myReceiver, filter);
    }


    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-02-24 22:47:58 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        edit_senderId = (EditText) findViewById(R.id.edit_sender_id);
        tv_pushToken = (TextView) findViewById(R.id.tv_push_token);
        tv_log = (TextView) findViewById(R.id.tv_log);
        btn_getPushToken = (Button) findViewById(R.id.btn_getPushToken);
        btn_mailTo = (Button) findViewById(R.id.btn_mailTo);
        btn_clean = (Button) findViewById(R.id.btn_clean);


        btn_getPushToken.setOnClickListener(btnListener);
        btn_mailTo.setOnClickListener(btnListener);
        btn_clean.setOnClickListener(btnListener);

        progress = new ProgressDialog(MainActivity.this);
        progress.setMessage("處理中");
    }


    public Button.OnClickListener btnListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.btn_getPushToken) {
                senderID = edit_senderId.getText().toString();
                getGCM_PUSH_ID();
            } else if (v.getId() == R.id.btn_mailTo) {

                /* Create the Intent */
                Intent emailIntent = new Intent(Intent.ACTION_SEND);

                /* Fill it with Data */
                emailIntent.setType("text/plain");
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Gcm Push Token");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, tv_pushToken.getText().toString());

                /* Send it */
                startActivity(emailIntent);

            } else if (v.getId() == R.id.btn_clean) {
                tv_log.setText("");
//              Intent intent = getPackageManager().getLaunchIntentForPackage("com.chbank.guard");
//              startActivity(intent);
            }

        }
    };

    private void getGCM_PUSH_ID() {
        new AsyncTask<Object, Object, Boolean>() {

            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                progress.show();
            }

            @Override
            protected Boolean doInBackground(Object... arg0) {
                // TODO Auto-generated method stub
                InstanceID instanceID = InstanceID.getInstance(MainActivity.this);
                try {
                    String token = instanceID.getToken(senderID,
                            GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                    if (token != null && !token.equals("")) {
                        pushID = token;
                        Log.d("token", token);
                        //訂閱主題"/topics/foo-bar"
                        subscribeTopics(pushID);
                        return true;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return true;
            }

            @Override
            protected void onPostExecute(Boolean msg) {
                tv_pushToken.setText(pushID);
                progress.dismiss();

            }
        }.execute(null, null, null);

    }

    private void subscribeTopics(String token) throws IOException {
        GcmPubSub pubSub = GcmPubSub.getInstance(this);
        pubSub.subscribe(token, "/topics/foo-bar", null);
    }

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            tv_log.setText(BundleHelper.toString(bundle) + tv_log.getText());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 註銷接收廣播服務
        this.unregisterReceiver(myReceiver);
    }


}
