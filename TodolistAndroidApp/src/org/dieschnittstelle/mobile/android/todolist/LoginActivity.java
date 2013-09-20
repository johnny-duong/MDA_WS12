package org.dieschnittstelle.mobile.android.todolist;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dieschnittstelle.mobile.android.todolist.db.UserdataHandler;
import org.dieschnittstelle.mobile.android.todolist.model.ColorHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	protected static final String logger = LoginActivity.class.getSimpleName();

	private EditText editEmail;
	private EditText editPassword;
	private Button btnLogin;
	private TextView linkToRegScreen;

	private UserdataHandler uDbHandler;

	private ColorHandler colorHandler = new ColorHandler();

	private TextWatcher watcher;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_login);

		uDbHandler = new UserdataHandler(this);

		watcher = new LocalTextWatcher();

		colorHandler
				.setActivityBackground(this, colorHandler.getMyColorBlack());

		editEmail = (EditText) findViewById(R.id.login_edit_email);
		editPassword = (EditText) findViewById(R.id.login_edit_password);

		btnLogin = (Button) findViewById(R.id.login_Btn);
		colorHandler.setButtonColor(btnLogin,
				colorHandler.getMyColorRoyalblue(),
				colorHandler.getMyColorWhite());

		linkToRegScreen = (TextView) findViewById(R.id.link_to_register);

		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String email = editEmail.getText().toString();
				String password = editPassword.getText().toString();

				try {

					String user = uDbHandler
							.getUserDataByEmail(email, password);

					if (uDbHandler.checkLogin(email, password)) {
						Toast.makeText(LoginActivity.this, "Welcome " + user,
								Toast.LENGTH_LONG).show();

						Log.i(logger, "onClick()" + v);

						Intent intent = new Intent(LoginActivity.this,
								TodolistActivity.class);
						startActivity(intent);
					} else {
						Toast.makeText(LoginActivity.this,
								"Invalid Username or Password!",
								Toast.LENGTH_LONG).show();
					}
				} catch (Exception e) {
					Toast.makeText(LoginActivity.this, e.getMessage(),
							Toast.LENGTH_LONG).show();
				}
			}
		});

		linkToRegScreen.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent intent = new Intent(LoginActivity.this,
						RegisterActivity.class);
				startActivity(intent);
			}
		});

		editPassword.addTextChangedListener(watcher);

	}

	private class LocalTextWatcher implements TextWatcher {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {
			System.out.println(validateEmail(editEmail.getText().toString()));
			enableSubmitIfReady();
		}
	}

	private void enableSubmitIfReady() {

		boolean emailReady = validateEmail(editEmail.getText().toString());
		boolean passwordReady;

		if (editPassword.getText().length() > 0) {
			passwordReady = true;
		} else {
			passwordReady = false;
		}

		if (emailReady && passwordReady)
			btnLogin.setEnabled(true);
		else
			btnLogin.setEnabled(false);
	}

	private boolean validateEmail(String email) {

		String validEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

		Pattern pattern = Pattern.compile(validEmail);
		Matcher matcher = pattern.matcher(email);

		return matcher.matches();
	}

	@Override
	protected void onResume() {
		super.onResume();
		editEmail.setText("");
		editPassword.setText("");
	}

}
