package com.teester.whatsnearby.data;

import com.teester.whatsnearby.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Questions associated with each object type
 */

public class PoiTypes {

	private static PoiTypes INSTANCE;
	//Amenities
	private OsmObjectType bank = new OsmObjectType("bank", "amenity", R.drawable.ic_bank, new String[]{"wheelchair"});
	private OsmObjectType bar = new OsmObjectType("bar", "amenity", R.drawable.ic_bar, new String[]{"wheelchair", "outdoor_seating", "wifi", "wifi_fee", "cash", "cheques", "credit_card", "debit_card", "contactless", "wheelchair_toilets"});
	private OsmObjectType cafe = new OsmObjectType("cafe", "amenity", R.drawable.ic_cafe, new String[]{"wheelchair", "outdoor_seating", "wifi", "wifi_fee", "cash", "cheques", "credit_card", "debit_card", "contactless", "wheelchair_toilets"});
	private OsmObjectType cinema = new OsmObjectType("cinema", "amenity", R.drawable.ic_cinema, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless", "wheelchair_toilets"});
	private OsmObjectType dentist = new OsmObjectType("dentist", "amenity", R.drawable.ic_dentist, new String[]{"wheelchair"});
	private OsmObjectType doctors = new OsmObjectType("doctors", "amenity", R.drawable.ic_doctors, new String[]{"wheelchair"});
	private OsmObjectType fast_food = new OsmObjectType("fast_food", "amenity", R.drawable.ic_fast_food, new String[]{"wheelchair", "wheelchair_toilets", "drive_through", "takeaway", "deliver", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	private OsmObjectType fuel = new OsmObjectType("fuel", "amenity", R.drawable.ic_fuel, new String[]{"wheelchair", "wheelchair_toilets", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	private OsmObjectType pharmacy = new OsmObjectType("pharmacy", "amenity", R.drawable.ic_pharmacy, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless", "dispensing"});
	private OsmObjectType pub = new OsmObjectType("pub", "amenity", R.drawable.ic_pub, new String[]{"wheelchair", "wheelchair_toilets", "drive_through", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	private OsmObjectType restaurant = new OsmObjectType("restaurant", "amenity", R.drawable.ic_restaurant, new String[]{"wheelchair", "wheelchair_toilets", "wifi", "wifi_fee", "outdoor_seating", "vegetarian", "vegan", "takeaway", "deliver", "reservation", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	//Shops
	private OsmObjectType alcohol = new OsmObjectType("alcohol", "shop", R.drawable.ic_alcohol, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	private OsmObjectType bakery = new OsmObjectType("bakery", "shop", R.drawable.ic_bakery, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	private OsmObjectType beauty = new OsmObjectType("beauty", "shop", R.drawable.ic_beauty, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	private OsmObjectType bicycle = new OsmObjectType("bicycle", "shop", R.drawable.ic_bicycle, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	private OsmObjectType butcher = new OsmObjectType("butcher", "shop", R.drawable.ic_butcher, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless", "halal", "kosher", "organic"});
	private OsmObjectType car = new OsmObjectType("car", "shop", R.drawable.ic_car, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card"});
	private OsmObjectType car_repair = new OsmObjectType("car_repair", "shop", R.drawable.ic_car_repair, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	private OsmObjectType chemist = new OsmObjectType("chemist", "shop", R.drawable.ic_chemist, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	private OsmObjectType clothes = new OsmObjectType("clothes", "shop", R.drawable.ic_clothes, new String[]{"wheelchair", "men", "women", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	private OsmObjectType convenience = new OsmObjectType("convenience", "shop", R.drawable.ic_convenience, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	private OsmObjectType cosmetics = new OsmObjectType("cosmetics", "shop", R.drawable.ic_cosmetics, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	private OsmObjectType department_store = new OsmObjectType("department_store", "shop", R.drawable.ic_department_store, new String[]{"wheelchair", "wheelchair_toilets", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	private OsmObjectType doityourself = new OsmObjectType("doityourself", "shop", R.drawable.ic_doityourself, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	private OsmObjectType florist = new OsmObjectType("florist", "shop", R.drawable.ic_florist, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	private OsmObjectType furniture = new OsmObjectType("furniture", "shop", R.drawable.ic_furniture, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	private OsmObjectType hairdresser = new OsmObjectType("hairdresser", "shop", R.drawable.ic_hairdresser, new String[]{"wheelchair", "men", "women", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	private OsmObjectType jewelry = new OsmObjectType("jewelry", "shop", R.drawable.ic_jewelry, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	private OsmObjectType laundry = new OsmObjectType("laundry", "shop", R.drawable.ic_laundry, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	private OsmObjectType mobile_phone = new OsmObjectType("mobile_phone", "shop", R.drawable.ic_mobile_phone, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	private OsmObjectType optician = new OsmObjectType("optician", "shop", R.drawable.ic_optician, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	private OsmObjectType perfumery = new OsmObjectType("perfumery", "shop", R.drawable.ic_perfumery, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	private OsmObjectType photo = new OsmObjectType("photo", "shop", R.drawable.ic_photo, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	private OsmObjectType shoes = new OsmObjectType("photo", "shop", R.drawable.ic_shoes, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	private OsmObjectType sports = new OsmObjectType("sports", "shop", R.drawable.ic_sports, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	private OsmObjectType stationary = new OsmObjectType("stationary", "shop", R.drawable.ic_stationery, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	private OsmObjectType supermarket = new OsmObjectType("supermarket", "shop", R.drawable.ic_supermarket, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless", "organic"});
	private OsmObjectType variety = new OsmObjectType("variety", "shop", R.drawable.ic_variety_store, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	private OsmObjectType veterinary = new OsmObjectType("veterinary", "shop", R.drawable.ic_veterinary, new String[]{"wheelchair", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	//Tourism
	private OsmObjectType hotel = new OsmObjectType("hotel", "tourism", R.drawable.ic_hotel, new String[]{"wheelchair", "wheelchair_toilets", "wifi", "wifi_fee", "cash", "cheques", "credit_card", "debit_card", "contactless"});
	private Map<String, OsmObjectType> map = new HashMap<>();

	private PoiTypes() {
		map.put("alcohol", this.alcohol);
		map.put("bakery", this.bakery);
		map.put("bank", this.bank);
		map.put("bar", this.bar);
		map.put("beauty", this.beauty);
		map.put("bicycle", this.bicycle);
		map.put("butcher", this.butcher);
		map.put("cafe", this.cafe);
		map.put("car", this.car);
		map.put("car_repair", this.car_repair);
		map.put("chemist", this.chemist);
		map.put("cinema", this.cinema);
		map.put("clothes", this.clothes);
		map.put("convenience", this.convenience);
		map.put("cosmetics", this.cosmetics);
		map.put("dentist", this.dentist);
		map.put("department_store", this.department_store);
		map.put("doctors", this.doctors);
		map.put("doityourself", this.doityourself);
		map.put("fast_food", this.fast_food);
		map.put("florist", this.florist);
		map.put("fuel", this.fuel);
		map.put("furniture", this.furniture);
		map.put("hairdresser", this.hairdresser);
		map.put("hotel", this.hotel);
		map.put("jewelry", this.jewelry);
		map.put("laundry", this.laundry);
		map.put("mobile_phone", this.mobile_phone);
		map.put("optician", this.mobile_phone);
		map.put("perfumery", this.perfumery);
		map.put("pharmacy", this.pharmacy);
		map.put("photo", this.photo);
		map.put("pub", this.pub);
		map.put("restaurant", this.restaurant);
		map.put("shoes", this.shoes);
		map.put("sports", this.sports);
		map.put("stationary", this.stationary);
		map.put("supermarket", this.supermarket);
		map.put("variety", this.variety);
		map.put("veterinary", this.veterinary);
	}

	private static synchronized PoiTypes getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PoiTypes();
		}
		return INSTANCE;
	}

	public static OsmObjectType getPoiType(String type) {
		getInstance();
		return INSTANCE.map.get(type);
	}
}
