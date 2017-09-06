package com.teester.mapquestions;

import java.util.HashMap;
import java.util.Map;

/**
 * Questions associated with each object type
 */

public class PoiTypes {

	//Amenities
	public OsmObjectType bank = new OsmObjectType("bank", "amenity", R.drawable.ic_bank, new String[] {"wheelchair"});
	public OsmObjectType bar = new OsmObjectType("bar", "amenity", R.drawable.ic_bar, new String[]{"wheelchair", "outdoor_seating", "wifi", "wifi_fee", "cash", "cheques", "credit_card", "debit_card", "contactless", "wheelchair_toilets"});
	public OsmObjectType cafe = new OsmObjectType("cafe", "amenity", R.drawable.ic_cafe, new String[]{"wheelchair", "outdoor_seating", "wifi", "wifi_fee", "cash", "cheques", "credit_card", "debit_card", "contactless", "wheelchair_toilets"});
	public OsmObjectType cinema = new OsmObjectType("cinema", "amenity", R.drawable.ic_cinema, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless", "wheelchair_toilets"});
	public OsmObjectType dentist = new OsmObjectType("dentist", "amenity", R.drawable.ic_dentist, new String[]{"wheelchair"});
	public OsmObjectType doctors = new OsmObjectType("doctors", "amenity",R.drawable.ic_doctors,  new String[]{"wheelchair"});
	public OsmObjectType fast_food = new OsmObjectType("fast_food", "amenity", R.drawable.ic_fast_food, new String[]{"wheelchair", "wheelchair_toilets", "drive_through", "takeaway", "deliver", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	public OsmObjectType fuel = new OsmObjectType("fuel", "amenity", R.drawable.ic_fuel, new String[]{"wheelchair", "wheelchair_toilets", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	public OsmObjectType pharmacy = new OsmObjectType("pharmacy", "amenity", R.drawable.ic_pharmacy, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless", "dispensing"});
	public OsmObjectType pub = new OsmObjectType("pub", "amenity", R.drawable.ic_pub, new String[]{"wheelchair", "wheelchair_toilets", "drive_through", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	public OsmObjectType restaurant = new OsmObjectType("restaurant", "amenity", R.drawable.ic_restaurant, new String[]{"wheelchair", "wheelchair_toilets", "wifi", "wifi_fee", "outdoor_seating", "vegetarian", "vegan", "takeaway", "deliver", "reservation", "cash", "cheques", "credit_card", "debit_card", "contactless"});

	//Shops
	public OsmObjectType alcohol = new OsmObjectType("alcohol", "shop", R.drawable.ic_alcohol, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	public OsmObjectType bakery = new OsmObjectType("bakery", "shop", R.drawable.ic_bakery, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	public OsmObjectType beauty = new OsmObjectType("beauty", "shop", R.drawable.ic_beauty, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	public OsmObjectType bicycle = new OsmObjectType("bicycle", "shop", R.drawable.ic_bicycle, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	public OsmObjectType butcher = new OsmObjectType("butcher", "shop", R.drawable.ic_butcher, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless", "halal", "kosher", "organic"});
	public OsmObjectType chemist = new OsmObjectType("chemist", "shop", R.drawable.ic_chemist, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	public OsmObjectType clothes = new OsmObjectType("clothes", "shop", R.drawable.ic_clothes, new String[]{"wheelchair", "men", "women", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	public OsmObjectType convenience = new OsmObjectType("convenience", "shop", R.drawable.ic_convenience, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	public OsmObjectType department_store = new OsmObjectType("department_store", "shop", R.drawable.ic_department_store, new String[]{"wheelchair", "wheelchair_toilets", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	public OsmObjectType doityourself = new OsmObjectType("doityourself", "shop", R.drawable.ic_doityourself, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	public OsmObjectType florist = new OsmObjectType("florist", "shop", R.drawable.ic_florist, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	public OsmObjectType hairdresser = new OsmObjectType("hairdresser", "shop", R.drawable.ic_hairdresser, new String[]{"wheelchair", "men", "women", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	public OsmObjectType jewelry = new OsmObjectType("jewelry", "shop", R.drawable.ic_jewelry, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	public OsmObjectType mobile_phone = new OsmObjectType("mobile_phone", "shop", R.drawable.ic_mobile_phone, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	public OsmObjectType optician = new OsmObjectType("optician", "shop", R.drawable.ic_optician, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	public OsmObjectType photo = new OsmObjectType("photo", "shop", R.drawable.ic_photo, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	public OsmObjectType shoes = new OsmObjectType("photo", "shop", R.drawable.ic_shoes, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	public OsmObjectType sports = new OsmObjectType("sports", "shop", R.drawable.ic_sports, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	public OsmObjectType stationary = new OsmObjectType("stationary", "shop", R.drawable.ic_stationery, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	public OsmObjectType supermarket = new OsmObjectType("supermarket", "shop", R.drawable.ic_supermarket, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless", "organic"});

	//Tourism
	public OsmObjectType hotel = new OsmObjectType("hotel", "tourism", R.drawable.ic_hotel, new String[]{"wheelchair", "wheelchair_toilets", "wifi", "wifi_fee", "cash", "cheques", "credit_card", "debit_card", "contactless"});

	Map<String, OsmObjectType> map = new HashMap<>();

	public PoiTypes() {
		map.put("alcohol", this.alcohol);
		map.put("bakery", this.bakery);
		map.put("bank", this.bank);
		map.put("bar", this.bar);
		map.put("beauty", this.beauty);
		map.put("bicycle", this.bicycle);
		map.put("butcher", this.butcher);
		map.put("cafe", this.cafe);
		map.put("chemist", this.chemist);
		map.put("cinema", this.cinema);
		map.put("clothes", this.clothes);
		map.put("convenience", this.convenience);
		map.put("dentist", this.dentist);
		map.put("department_store", this.department_store);
		map.put("doctors", this.doctors);
		map.put("doityourself", this.doityourself);
		map.put("fast_food", this.fast_food);
		map.put("florist", this.florist);
		map.put("fuel", this.fuel);
		map.put("hairdresser", this.hairdresser);
		map.put("hotel", this.hotel);
		map.put("jewelry", this.jewelry);
		map.put("mobile_phone", this.mobile_phone);
		map.put("optician", this.mobile_phone);
		map.put("pharmacy", this.pharmacy);
		map.put("photo", this.photo);
		map.put("pub", this.pub);
		map.put("restaurant", this.restaurant);
		map.put("shoes", this.shoes);
		map.put("sports", this.sports);
		map.put("stationary", this.stationary);
		map.put("supermarket", this.supermarket);
	}

	public OsmObjectType getPoiType(String type) {
		return map.get(type);
	}
}
