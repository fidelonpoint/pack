package gdg.ng.pack.state;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import gdg.ng.pack.R;


    public class State_Adapter extends ArrayAdapter<Stateitem> {
        Context mContext;
        int resourceId;
        Stateitem item;
        ArrayList<Stateitem> data = new ArrayList<Stateitem>();

        public State_Adapter(Context context, int layoutResourceId, ArrayList<Stateitem> data) {
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
                holder.active_state = (RadioButton) itemView.findViewById(R.id.radioButton);
                holder.title = (TextView) itemView.findViewById(R.id.state_names);

                itemView.setTag(holder);
            } else {
                holder = (ViewHolder) itemView.getTag();
            }


            item = getItem(position);
            holder.title.setText(item.getState_name());



            return itemView;
        }

        static class ViewHolder {
            RadioButton active_state;
            TextView title;
        }

    }

