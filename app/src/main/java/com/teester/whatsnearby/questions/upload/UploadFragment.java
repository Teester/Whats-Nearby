package com.teester.whatsnearby.questions.upload;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.teester.whatsnearby.R;

import java.util.Objects;

/**
 * Fragment to upload to osm
 */

public class UploadFragment extends Fragment implements View.OnClickListener {

	public static UploadFragment newInstance() {
		return new UploadFragment();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		return inflater.inflate(R.layout.fragment_upload, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Button authorise = view.findViewById(R.id.authorise);

		authorise.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		Objects.requireNonNull(getActivity()).finish();
	}

}
