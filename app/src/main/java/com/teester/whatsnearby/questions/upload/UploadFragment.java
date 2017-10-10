package com.teester.whatsnearby.questions.upload;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.teester.whatsnearby.R;

/**
 * Fragment to upload to osm
 */

public class UploadFragment extends Fragment implements View.OnClickListener, UploadFragmentContract.View {

	private static final String TAG = UploadFragment.class.getSimpleName();

//	private OnFragmentInteractionListener mListener;

	private TextView thanks_textview;
	private ImageView thanks_imageView;
	private Button authorise;
	private UploadFragmentContract.Presenter uploadPresenter;

	public UploadFragment() {
		// Required empty public constructor
	}

	public static UploadFragment newInstance() {
		return new UploadFragment();
	}


	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uploadPresenter = new UploadPresenter(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_upload, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		this.thanks_textview = view.findViewById(R.id.thanks_textview);
		this.thanks_imageView = view.findViewById(R.id.thanks_imageView);
		this.authorise = view.findViewById(R.id.authorise);

		this.authorise.setOnClickListener(this);
	}
//
//	@Override
//	public void onAttach(Context context) {
//		super.onAttach(context);
//		if (context instanceof OnFragmentInteractionListener) {
//			mListener = (OnFragmentInteractionListener) context;
//		} else {
//			throw new RuntimeException(context.toString()
//					+ " must implement OnFragmentInteractionListener");
//		}
//	}
//
//	@Override
//	public void onDetach() {
//		super.onDetach();
//		mListener = null;
//	}

	@Override
	public void onClick(View view) {
		getActivity().finish();
	}

	@Override
	public void setPresenter(UploadFragmentContract.Presenter presenter) {
		uploadPresenter = presenter;
	}

//	public interface OnFragmentInteractionListener {
//		void onUploadFragmentInteraction();
//	}
}
