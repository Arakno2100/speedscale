package control.common;

import model.bean.Utente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import service.GestioneProfilo;

@WebServlet("/common/UpdateAccountServlet")
public class UpdateAccountServlet extends HttpServlet {

    @EJB
    private GestioneProfilo gestioneProfilo;

    @Override
    public void init() throws ServletException {}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Utente utente = (Utente) request.getSession().getAttribute("utente");

        if (utente == null)
        {
            redirectToLogin(response, request);
            return ;
        }
        
        String newNome = request.getParameter("nome");
        String newCognome = request.getParameter("cognome");
        String newData = request.getParameter("dataNascita");
        String newTelefono = request.getParameter("phone");
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        
        
        try {
            gestioneProfilo.modificaDatiPersonali(utente, newNome, newCognome, formatter.parse(newData), newTelefono);
        } catch (ParseException ex) {
            Logger.getLogger(UpdateAccountServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        response.sendRedirect(request.getContextPath() + "/common/homepage.jsp");
    }

    private void redirectToLogin(HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.sendRedirect(request.getContextPath() + "/common/login.jsp");
    }
}