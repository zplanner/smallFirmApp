package ogloszenia.serwlety;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ogloszenia.baza.DostepDoBazy;
import ogloszenia.baza.OgloszeniaDAO;
import ogloszenia.exn.BladBazyDanych;
import ogloszenia.exn.NieznanyRekord;
import ogloszenia.model.Samochodowe;

@WebServlet("/DodajDoSchowka")
public class DodajDoSchowka extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		
		try {
			int idOgloszenia = Integer.parseInt(id);
			
			try(DostepDoBazy db = new DostepDoBazy()) {
				OgloszeniaDAO dao = db.ogloszeniaDAO();
				Samochodowe ogloszenie = dao.odczytajWgId(idOgloszenia);
				
				HttpSession sesja = request.getSession();
				
				Object o = sesja.getAttribute("schowek");
				if(o != null && o instanceof Collection) {
					Collection schowek = (Collection) o;
					schowek.add(ogloszenie);
				}
				
				// nie trzeba zapisywać w sesji, bo `schowek` jest referencją do wciąż tego samego obiektu
				
			} catch (BladBazyDanych | NieznanyRekord e) {
				e.printStackTrace();
			}
		} catch (NumberFormatException e) {
		}

		// polecenie dla przeglądarki "przejdź pod podany adres"
		response.sendRedirect("S09_OgloszeniaSesja");
	}
}
