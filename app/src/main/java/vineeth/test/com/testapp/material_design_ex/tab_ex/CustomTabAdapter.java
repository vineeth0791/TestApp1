package vineeth.test.com.testapp.material_design_ex.tab_ex;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import vineeth.test.com.testapp.fragment_example.Fragment1;
import vineeth.test.com.testapp.fragment_example.Fragment2;

public class CustomTabAdapter extends FragmentPagerAdapter {
    private final String[] tabs = new String[]{"First","Second"};
    public CustomTabAdapter(Context context, FragmentManager fragmentManager)
    {
        super(fragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT );
    }

    public Fragment getItem(int position)
    {
      switch (position)
      {
          case 0:
              return new Fragment1();
          case 1:
              return new Fragment2();
          default:
              return new Fragment1();
      }
    }

    public int getCount()
    {
        return tabs.length;
    }

    public CharSequence getPageTitle(int position)
    {
        return tabs[position];
    }
}
