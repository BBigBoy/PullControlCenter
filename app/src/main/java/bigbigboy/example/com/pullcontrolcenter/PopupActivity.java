package bigbigboy.example.com.pullcontrolcenter;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import bigbigboy.example.com.pullcontrolcenter.Util.DensityUtils;
import bigbigboy.example.com.pullcontrolcenter.Util.Loger;
import bigbigboy.example.com.pullcontrolcenter.popupwindow.PullPopupWindow;


public class PopupActivity extends BaseActivity {
    int screenHeight;
    PullPopupWindow pullPopupWindow;
    int touchDownY;
    /*
      *  for左右手势
      *  1.复制下面的内容到目标Activity
      *  2.目标Activity的onCreate()调用initGesture()
      *  3.目标Activity需implements OnTouchListener, OnGestureListener
      */
    private GestureDetector mGestureDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float);
        screenHeight = DensityUtils.getScreenH(this);
    }

    public void onBtnClick(View v) {
        if (v.getId() == R.id.popview) {
            pullPopupWindow.dismiss();
            pullPopupWindow = null;
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
                    if (pullPopupWindow == null) {
                        pullPopupWindow = new PullPopupWindow(this);
                        pullPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.START, 0, 0);
                    } else
                        pullPopupWindow.refreshCtrolView((int) ev.getRawY());
                    //  frameView.refreshCtrolView(DensityUtils.getScreenW(this), (int) (e1.getRawY() - e2.getRawY()));
                }
                break;
            case MotionEvent.ACTION_UP:
                if (pullPopupWindow != null && pullPopupWindow.isShowing())
                    pullPopupWindow.refreshCtrolView(screenHeight - DensityUtils.dip2px(this, 260));
                Loger.debug("ACTION_UP--->" + ev.getRawY());
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onBackPressed() {
        if (dismissPopupWindow()) return;

        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        dismissPopupWindow();
        super.onDestroy();
    }

    public boolean dismissPopupWindow() {
        if (pullPopupWindow != null && pullPopupWindow.isShowing()) {
            pullPopupWindow.dismiss();
            pullPopupWindow = null;
            return true;
        }
        return false;
    }
}
