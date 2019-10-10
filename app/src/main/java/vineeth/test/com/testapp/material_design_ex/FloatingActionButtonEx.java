package vineeth.test.com.testapp.material_design_ex;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import vineeth.test.com.testapp.R;

public class FloatingActionButtonEx extends AppCompatActivity {

    private Context context;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.fab_layout);
        context=FloatingActionButtonEx.this;
        setListView();
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displaySnackBar();
            }
        });
    }

    private void setListView()
    {
        RecyclerView listView=findViewById(R.id.list_view);
        String[] items=new String[]{"movies","Kabir Singh","Uri: The Surgical Strike","\tBharat","Mission Mangal","Gully Boy",
                "movies","Kabir Singh","Uri: The Surgical Strike","\tBharat","Mission Mangal","Gully Boy",
                "movies","Kabir Singh","Uri: The Surgical Strike","\tBharat","Mission Mangal","Gully Boy",
                "movies","Kabir Singh","Uri: The Surgical Strike","\tBharat","Mission Mangal","Gully Boy",
                "movies","Kabir Singh","Uri: The Surgical Strike","\tBharat","Mission Mangal","Gully Boy",
                "movies","Kabir Singh","Uri: The Surgical Strike","\tBharat","Mission Mangal","Gully Boy"};
        RecyclerAdapter adapter=new RecyclerAdapter(context,items);
        listView.setLayoutManager(new LinearLayoutManager(context));
        listView.setAdapter(adapter);

    }


    private void displaySnackBar()
    {
        Snackbar.make(findViewById(R.id.parent),"Inside snackbar",Snackbar.LENGTH_LONG)
                .setAction("Close", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
               .show();
    }
}
