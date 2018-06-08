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

@WebServlet({"/S02_OgloszeniaHtml", "/ogloszenia2.html"})
public class S02_OgloszeniaHtml extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		out.println("<!DOCTYPE html>");
		out.println("<html><head>");
		out.println("<title>Wszystkie ogłoszenia</title>");
		out.println("<link rel='stylesheet' type='text/css' href='styl.css'>");
		out.println("</head><body>");
		out.println("<h1>Serwlet wypisujący HTML</h1>");

		try(DostepDoBazy db = new DostepDoBazy()) {
            OgloszeniaDAO dao = db.ogloszeniaDAO();

            List<Samochodowe> ogloszenia = dao.wszystkieOgloszenia();
            for(Samochodowe ogl : ogloszenia) {
                out.println(ogl.dajHtml());
            }
        } catch (BladBazyDanych e) {
    		out.println("<p class='error'>" + e + "</p>");
        }
		out.println("</body></html>");
		out.close();
	}

}
