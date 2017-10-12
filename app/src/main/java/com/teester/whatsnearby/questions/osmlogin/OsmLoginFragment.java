package com.teester.whatsnearby.questions.osmlogin;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.teester.whatsnearby.R;
import com.teester.whatsnearby.data.source.OAuth;
import com.teester.whatsnearby.data.source.Preferences;
import com.teester.whatsnearby.data.source.SourceContract;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OsmLoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OsmLoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OsmLoginFragment extends Fragment implements View.OnClickListener, OsmLoginFragmentContract.View {

	private static final String TAG = OsmLoginFragment.class.getSimpleName();

	private OsmLoginFragmentContract.Presenter osmLoginPresenter;
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
		return new OsmLoginFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SourceContract.Preferences preferences = new Preferences(getContext());
		osmLoginPresenter = new OsmLoginPresenter(this, preferences);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_osm_login, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Button button = view.findViewById(R.id.osmLoginButton);
		button.setOnClickListener(this);
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

	@Override
	public void onClick(View view) {
		osmLoginPresenter.onClicked(view.getId());
	}

	@Override
	public void startOAuth() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				new OAuth(getContext());
			}
		}).start();
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
}
