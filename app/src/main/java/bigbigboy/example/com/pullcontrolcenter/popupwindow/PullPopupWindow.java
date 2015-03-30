package bigbigboy.example.com.pullcontrolcenter.popupwindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import bigbigboy.example.com.pullcontrolcenter.R;
import bigbigboy.example.com.pullcontrolcenter.Util.DensityUtils;
import bigbigboy.example.com.pullcontrolcenter.Util.Loger;

/**
 * Created by BigBigBoy on 2015/3/29.
 */
public class PullPopupWindow extends PopupWindow {
    private Context mContext;
    View rootView;
    LinearLayout poplinearlayout;
    int screenHeight;
    int controlViewHeight;

    public PullPopupWindow(Context mContext) {
        this.mContext = mContext;
        rootView = LayoutInflater.from(mContext).inflate(R.layout.floatview, null);
        setContentView(rootView);
        screenHeight = DensityUtils.getScreenH(mContext);
        controlViewHeight = DensityUtils.dip2px(mContext, 260);
        poplinearlayout = (LinearLayout) rootView.findViewById(R.id.poplinearlayout);
        poplinearlayout.setX(0);
        poplinearlayout.setY(screenHeight);
        setWidth(DensityUtils.getScreenW(mContext));
        setHeight(DensityUtils.getScreenH(mContext));
        setFocusable(false);
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
        //  poplinearlayout.requestLayout();
        //super.refreshCtrolView();
    }
}
