package com.teester.whatsnearby.questions.nothere;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.teester.whatsnearby.R;
import com.teester.whatsnearby.data.OsmObject;
import com.teester.whatsnearby.data.OsmObjectType;
import com.teester.whatsnearby.data.PoiTypes;
import com.teester.whatsnearby.questions.QuestionsActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NotHereFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NotHereFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotHereFragment extends Fragment implements AdapterView.OnItemClickListener, NotHereFragmentContract.View {

	private static final String TAG = NotHereFragment.class.getSimpleName();

	private ListView listView;
	private TextView textView;
	private NotHereFragmentContract.Presenter notHerePresenter;

	private OnFragmentInteractionListener mListener;

	public NotHereFragment() {
		// Required empty public constructor
	}

	public static NotHereFragment newInstance() {
		return new NotHereFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		notHerePresenter = new NotHerePresenter(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		notHerePresenter.getPoiDetails();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_not_here, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		this.textView = view.findViewById(R.id.question_textview);
		this.listView = view.findViewById(R.id.listView);
		this.listView.setOnItemClickListener(this);
	}

	public void onButtonPressed(ArrayList<OsmObject> uri) {
		if (mListener != null) {
			mListener.onNotHereFragmentInteraction();
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
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		notHerePresenter.onItemClicked(i);
	}

	@Override
	public void setTextview(String name) {
		textView.setText(String.format(getString(R.string.select_current_location), name));
	}

	@Override
	public void setAdapter(List<OsmObject> list) {
		UsersAdapter adapter = new UsersAdapter(getContext(), list);
		listView.setAdapter(adapter);
	}

	@Override
	public void startActivity() {
		Intent intent = new Intent(getActivity(), QuestionsActivity.class);
		startActivity(intent);
	}

	@Override
	public void setPresenter(NotHereFragmentContract.Presenter presenter) {
		notHerePresenter = presenter;
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
		void onNotHereFragmentInteraction();
	}

	public class UsersAdapter extends ArrayAdapter<OsmObject> {
		public UsersAdapter(Context context, List<OsmObject> users) {
			super(context, 0, users);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			// Get the data item for this position
			OsmObject poi = getItem(position);
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

			OsmObjectType objectType = PoiTypes.getPoiType(poi.getType());
			int drawable = objectType.getObjectIcon();
			image.setImageResource(drawable);

			// Return the completed view to render on screen
			return convertView;
		}

	}
}
