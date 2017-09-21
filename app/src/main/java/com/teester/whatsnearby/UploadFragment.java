package com.teester.whatsnearby;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Fragment to upload to osm
 */

public class UploadFragment extends Fragment implements View.OnClickListener {

	private static final String TAG = UploadFragment.class.getSimpleName();

	private OnFragmentInteractionListener mListener;

	private TextView thanks_textview;
	private ImageView thanks_imageView;
	private Button authorise;

	public UploadFragment() {
		// Required empty public constructor
	}

	public static UploadFragment newInstance() {
		return new UploadFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View questionFragmentView = inflater.inflate(R.layout.fragment_upload, container, false);
		this.thanks_textview = questionFragmentView.findViewById(R.id.thanks_textview);
		this.thanks_imageView = questionFragmentView.findViewById(R.id.thanks_imageView);
		this.authorise = questionFragmentView.findViewById(R.id.authorise);

		this.authorise.setOnClickListener(this);

		return questionFragmentView;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof OnFragmentInteractionListener) {
			mListener = (OnFragmentInteractionListener) context;
		} else {
			throw new RuntimeException(context.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	@Override
	public void onClick(View view) {
		getActivity().finish();
	}

	public interface OnFragmentInteractionListener {
		void onUploadFragmentInteraction();
	}
}
