package com.teester.whatsnearby.questions.nothere;

import com.teester.whatsnearby.data.OsmObject;

import java.util.List;

public interface NotHereFragmentContract {
	interface Presenter {
		void getPoiDetails();

		void onItemClicked(int i);

	}

	interface View {

		void setTextview(String string);

		void setAdapter(List<OsmObject> alternateList);

		void startActivity();
	}
}
