package com.lollipop.uprint.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lollipop.uprint.Config;
import com.lollipop.uprint.R;
import com.lollipop.uprint.font.RobotoTextView;
import com.lollipop.uprint.utils.BaseApiService;
import com.lollipop.uprint.utils.UtilsApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private RobotoTextView btnLogin;
    private EditText edtUsername, edtPassword;

    BaseApiService mApiService;
    Context mContext;

    private String id, uname;
    Config session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;
        session = new Config(getApplicationContext());
        mApiService = UtilsApi.getAPIService();

        btnLogin = findViewById(R.id.btnLogin);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString();
                String pass = edtPassword.getText().toString();

                mApiService.data("{\n" +
                        "\"case\":\"user_login\",\n" +
                        "\"user\":\""+username+"\",\n" +
                        "\"password\":\""+pass+"\"\n" +
                        "}").enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("status").equals("200")) {
                                    String msg = jsonRESULTS.getString("message");
                                    JSONArray data = jsonRESULTS.getJSONArray("data");
                                    for(int i=0; i<data.length(); i++){
                                        JSONObject obj = data.getJSONObject(i);
                                        id = obj.getString("id");
                                        uname = obj.getString("username");
                                    }
//                                    Toast.makeText(mContext, "ID:"+id+" & "+"USER:"+uname, Toast.LENGTH_SHORT).show();
                                    session.createLoginSession(id,uname);
                                    Intent intent = new Intent(mContext,MainActivity.class);
                                    startActivity(intent);

                                } else {
                                    String msg = jsonRESULTS.getString("message");
                                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });
    }

}
