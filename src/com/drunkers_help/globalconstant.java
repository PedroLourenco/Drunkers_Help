package com.drunkers_help;

public final class globalconstant {
	
	//log variables
	public static final boolean LOG = true;
    public static String TAG = "Drunker's Helper";

	public static double lat, lon;

	public static String cityName;

	public static int count = 0;

	public static final String[] IMAGES = new String[] {
			// Heavy images
			"drawable://" + R.drawable.amigos,
			"drawable://" + R.drawable.amstel,
			"drawable://" + R.drawable.asahi_black,
			"drawable://" + R.drawable.asahi,
			"drawable://" + R.drawable.bajitnka,
			"drawable://" + R.drawable.bankscaribbean,
			"drawable://" + R.drawable.baltika,
			"drawable://" + R.drawable.bavaria,
			"drawable://" + R.drawable.becks,
			"drawable://" + R.drawable.birra_moretti,
			"drawable://" + R.drawable.bitburger,
			"drawable://" + R.drawable.brahama,
			"drawable://" + R.drawable.bochkovoe,
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
			"drawable://" + R.drawable.daura,
			"drawable://" + R.drawable.dosequis,
			"drawable://" + R.drawable.duff, 
			"drawable://" + R.drawable.duvel,
			"drawable://" + R.drawable.estrella,
			"drawable://" + R.drawable.falcon, 
			"drawable://" + R.drawable.fino,
			"drawable://" + R.drawable.foster,
			"drawable://" + R.drawable.guiness,
			"drawable://" + R.drawable.heineken,
			"drawable://" + R.drawable.karhu,
			"drawable://" + R.drawable.karlovacko,
			"drawable://" + R.drawable.kilkenny,
			"drawable://" + R.drawable.kronenbourg_1994,
			"drawable://" + R.drawable.london_pride,
			"drawable://" + R.drawable.mahou,
			"drawable://" + R.drawable.mariestadsprima,
			"drawable://" + R.drawable.modeloespecial,
			"drawable://" + R.drawable.murphys,
			"drawable://" + R.drawable.negramodelo,
			"drawable://" + R.drawable.superbock,
			"drawable://" + R.drawable.skol,
			"drawable://" + R.drawable.old_speckled_hen,
			"drawable://" + R.drawable.pacifico,
			"drawable://" + R.drawable.palma_louca,
			"drawable://" + R.drawable.peroni_nastro,
			"drawable://" + R.drawable.pint,
			"drawable://" + R.drawable.royal_dutch,
			"drawable://" + R.drawable.sagres,
			"drawable://" + R.drawable.san_miguel,
			"drawable://" + R.drawable.sol,
			"drawable://" + R.drawable.stella_artois,
			"drawable://" + R.drawable.strela,
			"drawable://" + R.drawable.tagus,
			"drawable://" + R.drawable.tecate,
			"drawable://" + R.drawable.victoria };

	public static final Integer[] mThumbIds = { R.drawable.amigos,
			R.drawable.amstel, R.drawable.asahi_black, R.drawable.asahi,
			R.drawable.bajitnka, R.drawable.bankscaribbean, R.drawable.baltika,
			R.drawable.bavaria, R.drawable.becks, R.drawable.birra_moretti,
			R.drawable.bitburger, R.drawable.brahama, R.drawable.bochkovoe,
			R.drawable.budlightchelada, R.drawable.budweiser,
			R.drawable.buyjiu, R.drawable.carlsberg, R.drawable.cartablanca,
			R.drawable.chang, R.drawable.cobra, R.drawable.coral,
			R.drawable.corona, R.drawable.cristal, R.drawable.cruzcampo,
			R.drawable.daura, R.drawable.dosequis, R.drawable.duff,
			R.drawable.duvel, R.drawable.estrella, R.drawable.falcon,
			R.drawable.fino, R.drawable.foster, R.drawable.guiness,
			R.drawable.heineken, R.drawable.karhu, R.drawable.karlovacko,
			R.drawable.kilkenny, R.drawable.kronenbourg_1994,
			R.drawable.london_pride, R.drawable.mahou,
			R.drawable.mariestadsprima, R.drawable.modeloespecial,
			R.drawable.murphys, R.drawable.negramodelo, R.drawable.superbock,
			R.drawable.skol, R.drawable.old_speckled_hen, R.drawable.pacifico,
			R.drawable.palma_louca, R.drawable.peroni_nastro, R.drawable.pint,
			R.drawable.royal_dutch, R.drawable.sagres, R.drawable.san_miguel,
			R.drawable.sol, R.drawable.stella_artois, R.drawable.strela,
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
