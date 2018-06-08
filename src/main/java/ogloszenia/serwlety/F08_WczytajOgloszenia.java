package ogloszenia.serwlety;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import ogloszenia.baza.DostepDoBazy;
import ogloszenia.baza.OgloszeniaDAO;
import ogloszenia.exn.BladBazyDanych;
import ogloszenia.model.Samochodowe;

/**
 * Servlet Filter implementation class F08_WczytajOgloszenia
 */
@WebFilter("/P08_Ogloszenia.jsp")
public class F08_WczytajOgloszenia implements Filter {
	public void init(FilterConfig fConfig) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String parametrMin = request.getParameter("minpost");
		String parametrMax = request.getParameter("maxpost");
		
		BigDecimal cenaOd = str2BD(parametrMin);
		BigDecimal cenaDo = str2BD(parametrMax);
		
		try(DostepDoBazy db = new DostepDoBazy()) {
			OgloszeniaDAO dao = db.ogloszeniaDAO();
			List<Samochodowe> lista = dao.ogloszeniaWedlugCeny(cenaOd, cenaDo);
			request.setAttribute("ogloszenia", lista);
			
		} catch (BladBazyDanych e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	private static BigDecimal str2BD(String s) {
		if(s == null || s.isEmpty()) {
			return null;
		} else {
			return new BigDecimal(s);
		}
	}
}
