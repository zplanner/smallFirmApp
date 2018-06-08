package ogloszenia.serwlety;

import java.math.BigDecimal;
import java.util.List;

import ogloszenia.baza.DostepDoBazy;
import ogloszenia.baza.OgloszeniaDAO;
import ogloszenia.exn.BladBazyDanych;
import ogloszenia.model.Samochodowe;

public class DostarczycielDanych {
	private String min, max;
	
	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}
	
	public List<Samochodowe> getWszystkieOgloszenia() throws BladBazyDanych {
		try(DostepDoBazy db = new DostepDoBazy()) {
			OgloszeniaDAO dao = db.ogloszeniaDAO();
			return dao.wszystkieOgloszenia();
		}
	}

	public List<Samochodowe> getOgloszeniaWgCeny() throws BladBazyDanych {
		try(DostepDoBazy db = new DostepDoBazy()) {
			OgloszeniaDAO dao = db.ogloszeniaDAO();
			
			BigDecimal cenaOd = str2BD(min);
			BigDecimal cenaDo = str2BD(max);
			
			return dao.ogloszeniaWedlugCeny(cenaOd, cenaDo);
		}
	}

	private static BigDecimal str2BD(String s) {
		if(s == null || s.isEmpty()) {
			return null;
		} else {
			return new BigDecimal(s);
		}
	}
}
