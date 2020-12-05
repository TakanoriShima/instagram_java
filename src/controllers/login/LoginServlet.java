package controllers.login;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.User;
import utils.DBUtil;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getSession().getAttribute("flush") != null) {
			request.setAttribute("flush", request.getSession().getAttribute("flush"));
			request.getSession().removeAttribute("flush");
		}

		System.out.println("aaa");
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login/login.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		User login_user = null;

		EntityManager em = DBUtil.createEntityManager();

		try {
			if(email.equals("") || password.equals("")){
				email = "a";
				password = "a";
			}
			login_user = em.createNamedQuery("checkLoginEmailAndPassword", User.class).setParameter("email", email)
					.setParameter("password", password).getSingleResult();
			System.out.println("u: " + login_user);

			em.close();

			if (login_user != null) {
				// 認証できたらログイン状態にしてトップページへリダイレクト
				request.getSession().setAttribute("login_user", login_user);

				if (request.getSession().getAttribute("flush") != null) {
					request.setAttribute("flush", "ログインしました。");
					request.getSession().removeAttribute("flush");
				}

				response.sendRedirect(request.getContextPath() + "/top");
			} else {

				// if (request.getSession().getAttribute("flush") != null) {
				request.setAttribute("flush", "ユーザ情報を正しく入力してください。");
				request.getSession().removeAttribute("flush");
				// }

				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login/login.jsp");
				rd.forward(request, response);

			}

		} catch (Exception e) {
			// if (request.getSession().getAttribute("flush") != null) {
			request.setAttribute("flush", "ログイン情報を入力してください。");
			request.getSession().removeAttribute("flush");
			// }
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login/login.jsp");
			rd.forward(request, response);
			// response.sendRedirect(request.getContextPath() + "/login");

			// System.out.println("ユーザは存在しません。");
		}

	}
}
