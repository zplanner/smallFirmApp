package ogloszenia.serwlety;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ogloszenia.baza.DostepDoBazy;
import ogloszenia.baza.OgloszeniaDAO;
import ogloszenia.exn.BladBazyDanych;
import ogloszenia.model.Samochodowe;

@WebServlet({"/S01_OgloszeniaTxt", "/ogloszenia.txt"})
public class S01_OgloszeniaTxt extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
        try(DostepDoBazy db = new DostepDoBazy()) {
            OgloszeniaDAO dao = db.ogloszeniaDAO();

            List<Samochodowe> ogloszenia = dao.wszystkieOgloszenia();
            for(Samochodowe ogl : ogloszenia) {
                out.println(ogl);
            }
        } catch (BladBazyDanych e) {
            e.printStackTrace(out);
        }
		out.close();
	}

}
