package com.teester.whatsnearby.data;

import com.teester.whatsnearby.data.pois.AmenityBank;
import com.teester.whatsnearby.data.pois.AmenityBar;
import com.teester.whatsnearby.data.pois.AmenityCafe;
import com.teester.whatsnearby.data.pois.AmenityCinema;
import com.teester.whatsnearby.data.pois.AmenityDentist;
import com.teester.whatsnearby.data.pois.AmenityDoctors;
import com.teester.whatsnearby.data.pois.AmenityFastFood;
import com.teester.whatsnearby.data.pois.AmenityFuel;
import com.teester.whatsnearby.data.pois.AmenityPharmacy;
import com.teester.whatsnearby.data.pois.AmenityPub;
import com.teester.whatsnearby.data.pois.AmenityRestaurant;
import com.teester.whatsnearby.data.pois.Poi;
import com.teester.whatsnearby.data.pois.PoiContract;
import com.teester.whatsnearby.data.pois.ShopAlcohol;
import com.teester.whatsnearby.data.pois.ShopBakery;
import com.teester.whatsnearby.data.pois.ShopBeauty;
import com.teester.whatsnearby.data.pois.ShopBicycle;
import com.teester.whatsnearby.data.pois.ShopButcher;
import com.teester.whatsnearby.data.pois.ShopCar;
import com.teester.whatsnearby.data.pois.ShopCarRepair;
import com.teester.whatsnearby.data.pois.ShopChemist;
import com.teester.whatsnearby.data.pois.ShopClothes;
import com.teester.whatsnearby.data.pois.ShopConvenience;
import com.teester.whatsnearby.data.pois.ShopCosmetics;
import com.teester.whatsnearby.data.pois.ShopDepartmentStore;
import com.teester.whatsnearby.data.pois.ShopDoItYourself;
import com.teester.whatsnearby.data.pois.ShopFlorist;
import com.teester.whatsnearby.data.pois.ShopFurniture;
import com.teester.whatsnearby.data.pois.ShopHairdresser;
import com.teester.whatsnearby.data.pois.ShopJewelry;
import com.teester.whatsnearby.data.pois.ShopLaundry;
import com.teester.whatsnearby.data.pois.ShopMobilePhone;
import com.teester.whatsnearby.data.pois.ShopOptician;
import com.teester.whatsnearby.data.pois.ShopPerfumery;
import com.teester.whatsnearby.data.pois.ShopPhoto;
import com.teester.whatsnearby.data.pois.ShopShoes;
import com.teester.whatsnearby.data.pois.ShopSports;
import com.teester.whatsnearby.data.pois.ShopStationary;
import com.teester.whatsnearby.data.pois.ShopSupermarket;
import com.teester.whatsnearby.data.pois.ShopVariety;
import com.teester.whatsnearby.data.pois.ShopVeterinary;

import java.util.HashMap;
import java.util.Map;

/**
 * Questions associated with each object type
 */

public class PoiTypes {

	private static PoiTypes INSTANCE;
	private Map<String, Poi> map = new HashMap<>();

	private PoiTypes() {
		map.put("alcohol", new ShopAlcohol());
		map.put("bakery", new ShopBakery());
		map.put("bank", new AmenityBank());
		map.put("bar", new AmenityBar());
		map.put("beauty", new ShopBeauty());
		map.put("bicycle", new ShopBicycle());
		map.put("butcher", new ShopButcher());
		map.put("cafe", new AmenityCafe());
		map.put("car", new ShopCar());
		map.put("car_repair", new ShopCarRepair());
		map.put("chemist", new ShopChemist());
		map.put("cinema", new AmenityCinema());
		map.put("clothes", new ShopClothes());
		map.put("convenience", new ShopConvenience());
		map.put("cosmetics", new ShopCosmetics());
		map.put("dentist", new AmenityDentist());
		map.put("department_store", new ShopDepartmentStore());
		map.put("doctors", new AmenityDoctors());
		map.put("doityourself", new ShopDoItYourself());
		map.put("fast_food", new AmenityFastFood());
		map.put("florist", new ShopFlorist());
		map.put("fuel", new AmenityFuel());
		map.put("furniture", new ShopFurniture());
		map.put("hairdresser", new ShopHairdresser());
		map.put("jewelry", new ShopJewelry());
		map.put("laundry", new ShopLaundry());
		map.put("mobile_phone", new ShopMobilePhone());
		map.put("optician", new ShopOptician());
		map.put("perfumery", new ShopPerfumery());
		map.put("pharmacy", new AmenityPharmacy());
		map.put("photo", new ShopPhoto());
		map.put("pub", new AmenityPub());
		map.put("restaurant", new AmenityRestaurant());
		map.put("shoes", new ShopShoes());
		map.put("sports", new ShopSports());
		map.put("stationary", new ShopStationary());
		map.put("supermarket", new ShopSupermarket());
		map.put("variety", new ShopVariety());
		map.put("veterinary", new ShopVeterinary());
	}

	private static synchronized PoiTypes getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PoiTypes();
		}
		return INSTANCE;
	}

	public static PoiContract getPoiType(String type) {
		getInstance();
		return INSTANCE.map.get(type);
	}
}
