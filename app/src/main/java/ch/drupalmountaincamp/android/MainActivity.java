package ch.drupalmountaincamp.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ch.drupalmountaincamp.android.retrofit.SharedPrefManager;

public class MainActivity extends AppCompatActivity {

    Button mGetNode;
    Button mAddNode;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPrefManager = new SharedPrefManager(this);
        if (!sharedPrefManager.getSPIsLoggedIn()) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        mGetNode = findViewById(R.id.btn_get_node);
        mAddNode = findViewById(R.id.btn_add_node);

        mGetNode.setOnClickListener(getNodeSubmit);
        mAddNode.setOnClickListener(addNodeSubmit);
    }

    private View.OnClickListener addNodeSubmit = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, AddNodeActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener getNodeSubmit = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, GetNodeActivity.class);
            startActivity(intent);
        }
    };

}
