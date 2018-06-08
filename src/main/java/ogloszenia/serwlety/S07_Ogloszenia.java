package ogloszenia.serwlety;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ogloszenia.baza.DostepDoBazy;
import ogloszenia.baza.OgloszeniaDAO;
import ogloszenia.exn.BladBazyDanych;
import ogloszenia.model.Samochodowe;

@WebServlet("/S07_Ogloszenia")
public class S07_Ogloszenia extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private void dzialaj(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		
		// robimy forward, czyli wewnętrzne przekierowanie po stronie serwera
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/P07_Ogloszenia.jsp");
		if(dispatcher != null) {
			dispatcher.forward(request, response);
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		dzialaj(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		dzialaj(request, response);
	}
	
	private static BigDecimal str2BD(String s) {
		if(s == null || s.isEmpty()) {
			return null;
		} else {
			return new BigDecimal(s);
		}
	}
}
