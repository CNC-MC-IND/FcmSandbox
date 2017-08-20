package cnc_mc_monitor.ncs.kmu.ac.kr.fcmsandbox;

/**
 * Created by eddie on 2017. 8. 20..
 */

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + token);

        // 서버에 토큰 등록
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.

        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("gcm", token)
                .build();

        //request
        Request request = new Request.Builder()
                .addHeader("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InR0dHQiLCJwYXNzd29yZCI6IiQyYSQxMiRYb2w3WnBqd0NlS3IxS2s5Vm9oUXdlSTRDQ2ZWSGtwN1BSUkZ1NG1JMWZ6blRvSVQyWTB3YSIsIm5hbWUiOiJraW1zYW5nIiwib3JnYW5pemF0aW9uIjoic2V4aW9uIiwidG9rZW4iOiIiLCJ0aW1lc3RhbXAiOjE1MDIyNzYzMzksImlhdCI6MTUwMjg2OTM0OH0.adgFAJpRXQMqKv1EIxbhAVtu46pvj6rCb8cUrodcHPs")
                .url("http://localhost:3000/fcm/regist_gcm")
                .post(body)
                .build();

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}