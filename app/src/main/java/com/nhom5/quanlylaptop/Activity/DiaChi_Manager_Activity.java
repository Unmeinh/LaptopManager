package com.nhom5.quanlylaptop.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nhom5.quanlylaptop.DAO.DiaChiDAO;
import com.nhom5.quanlylaptop.DAO.KhachHangDAO;
import com.nhom5.quanlylaptop.Entity.DiaChi;
import com.nhom5.quanlylaptop.Entity.KhachHang;
import com.nhom5.quanlylaptop.R;

import java.util.ArrayList;

public class DiaChi_Manager_Activity extends AppCompatActivity {

    Context context = this;
    EditText editTextHoTen, editTextSDT, editTextDiaChi;
    Spinner spinnerThanhPho, spinnerQuanHuyen, spinnerXaPhuong;
    AppCompatButton buttonFinish;
    KhachHang khachHang;
    DiaChiDAO diaChiDAO;
    String maDC;
    int type = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_chi_manager);
        editTextHoTen = findViewById(R.id.editText_HoTen);
        editTextSDT = findViewById(R.id.editText_SDT);
        editTextDiaChi = findViewById(R.id.editText_DiaChi);

        spinnerThanhPho = findViewById(R.id.spinner_ThanhPho);
        spinnerQuanHuyen = findViewById(R.id.spinner_QuanHuyen);
        spinnerXaPhuong = findViewById(R.id.spinner_PhuongXa);
        buttonFinish = findViewById(R.id.button_Finish);

        diaChiDAO = new DiaChiDAO(context);
        getUser();
        useToolbar();
        getDataIntent();
        setSpinnerThanhPho();
        setSpinnerQuanHuyen();
        if (type != -1) {
            openInsertOrUpdateDiaChi(type);
        }
        if (type == 1){
            setInput();
        }
    }

    private void setSpinnerThanhPho() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, thanhPho);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerThanhPho.setAdapter(adapter);

        spinnerThanhPho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tp = spinnerThanhPho.getItemAtPosition(position).toString();
                setDataSpinnerQH(tp);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setDataSpinnerQH(String tp) {
        switch (tp) {
            case "C???n Th??": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, quanThuocCanTho);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerQuanHuyen.setAdapter(adapter);
                break;
            }
            case "???? N???ng": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, quanThuocDaNang);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerQuanHuyen.setAdapter(adapter);
                break;
            }
            case "H?? N???i": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, quanThuocHaNoi);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerQuanHuyen.setAdapter(adapter);
                break;
            }
            case "Thanh H??a": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, quanThuocThanhHoa);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerQuanHuyen.setAdapter(adapter);
                break;
            }
            case "Ninh B??nh": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, quanThuocNinhBinh);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerQuanHuyen.setAdapter(adapter);
                break;
            }
            case "Th??i B??nh": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, quanThuocThaiBinh);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerQuanHuyen.setAdapter(adapter);
                break;
            }
        }
    }

    private void setSpinnerQuanHuyen() {
        spinnerQuanHuyen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String qh = adapterView.getItemAtPosition(i).toString();
                setDataSpinnerXP(qh);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setDataSpinnerXP(String qh) {
        switch (qh) {
            case "Phong ??i???n": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocPhongDien);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "Ninh Ki???u": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocNinhKieu);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "B??nh Th???y": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocBinhThuy);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "?? M??n": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocOMon);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "Th???t N???t": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocThotNot);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "C??? ?????": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocCoDo);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            //???? n???ng
            case "H???i Ch??u": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocHaiChau);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "C???m L???": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocCamLe);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "Thanh Kh??": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocThanhKhe);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "Li??n Chi???u": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocLienChieu);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "Ng?? H??nh S??n": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocNguHanhSon);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            //h?? n???i
            case "B???c T??? Li??m": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocBacTuLiem);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "Nam T??? Li??m": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocNamTuLiem);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "?????ng ??a": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocDongDa);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "C???u Gi???y": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocCauGiay);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            //thanh h??a
            case "Nga S??n": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocNgaSon);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "N??ng C???ng": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocNongCong);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "H?? Trung": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocHaTrung);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "H???u L???c": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocHauLoc);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "Th??? Xu??n": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocThoXuan);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            //ninh b??nh
            case "Hoa L??": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocHoaLu);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "Kim S??n": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocKimSon);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "Quan Nho": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocQuanNho);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "Tam ??i???p": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocTamDiep);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            //th??i b??nh
            case "H??ng H??": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocHungHa);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "Th??i Th???y": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocThaiThuy);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "????ng H??ng": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocDongHung);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
            case "Ki???n X????ng": {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, xaThuocKienXuong);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerXaPhuong.setAdapter(adapter);
                break;
            }
        }
    }

    private void getUser() {
        SharedPreferences pref = getSharedPreferences("Who_Login", MODE_PRIVATE);
        if (pref == null) {
            khachHang = null;
        } else {
            String user = pref.getString("who", "");
            KhachHangDAO khachHangDAO = new KhachHangDAO(context);
            ArrayList<KhachHang> list = khachHangDAO.selectKhachHang(null, "maKH=?", new String[]{user}, null);
            if (list.size() > 0) {
                khachHang = list.get(0);
            }
        }
    }

    private void useToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_Normal));
        TextView titleToolbar = findViewById(R.id.textView_Title_Toolbar);
        titleToolbar.setText("?????a ch??? nh???n h??ng");
        ImageButton back = findViewById(R.id.imageButton_Back_Toolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void openInsertOrUpdateDiaChi(int type) {
        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoTen = editTextHoTen.getText().toString();
                String sdt = editTextSDT.getText().toString();
                String diaChiCT = editTextDiaChi.getText().toString();
                String thanhPho = (String) spinnerThanhPho.getSelectedItem();
                String quanHuyen = (String) spinnerQuanHuyen.getSelectedItem();
                String xaPhuong = (String) spinnerXaPhuong.getSelectedItem();

                if (khachHang != null) {
                    if (checkInput() == 1) {
                        if (type == 0) {
                            DiaChi diaChi = new DiaChi("DC", khachHang.getMaKH(), hoTen, sdt, diaChiCT, thanhPho, quanHuyen, xaPhuong);
                            diaChiDAO.insertDiaChi(diaChi);
                            finish();
                        } else if (type == 1) {
                            if (maDC != null) {
                                DiaChi diaChi = new DiaChi(maDC, khachHang.getMaKH(), hoTen, sdt, diaChiCT, thanhPho, quanHuyen, xaPhuong);
                                diaChiDAO.updateDiaChi(diaChi);
                                finish();
                            }
                        }
                    }
                }
            }
        });
    }

    private void setInput(){
        if (maDC != null){
            DiaChi getDC = null;
            ArrayList<DiaChi> list = diaChiDAO.selectDiaChi(null, "maDC=?", new String[]{maDC}, null);
            if (list.size() > 0){
                getDC = list.get(0);
            }
            if (getDC != null){
                editTextHoTen.setText(getDC.getTenNguoiNhan());
                editTextSDT.setText(getDC.getSDT());
                editTextDiaChi.setText(getDC.getDiaChi());
                int posTp = -1;
                for (int i = 0; i < spinnerThanhPho.getCount(); i++) {
                    if (spinnerThanhPho.getItemAtPosition(i).toString().equals(getDC.getThanhPho())){
                        posTp = i;
                    }
                }
                if (posTp != -1){
                    spinnerThanhPho.setSelection(posTp);
                    setDataSpinnerQH(getDC.getThanhPho());
                    int posQh = -1;
                    for (int i = 0; i < spinnerQuanHuyen.getCount(); i++) {
                        if (spinnerQuanHuyen.getItemAtPosition(i).toString().equals(getDC.getQuanHuyen())){
                            posQh = i;
                        }
                    }
                    setDataSpinnerXP(getDC.getQuanHuyen());
                    for (int i = 0; i < spinnerXaPhuong.getCount(); i++) {
                        if (spinnerXaPhuong.getItemAtPosition(i).toString().equals(getDC.getXaPhuong())){
                            if (posQh != -1){
                                int finalPosQh = posQh;
                                int finalPosXp = i;
                                new Handler().postDelayed(new Runnable() {
                                    public void run() {
                                        spinnerQuanHuyen.setSelection(finalPosQh);
                                        new Handler().postDelayed(new Runnable() {
                                            public void run() {
                                                spinnerXaPhuong.setSelection(finalPosXp,true);
                                            }
                                        }, 100);
                                    }
                                }, 100);
                            }
                        }
                    }
                }
            }
        }
    }

    private int checkInput() {
        int check = 1;
        String hoTen = editTextHoTen.getText().toString();
        String sdt = editTextSDT.getText().toString();
        String diaChiCT = editTextDiaChi.getText().toString();

        if (hoTen.isEmpty()) {
            Toast.makeText(context, "T??n ng?????i nh???n kh??ng b??? tr???ng!", Toast.LENGTH_SHORT).show();
            check = -1;
        }

        if (sdt.isEmpty()) {
            Toast.makeText(context, "S??? ??i???n tho???i kh??ng b??? tr???ng!", Toast.LENGTH_SHORT).show();
            check = -1;
        }

        if (diaChiCT.isEmpty()) {
            Toast.makeText(context, "?????a ch??? ng?????i nh???n kh??ng b??? tr???ng!", Toast.LENGTH_SHORT).show();
            check = -1;
        }

        if (spinnerThanhPho != null) {
            if (spinnerThanhPho.getSelectedItemPosition() == 0) {
                Toast.makeText(context, "T???nh/ Th??nh ph??? ph???i ???????c ch???n!", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }

        if (spinnerQuanHuyen != null) {
            if (spinnerQuanHuyen.getSelectedItemPosition() == 0) {
                Toast.makeText(context, "Qu???n/ Huy???n ph???i ???????c ch???n!", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }

        if (spinnerXaPhuong != null) {
            if (spinnerXaPhuong.getSelectedItemPosition() == 0) {
                Toast.makeText(context, "X??/ Ph?????ng ph???i ???????c ch???n!", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }

        return check;
    }

    private void getDataIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            maDC = intent.getStringExtra("maDC");
            type = intent.getIntExtra("typeDC", -1);
        }
    }

    String[] thanhPho = {"Ch???n T???nh/ Th??nh ph???", "H?? N???i", "Ninh B??nh", "Th??i B??nh", "???? N???ng", "Thanh H??a", "C???n Th??"};
    //qu???n / huy???n
    String[] quanThuocCanTho = {"Ch???n Qu???n/ Huy???n", "Phong ??i???n", "Ninh Ki???u", "B??nh Th???y", "?? M??n", "Th???t N???t", "C??? ?????"};
    String[] quanThuocDaNang = {"Ch???n Qu???n/ Huy???n", "H???i Ch??u", "C???m L???", "Thanh Kh??", "Li??n Chi???u", "Ng?? H??nh S??n"};
    String[] quanThuocHaNoi = {"Ch???n Qu???n/ Huy???n", "B???c T??? Li??m", "Nam T??? Li??m", "?????ng ??a", "C???u Gi???y"};
    String[] quanThuocThanhHoa = {"Ch???n Qu???n/ Huy???n", "Nga S??n", "N??ng C???ng", "H?? Trung", "H???u L???c", "Th??? Xu??n"};
    String[] quanThuocNinhBinh = {"Ch???n Qu???n/ Huy???n", "Hoa L??", "Kim S??n", "Quan Nho", "Tam ??i???p"};
    String[] quanThuocThaiBinh = {"Ch???n Qu???n/ Huy???n", "H??ng H??", "Th??i Th???y", "????ng H??ng", "Ki???n X????ng"};
    //Ph?????ng/ X??
    String[] xaThuocPhongDien = {"Ch???n Ph?????ng/ X??", "Phong B??nh", "Phong H???i", "??i???n H????ng", "??i???n H???i", "??i???n H??a", "??i???n M??n", "??i???n L???c"};
    String[] xaThuocNinhKieu = {"Ch???n Ph?????ng/ X??", "An H??a", "Th???i B??nh", "An Nghi???p", "An C??", "An H???i", "T??n An", "An Ph??", "Xu??n Kh??nh", "H??ng L???i"};
    String[] xaThuocBinhThuy = {"Ch???n Ph?????ng/ X??", "B??i H???u Ngh??a", "Long H??a", "Tr?? An", "Th???i An ????ng"};
    String[] xaThuocOMon = {"Ch???n Ph?????ng/ X??", "?????nh M??n", "Ph?????c Th???i", "T??n Th???i", "Th???i ????ng", "Tr?????ng L???c", "Tr?????ng Xu??n"};
    String[] xaThuocThotNot = {"Ch???n Ph?????ng/ X??", "Trung Ki??n", "Thu???n H??ng", "T??n H??ng", "T??n L???c"};
    String[] xaThuocCoDo = {"Ch???n Ph?????ng/ X??", "????ng Hi???p", "????ng Th???ng", "Trung H??ng", "Th???ch Ph??", "Trung An"};
    //
    String[] xaThuocHaiChau = {"Ch???n Ph?????ng/ X??", "H???i Ch??u 1", "H???i Ch??u 2", "Th???ch Thang", "Thanh B??nh", "Thu???n Ph?????c", "Nam D????ng", "Ph?????c Ninh", "B??nh Hi??n", "H??a C?????ng Nam", "H??a C?????ng B???c"};
    String[] xaThuocCamLe = {"Ch???n Ph?????ng/ X??", "Khu?? Trung", "H??a Th??? ????ng", "H??a Th??? T??y", "H??a An", "H??a Ph??t", "H??a Xu??n"};
    String[] xaThuocThanhKhe = {"Ch???n Ph?????ng/ X??", "V??nh Trung", "T??n Ch??nh", "Th???c Gi??n", "Ch??nh Gi??n", "Tam Thu???n", "Xu??n H??"};
    String[] xaThuocLienChieu = {"Ch???n Ph?????ng/ X??", "H??a Minh", "H??a Kh??nh Nam", "Ho?? Kh??nh B???c", "H??a Hi???p Nam", " Ho?? Hi???p B???c"};
    String[] xaThuocNguHanhSon = {"Ch???n Ph?????ng/ X??", "H??a H???i", "H??a Qu??", "Khu?? M???", "M??? An"};
    //
    String[] xaThuocBacTuLiem = {"Ch???n Ph?????ng/ X??", "?????c Th???ng", "????ng Ng???c", "Th???y Ph????ng", "Li??n M???c", "Th?????ng C??t", "T??y T???u", "Minh Khai", "Ph?? Di???n", "Ph??c Di???n", "Xu??n ?????nh"};
    String[] xaThuocNamTuLiem = {"Ch???n Ph?????ng/ X??", "Xu??n Ph????ng", "Ph????ng Canh", "Ng???c M???ch", "M??? Tr??", "T??y M???"};
    String[] xaThuocDongDa = {"Ch???n Ph?????ng/ X??", "C??t Linh", "H??ng B???t", "L??ng H???", "L??ng Th?????ng", "Kim Li??n", "Kh????ng Th?????ng", "Nam ?????ng", "Ng?? T?? S???"};
    String[] xaThuocCauGiay = {"Ch???n Ph?????ng/ X??", "Ngh??a ????", "Ngh??a T??n", "Mai D???ch", "Y??n H??a", "Trung H??a"};
    //
    String[] xaThuocNgaSon = {"Ch???n Ph?????ng/ X??", "Nga An", "Nga B???ch", "Nga ??i???n", "Nga Gi??p", "Nga H???i", "Nga H??ng", "Nga Li??n", "Nga L??nh", "Nga M???", "Nga Nh??n", "Nga Ph??", "Nga Th???ch", "Nga Th??i", "Nga Th???ng", "Nga Thanh", "Nga Th??nh", "Nga Thi???n", "Nga Th???y", "Nga Trung", "Nga Tr?????ng", "Nga V??n", "Nga V???nh", "Nga Y??n"};
    String[] xaThuocNongCong = {"Ch???n Ph?????ng/ X??", "C??ng B??nh", "C??ng Ch??nh", "Ho??ng S??n", "Minh Kh??i", "Minh Ngh??a", "Minh Th???", "T??n Khang", "T??n Ph??c"};
    String[] xaThuocHaTrung = {"Ch???n Ph?????ng/ X??", "H?? B???c", "H?? B??nh", "H?? Ch??u", "H?? Giang", "H?? H???i", "H?? Lai", "Y???n S??n", "L??nh To???i", "Y??n D????ng"};
    String[] xaThuocHauLoc = {"Ch???n Ph?????ng/ X??", "C???u L???c", "Ch??u L???c", "??a L???c", "?????i L???c", "?????ng L???c", "H???i L???c", "Hoa L???c", "H??a L???c", "H??ng L???c", "Li??n L???c", "L???c S??n", "L???c T??n"};
    String[] xaThuocThoXuan = {"Ch???n Ph?????ng/ X??", "Th??? B??nh", "Th??? C?????ng", "Th??? D??n", "Th??? Di??n", "Th??? H???i", "Th??? L??m", "Th??? L???p", "Th??? L???c", "Th??? Minh", "Th??? Ng???c"};
    //
    String[] xaThuocHoaLu = {"Ch???n Ph?????ng/ X??", "Ninh Giang", "Ninh Khang", "Ninh M???", "Ninh V??n", "Ninh Th???ng", "Ninh H???i", "Ninh Xu??n"};
    String[] xaThuocKimSon = {"Ch???n Ph?????ng/ X??", "Ph??t Di???m", "Ch???t B??nh", "Y??n M???t", "Kim ????ng", "Lai Th??nh", "Y??n L???c", "T??n Th??nh", "L??u Ph????ng"};
    String[] xaThuocQuanNho = {"Ch???n Ph?????ng/ X??", "C??c Ph????ng", "?????ng Phong", "?????c Long", "Gia L??m", "Gia S??n", "Gia Th???y", "Gia T?????ng", "K??? Ph??", "L???c V??n", "L???ng Phong"};
    String[] xaThuocTamDiep = {"Ch???n Ph?????ng/ X??", "Quang S??n", "Y??n B??nh", "Y??n S??n", "????ng S??n", "T??y S??n", "T??n B??nh"};
    //
    String[] xaThuocHungHa = {"Ch???n Ph?????ng/ X??", "Canh T??n", "Ch?? H??a", "Chi L??ng", "C???ng H??a", "D??n Ch???", "??i???p N??ng", "??oan H??ng", "?????c L???p"};
    String[] xaThuocThaiThuy = {"Ch???n Ph?????ng/ X??", "Th??i D????ng", "Th??i Giang", "Th??i H??", "Th??i Ho??", "Th??i H???c", "Th??i H???ng", "Th??i H??ng", "Th??i Nguy??n", "Th??i Ph??c", "Th??i S??n"};
    String[] xaThuocDongHung = {"Ch???n Ph?????ng/ X??", "????ng D????ng", "????ng Ho??ng", "????ng ??", "????ng Phong", "????ng Huy", "????ng L??nh", "????ng Kinh", "????ng T??n"};
    String[] xaThuocKienXuong = {"Ch???n Ph?????ng/ X??", "Quang Minh", "B??nh Minh", "Th?????ng Hi???n", " Quang B??nh", " Quang L???ch", "V?? Trung", "V?? Qu??"};

}