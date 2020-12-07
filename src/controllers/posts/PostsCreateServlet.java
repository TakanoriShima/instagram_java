package controllers.posts;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import models.Post;
import models.User;
import models.validators.PostValidator;
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String filename = "";
		String filePath = "";

		EntityManager em = DBUtil.createEntityManager();

		Post p = new Post();

		// レポート作成者のidを取得
		User login_user = (User) request.getSession().getAttribute("login_user");

		p.setUser(login_user);

		try {

			// 画像アップロード
			Part part = request.getPart("image");

			if (part.getSize() != 0) {

				// localに画像をアップロード
				filename = getFileName(part);

				filePath = getServletContext().getRealPath("/photos/") + filename;

				// System.out.println("filePath!!!" + filePath);
				//
				File uploadDir = new File(getServletContext().getRealPath("/photos/"));
				if (!uploadDir.exists()) {
					uploadDir.mkdir();
				}

				part.write(filePath);

				/* S3 */
				String region = (String) this.getServletContext().getAttribute("region");
				String awsAccessKey = (String) this.getServletContext().getAttribute("awsAccessKey");
				String awsSecretKey = (String) this.getServletContext().getAttribute("awsSecretKey");
				String bucketName = (String) this.getServletContext().getAttribute("bucketName");

				// 認証情報を用意
				AWSCredentials credentials = new BasicAWSCredentials(
						// アクセスキー
						awsAccessKey,
						// シークレットキー
						awsSecretKey);

				// クライアントを生成
				AmazonS3 s3 = AmazonS3ClientBuilder.standard()
						// 認証情報を設定
						.withCredentials(new AWSStaticCredentialsProvider(credentials))
						// リージョンを AP_NORTHEAST_1(東京) に設定
						.withRegion(region).build();

				// === ファイルから直接アップロードする場合 ===
				// アップロードするファイル
				File file = new File(filePath);
				// ファイルをアップロード
				s3.putObject(
						// アップロード先バケット名
						bucketName,
						// アップロード後のキー名
						"photos/" + filename,
						// ファイルの実体
						file);
			}
		} catch (Exception e) {
			System.out.println("S3失敗");
		}

		// データベースに情報を保存

		p.setTitle(request.getParameter("title"));
		p.setContent(request.getParameter("content"));

		p.setImage(filename);

		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		p.setCreated_at(currentTime);

		List<String> errors = PostValidator.validate(p);

		if (errors.size() > 0) {

			em.close();

			request.setAttribute("_token", request.getSession().getId());
			request.setAttribute("post", p);
			request.setAttribute("errors", errors);

			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/posts/new.jsp");
			rd.forward(request, response);
		} else {
			em.getTransaction().begin();
			em.persist(p);
			em.getTransaction().commit();
			em.close();

			if (request.getSession().getAttribute("flush") != null) {
				request.getSession().removeAttribute("flush");
				request.getSession().setAttribute("flush", "新規投稿が完了しました。");
			}

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
