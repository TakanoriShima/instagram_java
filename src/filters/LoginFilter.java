package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.User;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/*")
public class LoginFilter implements Filter {

    /**
     * Default constructor.
     */
    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
        // TODO Auto-generated method stub
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String context_path = ((HttpServletRequest) request).getContextPath();
        String servlet_path = ((HttpServletRequest) request).getServletPath();

        // ログインしていないのに、ログイン後のリソースにアクセスする場合
        HttpSession session = ((HttpServletRequest) request).getSession();
        User u = (User) session.getAttribute("login_user");

        System.out.println("User: " + u);
        System.out.println("サーブレットパス: " + servlet_path);

        if(u == null && servlet_path.equals("/top")){
        	session.setAttribute("flush", "不正アクセスです。ログインしてください。");
        	((HttpServletResponse) response).sendRedirect(context_path + "/login");
           return;
        }
        if(u == null && servlet_path.equals("/posts/new")){
        	session.setAttribute("flush", "不正アクセスです。ログインしてください。");
        	((HttpServletResponse) response).sendRedirect(context_path + "/login");
           return;
        }
        if(u == null && servlet_path.equals("/posts/show")){
        	session.setAttribute("flush", "不正アクセスです。ログインしてください。");
        	((HttpServletResponse) response).sendRedirect(context_path + "/login");
           return;
        }
        if(u == null && servlet_path.equals("/posts/new")){
        	session.setAttribute("flush", "不正アクセスです。ログインしてください。");
        	((HttpServletResponse) response).sendRedirect(context_path + "/login");
           return;
        }
        if(u == null && servlet_path.equals("/users/index")){
        	session.setAttribute("flush", "不正アクセスです。ログインしてください。");
        	((HttpServletResponse) response).sendRedirect(context_path + "/login");
           return;
        }

        chain.doFilter(request, response);
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
        // TODO Auto-generated method stub
    }

}