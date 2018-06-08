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

@WebServlet({"/S09_Ogloszenia", "/S09_OgloszeniaSesja", "/ogloszenia.html", "/ogloszenia"})
public class S09_Ogloszenia extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String min = request.getParameter("min");
		String max = request.getParameter("max");
		
		BigDecimal cenaOd = min == null || min.isEmpty() ? null : new BigDecimal(min);
		BigDecimal cenaDo = max == null || max.isEmpty() ? null : new BigDecimal(max);

		try(DostepDoBazy db = new DostepDoBazy()) {
			OgloszeniaDAO dao = db.ogloszeniaDAO();
			List<Samochodowe> ogloszenia = dao.ogloszeniaWedlugCeny(cenaOd, cenaDo);
			
			request.setAttribute("ogloszenia", ogloszenia);
			
			RequestDispatcher dispatcher =
					request.getRequestDispatcher("WEB-INF/P09_Ogloszenia.jsp");
			
			if(dispatcher != null) {
				dispatcher.forward(request, response);
			}
			
		} catch (BladBazyDanych e) {
			e.printStackTrace();
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
