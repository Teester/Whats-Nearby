package com.teester.mapquestions;

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
public class OsmLoginFragment extends Fragment {

	private static final String TAG = OsmLoginFragment.class.getSimpleName();

	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	private OnFragmentInteractionListener mListener;

	public OsmLoginFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment OsmLoginFragment.
	 */
	// TODO: Rename and change types and number of parameters
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
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit();
				editor.putString("oauth_verifier", "");
				editor.putString("oauth_token", "");
				editor.putString("oauth_token_secret", "");
				editor.apply();
				SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
				Log.i(TAG, preferences.getString("oauth_verifier", "None"));
				OAuth oAuth = new OAuth(getContext());
				oAuth.execute();
			}
		});


		return osmLoginFragmentView;
	}

	// TODO: Rename method, update argument and hook method into UI event
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


	@Override
	public void onResume() {
		super.onResume();
		Log.i(TAG, "in onResume");
//		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit();
//		editor.putString("oauth_verifier", "");
//		editor.putString("oauth_token", "");
//		editor.apply();
//		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
//		Log.i(TAG, "Pref: " + preferences.getString("oauth_verifier", "None"));
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
		// TODO: Update argument type and name
		void onOsmLoginFragmentInteraction(Uri uri);
	}
}
