package com.nhom5.quanlylaptop.KH_Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom5.quanlylaptop.ActivityKH.Info_Laptop_Activity;
import com.nhom5.quanlylaptop.DAO.GioHangDAO;
import com.nhom5.quanlylaptop.Entity.GioHang;
import com.nhom5.quanlylaptop.Entity.Laptop;
import com.nhom5.quanlylaptop.R;
import com.nhom5.quanlylaptop.Support.ChangeType;

import java.util.ArrayList;

public class KH_GioHang_Adapter extends RecyclerView.Adapter<KH_GioHang_Adapter.AuthorViewHolder> {

    Context context;
    ArrayList<Laptop> listLap;
    ArrayList<GioHang> listGio;
    GioHangDAO gioHangDAO;
    String TAG = "KH_GioHang_Adapter_____";

    public KH_GioHang_Adapter(ArrayList<Laptop> listLap, ArrayList<GioHang> listGio, Context context) {
        this.listLap = listLap;
        this.listGio = listGio;
        this.context = context;
        gioHangDAO = new GioHangDAO(context);
    }

    @NonNull
    @Override
    public KH_GioHang_Adapter.AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup vGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.cardview_gio_hang, vGroup, false);
        return new KH_GioHang_Adapter.AuthorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull KH_GioHang_Adapter.AuthorViewHolder author, @SuppressLint("RecyclerView") final int pos) {
        Laptop laptop = setRow(pos, author);

        author.giam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GioHang old = listGio.get(pos);
                int soLuong = old.getSoLuong();
                if (soLuong > 1) {
                    soLuong--;
                    GioHang gioHang = new GioHang(old.getMaGio(), old.getMaLaptop(), old.getMaKH(), old.getNgayThem(), soLuong);
                    gioHangDAO.updateGioHang(gioHang);
                    listGio = gioHangDAO.selectGioHang(null, null, null, null);
                    setRow(pos, author);
                } else {
                    Toast.makeText(context, "!!!Tối thiểu một sản phẩm trong giỏ hàng!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        author.tang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GioHang old = listGio.get(pos);
                int soLuong = old.getSoLuong();
                soLuong++;
                GioHang gioHang = new GioHang(old.getMaGio(), old.getMaLaptop(), old.getMaKH(), old.getNgayThem(), soLuong);
                gioHangDAO.updateGioHang(gioHang);
                listGio = gioHangDAO.selectGioHang(null, null, null, null);
                setRow(pos, author);
            }
        });

        author.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GioHang gio = listGio.get(pos);
                gioHangDAO.deleteGioHang(gio);
            }
        });

        author.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Info_Laptop_Activity.class);
                if (laptop != null) {
                    final Bundle bundle = new Bundle();
                    bundle.putBinder("laptop", laptop);
                    Log.d(TAG, "onBindViewHolder: Laptop: " + laptop.toString());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Load thông tin sản phẩm lỗi!\nXin vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listGio.size();
    }

    public static class AuthorViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLaptop, tang, giam;
        TextView name, gia, soLuong;
        ImageButton delete;

        public AuthorViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLaptop = itemView.findViewById(R.id.imageView_Laptop);
            name = itemView.findViewById(R.id.textView_TenLaptop);
            gia = itemView.findViewById(R.id.textView_GiaTien);
            soLuong = itemView.findViewById(R.id.textView_Soluong);
            delete = itemView.findViewById(R.id.imageView_Delete);
            tang = itemView.findViewById(R.id.imageButton_Tang);
            giam = itemView.findViewById(R.id.imageButton_Giam);
        }
    }

    public Laptop setRow(int pos, @NonNull KH_GioHang_Adapter.AuthorViewHolder author) {
        Log.d(TAG, "setRow: " + pos);
        GioHang gioHang = listGio.get(pos);
        Laptop laptop = new Laptop("null", "null", "null", "null", "0", new byte[]{});
        Log.d(TAG, "setRow: GioHang: " + gioHang.toString());

        for (int i = 0; i < listLap.size(); i++) {
            Laptop getLap = listLap.get(i);
            if (gioHang.getMaLaptop().equals(getLap.getMaLaptop())) {
                laptop = getLap;
            }
        }

        ChangeType changeType = new ChangeType();
        Bitmap anhLap = changeType.byteToBitmap(laptop.getAnhLaptop());

        author.imgLaptop.setImageBitmap(anhLap);
        author.name.setText(laptop.getTenLaptop());
        author.gia.setText(laptop.getGiaTien() + " VNĐ");
        author.soLuong.setText(String.valueOf(gioHang.getSoLuong()));
        return laptop;
    }
}