package com.teester.whatsnearby;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OsmLoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OsmLoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OsmLoginFragment extends Fragment implements View.OnClickListener {

	private static final String TAG = OsmLoginFragment.class.getSimpleName();

	private OnFragmentInteractionListener mListener;

	public OsmLoginFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @return A new instance of fragment OsmLoginFragment.
	 */
	public static OsmLoginFragment newInstance() {
		OsmLoginFragment fragment = new OsmLoginFragment();;
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View osmLoginFragmentView =  inflater.inflate(R.layout.fragment_osm_login, container, false);
		Button button = osmLoginFragmentView.findViewById(R.id.osmLoginButton);
		button.setOnClickListener(this);

		return osmLoginFragmentView;
	}

	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onOsmLoginFragmentInteraction(uri);
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

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated
	 * to the activity and potentially other fragments contained in that
	 * activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFragmentInteractionListener {
		void onOsmLoginFragmentInteraction(Uri uri);
	}

	@Override
	public void onClick(View view) {
		Log.i(TAG, "in onClick");
		if (view.getId() == R.id.osmLoginButton) {
			SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
			SharedPreferences.Editor editor = sharedPreferences.edit();

			editor.putString("oauth_verifier", "");
			editor.putString("oauth_token", "");
			editor.putString("oauth_token_secret", "");
			editor.apply();

			OAuth oAuth = new OAuth(getContext());
			oAuth.execute();
		}
	}
}
