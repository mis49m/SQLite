package tr.com.mis49m.sqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHandler db;
    TextView tvCount;
    EditText etID, etName, etPhone;
    Button btnInsert, btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db=new DatabaseHandler(getApplicationContext());

        //-- read ui references
        tvCount = (TextView) findViewById(R.id.tv_count);
        etID = (EditText) findViewById(R.id.txt_id);
        etName = (EditText) findViewById(R.id.txt_name);
        etPhone = (EditText) findViewById(R.id.txt_phone);
        etPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        btnInsert = (Button) findViewById(R.id.btn_insert);
        btnUpdate = (Button) findViewById(R.id.btn_update);
        btnDelete = (Button) findViewById(R.id.btn_delete);

        showContactCounts();
        isUpdateForm(false);
    }

    public void delete(View view){
        String id = etID.getText().toString();

        Contact c =new Contact(Integer.valueOf(id), "", "");
        int r = db.deleteContact(c);

        if(r>0){
            showMessage("Contact is updated!");
            showContactCounts();
            clearValues();
        }else{
            showMessage("Error!");
        }
    }

    public void update(View view){
        String id = etID.getText().toString();
        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();

        Contact c =new Contact(Integer.valueOf(id), name, phone);
        int r = db.updateContact(c);

        if(r>0){
            showMessage("Contact is updated!");
            clearValues();
        }else{
            showMessage("Error!");
        }
    }

    public void getContact(View view){
        String id = etID.getText().toString();

        Contact c = db.getContact(Integer.valueOf(id));
        etName.setText(c.getName());
        etPhone.setText(c.getPhoneNumber());

        isUpdateForm(true);
    }

    public void insert(View view){
        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();

        Contact c =new Contact(0, name, phone);
        long r=db.addContact(c);

        if(r>0){
            showMessage("Contact is inserted!");
            showContactCounts();
            clearValues();
        }else{
            showMessage("Error!");
        }

    }

    private void showContactCounts(){
        tvCount.setText("Contacts count : " + db.getContactsCount());
    }

    private void clearValues(){
        etID.setText("");
        etName.setText("");
        etPhone.setText("");

        isUpdateForm(false);
    }

    private void isUpdateForm(boolean isUpdate){
        btnInsert.setEnabled(!isUpdate);
        btnUpdate.setEnabled(isUpdate);
        btnDelete.setEnabled(isUpdate);
    }

    private void showMessage(String value){
        Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
    }

}
