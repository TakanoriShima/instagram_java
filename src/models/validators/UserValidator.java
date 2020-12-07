package models.validators;

import java.util.ArrayList;
import java.util.List;

import models.User;

public class UserValidator {
	public static List<String> validate(User u) {
		List<String> errors = new ArrayList<String>();

		String name_error = _validateName(u.getName());
		if (!name_error.equals("")) {
			errors.add(name_error);
		}

		String email_error = _validateEmail(u.getEmail());
		if (!email_error.equals("")) {
			errors.add(email_error);
		}

		String password_error = _validatePassword(u.getPassword());
		if (!password_error.equals("")) {
			errors.add(password_error);
		}

		return errors;
	}

	private static String _validateName(String name) {
		if (name == null || name.equals("")) {
			return "名前を入力してください。";
		}

		return "";
	}

	private static String _validateEmail(String email) {
		if (email == null || email.equals("")) {
			return "メールアドレスを入力してください。";
		}

		return "";

	}

	private static String _validatePassword(String password) {
		if (password == null || password.equals("")) {
			return "パスワードを入力してください。";
		}

		return "";

	}
}
