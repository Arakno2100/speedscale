package control.admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import model.bean.Marca;
import model.bean.Prodotto;
import model.bean.Scala;
import model.dao.ProdottoDAO;

@WebServlet("/admin/UpdateProductServlet")
public class UpdateProductServlet extends HttpServlet {
    
    @EJB
    private ProdottoDAO prodottoDAO;

    @Override
    public void init() throws ServletException {}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        long id = Long.parseLong(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String descrizione = request.getParameter("descrizione");
        
        String scalaStr = request.getParameter("scala");
        Scala scala = Scala.valueOf(scalaStr);
        
        String marcaStr = request.getParameter("marca");
        Marca marca = Marca.valueOf(marcaStr);
        
        float prezzo = Float.parseFloat(request.getParameter("prezzo"));
        int quantita = Integer.parseInt(request.getParameter("quantita"));
        
        String urlsStr = request.getParameter("urls");
        List<String> urls = new ArrayList();
        urls.add(urlsStr);
        
        //Recuperare prodotto da modificare
        Prodotto prodotto = prodottoDAO.findById(id);
        
        if (prodotto == null) {
            //Redirect pagina di errore
        }
        
        prodotto.setNome(nome);
        prodotto.setDescrizione(descrizione);
        prodotto.setScala(scala);
        prodotto.setMarca(marca);
        prodotto.setPrezzo(prezzo);
        prodotto.setQuantitàDisponibile(quantita);
        prodotto.setUrls(urls);
        
        prodottoDAO.save(prodotto);
        
        response.sendRedirect(request.getContextPath() + "/admin/RetrieveProductsServlet");
    }
}