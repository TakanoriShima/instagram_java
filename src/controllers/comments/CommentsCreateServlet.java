package controllers.comments;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Comment;
import models.Post;
import models.User;
import utils.DBUtil;

/**
 * Servlet implementation class CommentsCreateServlet
 */
@WebServlet("/comments/create")
public class CommentsCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentsCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        System.out.println("OK");
        EntityManager em = DBUtil.createEntityManager();

        Post post = em.find(Post.class, Integer.parseInt(request.getParameter("post_id")));
        User login_user = (User) request.getSession().getAttribute("login_user");
        Comment m = new Comment();
        m.setUser(login_user);
        m.setPost(post);
        m.setContent(request.getParameter("content"));

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        m.setCreated_at(currentTime);

        em.getTransaction().begin();
        em.persist(m);
        em.getTransaction().commit();

        em.close();

        request.getSession().setAttribute("flush", "新規コメントの投稿が完了しました。");

        response.sendRedirect(request.getContextPath() + "/posts/show?id=" + post.getId());

    }

}
