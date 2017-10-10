package com.teester.whatsnearby.questions.nothere;

import com.teester.whatsnearby.model.OsmObject;
import com.teester.whatsnearby.model.data.PoiList;

import java.util.ArrayList;
import java.util.List;

public class NotHerePresenter implements NotHereFragmentContract.Presenter {

	NotHereFragmentContract.View view;
	List<OsmObject> poiList;
	List<OsmObject> alternateList;

	public NotHerePresenter(NotHereFragmentContract.View view) {
		this.view = view;
		poiList = PoiList.getInstance().getPoiList();
		alternateList = poiList.subList(1, poiList.size());
	}

	@Override
	public void getPoiDetails() {
		String name = poiList.get(0).getName();
		view.setTextview(name);
		view.setAdapter(alternateList);
	}

	@Override
	public void onItemClicked(int i) {
		ArrayList<OsmObject> intentList = new ArrayList<OsmObject>();
		intentList.add(0, this.alternateList.get(i));
		PoiList.getInstance().setPoiList(intentList);
		view.startActivity();
	}

	@Override
	public void init() {

	}

	@Override
	public void destroy() {

	}
}
