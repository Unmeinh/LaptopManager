package com.nhom5.quanlylaptop.NAV_Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.nhom5.quanlylaptop.DAO.KhachHangDAO;
import com.nhom5.quanlylaptop.DAO.NhanVienDAO;
import com.nhom5.quanlylaptop.Entity.KhachHang;
import com.nhom5.quanlylaptop.Entity.NhanVien;
import com.nhom5.quanlylaptop.R;
import com.nhom5.quanlylaptop.Support.ChangeType;

import java.util.ArrayList;

public class QL_NhanVien_Adapter extends RecyclerView.Adapter<QL_NhanVien_Adapter.AuthorViewHolder> {

    Context context;
    int posNV;
    ArrayList<NhanVien> listNV;
    NhanVienDAO nhanVienDAO;
    String TAG = "QL_NhanVien_Adapter_____";
    TextView countNV;

    public QL_NhanVien_Adapter(ArrayList<NhanVien> listNV, Context context, TextView countNV) {
        this.listNV = listNV;
        this.context = context;
        nhanVienDAO = new NhanVienDAO(context);
        this.countNV = countNV;
    }

    @NonNull
    @Override
    public QL_NhanVien_Adapter.AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup vGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.cardview_nva_user, vGroup, false);
        return new QL_NhanVien_Adapter.AuthorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull QL_NhanVien_Adapter.AuthorViewHolder author, @SuppressLint("RecyclerView") final int pos) {
        NhanVien nhanVien = setRow(pos, author);

        author.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, nhanVien.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        author.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosNV(pos);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (countNV != null){
            if (listNV.size() > 0) {
                countNV.setText(String.valueOf(listNV.size()));
            } else {
                countNV.setText(String.valueOf(0));
            }
        }
        return listNV.size();
    }

    public class AuthorViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        ImageView avatar;
        TextView name, gender, phone;

        public AuthorViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.imageView_Avatar);
            name = itemView.findViewById(R.id.textView_TenUser);
            gender = itemView.findViewById(R.id.textView_GioiTinh);
            phone = itemView.findViewById(R.id.textView_SDT);
            itemView.setOnCreateContextMenuListener((View.OnCreateContextMenuListener) this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem edit = menu.add(Menu.NONE, R.id.item_CapNhat, Menu.NONE, "Cập nhật");
            MenuItem delete = menu.add(Menu.NONE, R.id.item_Xoa, Menu.NONE, "Xóa");
            edit.setOnMenuItemClickListener(onEditMenu);
            delete.setOnMenuItemClickListener(onEditMenu);
        }

        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @SuppressLint({"NonConstantResourceId", "NotifyDataSetChanged"})
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                NhanVien nhanVien = listNV.get(getPosNV());

                switch (item.getItemId()) {
                    case R.id.item_Xoa:
                        nhanVienDAO.deleteNhanVien(nhanVien);
                        listNV.clear();
                        listNV.addAll(nhanVienDAO.selectNhanVien(null, null, null, null));
                        notifyDataSetChanged();
                        break;
                    case R.id.item_CapNhat:
                        openDialogUpdate(nhanVien);
                        break;
                }

                return true;
            }
        };

    }

    public NhanVien setRow(int pos, @NonNull QL_NhanVien_Adapter.AuthorViewHolder author) {
        Log.d(TAG, "setRow: " + pos);
        NhanVien nhanVien = listNV.get(pos);
        Log.d(TAG, "setRow: NhanVien: " + nhanVien.toString());

        ChangeType changeType = new ChangeType();
        Bitmap avatar = changeType.byteToBitmap(nhanVien.getAvatar());

        author.avatar.setImageBitmap(avatar);
        author.name.setText(changeType.fullNameNhanVien(nhanVien));
        author.gender.setText(nhanVien.getGioiTinh());
        author.phone.setText(nhanVien.getPhone());
        return nhanVien;
    }

    @Override
    public void onViewRecycled(@NonNull QL_NhanVien_Adapter.AuthorViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }

    private void openDialogUpdate(NhanVien nv) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inft = ((Activity) context).getLayoutInflater();
        View view = inft.inflate(R.layout.dialog_add_edit_sth, null);

        TextView title = view.findViewById(R.id.textView_Title_Dialog);
        TextInputLayout textInput_LastName = view.findViewById(R.id.textInput_LastName);
        TextInputLayout textInput_FirstName = view.findViewById(R.id.textInput_FirstName);
        TextInputLayout textInput_Email = view.findViewById(R.id.textInput_Email);
        TextInputLayout textInput_SDT = view.findViewById(R.id.textInput_SDT);
        TextInputLayout textInput_Password = view.findViewById(R.id.textInput_Password);
        Button button_Dialog = view.findViewById(R.id.button_Dialog);
        ChangeType changeType = new ChangeType();

        title.setText("Cập nhật Nhân viên");
        textInput_LastName.getEditText().setText(nv.getHoNV());
        textInput_FirstName.getEditText().setText(nv.getTenNV());
        textInput_Email.getEditText().setText(nv.getEmail());
        textInput_SDT.getEditText().setText(nv.getPhone());
        textInput_Password.setVisibility(View.GONE);
        button_Dialog.setText("Cập nhật");

        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        button_Dialog.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                String lastName = changeType.deleteSpaceText(textInput_LastName.getEditText().getText().toString());
                String firstName = changeType.deleteSpaceText(textInput_FirstName.getEditText().getText().toString());
                String email = changeType.deleteSpaceText(textInput_Email.getEditText().getText().toString());
                String sdt = changeType.deleteSpaceText(textInput_SDT.getEditText().getText().toString());
                nv.setHoNV(lastName);
                nv.setTenNV(firstName);
                nv.setEmail(email);
                nv.setPhone(sdt);

                nhanVienDAO.updateNhanVien(nv);
                dialog.dismiss();
                listNV.clear();
                listNV.addAll(nhanVienDAO.selectNhanVien(null, null, null, null));
                notifyDataSetChanged();
            }
        });

    }

    public int getPosNV() {
        return posNV;
    }

    public void setPosNV(int posNV) {
        this.posNV = posNV;
    }
}
