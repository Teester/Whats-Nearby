package com.teester.whatsnearby.questions.nothere;

import com.teester.whatsnearby.BasePresenter;
import com.teester.whatsnearby.BaseView;
import com.teester.whatsnearby.model.OsmObject;

import java.util.ArrayList;
import java.util.List;

public interface NotHereFragmentContract {
	interface Presenter extends BasePresenter {
		void getPoiDetails();

		void onItemClicked(int i);
	}

	interface View extends BaseView<Presenter> {

		void setTextview(String string);

		void setAdapter(List<OsmObject> alternateList);

		void startActivity(ArrayList<OsmObject> intentList);
	}
}
