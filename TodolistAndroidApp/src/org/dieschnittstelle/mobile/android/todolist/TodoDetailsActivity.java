package org.dieschnittstelle.mobile.android.todolist;

import org.dieschnittstelle.mobile.android.todolist.db.DatabaseHandler;
import org.dieschnittstelle.mobile.android.todolist.model.ColorHandler;
import org.dieschnittstelle.mobile.android.todolist.model.Todo;

import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class TodoDetailsActivity extends FragmentActivity implements
		OnClickListener, OnDateSetListener {

	public static final String ARG_CURRENT_TODO_OBJECT = TodolistActivity.ARG_TODO_OBJECT;
	public static final int RESPONSE_TODO_UPDATED = 1;
	public static final int RESPONSE_TODO_DELETED = 2;
	public static final int RESPONSE_NOCHANGE = -1;

	protected static final String logger = TodoDetailsActivity.class
			.getSimpleName();

	private DatabaseHandler dbHandler;
	private Todo currentTodo;

	private EditText editName;
	private EditText editContent;
	private Button btnDate;
	private Button btnSave;
	private Button btnDelete;
	private CheckBox doneBox;
	private CheckBox importantBox;
	private DatePicker datepicker;
	private String date;

	private ColorHandler colorHandler = new ColorHandler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_details);
		colorHandler
				.setActivityBackground(this, colorHandler.getMyColorBlack());

		dbHandler = new DatabaseHandler(this);
		date = new String();

		try {

			this.editName = (EditText) findViewById(R.id.details_edit_name);
			this.editContent = (EditText) findViewById(R.id.details_edit_content);

			this.datepicker = (DatePicker) findViewById(R.id.datepicker);

			this.btnSave = (Button) findViewById(R.id.save_Btn);
			this.btnDelete = (Button) findViewById(R.id.delete_Btn);
			this.btnDate = (Button) findViewById(R.id.date_Btn);

			colorHandler.setButtonColor(this.btnSave,
					colorHandler.getMyColorRoyalblue(),
					colorHandler.getMyColorWhite());
			colorHandler.setButtonColor(this.btnDelete,
					colorHandler.getMyColorFirebrick(),
					colorHandler.getMyColorWhite());
			colorHandler.setButtonColor(this.btnDate,
					colorHandler.getMyColorSnow(),
					colorHandler.getMyColorWhite());

			this.importantBox = (CheckBox) findViewById(R.id.details_check_important);

			this.importantBox
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							currentTodo.setImportant(isChecked);
						}
					});

			this.doneBox = (CheckBox) findViewById(R.id.details_check_done);

			this.doneBox
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							currentTodo.setDone(isChecked);
						}
					});

			currentTodo = (Todo) getIntent().getSerializableExtra(
					ARG_CURRENT_TODO_OBJECT);

			Log.i(logger, currentTodo.getDate());

			if (currentTodo != null) {
				editName.setText(currentTodo.getName());
				editContent.setText(currentTodo.getContent());
				this.importantBox.setChecked(currentTodo.isImportant());
				this.doneBox.setChecked(currentTodo.isDone());
				colorHandler.setButtonText(this.btnDate,
						this.currentTodo.getDate());

			} else {
				this.currentTodo = new Todo();
			}

			this.btnSave.setOnClickListener(this);
			this.btnDelete.setOnClickListener(this);
			this.btnDate.setOnClickListener(this);

		} catch (Exception e) {
			String err = "got exception: " + e;
			Log.e(logger, err, e);
			Toast.makeText(this, err, Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onClick(View view) {
		if (view == this.btnSave) {
			todoSave();
		} else if (view == this.btnDelete) {
			todoDelete();
		} else if (view == this.btnDate) {
			showDatePickerDialog(datepicker);
		} else {
			Log.w(logger,
					"got onClick() on view where it will not be handled: "
							+ view);
		}
	}

	protected void todoSave() {
		String inputName = editName.getText().toString();
		if (inputName.trim().length() == 0) {
			Toast.makeText(this, "Please enter a name for your Todo!",
					Toast.LENGTH_SHORT).show();
		} else {
			currentTodo.setName(this.editName.getText().toString());
			currentTodo.setContent(this.editContent.getText().toString());

			if (this.importantBox.isChecked()) {
				currentTodo.setImportant(true);
			}
			if (this.doneBox.isChecked()) {
				currentTodo.setDone(true);
			}

			dbHandler.saveTodo(currentTodo);

			Intent returnIntent = new Intent();

			returnIntent.putExtra(ARG_CURRENT_TODO_OBJECT, currentTodo);
			setResult(RESPONSE_TODO_UPDATED, returnIntent);
			finish();
		}

	}

	public void showDatePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getSupportFragmentManager(), "datePicker");
	}

	protected void todoDelete() {
		Intent returnIntent = new Intent();

		returnIntent.putExtra(ARG_CURRENT_TODO_OBJECT, currentTodo);
		setResult(RESPONSE_TODO_DELETED, returnIntent);
		finish();
	}

	public void onDateSet(DatePicker view, int year, int month, int day) {

		StringBuilder sb = new StringBuilder();

		String setMonth = String.valueOf(month);
		;

		switch (month) {
		case 0: {
			setMonth = "January";
		}
			break;
		case 1: {
			setMonth = "February";
		}
			break;
		case 2: {
			setMonth = "March";
		}
			break;
		case 3: {
			setMonth = "April";
		}
			break;
		case 4: {
			setMonth = "May";
		}
			break;
		case 5: {
			setMonth = "June";
		}
			break;
		case 6: {
			setMonth = "July";
		}
			break;
		case 7: {
			setMonth = "August";
		}
			break;
		case 8: {
			setMonth = "September";
		}
			break;
		case 9: {
			setMonth = "October";
		}
			break;
		case 10: {
			setMonth = "November";
		}
			break;
		case 11: {
			setMonth = "December";
		}

		default:
			break;
		}

		String setYear = String.valueOf(year);
		String setDay = String.valueOf(day);

		sb.append(setMonth + " ");
		sb.append(setDay + ", ");
		sb.append(setYear);

		date = sb.toString();
		this.currentTodo.setDate(date);

		colorHandler.setButtonText(this.btnDate, date);

		Log.i(logger, date);
	}

}
