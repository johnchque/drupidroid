package ch.drupalmountaincamp.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import ch.drupalmountaincamp.android.model.Node;
import ch.drupalmountaincamp.android.model.Title;
import ch.drupalmountaincamp.android.model.Type;
import ch.drupalmountaincamp.android.retrofit.ApiService;
import ch.drupalmountaincamp.android.retrofit.SharedPrefManager;
import ch.drupalmountaincamp.android.retrofit.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNodeActivity extends AppCompatActivity {

    SharedPrefManager sharedPrefManager;
    ApiService mApiService;
    EditText mTxtNodeTitle;
    EditText mTxtNodeType;
    Button mAddNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_node);
        mTxtNodeTitle = findViewById(R.id.txt_node_title);
        mTxtNodeType = findViewById(R.id.txt_node_type);
        sharedPrefManager = new SharedPrefManager(this);
        mApiService = UtilsApi.getAPIService();

        mAddNode = findViewById(R.id.btn_add_node);
        mAddNode.setOnClickListener(getNodeSubmit);
    }

    private View.OnClickListener getNodeSubmit = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String basic_auth = sharedPrefManager.getSPBasicAuth();
            String csrf_token = sharedPrefManager.getSPCsrfToken();

            String node_title = mTxtNodeTitle.getText().toString();
            ArrayList<Title> title = new ArrayList<>();
            title.add(0, new Title(node_title));

            String node_type = mTxtNodeType.getText().toString();
            ArrayList<Type> type = new ArrayList<>();
            type.add(0, new Type(node_type));

            Node node = new Node(title, type);
            mApiService.addNode(basic_auth, csrf_token, node)
                    .enqueue(new Callback<Node>() {
                        @Override
                        public void onResponse(Call<Node> call, Response<Node> response) {
                            if (response.isSuccessful()) {
                                Intent intent = new Intent(AddNodeActivity.this, GetNodeActivity.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(Call<Node> call, Throwable t) {
                            Log.e("debug", "onFailure: ERROR > " + t.toString());
                        }
                    });
        }
    };

}
