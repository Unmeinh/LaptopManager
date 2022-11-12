package com.nhom5.quanlylaptop.FragmentKH;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.nhom5.quanlylaptop.Activity.Account_Manager_Activity;
import com.nhom5.quanlylaptop.ActivityKH.KH_DonHang_Activity;
import com.nhom5.quanlylaptop.ActivityKH.KH_Voucher_Activity;
import com.nhom5.quanlylaptop.R;

public class KH_Account_Fragment extends Fragment {

    LinearLayout maVoucher, danhGiaSanPham, hdsd, nhomSanVou, gioHang, doiMatKhau, thietLapTaiKhoan;
    AppCompatButton logOut;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kh_account, container, false);
        maVoucher = view.findViewById(R.id.onclick_Ma_Voucher);
        danhGiaSanPham = view.findViewById(R.id.onclick_Danh_Gia_San_Pham);
        hdsd = view.findViewById(R.id.onclick_Huong_Dan_Su_Dung);
        nhomSanVou = view.findViewById(R.id.onclick_Nhom_San_Voucher);
        gioHang = view.findViewById(R.id.onclick_Gio_Hang);
        doiMatKhau = view.findViewById(R.id.onclick_Doi_Mat_Khau);
        thietLapTaiKhoan = view.findViewById(R.id.onclick_Thiet_Lap_Tai_Khoan);
        logOut = view.findViewById(R.id.button_LogOut);
        clickMaVoucher();
        clickDanhGiaSanPham();
        clickHuongDanSuDung();
        clickNhomSanVou();
        clickGioHang();
        clickDoiMatKhau();
        clickThietLapTaiKhoan();

        ImageView imageView = view.findViewById(R.id.imageView_Avatar);
        imageView.setImageResource(R.drawable.hugh_jackman);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    private void clickMaVoucher(){
        maVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), KH_Voucher_Activity.class));
            }
        });
    }

    private void clickDanhGiaSanPham(){
        danhGiaSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), KH_DonHang_Activity.class));
            }
        });
    }

    private void clickHuongDanSuDung(){
        hdsd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void clickNhomSanVou(){
        nhomSanVou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void clickGioHang(){
        gioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void clickDoiMatKhau(){
        View view = getLayoutInflater().inflate(R.layout.dialog_doi_pass, null);
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        dialog.setContentView(view);
        doiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        Button cancel = view.findViewById(R.id.button_Cancel_Dialog);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputLayout text1 = view.findViewById(R.id.textInput_Current_Password);
                TextInputLayout text2 = view.findViewById(R.id.textInput_New_Password);
                TextInputLayout text3 = view.findViewById(R.id.textInput_Confirm_Password);
                text1.getEditText().setText("");
                text2.getEditText().setText("");
                text3.getEditText().setText("");
                text1.getEditText().clearFocus();
                text2.getEditText().clearFocus();
                text3.getEditText().clearFocus();
                dialog.dismiss();
            }
        });
    }

    private void clickThietLapTaiKhoan(){
        thietLapTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Account_Manager_Activity.class);
                startActivity(intent);
            }
        });
    }

}