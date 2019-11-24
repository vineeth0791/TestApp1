package vineeth.test.com.testapp.fragment_example;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import vineeth.test.com.testapp.R;

public class Fragment1 extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle values)
    {
        Log.d("Fragment1","Inside Fragment1 on create view");
        setRetainInstance(true);
      return inflater.inflate(R.layout.fragment1,parent,false);

    }

    public void onAttach(Context context)
    {
        super.onAttach(context);
        Log.d("Fragment1","Inside Fragment1 on attach");
    }

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d("Fragment1","Inside Fragment1 on create");
    }

    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        Log.d("Fragment1","Inside Fragment1 on activity created");
    }

    public void onStart()
    {
        super.onStart();
        Log.d("Fragment1","Inside Fragment1 on start");
    }

    public void onResume()
    {
        super.onResume();
        Log.d("Fragment1","Inside Fragment1 on resume");
    }

    public void onPause()
    {
        super.onPause();
        Log.d("Fragment1","Inside Fragment1 on pause");
    }

    public void onStop()
    {
        super.onStop();
        Log.d("Fragment1","Inside Fragment1 on stop");
    }

    public void onDestroyView()
    {
        super.onDestroyView();
        Log.d("Fragment1","Inside Fragment1 on destroy view");
    }

    public void onDestroy()
    {
        super.onDestroy();
        Log.d("Fragment1","Inside Fragment1 on destroy");
    }

    public void onDetach()
    {
        super.onDetach();
        Log.d("Fragment1","Inside Fragment1 on detach");
    }
}
