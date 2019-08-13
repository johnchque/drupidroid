package ch.drupalmountaincamp.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ch.drupalmountaincamp.android.retrofit.ApiService;
import ch.drupalmountaincamp.android.retrofit.SharedPrefManager;
import ch.drupalmountaincamp.android.retrofit.UtilsApi;
import ch.drupalmountaincamp.android.model.LoginData;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    SharedPrefManager sharedPrefManager;
    ApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPrefManager = new SharedPrefManager(this);
        if (sharedPrefManager.getSPIsLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        mEmailView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);
        sharedPrefManager = new SharedPrefManager(LoginActivity.this);
        mApiService = UtilsApi.getAPIService();
        Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);

        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mProgressView = findViewById(R.id.login_progress);
    }

    private void attemptLogin() {
        final String email = mEmailView.getText().toString().trim();
        final String password = mPasswordView.getText().toString().trim();

        mApiService.loginRequest(new LoginData(email, password))
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            mProgressView.setVisibility(View.GONE);
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (!jsonRESULTS.getString("csrf_token").isEmpty()) {
                                    String csrf_token = jsonRESULTS.getString("csrf_token");
                                    String user_id = jsonRESULTS.getJSONObject("current_user").getString("uid");
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_CSRF_TOKEN, csrf_token);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_USER_ID, user_id);
                                    sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_IS_LOGGED_IN, true);
                                    String basic_auth = email + ":" + password;
                                    byte[] bytes_basic_auth = basic_auth.getBytes();
                                    String encoded_basic_auth = android.util.Base64.encodeToString(bytes_basic_auth, android.util.Base64.DEFAULT);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_BASIC_AUTH, "Basic " + encoded_basic_auth.trim());

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    String error_message = jsonRESULTS.getString("error_msg");
                                    Toast.makeText(LoginActivity.this, error_message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            mProgressView.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        mProgressView.setVisibility(View.GONE);
                    }
                });
    }

}
