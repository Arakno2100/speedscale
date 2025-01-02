package control.common;

import service.RegistroUtenti;
import model.bean.Utente;
import model.bean.Ruolo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;

@WebServlet("/common/Login")
public class LoginServlet extends HttpServlet {

    @EJB
    private RegistroUtenti registroUtenti;

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        List<String> errors = validateInputs(email, password);
        
        if (!errors.isEmpty()) {
            forwardToLoginPage(request, response, errors);
            return ;
        }
        
        email = email.trim();
        password = encryptPassword(password);
        
        System.out.println("Credenziali ok");

        // Autenticazione dell'utente
        Utente utente = registroUtenti.authenticate(email, password);
        
        if (utente != null) {
            // Se l'utente è stato autenticato, inizializza la sessione
            HttpSession session = request.getSession();
            registroUtenti.inizializzaSessione(utente, session);

            // Redirect alla home o a una pagina protetta
            redirectUser(request, response, utente);
        } else {
            // Se l'autenticazione fallisce, mostra un messaggio di errore
            errors.add("Username o password non validi!");
            forwardToLoginPage(request, response, errors);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    private List<String> validateInputs(String email, String password) {
        List<String> errors = new ArrayList<>();

        if (email == null || email.trim().isEmpty())
            errors.add("Il campo 'email' non può essere vuoto!");

        if (password == null || password.trim().isEmpty())
            errors.add("Il campo 'password' non può essere vuoto!");

        return errors;
    }
    
    private void forwardToLoginPage(HttpServletRequest request, HttpServletResponse response, List<String> errors) throws ServletException, IOException {
        request.setAttribute("errors", errors);
        request.getRequestDispatcher("/common/login.jsp").forward(request, response);
    }
    
    private String encryptPassword(String password) {
        StringBuilder hashString = null;

        try
        {
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-512");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            hashString = new StringBuilder();

            for (int i = 0; i < hash.length; i++)
                hashString.append(Integer.toHexString((hash[i] & 0xFF) | 0x100), 1, 3);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return hashString.toString();
    }
    
    private void redirectUser(HttpServletRequest request, HttpServletResponse response, Utente utente) throws IOException {
        List<Ruolo> ruoli = utente.getRuoli();
        
        if (ruoli.contains(Ruolo.CLIENTE))
            response.sendRedirect(request.getContextPath() + "/common/LoadOrCreateCartServlet");
        else if (ruoli.contains(Ruolo.AMMINISTRATORE))
            response.sendRedirect(request.getContextPath() + "/admin/homepage.jsp");
    }
}
