package com.teester.mapquestions;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

import static android.content.Context.MODE_PRIVATE;

/**
 * Fragment to upload to osm
 */

public class UploadFragment extends Fragment {
	private static final String TAG = UploadFragment.class.getSimpleName();

	protected static final String ARG_PARAM1 = "param1";

	private OnFragmentInteractionListener mListener;
	private ArrayList<Answer> answers;
	public static boolean logged_in = false;

	private TextView thanks_textview;
	private ImageView thanks_imageView;
	private Button authorise;

	public UploadFragment() {
		// Required empty public constructor
	}

	public static UploadFragment newInstance() {
		ArrayList<Answer> answers = new ArrayList<Answer>();
		UploadFragment fragment = new UploadFragment();
		Bundle args = new Bundle();
		args.putParcelableArrayList(ARG_PARAM1, answers);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			this.answers = QuestionsActivity.answers;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View questionFragmentView = inflater.inflate(R.layout.fragment_upload, container, false);
		this.thanks_textview = questionFragmentView.findViewById(R.id.thanks_textview);
		this.thanks_imageView = questionFragmentView.findViewById(R.id.thanks_imageView);
		this.authorise = questionFragmentView.findViewById(R.id.authorise);
		return questionFragmentView;
	}


	@Override
	public void onStart() {
		super.onStart();

		//this.answers = QuestionsActivity.answers;
		if (logged_in = true) {
			this.thanks_imageView.setImageResource(R.drawable.ic_yes);
			this.thanks_textview.setText(R.string.upload_thanks);
			this.authorise.setVisibility(View.INVISIBLE);
			try {
				new UploadToOSM(this.answers, this.getContext());
			} catch (OAuthNotAuthorizedException e) {
				e.printStackTrace();
			} catch (OAuthExpectationFailedException e) {
				e.printStackTrace();
			} catch (OAuthCommunicationException e) {
				e.printStackTrace();
			} catch (OAuthMessageSignerException e) {
				e.printStackTrace();
			}
		} else {
			OAuth oAuth = new OAuth(this.getContext());
			oAuth.execute();
		}
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
	public void onResume() {
		super.onResume();
		Log.i(TAG, "In onResume()");
	}

	public interface OnFragmentInteractionListener {
		void onUploadFragmentInteraction();
	}
}
