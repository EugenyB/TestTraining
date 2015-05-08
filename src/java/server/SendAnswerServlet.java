
package server;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Eugeny
 */
@WebServlet(name = "SendAnswerServlet", urlPatterns = {"/sendanswer"})
public class SendAnswerServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer current = (Integer)session.getAttribute("current");
        
        String ans = request.getParameter("ans");
        if ("Next".equals(ans)) {
            current = (current + 1) % 50;
        }
        if ("Prev".equals(ans)) {
            current = (current + 50-1) % 50;
        }
        if ("Accept".equals(ans)) {
            int[] answers = (int[]) session.getAttribute("answers");
            String q = request.getParameter("q");
            if (q!=null) {
                int a = Integer.parseInt(q);
                answers[current] = a;
                session.setAttribute("answers", answers);
            }
            current = (current + 1) % 50;    
        }
        if ("Finish".equals(ans) && "1".equals(request.getParameter("finish"))) {
                response.sendRedirect("finish");
        } else {
            session.setAttribute("current", current);
            response.sendRedirect("run");    
        }
        
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
