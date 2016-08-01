package gdg.ng.pack.MyReportFeed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import gdg.ng.pack.R;


public class MyReportAdapter extends ArrayAdapter<MyReportItem> {
    Context mContext;
    int resourceId;
    MyReportItem item;
    ArrayList<MyReportItem> data = new ArrayList<MyReportItem>();

    public MyReportAdapter(Context context, int layoutResourceId, ArrayList<MyReportItem> data) {
        super(context, layoutResourceId, data);
        this.mContext = context;
        this.resourceId = layoutResourceId;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        ViewHolder holder = null;

        if (itemView == null) {
            final LayoutInflater layoutInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = layoutInflater.inflate(resourceId, parent, false);

            holder = new ViewHolder();
            holder.imgItem = (ImageView) itemView.findViewById(R.id.thumbImage2);
            holder.title = (TextView) itemView.findViewById(R.id.myreport_title);
            holder.address = (TextView) itemView.findViewById(R.id.myreport_location);
            holder.post_time = (TextView) itemView.findViewById(R.id.myreport_time);


            itemView.setTag(holder);
        } else {
            holder = (ViewHolder) itemView.getTag();
        }

        holder.imgItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent(getContext(), ReportDetials.class);

               // intent.putExtra("image",item.getPlaceHolder());
                //mContext.startActivity(intent);

                Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        item = getItem(position);
        holder.imgItem.setImageResource(item.getPlaceHolder());
        holder.title.setText(item.getTitle());
        holder.address.setText(item.getAddress());
        holder.post_time.setText(item.getTime());




        return itemView;
    }

    static class ViewHolder {
        ImageView imgItem;
        TextView title, address,support_count, post_time,seen_count;
    }

}