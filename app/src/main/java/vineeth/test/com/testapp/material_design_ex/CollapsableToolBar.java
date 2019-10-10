package vineeth.test.com.testapp.material_design_ex;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import vineeth.test.com.testapp.R;

public class CollapsableToolBar extends AppCompatActivity {
    private Context context;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collapsable_toolbar_ex);
        context=CollapsableToolBar.this;
        getSupportActionBar().hide();

        RecyclerView listView=findViewById(R.id.recycler_view);
        String[] items=new String[]{"movies","Kabir Singh","Uri: The Surgical Strike","\tBharat","Mission Mangal","Gully Boy",
                "movies","Kabir Singh","Uri: The Surgical Strike","\tBharat","Mission Mangal","Gully Boy",
                "movies","Kabir Singh","Uri: The Surgical Strike","\tBharat","Mission Mangal","Gully Boy",
                "movies","Kabir Singh","Uri: The Surgical Strike","\tBharat","Mission Mangal","Gully Boy",
                "movies","Kabir Singh","Uri: The Surgical Strike","\tBharat","Mission Mangal","Gully Boy",
                "movies","Kabir Singh","Uri: The Surgical Strike","\tBharat","Mission Mangal","Gully Boy"};
        RecyclerAdapter adapter=new RecyclerAdapter(context,items);
        listView.setLayoutManager(new LinearLayoutManager(context));
        listView.setAdapter(adapter);

        /*CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Title");*/
    }


}
