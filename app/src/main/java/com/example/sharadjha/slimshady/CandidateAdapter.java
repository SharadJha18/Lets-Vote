package com.example.sharadjha.slimshady;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sharadjha.slimshady.Data.CandidateData;

import java.util.List;

public class CandidateAdapter extends ArrayAdapter<CandidateData> {

    public CandidateAdapter(Context context, int resource, List<CandidateData> candidateDatas) {
        super(context, resource, candidateDatas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.candidate_item_layout, parent, false);
        }

        // Find the earthquake at the given position in the list of earthquakes
        CandidateData candidateData = getItem(position);

        TextView tvName = (TextView) listItemView.findViewById(R.id.tvName);
        TextView tvUsername = (TextView) listItemView.findViewById(R.id.tvUsername);
//        TextView tvVoterId = (TextView) listItemView.findViewById(R.id.tvVoterId);
//        TextView tvDob = (TextView) listItemView.findViewById(R.id.tvDob);
//        TextView tvMobile = (TextView) listItemView.findViewById(R.id.tvMobile);
//        TextView tvGender = (TextView) listItemView.findViewById(R.id.tvGender);
//        TextView tvAddress = (TextView) listItemView.findViewById(R.id.tvAddress);
//        TextView tvParty = (TextView) listItemView.findViewById(R.id.tvParty);

        tvName.setText(candidateData.getName());
        tvUsername.setText(candidateData.getUsername());
//        tvVoterId.setText(candidateData.getVoterId());
//        tvDob.setText(candidateData.getDob());
//        tvAddress.setText(candidateData.getAddress());
//        tvMobile.setText(candidateData.getMobile());
//        tvParty.setText(candidateData.getParty());
//        tvGender.setText(candidateData.getGender());

        return listItemView;
    }
}

