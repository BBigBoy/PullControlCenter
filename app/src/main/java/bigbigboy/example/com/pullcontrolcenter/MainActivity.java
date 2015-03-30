package bigbigboy.example.com.pullcontrolcenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void toFloat(View v) {
        switch (v.getId()) {
            case R.id.popup:startActivity(new Intent(this,PopupActivity.class));
                break;

            case R.id.frame:startActivity(new Intent(this,FrameActivity.class));
                break;
        }

    }
}
