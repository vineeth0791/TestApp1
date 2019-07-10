package vineeth.test.com.testapp.fragment_example;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import vineeth.test.com.testapp.R;

public class Fragment1 extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle values)
    {
      return inflater.inflate(R.layout.fragment1,parent,false);
    }
}
