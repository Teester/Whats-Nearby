package com.teester.whatsnearby.questions.nothere;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import java.util.List;

public class NotHereFragment extends Fragment implements NotHereFragmentContract.View {

	private RecyclerView recyclerView;
	private TextView textView;
	private NotHereFragmentContract.Presenter notHerePresenter;

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
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		return inflater.inflate(R.layout.fragment_not_here, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		this.textView = view.findViewById(R.id.question_textview);
		this.recyclerView = view.findViewById(R.id.recyclerView);
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

	public interface OnFragmentInteractionListener {
	}

	public class PoiAdapter extends RecyclerView.Adapter<PoiAdapter.ViewHolder> {
		private List<OsmObject> poiList;

		PoiAdapter(List<OsmObject> poiList) {
			this.poiList = poiList;
		}

		@NonNull
		@Override
		public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			View view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_poi, parent, false);
			view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					notHerePresenter.onItemClicked(recyclerView.getChildAdapterPosition(view));
				}
			});
			return new ViewHolder(view);
		}

		@Override
		public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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

			ViewHolder(View view) {
				super(view);
				name = view.findViewById(R.id.name);
				type = view.findViewById(R.id.about_list_content);
				distance = view.findViewById(R.id.distance);
				image = view.findViewById(R.id.type_icon);
			}
		}
	}
}
