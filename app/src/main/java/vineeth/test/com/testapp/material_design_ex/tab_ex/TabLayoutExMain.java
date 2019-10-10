package vineeth.test.com.testapp.material_design_ex.tab_ex;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import vineeth.test.com.testapp.R;

public class TabLayoutExMain extends AppCompatActivity {
   private Context context;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_layout_main);
        context=TabLayoutExMain.this;
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new CustomTabAdapter(context,getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }
}
