package vineeth.test.com.testapp.material_design_ex.bottom_sheets;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import vineeth.test.com.testapp.R;
import vineeth.test.com.testapp.material_design_ex.RecyclerAdapter;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle args)
    {
        args = getArguments();
        Toast.makeText(getActivity(),args.getString("key"),Toast.LENGTH_SHORT).show();
       View view =  inflater.inflate(R.layout.collapsable_toolbar_ex,parent);


      Context context = getActivity();

        RecyclerView listView=view.findViewById(R.id.recycler_view);
        String[] items=new String[]{"movies","Kabir Singh","Uri: The Surgical Strike","\tBharat","Mission Mangal","Gully Boy",
                "movies","Kabir Singh","Uri: The Surgical Strike","\tBharat","Mission Mangal","Gully Boy",
                "movies","Kabir Singh","Uri: The Surgical Strike","\tBharat","Mission Mangal","Gully Boy",
                "movies","Kabir Singh","Uri: The Surgical Strike","\tBharat","Mission Mangal","Gully Boy",
                "movies","Kabir Singh","Uri: The Surgical Strike","\tBharat","Mission Mangal","Gully Boy",
                "movies","Kabir Singh","Uri: The Surgical Strike","\tBharat","Mission Mangal","Gully Boy"};
        RecyclerAdapter adapter=new RecyclerAdapter(context,items);
        listView.setLayoutManager(new LinearLayoutManager(context));
        listView.setAdapter(adapter);

      return view;
    }
}
