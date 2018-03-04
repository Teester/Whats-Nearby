package com.teester.whatsnearby.questions.nothere;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.teester.whatsnearby.R;
import com.teester.whatsnearby.data.OsmObject;
import com.teester.whatsnearby.questions.QuestionsActivity;

import java.util.ArrayList;
import java.util.List;

public class NotHereFragment extends Fragment implements NotHereFragmentContract.View {

	private RecyclerView recyclerView;
	private TextView textView;
	private NotHereFragmentContract.Presenter notHerePresenter;

	private OnFragmentInteractionListener mListener;

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
		super.onCreateView(inflater, container, savedInstanceState);
		return inflater.inflate(R.layout.fragment_not_here, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		this.textView = view.findViewById(R.id.question_textview);
		this.recyclerView = view.findViewById(R.id.recyclerView);
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
			throw new ClassCastException(context.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	@Override
	public void setTextview(String name) {
		textView.setText(String.format(getString(R.string.select_current_location), name));
	}

	@Override
	public void setAdapter(List<OsmObject> list) {
		PoiAdapter adapter = new PoiAdapter(list);
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setHasFixedSize(true);
		recyclerView.setAdapter(adapter);
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

	public interface OnFragmentInteractionListener {
		void onNotHereFragmentInteraction();
	}

	public class PoiAdapter extends RecyclerView.Adapter<PoiAdapter.ViewHolder> {
		private List<OsmObject> poiList;

		public PoiAdapter(List<OsmObject> poiList) {
			this.poiList = poiList;
		}

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_poi, parent, false);
			view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					notHerePresenter.onItemClicked(recyclerView.getChildAdapterPosition(view));
				}
			});
			ViewHolder recyclerViewHolder = new ViewHolder(view);
			return recyclerViewHolder;
		}

		@Override
		public void onBindViewHolder(ViewHolder holder, int position) {
			OsmObject poi = poiList.get(position);
			holder.name.setText(poi.getName());
			holder.type.setText(poi.getType());
			holder.distance.setText(String.format(getString(R.string.distance_away), poi.getDistance()));
			holder.image.setImageResource(poi.getDrawable());
		}

		@Override
		public int getItemCount() {
			return poiList.size();
		}

		public class ViewHolder extends RecyclerView.ViewHolder {
			public TextView name;
			public TextView type;
			public TextView distance;
			public ImageView image;

			public ViewHolder(View view) {
				super(view);
				name = view.findViewById(R.id.name);
				type = view.findViewById(R.id.about_list_content);
				distance = view.findViewById(R.id.distance);
				image = view.findViewById(R.id.type_icon);
			}
		}
	}
}
