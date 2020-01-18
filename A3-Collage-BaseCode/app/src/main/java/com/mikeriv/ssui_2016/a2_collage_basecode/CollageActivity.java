package com.mikeriv.ssui_2016.a2_collage_basecode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.mikeriv.ssui_2016.a2_collage_basecode.drawing.VisualElement;
import com.mikeriv.ssui_2016.a2_collage_basecode.tests.CollageViewTestHelper;
import com.mikeriv.ssui_2016.a2_collage_basecode.views.CollageView;

public class CollageActivity extends AppCompatActivity {

    public static final String TAG = "SSUI-MOBILE-COLLAGE-TESTS";

    // The toolbar with the settings icon
    //带有设置图标的工具栏
    private Toolbar mSupportActionBar;
    // The container holding out collage view
    //容纳拼贴画视图的容器
    private FrameLayout mCollageFrame;
    // The host view that holds a reference to our custom view hierarchy
    private CollageView mCollageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Magic for creating the settings icon/choices
        setContentView(R.layout.activity_collage);
        mSupportActionBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mSupportActionBar);

        // Grab "Frame" then create basic view to hold the collage
        mCollageFrame = (FrameLayout) findViewById(R.id.frame_collage);
        if (mCollageFrame != null) {
            mCollageView = new CollageView(this);
            mCollageFrame.addView(mCollageView);
            // TODO create the root visual element of your collage view
            //创建大学视图的根视觉元素
            // using your created BaseVisualElement class and set it mCollageView.setChildVisualElement(rootVisualElement);
            //使用您创建的BaseVisualElement类并将其设置为mCollageView.setChildVisualElement（rootVisualElement）;
            refreshViewHierarchy();
        }

    }

    /**
     * Gets called every time the user presses the menu button.
     * Use if your menu is dynamic.
     *每次用户按下菜单按钮时都会调用它。 如果菜单是动态的，请使用。
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        // Adds out test options to the menu bar
        CollageViewTestHelper.createTestMenuItems(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        boolean didHandleAction = CollageViewTestHelper.onTestItemSelected(
                item,
                mCollageView,
                this);
        if (didHandleAction) {
            refreshViewHierarchy();
        }
        return didHandleAction;
    }

    /**
     * Function to put your custom collage into
     * You may create additional methods like this to test
     * functionality
     * 将您的自定义拼贴放入其中
     * 您可以创建这样的其他方法来测试功能
     */
    private void initCustomCollage() {
        // TODO: Part 2: Implement a Custom Collage

        // Finish off by refreshing the view Hierarchy
        refreshViewHierarchy();
    }

    /**
     * Helper method to refresh the custom drawing hierarchy
     * 帮助方法刷新自定义绘图层次结构
     */
    private void refreshViewHierarchy() {
        if (mCollageView == null) {
            return;
        }
        mCollageView.requestLayout();
        mCollageView.invalidate();
    }

}
