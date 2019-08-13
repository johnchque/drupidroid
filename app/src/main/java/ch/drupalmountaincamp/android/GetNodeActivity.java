package ch.drupalmountaincamp.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GetNodeActivity extends AppCompatActivity {

    Button mGetNode;
    EditText mTxtNodeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_node);

        mTxtNodeId = findViewById(R.id.txt_node_id);
        mGetNode = findViewById(R.id.btn_get_node_by_id);
        mGetNode.setOnClickListener(getNodeSubmit);
    }

    private View.OnClickListener getNodeSubmit = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(GetNodeActivity.this, NodeDetailActivity.class);
            String node_id = mTxtNodeId.getText().toString();
            intent.putExtra("node_id", node_id);
            startActivity(intent);
        }
    };

}
