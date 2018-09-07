package apps.pappaya.narayanaschoolentry.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import apps.pappaya.narayanaschoolentry.Activities.SingleStudentViewActivity;
import apps.pappaya.narayanaschoolentry.DataModels.StudentsDataModel;
import apps.pappaya.narayanaschoolentry.R;


/**
 * Created by Ravi Tamada on 21/02/17.
 * www.androidhive.info
 */

public class StudentsListAdapter extends RecyclerView.Adapter<StudentsListAdapter.MyViewHolder> {
    private Context mContext;

    ArrayList<StudentsDataModel> StudentsArrayList;



    public StudentsListAdapter(Context mContext, ArrayList<StudentsDataModel> StudentsArrayList) {
        this.mContext = mContext;
        this.StudentsArrayList = StudentsArrayList;

        Log.d(" StudentsArrayList "," SIZE ## "+StudentsArrayList.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_single_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        StudentsDataModel studentsDataModel=StudentsArrayList.get(position);
        holder.student_name_TV.setText(studentsDataModel.getStudent_Name());

        holder.Student_LinLay_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            Intent intent=    new Intent(mContext, SingleStudentViewActivity.class);

                Bundle b = new Bundle();
                b.putSerializable("SingleStudent", StudentsArrayList.get(position));
                intent.putExtras(b);

//                intent.putExtra("SingleStudent",StudentsArrayList.get(position));
                mContext.startActivity(intent);

            }
        });

    }



//    @Override
//    public long getItemId(int position) {
//        return StudentsArrayList.get(position).getStudent_ID();
//    }


    @Override
    public int getItemCount() {
        return StudentsArrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView student_name_TV;
        LinearLayout Student_LinLay_ID;


        public MyViewHolder(View view) {
            super(view);

            student_name_TV=(TextView)view.findViewById(R.id.student_name_TV);
            Student_LinLay_ID=(LinearLayout) view.findViewById(R.id.Student_LinLay_ID);

        }

    }

}