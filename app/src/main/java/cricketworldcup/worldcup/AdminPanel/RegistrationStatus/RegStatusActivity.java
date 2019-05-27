package cricketworldcup.worldcup.AdminPanel.RegistrationStatus;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cricketworldcup.worldcup.AdminPanel.AdminHomeActivity;
import cricketworldcup.worldcup.AdminPanel.all_data_model;
import cricketworldcup.worldcup.R;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class RegStatusActivity extends AppCompatActivity {
    private Spinner gm, rsm, dsm;

    private File directory, sd, file;
    private WritableWorkbook workbook;
    private List<all_data_model> list;
    private List<all_filter_model_single> listSingle;
    private static final String TAG = "APP";
    private DatabaseReference databaseReference, datab;

    private String dsmname;
    private String smname;
    private String smffc;
    private String rsmname;
    private String rsmffc;
    private String rfid;
    private String region;

    private String userdsmcode;
    private String username;
    private String userphoneno;
    private TextView total;
    private int j = 0;
    private String gm_pick = "no";
    private String rsm_pick = "no";
    private String dsm_pick = "no";
    private int g = 0, r = 0, d = 0;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_status);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        datab = FirebaseDatabase.getInstance().getReference().child("data");
        gm = findViewById(R.id.gm_spinner);
        rsm = findViewById(R.id.rsm_spinner);
        dsm = findViewById(R.id.dsm_spinner);
        ShowGmSpinner();
        ShowRsmSpinner(R.array.rsm_normal);
        ShowDsmSpinner(R.array.dsm_normal);
        list = new ArrayList<>();
        listSingle = new ArrayList<>();
        total = findViewById(R.id.tv_showtotal);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("File Downloading.....");
        progressDialog.setCanceledOnTouchOutside(false);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    userdsmcode = String.valueOf(ds.child("userdsmcode").getValue());
                    username = String.valueOf(ds.child("username").getValue());
                    userphoneno = String.valueOf(ds.child("userphoneno").getValue());

                    AllThisInfo(userdsmcode, username, userphoneno);


                }


                int l = (int) dataSnapshot.getChildrenCount();
                total.setText("Total:  " + l + " ");
                Toast.makeText(RegStatusActivity.this, "" + l, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void FilterOperationSingleValue(String gm_value) {

        final String value = gm_value;
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    userdsmcode = String.valueOf(ds.child("userdsmcode").getValue());
                    username = String.valueOf(ds.child("username").getValue());
                    userphoneno = String.valueOf(ds.child("userphoneno").getValue());
                    AllThisInfoSingleFilter(userdsmcode, username, userphoneno, value);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void AllThisInfoSingleFilter(final String userdsmcode, String username, String userphoneno, final String value) {
        Toast.makeText(this, "Its Come to All Data List", Toast.LENGTH_SHORT).show();
        final String dsm = userdsmcode;
        final String name = username;
        final String phone = userphoneno;
        datab.orderByChild("G").equalTo(dsm).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    dsmname = String.valueOf(ds.child("A").getValue());
                    smname = String.valueOf(ds.child("B").getValue());
                    smffc = String.valueOf(ds.child("C").getValue());
                    rsmname = String.valueOf(ds.child("D").getValue());
                    rsmffc = String.valueOf(ds.child("E").getValue());
                    rfid = String.valueOf(ds.child("F").getValue());
                    region = String.valueOf(ds.child("H").getValue());
                }

                if (smname.equals(value)) {
                    listSingle.add(new all_filter_model_single(dsm, name, phone, dsmname, smname, smffc, rsmname, rsmffc, rfid, userdsmcode, region));

                }
                //   listSingle.add(new all_data_model(dsm, name, phone, dsmname, smname, smffc, rsmname, rsmffc, rfid, userdsmcode, region));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void ShowDsmSpinner(int data) {
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, data, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dsm.setAdapter(arrayAdapter);
        dsm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                //    questionInfo.put("question_four", adapterView.getItemAtPosition(i).toString());
                //  textView.setText((CharSequence) adapterView.getItemAtPosition(i));

                //profile = String.valueOf(adapterView.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void ShowRsmSpinner(int data) {

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, data, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rsm.setAdapter(arrayAdapter);
        rsm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                   if(i >j){
                       rsm_pick = String.valueOf(adapterView.getItemAtPosition(i));
                       if(rsm_pick.equals("MR. A.B.M. AHSANUL HOQUE")){
                           ShowDsmSpinner(R.array.rsm10);
                       }
                       if(rsm_pick.equals("MR. MD. ISMAIL HOSSAIN")){
                           ShowDsmSpinner(R.array.rsm1);
                       }
                       if(rsm_pick.equals("MR. MD. NASIR UDDIN HAIDER")){
                           ShowDsmSpinner(R.array.rsm2);
                       }
                       if(rsm_pick.equals("MR. MD. SHAFIQUL ISLAM")){
                           ShowDsmSpinner(R.array.rsm3);
                       }
                       if(rsm_pick.equals("MR. KAZI BENOZIR AHMED")){
                           ShowDsmSpinner(R.array.rsm4);
                       }
                       if(rsm_pick.equals("MR. MD. SIDDIQUR RAHMAN")){
                           ShowDsmSpinner(R.array.rsm5);
                       }
                       if(rsm_pick.equals("MR. MD. NASIR UDDIN")){
                           ShowDsmSpinner(R.array.rsm6);
                       }
                       if(rsm_pick.equals("MR. MD. ROBIUL OUAL")){
                           ShowDsmSpinner(R.array.rsm7);
                       }
                       if(rsm_pick.equals("MR. A.B.M. AHSANUL HOQUE")){
                           ShowDsmSpinner(R.array.rsm8);
                       }
                       if(rsm_pick.equals("MR. A.B.M. AHSANUL HOQUE")){
                           ShowDsmSpinner(R.array.rsm9);
                       }
                       if(rsm_pick.equals("MR. A.B.M. AHSANUL HOQUE")){
                           ShowDsmSpinner(R.array.rsm11);
                       }
                       if(rsm_pick.equals("MR. A.B.M. AHSANUL HOQUE")){
                           ShowDsmSpinner(R.array.rsm12);
                       }
                       if(rsm_pick.equals("MR. A.B.M. AHSANUL HOQUE")){
                           ShowDsmSpinner(R.array.rsm13);
                       }







                   }
                //    questionInfo.put("question_four", adapterView.getItemAtPosition(i).toString());
                //  textView.setText((CharSequence) adapterView.getItemAtPosition(i));

                //profile = String.valueOf(adapterView.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void AllThisInfo(final String userdsmcode, String username, String userphoneno) {

        final String dsm = userdsmcode;
        final String name = username;
        final String phone = userphoneno;
        datab.orderByChild("G").equalTo(dsm).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    dsmname = String.valueOf(ds.child("A").getValue());
                    smname = String.valueOf(ds.child("B").getValue());
                    smffc = String.valueOf(ds.child("C").getValue());
                    rsmname = String.valueOf(ds.child("D").getValue());
                    rsmffc = String.valueOf(ds.child("E").getValue());
                    rfid = String.valueOf(ds.child("F").getValue());
                    region = String.valueOf(ds.child("H").getValue());
                }

//  if(smname.equals("MR. SHAMSUR RAHMAN BHUIYAN")) {
//                            list.add(new all_data_model(dsm, name, phone, dsmname, smname, smffc, rsmname, rsmffc, rfid, userdsmcode, region));
//
//                }
                list.add(new all_data_model(dsm, name, phone, dsmname, smname, smffc, rsmname, rsmffc, rfid, userdsmcode, region));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void ShowGmSpinner() {
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.gm_normal, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gm.setAdapter(arrayAdapter);
        gm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                g = 1;
                // Toast.makeText(RegStatusActivity.this, "Choose Something", Toast.LENGTH_SHORT).show();

                if (i > j) {
                    if (i == 1) {
                        ShowRsmSpinner(R.array.gm1);
                    }
                    if (i == 2) {

                        ShowRsmSpinner(R.array.gm2);
                    }

                    if (i == 3) {
                        ShowRsmSpinner(R.array.gm3);
                    }
                    gm_pick = String.valueOf(adapterView.getItemAtPosition(i));

                }
                //    questionInfo.put("question_four", adapterView.getItemAtPosition(i).toString());
                //  textView.setText((CharSequence) adapterView.getItemAtPosition(i));

                //profile = String.valueOf(adapterView.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void DownLoadFile(View view) {
        progressDialog.show();
        createExcelSheet();


    }

    public void createExcelSheet() {
        if (isStoragePermissionGranted()) {
            String csvFile = "Info.xls";
            sd = Environment.getExternalStorageDirectory();
            directory = new File(sd.getAbsolutePath());
            file = new File(directory, csvFile);
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            try {
                workbook = Workbook.createWorkbook(file, wbSettings);
                createFirstSheet();

                //closing cursor
                workbook.write();
                workbook.close();

                Toast.makeText(this, "File Downloaded !", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            Toast.makeText(this, "Permission Denied !", Toast.LENGTH_SHORT).show();
        }
    }

    public void createFirstSheet() {
        try {

            //Excel sheet name. 0 (number)represents first sheet
            WritableSheet sheet = workbook.createSheet("sheet1", 0);
            // column and row title
            sheet.addCell(new Label(0, 0, "DSMCODE"));
            sheet.addCell(new Label(1, 0, "USERNAME"));
            sheet.addCell(new Label(2, 0, "PHONE_NO"));
            sheet.addCell(new Label(3, 0, "DSM_NAME"));
            sheet.addCell(new Label(4, 0, "SM_NAME"));
            sheet.addCell(new Label(5, 0, "SM_FFC"));
            sheet.addCell(new Label(6, 0, "RSMNAME"));
            sheet.addCell(new Label(7, 0, "RSM_FFC"));
            sheet.addCell(new Label(8, 0, "RFID"));
            sheet.addCell(new Label(9, 0, "FFC"));
            sheet.addCell(new Label(10, 0, "REGION"));


            for (int i = 0; i < list.size(); i++) {
                sheet.addCell(new Label(0, i + 1, list.get(i).getUserdsmcode()));
                sheet.addCell(new Label(1, i + 1, list.get(i).getUsername()));
                sheet.addCell(new Label(2, i + 1, list.get(i).getUserphoneno()));
                sheet.addCell(new Label(3, i + 1, list.get(i).getA()));
                sheet.addCell(new Label(4, i + 1, list.get(i).getB()));
                sheet.addCell(new Label(5, i + 1, list.get(i).getC()));
                sheet.addCell(new Label(6, i + 1, list.get(i).getD()));
                sheet.addCell(new Label(7, i + 1, list.get(i).getE()));
                sheet.addCell(new Label(8, i + 1, list.get(i).getF()));
                sheet.addCell(new Label(9, i + 1, list.get(i).getG()));
                sheet.addCell(new Label(10, i + 1, list.get(i).getH()));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
        }
    }

    public void DownLoadShortedData(View view) {
        //apply filter

        if (gm_pick.equals("no") && rsm_pick.equals("no") && dsm_pick.equals("no")) {

            Toast.makeText(this, "No Filter To Apply", Toast.LENGTH_SHORT).show();
        } else if (!gm_pick.equals("no") && rsm_pick.equals("no") && dsm_pick.equals("no")) {
            list.clear();
            Toast.makeText(this, "GM/SM Filter Applying", Toast.LENGTH_SHORT).show();
            FilterOperationSingleValue(gm_pick);

        }


    }


    public void DownloadFilterData(View view) {
        createExcelSheetSingle();
    }

    private void createExcelSheetSingle() {
        if (isStoragePermissionGranted()) {
            String csvFile = "Info.xls";
            sd = Environment.getExternalStorageDirectory();
            directory = new File(sd.getAbsolutePath());
            file = new File(directory, csvFile);
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            try {
                workbook = Workbook.createWorkbook(file, wbSettings);
                createFirstSheetSingle();

                //closing cursor
                workbook.write();
                workbook.close();

                Toast.makeText(this, "File Downloaded !", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            Toast.makeText(this, "Permission Denied !", Toast.LENGTH_SHORT).show();
        }

    }

    private void createFirstSheetSingle() {


        try {

            //Excel sheet name. 0 (number)represents first sheet
            WritableSheet sheet = workbook.createSheet("sheet1", 0);
            // column and row title
            sheet.addCell(new Label(0, 0, "DSMCODE"));
            sheet.addCell(new Label(1, 0, "USERNAME"));
            sheet.addCell(new Label(2, 0, "PHONE_NO"));
            sheet.addCell(new Label(3, 0, "DSM_NAME"));
            sheet.addCell(new Label(4, 0, "SM_NAME"));
            sheet.addCell(new Label(5, 0, "SM_FFC"));
            sheet.addCell(new Label(6, 0, "RSMNAME"));
            sheet.addCell(new Label(7, 0, "RSM_FFC"));
            sheet.addCell(new Label(8, 0, "RFID"));
            sheet.addCell(new Label(9, 0, "FFC"));
            sheet.addCell(new Label(10, 0, "REGION"));


            for (int i = 0; i < listSingle.size(); i++) {
                sheet.addCell(new Label(0, i + 1, listSingle.get(i).getUserdsmcode()));
                sheet.addCell(new Label(1, i + 1, listSingle.get(i).getUsername()));
                sheet.addCell(new Label(2, i + 1, listSingle.get(i).getUserphoneno()));
                sheet.addCell(new Label(3, i + 1, listSingle.get(i).getA()));
                sheet.addCell(new Label(4, i + 1, listSingle.get(i).getB()));
                sheet.addCell(new Label(5, i + 1, listSingle.get(i).getC()));
                sheet.addCell(new Label(6, i + 1, listSingle.get(i).getD()));
                sheet.addCell(new Label(7, i + 1, listSingle.get(i).getE()));
                sheet.addCell(new Label(8, i + 1, listSingle.get(i).getF()));
                sheet.addCell(new Label(9, i + 1, listSingle.get(i).getG()));
                sheet.addCell(new Label(10, i + 1, listSingle.get(i).getH()));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(RegStatusActivity.this,AdminHomeActivity.class));
        RegStatusActivity.this.overridePendingTransition(0,0);
    }
}
