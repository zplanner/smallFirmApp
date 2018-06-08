package ogloszenia.serwlety;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* Przykład serwletu wysyłajcego w wyniku dane binarne.
 * Zamiast PrintWriter używa się tutaj OutputStream.
 */
@WebServlet("/foto")
public class Foto extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//private static final String KATALOG = "C:/Tools/foto";
	private static final String KATALOG = "/home/patryk/auta/foto";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		
		Path plik = Paths.get(KATALOG, id+".jpg");
		
		byte[] dane = Files.readAllBytes(plik);
		
		response.setContentType("image/jpeg");
		try(OutputStream output = response.getOutputStream()) {
			output.write(dane);
		}
	}
}
