package bigbigboy.example.com.pullcontrolcenter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import bigbigboy.example.com.pullcontrolcenter.Util.DensityUtils;
import bigbigboy.example.com.pullcontrolcenter.Util.Loger;


public class FrameActivity extends BaseActivity {
    View frameView;
    int touchDownY;
    FrameLayout rootView;
    int screenHeight;
    int controlViewHeight;

    LinearLayout poplinearlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float);
        screenHeight = DensityUtils.getScreenH(this);
        controlViewHeight = DensityUtils.dip2px(this, 260);
        rootView = (FrameLayout) getWindow().getDecorView();

    }

    public void onBtnClick(View v) {
        if (v.getId() == R.id.popview) {
            dismissFloatView();
            return;
        }
        Toast.makeText(this, "您点击了控制中心菜单按钮" + v.getId(), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDownY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                if ((touchDownY > (screenHeight - 15)) && (touchDownY > (ev.getRawY() + 10))) {
                    Loger.debug("frameView--->" + ev.getRawY());
                    //  int height = (int) (touchDownY - ev.getRawY());
                    if (frameView == null) {
                        frameView = LayoutInflater.from(this).inflate(R.layout.floatview, null);
                        frameView.setVisibility(View.INVISIBLE);
                        rootView.addView(frameView);
                        poplinearlayout = (LinearLayout) frameView.findViewById(R.id.poplinearlayout);
                    } else
                        refreshCtrolView((int) ev.getRawY());
                    //  frameView.refreshCtrolView(DensityUtils.getScreenW(this), (int) (e1.getRawY() - e2.getRawY()));
                }
                break;
            case MotionEvent.ACTION_UP:
                if (frameView != null)
                    refreshCtrolView(screenHeight - DensityUtils.dip2px(this, 260));
                Loger.debug("ACTION_UP--->" + ev.getRawY());
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onBackPressed() {
        if (dismissFloatView()) return;

        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        dismissFloatView();
        super.onDestroy();
    }

    public boolean dismissFloatView() {
        if (frameView != null) {
            rootView.removeView(frameView);
            frameView = null;
            return true;
        }
        return false;
    }

    public void refreshCtrolView(int y) {
        int plusHeight = (screenHeight - y) - controlViewHeight;
        if (plusHeight > 0) {
            ViewGroup.LayoutParams layoutParams = poplinearlayout.getLayoutParams();
            layoutParams.height = (int) (controlViewHeight + plusHeight * 0.4);
            poplinearlayout.setLayoutParams(layoutParams);
            y = (int) (screenHeight - (controlViewHeight + plusHeight * 0.3));
            Loger.debug("poplinearlayout.getY()---->" + poplinearlayout.getY() + "\nplusHeight---->" + plusHeight);
        }
        poplinearlayout.setY(y);
        if (frameView.getVisibility() == View.INVISIBLE) {
            frameView.setVisibility(View.VISIBLE);
        }

        //  poplinearlayout.requestLayout();
        //super.refreshCtrolView();
    }
}
