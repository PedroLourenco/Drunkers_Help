package com.drunkers_helper.util;

import com.drunkers_help.R;

public final class globalconstant {
	
	//log variables
	public static final boolean LOG = false;
    public static String TAG = "Drunker's Helper";

	public static double lat, lon;
	
	public static String cityName = null; 
	
	
	public static int count = 0;

	public static final String[] IMAGES = new String[] {
			
		    "drawable://" + R.drawable.aguila,
			"drawable://" + R.drawable.amigos,
			"drawable://" + R.drawable.amstel,
			"drawable://" + R.drawable.antares_kolsch,			
			"drawable://" + R.drawable.asahi_black,
			"drawable://" + R.drawable.asahi,			
			"drawable://" + R.drawable.baltika,			
			"drawable://" + R.drawable.baltika3,
			"drawable://" + R.drawable.baltika4,
			"drawable://" + R.drawable.baltika7,
			"drawable://" + R.drawable.bamberg,
			"drawable://" + R.drawable.bankscaribbean,
			"drawable://" + R.drawable.bavaria,
			"drawable://" + R.drawable.becks,
			"drawable://" + R.drawable.birra_moretti,
			"drawable://" + R.drawable.bitburger,
			"drawable://" + R.drawable.brahama,
			"drawable://" + R.drawable.brooklyn,
			"drawable://" + R.drawable.bud_light,
			"drawable://" + R.drawable.bochkovoe,
			"drawable://" + R.drawable.bourbon_county,
			"drawable://" + R.drawable.budlightchelada,
			"drawable://" + R.drawable.budweiser,
			"drawable://" + R.drawable.buyjiu,
			"drawable://" + R.drawable.carlsberg,
			"drawable://" + R.drawable.cartablanca,
			"drawable://" + R.drawable.chang, 
			"drawable://" + R.drawable.cobra,
			"drawable://" + R.drawable.coral,
			"drawable://" + R.drawable.corona,
			"drawable://" + R.drawable.cristal,
			"drawable://" + R.drawable.cruzcampo,
			"drawable://" + R.drawable.dama,
			"drawable://" + R.drawable.daura,
			"drawable://" + R.drawable.dosequis,
			"drawable://" + R.drawable.duff, 
			"drawable://" + R.drawable.duvel,
			"drawable://" + R.drawable.elland_porter,
			"drawable://" + R.drawable.esb,
			"drawable://" + R.drawable.estrella,
			"drawable://" + R.drawable.falcon, 
			"drawable://" + R.drawable.fino,
			"drawable://" + R.drawable.foster,
			"drawable://" + R.drawable.founders_kbs,
			"drawable://" + R.drawable.franklins_conqueror,
			"drawable://" + R.drawable.guiness,
			"drawable://" + R.drawable.half_cycle_ipa,
			"drawable://" + R.drawable.harvest,
			"drawable://" + R.drawable.heady_topper,
			"drawable://" + R.drawable.heineken,
			"drawable://" + R.drawable.imperial_russian_stoutt,
			"drawable://" + R.drawable.karhu,
			"drawable://" + R.drawable.karlovacko,
			"drawable://" + R.drawable.kilkenny,
			"drawable://" + R.drawable.kronenbourg_1994,
			"drawable://" + R.drawable.london_pride,
			"drawable://" + R.drawable.mahou,
			"drawable://" + R.drawable.mariestadsprima,
			"drawable://" + R.drawable.medalla,
			"drawable://" + R.drawable.modeloespecial,
			"drawable://" + R.drawable.murphys,
			"drawable://" + R.drawable.negramodelo,
			"drawable://" + R.drawable.superbock,
			"drawable://" + R.drawable.skol,
			"drawable://" + R.drawable.old_speckled_hen,
			"drawable://" + R.drawable.pacifico,
			"drawable://" + R.drawable.pale_ale,
			"drawable://" + R.drawable.palma_louca,
			"drawable://" + R.drawable.parabola,
			"drawable://" + R.drawable.peroni_nastro,
			"drawable://" + R.drawable.pint,
			"drawable://" + R.drawable.pliny_the_elder,
			"drawable://" + R.drawable.pliny_the_younger,
			"drawable://" + R.drawable.presidente,
			"drawable://" + R.drawable.polar,
			"drawable://" + R.drawable.quilmes,
			"drawable://" + R.drawable.royal_dutch,
			"drawable://" + R.drawable.sagres,
			"drawable://" + R.drawable.san_miguel,
			"drawable://" + R.drawable.sol,
			"drawable://" + R.drawable.stella_artois,
			"drawable://" + R.drawable.strela,
			"drawable://" + R.drawable.tagus,
			"drawable://" + R.drawable.tecate,
			"drawable://" + R.drawable.victoria };

	public static final Integer[] mThumbIds = {R.drawable.aguila, R.drawable.amigos,
			R.drawable.amstel, R.drawable.antares_kolsch, R.drawable.asahi_black, 
			R.drawable.asahi, R.drawable.baltika, R.drawable.baltika3,
			R.drawable.baltika4, R.drawable.baltika7, R.drawable.bamberg, R.drawable.bankscaribbean, 
			R.drawable.bavaria,R.drawable.becks, R.drawable.birra_moretti,
			R.drawable.bitburger, R.drawable.brahama, R.drawable.brooklyn, R.drawable.bud_light, R.drawable.bochkovoe,
			R.drawable.bourbon_county,
			R.drawable.budlightchelada, R.drawable.budweiser,
			R.drawable.buyjiu, R.drawable.carlsberg, R.drawable.cartablanca,
			R.drawable.chang, R.drawable.cobra, R.drawable.coral,
			R.drawable.corona, R.drawable.cristal, R.drawable.cruzcampo, R.drawable.dama,
			R.drawable.daura, R.drawable.dosequis, R.drawable.duff,
			R.drawable.duvel, R.drawable.elland_porter,  R.drawable.esb, R.drawable.estrella, R.drawable.falcon,
			R.drawable.fino, R.drawable.foster, R.drawable.founders_kbs, R.drawable.franklins_conqueror, R.drawable.guiness, R.drawable.half_cycle_ipa,
			R.drawable.harvest, R.drawable.heady_topper, R.drawable.heineken, R.drawable.imperial_russian_stoutt, R.drawable.karhu, R.drawable.karlovacko,
			R.drawable.kilkenny, R.drawable.kronenbourg_1994,
			R.drawable.london_pride, R.drawable.mahou,
			R.drawable.mariestadsprima, R.drawable.medalla, R.drawable.modeloespecial,
			R.drawable.murphys, R.drawable.negramodelo, R.drawable.superbock,
			R.drawable.skol, R.drawable.old_speckled_hen, R.drawable.pacifico, R.drawable.pale_ale,
			R.drawable.palma_louca, R.drawable.parabola, R.drawable.peroni_nastro, R.drawable.pint, R.drawable.pliny_the_elder, R.drawable.pliny_the_younger, R.drawable.presidente,
			R.drawable.polar, R.drawable.quilmes, R.drawable.royal_dutch, R.drawable.sagres, 
			R.drawable.san_miguel, R.drawable.sol, R.drawable.stella_artois, R.drawable.strela,
			R.drawable.tagus, R.drawable.tecate, R.drawable.victoria };

	private globalconstant() {
	}

	public static class Config {
		public static final boolean DEVELOPER_MODE = false;
	}

	public static class Extra {
		public static final String IMAGES = "com.drunkers_help.IMAGES";
		public static final String IMAGE_POSITION = "com.drunkers_help.IMAGE_POSITION";
	}

}
