package com.mysafetynet.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mysafetynet.Model.UserListModel;
import com.mysafetynet.R;
import com.mysafetynet.activities.EditChildActivity;
import com.mysafetynet.activities.ViewChildActivity;
import com.mysafetynet.customs.MySafetyText;
import com.mysafetynet.network.ApiConstants;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    private final ArrayList<UserListModel.Data> mValues;


    private boolean isEditable;
    private Context context;

    public UserListAdapter(ArrayList<UserListModel.Data> items, boolean isEditable) {
        mValues = items;
        this.isEditable = isEditable;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_child_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        displayImage(holder.mItem.getUser_image(), holder.imvProfile);
        holder.txtChildName.setText(String.format(Locale.getDefault(), "%s %s", holder.mItem.getFirst_name(), holder.mItem.getLast_name()));

        holder.txtChildyear.setText(String.format(Locale.getDefault(), "%s Year", holder.mItem.getChild_age()));
        holder.txtChildGender.setText(String.format(Locale.getDefault(), "%s", holder.mItem.getGender()));
        if (holder.mItem.getStatus().equalsIgnoreCase("0")) {
            holder.txtChildStatus.setText("In Active");
            holder.txtChildStatus.setTextColor(context.getResources().getColor(R.color.colorInActive));
        } else if (holder.mItem.getStatus().equalsIgnoreCase("1")) {
            holder.txtChildStatus.setText("Active");
            holder.txtChildStatus.setTextColor(context.getResources().getColor(R.color.colorActive));
        } else {
            holder.txtChildStatus.setText("In Progress");
            holder.txtChildStatus.setTextColor(context.getResources().getColor(R.color.colorInProgress));
        }
        holder.txtOrderStatus.setText(String.format(Locale.getDefault(), "%s", holder.mItem.getOrder_status().equalsIgnoreCase("0") ? "Archived" : ""));
        if (!holder.mItem.getOrder_status().equalsIgnoreCase("0")){
            holder.viewPipe.setVisibility(View.INVISIBLE);

        }
        if (isEditable) {
            holder.layoutProgrress.setVisibility(View.GONE);
        } else {
            holder.layoutProgrress.setVisibility(View.VISIBLE);
        }
        holder.bind(position, holder.mItem);
    }

    private void displayImage(String imagePath, CircleImageView imvProfile) {

        Glide.with(context)
                .asBitmap()
                .load(imagePath)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.girl)
                        .error(R.drawable.girl))
                .into(imvProfile);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    public void clear() {
        mValues.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imvProfile)
        CircleImageView imvProfile;
        @BindView(R.id.layoutImage)
        LinearLayout layoutImage;
        @BindView(R.id.txtChildName)
        MySafetyText txtChildName;
        @BindView(R.id.txtChildyear)
        MySafetyText txtChildyear;
        @BindView(R.id.txtChildGender)
        MySafetyText txtChildGender;
        @BindView(R.id.layoutData)
        LinearLayout layoutData;
        @BindView(R.id.layoutArrow)
        LinearLayout layoutArrow;
        @BindView(R.id.txtChildStatus)
        MySafetyText txtChildStatus;
        @BindView(R.id.txtOrderStatus)
        MySafetyText txtOrderStatus;
        @BindView(R.id.layoutProgrress)
        RelativeLayout layoutProgrress;@BindView(R.id.viewPipe)
        View viewPipe;
        public UserListModel.Data mItem;
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

        public void bind(int position, final UserListModel.Data mItem) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(ApiConstants.TAGS.first_name, mItem.getFirst_name());
                    bundle.putString(ApiConstants.TAGS.last_name, mItem.getLast_name());
                    bundle.putString(ApiConstants.TAGS.dob, mItem.getDob());
                    bundle.putString(ApiConstants.TAGS.age, mItem.getChild_age());
                    bundle.putString(ApiConstants.TAGS.gender, mItem.getGender());
                    bundle.putString(ApiConstants.TAGS.child_id, mItem.getId());
                    bundle.putString(ApiConstants.TAGS.order_status, mItem.getOrder_status());
                    bundle.putString(ApiConstants.TAGS.status, mItem.getStatus());
                    bundle.putString(ApiConstants.TAGS.subscription_id, mItem.getSubscription_id());

                    Intent intent = null;
                    if (isEditable) {
                        intent = new Intent(v.getContext(), EditChildActivity.class);
                    } else {
                        intent = new Intent(v.getContext(), ViewChildActivity.class);
                    }
                    intent.putExtras(bundle);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
