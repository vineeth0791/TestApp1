package vineeth.test.com.testapp.material_design_ex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import vineeth.test.com.testapp.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.AdapterViewHolder>
{
    String[] items;
    Context context;
    public RecyclerAdapter(Context context, String[] items)
    {
        this.items = items;
        this.context = context;
    }
    public  class AdapterViewHolder extends RecyclerView.ViewHolder
    {
        TextView textView;
        public AdapterViewHolder(View view)
        {
            super(view);
            textView=view.findViewById(R.id.item_name);

        }
    }

    public AdapterViewHolder onCreateViewHolder(ViewGroup parent, int type)
    {
        View view= LayoutInflater.from(context).inflate(R.layout.support_fab,parent,false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterViewHolder holder, int position)
    {
        holder.textView.setText(items[position]);
    }

    public int getItemCount()
    {
        return items.length;
    }
}
