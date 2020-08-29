package controllers.posts;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import models.Post;
import models.User;
import utils.DBUtil;
import utils.EncryptUtil;

/**
 * Servlet implementation class PostsCreateServlet
 */
@MultipartConfig
@WebServlet("/posts/create")
public class PostsCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostsCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 画像アップロード
        Part part = request.getPart("image");

        if (part.getSize() != 0) {

            String filename = getFileName(part);

            String filePath = getServletContext().getRealPath("/uploads/") + filename;

            System.out.println("filePath!!!" + filePath);

            File uploadDir = new File(getServletContext().getRealPath("/uploads/"));
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }


            part.write(filePath);

            EntityManager em = DBUtil.createEntityManager();

            Post p = new Post();

            //レポート作成者のidを取得
            User login_user = (User) request.getSession().getAttribute("login_user");
            p.setUser(login_user);

            p.setTitle(request.getParameter("title"));
            p.setContent(request.getParameter("content"));

            p.setImage(filename);

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            p.setCreated_at(currentTime);

            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();

            em.close();

            request.getSession().setAttribute("flush", "新規投稿が完了しました。");

            response.sendRedirect(request.getContextPath() + "/top");

        }
    }

    // 拡張子を変えずに、ランダムな名前のファイルを生成する
    private String getFileName(Part part) {
        String[] headerArrays = part.getHeader("Content-Disposition").split(";");
        String fileName = null;
        for (String head : headerArrays) {
            if (head.trim().startsWith("filename")) {
                fileName = head.substring(head.indexOf('"')).replaceAll("\"", "");
            }
        }

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        String randName = EncryptUtil.getWordEncrypt(currentTime.toString());
        String extension = fileName.substring(fileName.lastIndexOf("."));

        String rndFileName = randName + extension;

        return rndFileName;
    }

}