package com.teester.mapquestions;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NotHereFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NotHereFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotHereFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	public static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	private static final String TAG = NotHereFragment.class.getSimpleName();


	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;
	private ArrayList<OsmObject> poiList;
	private ListView listView;


	private OnFragmentInteractionListener mListener;

	public NotHereFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment BlankFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static NotHereFragment newInstance(Parcelable param1) {
		NotHereFragment fragment = new NotHereFragment();
		Bundle args = new Bundle();
		args.putParcelable(ARG_PARAM1, param1);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			this.poiList = getArguments().getParcelableArrayList(ARG_PARAM1);
		}

		Log.d(TAG, ""+this.poiList);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View notHereFragmentView = inflater.inflate(R.layout.fragment_not_here, container, false);
		this.listView = notHereFragmentView.findViewById(R.id.listView);
		return notHereFragmentView;
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(ArrayList<OsmObject> uri) {
		if (mListener != null) {
			mListener.onNotHereFragmentInteraction(uri);
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		// Construct the data source
		ArrayList<OsmObject> arrayOfUsers = new ArrayList<OsmObject>();
		// Create the adapter to convert the array to views
		UsersAdapter adapter = new UsersAdapter(this.getContext(), this.poiList);
		// Attach the adapter to a ListView
		this.listView.setAdapter(adapter);
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
		void onNotHereFragmentInteraction(ArrayList<OsmObject> poiList);
	}


	public class UsersAdapter extends ArrayAdapter<OsmObject> {
		public UsersAdapter(Context context, ArrayList<OsmObject> users) {
			super(context, 0, users);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			// Get the data item for this position
			OsmObject poi = getItem(position);
			PoiTypes poiTypes = new PoiTypes();
			Log.d(TAG, "" + poi);
			// Check if an existing view is being reused, otherwise inflate the view
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.poi_list_item, parent, false);
			}
			// Lookup view for data population
			TextView name = convertView.findViewById(R.id.name);
			TextView type = convertView.findViewById(R.id.type);
			TextView distance = convertView.findViewById(R.id.distance);
			ImageView image = convertView.findViewById(R.id.type_icon);
			// Populate the data into the template view using the data object
			name.setText(poi.getName());
			type.setText(poi.getType());
			distance.setText(String.format(getString(R.string.distance_away), poi.getDistance()));

			int drawable;
			OsmObjectType objectType = poiTypes.getPoiType(poi.getType());
			drawable = objectType.getDrawable(getContext());
			image.setImageResource(drawable);

			// Return the completed view to render on screen
			return convertView;
		}
	}
}
