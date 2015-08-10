package com.teachertipsforparents.bullybx.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.teachertipsforparents.bullybx.R;
import com.teachertipsforparents.bullybx.customclasses.Report;

/**
 * Created by christinajackey on 8/5/15.
 */
public class ReportAdapter extends BaseAdapter {

    Context mContext;
    Report[] mReportArray;
    public final static String TAG = ReportAdapter.class.getSimpleName();

    public ReportAdapter(Context context, Report[] reports ) {
        mContext = context;
        mReportArray = reports;


    }

    @Override
    public int getCount() {
        return mReportArray.length;
    }

    @Override
    public Object getItem(int position) {
        return mReportArray[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;    // we wont use
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;


        if (convertView == null) {
            // brand new
            convertView = LayoutInflater.from(mContext).inflate(R.layout.report_list_item, null);
            holder = new ViewHolder();

            holder.victimNameTextView = (TextView) convertView.findViewById(R.id.victimNameTextView);
            holder.dateOfIncidentTextView = (TextView) convertView.findViewById(R.id.dateTextView);
            holder.ifThereWasAnImageView = (ImageView) convertView.findViewById(R.id.ifThereWasAnImage);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Report report = mReportArray[position];
        String dateOfReport = report.getDateAndTime();
        String nameOfVictim = report.getVictimName();

        holder.victimNameTextView.setText("Victim: " + nameOfVictim);
        holder.dateOfIncidentTextView.setText("Sent: "  + dateOfReport);

/*

        final Bitmap image = report.getImage();

        if (image != null ) {

            holder.ifThereWasAnImageView.setVisibility(View.VISIBLE);
        } else {
            holder.ifThereWasAnImageView .setVisibility(View.INVISIBLE);

        }

*/

        return convertView;
    }
    }



 class ViewHolder {
        TextView victimNameTextView;
        TextView dateOfIncidentTextView;
        ImageView ifThereWasAnImageView;


    }

/*





    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            // brand new
            convertView = LayoutInflater.from(mContext).inflate(R.layout.resource_list_item, null);
            holder = new ViewHolder();

            holder.recourseName = (TextView) convertView.findViewById(R.id.resourceTextView);
            holder.viewRecourse = (Button) convertView.findViewById(R.id.viewRecourceButton);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Recourse recourse = mRecourseArray[position];

        String nameOfRecurse = recourse.getName();
        final String link = recourse.getLink();

        holder.recourseName.setText(nameOfRecurse);
        holder.viewRecourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(mContext  , WebviewActivity.class);
                browserIntent.putExtra("link" , link);
                mContext.startActivity(browserIntent);
            }
        });









        return convertView;
    }

    public static class ViewHolder {
        TextView recourseName;
        Button viewRecourse;
    }


}
*/
