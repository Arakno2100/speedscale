package control.common;

import model.bean.Utente;
import service.RegistroUtenti;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;


@WebServlet("/common/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {

    @EJB
    private RegistroUtenti registroUtenti;

    @Override
    public void init() throws ServletException {}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> errors = validateInput(request);

        if (!errors.isEmpty())
        {
            forwardToRegistrationPageWithErrors(request, response, errors);
            return ;
        }
        
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String data = request.getParameter("dataNascita");
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        
        try {            
            Utente nuovoUtente = registroUtenti.createUtente(email, password, nome, cognome, formatter.parse(data));
            
            System.out.println("Registrato nuovo utente:\t" + nuovoUtente);

            response.sendRedirect(request.getContextPath() + "/common/login.jsp");
        } catch (IllegalArgumentException e) {
            errors.add(e.getMessage());
            forwardToRegistrationPageWithErrors(request, response, errors);
        } catch (Exception e) {
            throw new ServletException("Errore durante il salvataggio dei dati", e);
        }
    }

    private List<String> validateInput(HttpServletRequest request) {
        List<String> errors = new ArrayList<>();

        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String data = request.getParameter("dataNascita");

        if (isNullOrEmpty(nome)) errors.add("Il nome è obbligatorio.");
        if (isNullOrEmpty(cognome)) errors.add("Il cognome è obbligatorio.");
        if (isInvalidEmail(email)) errors.add("L'email non è valida.");
        if (isNullOrEmpty(password)) errors.add("La password è obbligatoria.");
        if (isNullOrEmpty(data)) errors.add("La data di nascita è obbligatoria.");

        return errors;
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private boolean isInvalidEmail(String email) {
        return isNullOrEmpty(email) || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    private void forwardToRegistrationPageWithErrors(HttpServletRequest request, HttpServletResponse response, List<String> errors)
            throws ServletException, IOException {
        request.setAttribute("errors", errors);
        request.getRequestDispatcher("/common/registration.jsp").forward(request, response);
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
}