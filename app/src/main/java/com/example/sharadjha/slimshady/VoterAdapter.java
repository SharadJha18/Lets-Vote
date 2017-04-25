package com.example.sharadjha.slimshady;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sharadjha.slimshady.Data.VoterData;

import java.util.List;

public class VoterAdapter extends ArrayAdapter<VoterData> {

    public VoterAdapter(Context context, int resource, List<VoterData> voterDatas) {
        super(context, resource, voterDatas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.voter_item_layout, parent, false);
        }

        // Find the earthquake at the given position in the list of earthquakes
        VoterData voterData = getItem(position);

        TextView tvName = (TextView) listItemView.findViewById(R.id.tvName);
        TextView tvVoterId = (TextView) listItemView.findViewById(R.id.tvVoterId);
//        TextView tvVoterId = (TextView) listItemView.findViewById(R.id.tvVoterId);
//        TextView tvDob = (TextView) listItemView.findViewById(R.id.tvDob);
//        TextView tvMobile = (TextView) listItemView.findViewById(R.id.tvMobile);
//        TextView tvGender = (TextView) listItemView.findViewById(R.id.tvGender);
//        TextView tvAddress = (TextView) listItemView.findViewById(R.id.tvAddress);
//        TextView tvParty = (TextView) listItemView.findViewById(R.id.tvParty);

        tvName.setText(voterData.getName());
        tvVoterId.setText(voterData.getVoterId());
//        tvVoterId.setText(voterData.getVoterId());
//        tvDob.setText(voterData.getDob());
//        tvAddress.setText(voterData.getAddress());
//        tvMobile.setText(voterData.getMobile());
//        tvParty.setText(voterData.getParty());
//        tvGender.setText(voterData.getGender());

        return listItemView;
    }
}

