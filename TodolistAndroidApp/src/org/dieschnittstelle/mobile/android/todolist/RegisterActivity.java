package org.dieschnittstelle.mobile.android.todolist;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dieschnittstelle.mobile.android.todolist.db.UserdataHandler;
import org.dieschnittstelle.mobile.android.todolist.model.ColorHandler;
import org.dieschnittstelle.mobile.android.todolist.model.User;

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
import android.widget.Toast;

public class RegisterActivity extends Activity {

	protected static final String logger = RegisterActivity.class
			.getSimpleName();

	private EditText editName;
	private EditText editSurname;
	private EditText editEmail;
	private EditText editPassword;
	private Button btnRegister;

	private UserdataHandler uDbHandler;

	private ColorHandler colorHandler = new ColorHandler();

	private TextWatcher watcher;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_register);

		uDbHandler = new UserdataHandler(this);

		watcher = new LocalTextWatcher();

		colorHandler
				.setActivityBackground(this, colorHandler.getMyColorBlack());

		editName = (EditText) findViewById(R.id.regis_edit_name);
		editSurname = (EditText) findViewById(R.id.regis_edit_surname);
		editEmail = (EditText) findViewById(R.id.regis_edit_email);
		editPassword = (EditText) findViewById(R.id.regis_edit_password);

		btnRegister = (Button) findViewById(R.id.register_Btn);
		btnRegister.setEnabled(false);
		colorHandler.setButtonColor(btnRegister,
				colorHandler.getMyColorRoyalblue(),
				colorHandler.getMyColorWhite());

		btnRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				User newUser = new User();

				newUser.setName(editName.getText().toString());
				newUser.setSurname(editSurname.getText().toString());
				newUser.setEmail(editEmail.getText().toString());
				newUser.setPassword(editPassword.getText().toString());

				uDbHandler.addUser(newUser);

				Log.i(logger, "onClick()" + v + newUser.getPersonalData());
				Toast.makeText(RegisterActivity.this,
						"User added successfully!", Toast.LENGTH_SHORT).show();

				Intent intent = new Intent(RegisterActivity.this,
						LoginActivity.class);
				startActivity(intent);
			}
		});
		editEmail.addTextChangedListener(watcher);
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

		boolean validEmail = validateEmail(editEmail.getText().toString());
		boolean validPass = validatePassword(editPassword.getText().toString());
		boolean passwordReady = false;
		boolean longEnough = editPassword.getText().length() > 5;

		Log.i(logger, "enableSubmitIfReady()" + longEnough + validEmail
				+ validPass);

		if (validPass && longEnough) {
			passwordReady = true;
		}

		if (validEmail && passwordReady) {
			btnRegister.setEnabled(true);
		}
	}

	private boolean validateEmail(String email) {

		String validEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

		Pattern pattern = Pattern.compile(validEmail);
		Matcher matcher = pattern.matcher(email);

		return matcher.matches();
	}

	private boolean validatePassword(String password) {

		String validPass = "[0-9]+";

		Pattern pattern = Pattern.compile(validPass);
		Matcher matcher = pattern.matcher(password);

		return matcher.matches();
	}

}
