package com.tf_staff.parkemlandscape.adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.tf_staff.parkemlandscape.Models.AreaAdminParkingModel;
import com.tf_staff.parkemlandscape.R;

import java.util.List;


public class AreaAdminParkingHistoryAdapter extends RecyclerView.Adapter<AreaAdminParkingHistoryAdapter.MyViewHolder> {
    List<AreaAdminParkingModel> parkingModelList;
    Context context;

    public AreaAdminParkingHistoryAdapter(List<AreaAdminParkingModel> parkingModelList, Context context) {
        this.parkingModelList = parkingModelList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_admin_history, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AreaAdminParkingModel areaAdminParkingModel = parkingModelList.get(position);
        holder.checkInTextView.setText("In: " + areaAdminParkingModel.getCheckInTime());
        holder.checkOutTextView.setText("Out: " + areaAdminParkingModel.getCheckOutTime());
        holder.cashTextView.setText("Cash: " + areaAdminParkingModel.getCash());
        holder.vehicleTypeTextView.setText("Vehicle: " + areaAdminParkingModel.getVehicleType());
        holder.paymentModeTextView.setText("Payment: " + areaAdminParkingModel.getPaymentMode());
        holder.feedbackTextView.setText("Feedback: " + areaAdminParkingModel.getFeedback());
    }

    @Override
    public int getItemCount() {
        return parkingModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView checkInTextView;
        TextView checkOutTextView;
        TextView cashTextView;
        TextView vehicleTypeTextView;
        TextView paymentModeTextView;
        TextView feedbackTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            checkInTextView = (TextView) itemView.findViewById(R.id.checkInTextView);
            checkOutTextView = (TextView) itemView.findViewById(R.id.checkOutTextView);
            cashTextView = (TextView) itemView.findViewById(R.id.cashTextView);
            paymentModeTextView = (TextView) itemView.findViewById(R.id.paymentModeTextView);
            vehicleTypeTextView = (TextView) itemView.findViewById(R.id.vehicleTypeTextView);
            feedbackTextView = (TextView) itemView.findViewById(R.id.feedbackTextView);
        }
    }
}
