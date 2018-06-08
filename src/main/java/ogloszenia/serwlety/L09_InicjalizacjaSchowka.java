package ogloszenia.serwlety;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.TreeSet;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import ogloszenia.model.Samochodowe;

@WebListener
public class L09_InicjalizacjaSchowka implements HttpSessionListener {

    public void sessionCreated(HttpSessionEvent se)  {
    	HttpSession sesja = se.getSession();
    	sesja.setMaxInactiveInterval(60);  // po minucie sesja wygasa
    	
    	System.out.println("Utworzono sesję " + sesja.getId()
    		+ " " + sesja.getCreationTime());
    	
    	//sesja.setAttribute("schowek", new LinkedHashSet<>());
    	sesja.setAttribute("schowek",
    			new TreeSet<Samochodowe>((o1, o2) -> o1.getCena().compareTo(o2.getCena())));    
    }

    public void sessionDestroyed(HttpSessionEvent se)  { 
    	HttpSession sesja = se.getSession();
    	System.out.println("Koniec sesji    " + sesja.getId()
		+ " " + sesja.getCreationTime());
    }
	
}
