
package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "StartTestServlet", urlPatterns = {"/starttest"})
public class StartTestServlet extends HttpServlet {

    List<Question> block1;
    List<Question> block2;
    List<Question> block3;
    List<Question> block4;

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
        String fio = request.getParameter("fio");
        String group = request.getParameter("group");
        HttpSession session = request.getSession();
        session.setAttribute("group", group);
        session.setAttribute("fio", fio);
        session.setAttribute("starttime", new Date());
        session.setAttribute("current", 0);
        session.setAttribute("total", 50);
        List<Question> questions = createTest();
        session.setAttribute("questions", questions);
        int[] answers = new int[50];
        for (int i=0; i<answers.length; i++) {
            answers[i] = -1;
        }
        session.setAttribute("answers", answers);
        response.sendRedirect("run");
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

    private List<Question> createTest() {
        List<Question> b1 = new ArrayList<>(block1);
        List<Question> b2 = new ArrayList<>(block2);
        List<Question> b3 = new ArrayList<>(block3);
        List<Question> b4 = new ArrayList<>(block4);
        Collections.shuffle(b1);
        Collections.shuffle(b2);
        Collections.shuffle(b3);
        Collections.shuffle(b4);
        List<Question> result = new ArrayList<>(50);
        result.addAll(b1.subList(0, 10));
        result.addAll(b2.subList(0, 10));
        result.addAll(b3.subList(0, 15));
        result.addAll(b4.subList(0, 15));
        
        for (Question q : result) {
            System.out.println(q);
        }
        return result;
    }

    @Override
    public void init() throws ServletException {
        super.init();
            block1 = readBlock("q1.txt");
            block2 = readBlock("q2.txt");
            block3 = readBlock("q3.txt");
            block4 = readBlock("q4.txt");

//        try (BufferedReader in = new BufferedReader(new FileReader("testtraining.ini"))){
//            String dirname = in.readLine();
////            block1 = readBlock(dirname+"/q1.txt");
////            block2 = readBlock(dirname+"/q2.txt");
////            block3 = readBlock(dirname+"/q3.txt");
////            block4 = readBlock(dirname+"/q4.txt");
//        } catch (IOException ex) {
//            Logger.getLogger(StartTestServlet.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
    }

    private List<Question> readBlock(String fileName) {
        List<Question> block = new ArrayList<>();
        try (InputStream openStream = new URL("http://berkut.homelinux.com/download/test/"+fileName).openStream()) { // url
            //BufferedReader in = new BufferedReader(new FileReader(fileName));
            
            BufferedReader in = new BufferedReader(new InputStreamReader(openStream, "cp1251")); // url
            String question;
            while ((question=in.readLine())!=null) {
                String pictureName = in.readLine();
                String addition = in.readLine();
                String[] answers = new String[4];
                int correct = -1;
                for (int i=0;i<answers.length; i++) {
                    String ans = in.readLine();
                    if (!ans.isEmpty() && ans.charAt(0)=='+') {
                        correct = i;
                        answers[i] = ans.substring(1);
                    } else answers[i] = ans; 
                }
                Question q = new Question(question, pictureName, addition, answers, correct);
                block.add(q);
                in.readLine(); // пустая строка
            }
            return block;
        } catch (IOException ex) {
            Logger.getLogger(StartTestServlet.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return block;
    }
    
}
