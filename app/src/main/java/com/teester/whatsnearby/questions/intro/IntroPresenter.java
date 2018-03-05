package com.teester.whatsnearby.questions.intro;

import com.teester.whatsnearby.data.OsmObject;
import com.teester.whatsnearby.data.PoiList;

public class IntroPresenter implements IntroFragmentContract.Presenter {

	private IntroFragmentContract.View view;
	private OsmObject poi;

	public IntroPresenter(IntroFragmentContract.View view) {
		this.view = view;
		poi = PoiList.getInstance().getPoiList().get(0);
	}

	private String getAddress() {
		String street = poi.getTag("addr:street");
		if (street == null) { street = ""; }
		String city = poi.getTag("addr:city");
		if (city == null) { city = ""; }
		String number = poi.getTag("addr:housenumber");
		if (number == null) { number = ""; }
		String key = String.format("%s %s %s", number, street, city);
		return key;
	}

	@Override
	public void getDetails() {
		String address = getAddress();
		view.showDetails(poi.getName(), address, poi.getDrawable());
	}

}
