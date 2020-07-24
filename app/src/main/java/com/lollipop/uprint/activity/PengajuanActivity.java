package com.lollipop.uprint.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.lollipop.uprint.Config;
import com.lollipop.uprint.R;
import com.lollipop.uprint.utils.AsyncTaskCompleteListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PengajuanActivity extends AppCompatActivity implements AsyncTaskCompleteListener {

    private TextView txtTotal;
    private EditText edtJmlPage,edtJmlhRangkap,edtUpload;
    private Spinner spinUkuran;
    private RadioGroup rgDelivery;
    private RadioButton rb_yes, rb_no;
    private int i = 1;
    private int ongkir = 15000;
    private int total=0;
    private Button btnUpload,btnSubmit;

    private final int GALLERY = 1;
    private ArrayList<HashMap<String, String>> arraylist;
    private String url = "https://www.google.com";
    private static final int BUFFER_SIZE = 1024 * 2;
    private static final String IMAGE_DIRECTORY = "/demonuts_upload_gallery";
//    private String status= "YA";

    Config session;
    private String id_user;
    ProgressDialog pd;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengajuan);

        requestMultiplePermissions();
        session = new Config(getApplicationContext());
        mContext = this;

        HashMap<String, String> user = session.getUserDetails();
        id_user = user.get(Config.KEY_ID);

        pd = new ProgressDialog(mContext);

        btnUpload = findViewById(R.id.btnUpload);
        btnSubmit = findViewById(R.id.btnSubmit);

        spinUkuran = findViewById(R.id.spinUkuranKertas);
        edtJmlPage = findViewById(R.id.edtJmlhPage);
        edtJmlhRangkap = findViewById(R.id.edtJmlhRangkap);
        edtUpload = findViewById(R.id.edtUpload);
        rgDelivery = findViewById(R.id.delivery);
        rb_yes = findViewById(R.id.rb1_yes);
        rb_no = findViewById(R.id.rb1_no);
        txtTotal = findViewById(R.id.txtTotal);

        edtJmlhRangkap.setEnabled(false);

        String s = null;
        try {
            // The comma in the format specifier does the trick
            s = String.format("%,d", ongkir);
        } catch (NumberFormatException e) {
        }

        txtTotal.setText(s);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(intent,1);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.setMessage("Sedang Menyimpan Data...");
                pd.setCancelable(false);
                pd.show();

                String jml_page = edtJmlPage.getText().toString();
                String jml_rangkap = edtJmlhRangkap.getText().toString();
                String ukuran = spinUkuran.getSelectedItem().toString();
                String path = edtUpload.getText().toString();
                String total = txtTotal.getText().toString().replace(",","");

                int selected = rgDelivery.getCheckedRadioButtonId();
                RadioButton rbDelivery = (RadioButton)findViewById(selected);

                String status_deliv = rbDelivery.getText().toString();
                String status="";
                if(status_deliv.equalsIgnoreCase("YA (Biaya Ongkir Rp 15,000)")){
                    status="TRUE";
                }else {
                    status="FALSE";
                }

                uploadPDFfile(path,jml_page,jml_rangkap,ukuran,total,status);

//                Toast.makeText(PengajuanActivity.this, id_user, Toast.LENGTH_SHORT).show();
            }
        });

        edtJmlPage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(editable.length()>0){
                    edtJmlhRangkap.setEnabled(true);
                }else{
                    edtJmlhRangkap.setEnabled(false);
                }

                String jml_page = edtJmlPage.getText().toString();

                int num=0;
                if(jml_page.equalsIgnoreCase("")){
                    num=0;
                }else{
                    num = Integer.parseInt(edtJmlPage.getText().toString());
                }

                total = ((num * 1000) * i);
                int plus = total+ongkir;

                String s = null;
                try {
                    // The comma in the format specifier does the trick
                    s = String.format("%,d", plus);
                } catch (NumberFormatException e) {
                }

                txtTotal.setText(s);

            }
        });

        edtJmlhRangkap.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String jml_rangkap = edtJmlhRangkap.getText().toString();

//                int num=0;
//                if(jml_rangkap.equalsIgnoreCase("")){
//                    num=0;
//                }else{
//                    num = Integer.parseInt(edtJmlPage.getText().toString());
//                }

//                String edited = txtTotal.getText().toString().replace(",","");
//                num = Integer.parseInt(edited);
                int rangkap = Integer.parseInt(edtJmlhRangkap.getText().toString());

                int total_all = (rangkap*total)+ongkir;
                i = rangkap;

                String s = null;
                try {
                    // The comma in the format specifier does the trick
                    s = String.format("%,d", total_all);
                } catch (NumberFormatException e) {
                }

                txtTotal.setText(s);
            }
        });

        rgDelivery.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(rb_yes.isChecked() == true){
                    ongkir = 15000;
                    String edited = txtTotal.getText().toString().replace(",","");
                    int num = Integer.parseInt(edited) + ongkir;

                    String s = null;
                    try {
                        // The comma in the format specifier does the trick
                        s = String.format("%,d", num);
                    } catch (NumberFormatException e) {
                    }

                    txtTotal.setText(s);

                }else{
                    ongkir = 0;
                    String edited = txtTotal.getText().toString().replace(",","");
                    int num = Integer.parseInt(edited) - 15000;

                    String s = null;
                    try {
                        // The comma in the format specifier does the trick
                        s = String.format("%,d", num);
                    } catch (NumberFormatException e) {
                    }

                    txtTotal.setText(s);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // Get the Uri of the selected file
            Uri uri = data.getData();

            String path = getFilePathFromURI(PengajuanActivity.this,uri);
            Log.d("ioooo",path);

            setFilename(path);
//            uploadPDFfile(path);

        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    private void setFilename(String path) {
        edtUpload.setText(path);
    }

//    uploadPDFfile(path,jml_page,jml_rangkap,ukuran,total,status);
    private void uploadPDFfile(String path,String jml_page,String jml_rangkap,String ukuran,String _total,String status) {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("case", "input_data");
        map.put("url", "http://uprint.aplikasiulun.com/android/api.php");
        map.put("filename", path);
        map.put("jml_page",jml_page);
        map.put("jml_rangkap",jml_rangkap);
        map.put("ukuran",ukuran);
        map.put("total",_total);
        map.put("status",status);
        map.put("id_user",id_user);
        new MultiPartRequester(this, map, GALLERY, this);

    }

    @Override
    public void onTaskCompleted(String response, int serviceCode) {

        Log.d("res", response);
        switch (serviceCode) {

            case GALLERY:
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    jsonObject.toString().replace("\\\\","");
                    if (jsonObject.getString("status").equals("true")) {

                        arraylist = new ArrayList<HashMap<String, String>>();
                        JSONArray dataArray = jsonObject.getJSONArray("data");

                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject dataobj = dataArray.getJSONObject(i);
                            url = dataobj.optString("pathToFile");
                        }

                        pd.dismiss();
                        finish();
//                        edtUpload.setText(url);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
    }

    public static String getFilePathFromURI(Context context, Uri contentUri) {
        //copy file and send new file path
        String fileName = getFileName(contentUri);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }
        if (!TextUtils.isEmpty(fileName)) {
            File copyFile = new File(wallpaperDirectory + File.separator + fileName);
            // create folder if not exists

            copy(context, contentUri, copyFile);
            return copyFile.getAbsolutePath();
        }
        return null;
    }

    public static String getFileName(Uri uri) {
        if (uri == null) return null;
        String fileName = null;
        String path = uri.getPath();
        int cut = path.lastIndexOf('/');
        if (cut != -1) {
            fileName = path.substring(cut + 1);
        }
        return fileName;
    }

    public static void copy(Context context, Uri srcUri, File dstFile) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(srcUri);
            if (inputStream == null) return;
            OutputStream outputStream = new FileOutputStream(dstFile);
            copystream(inputStream, outputStream);
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int copystream(InputStream input, OutputStream output) throws Exception, IOException {
        byte[] buffer = new byte[BUFFER_SIZE];

        BufferedInputStream in = new BufferedInputStream(input, BUFFER_SIZE);
        BufferedOutputStream out = new BufferedOutputStream(output, BUFFER_SIZE);
        int count = 0, n = 0;
        try {
            while ((n = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
                out.write(buffer, 0, n);
                count += n;
            }
            out.flush();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                Log.e(e.getMessage(), String.valueOf(e));
            }
            try {
                in.close();
            } catch (IOException e) {
                Log.e(e.getMessage(), String.valueOf(e));
            }
        }
        return count;
    }


    private void  requestMultiplePermissions(){
        Dexter.withActivity(this)
                .withPermissions(

                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }
}
