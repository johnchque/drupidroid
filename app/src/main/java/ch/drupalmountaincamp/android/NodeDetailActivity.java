package ch.drupalmountaincamp.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import ch.drupalmountaincamp.android.model.Node;
import ch.drupalmountaincamp.android.retrofit.ApiService;
import ch.drupalmountaincamp.android.retrofit.SharedPrefManager;
import ch.drupalmountaincamp.android.retrofit.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NodeDetailActivity extends AppCompatActivity {

    SharedPrefManager sharedPrefManager;
    ApiService mApiService;
    String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_detail);

        sharedPrefManager = new SharedPrefManager(this);
        mApiService = UtilsApi.getAPIService();

        Intent intent = getIntent();
        String node_id = intent.getStringExtra("node_id");
        requestData(node_id);
    }

    private void requestData(String node_id) {
        String basic_auth = sharedPrefManager.getSPBasicAuth();

        mApiService.getNode(basic_auth, node_id)
                .enqueue(new Callback<Node>() {
                    @Override
                    public void onResponse(Call<Node> call, Response<Node> response) {
                        if (response.isSuccessful()) {
                            try {
                                Node node = response.body();

                                mTitle = node.getTitle().get(0).getValue();
                                Log.i("Node title", node.getTitle().get(0).getValue());
                                setTitle(mTitle);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Node> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                    }
                });
    }
}
