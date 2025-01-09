package control.common;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.bean.Utente;

@WebServlet("/common/RetrieveAccountOrdersServlet")
public class RetrieveAccountOrdersServlet extends HttpServlet {
    
    @Override
    public void init() throws ServletException {}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        Utente utente = (Utente) request.getSession().getAttribute("utente");
        
        request.setAttribute("orders", utente.getOrdini());
        
        System.out.println("Ordini account:\t" + utente.getOrdini());
        
        request.getRequestDispatcher("/common/manageOrders.jsp").forward(request, response);
    }
}