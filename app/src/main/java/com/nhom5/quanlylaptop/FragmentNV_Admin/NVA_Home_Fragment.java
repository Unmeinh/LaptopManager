package com.nhom5.quanlylaptop.FragmentNV_Admin;

import static android.content.Context.MODE_PRIVATE;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nhom5.quanlylaptop.Activity.Info_Laptop_Activity;
import com.nhom5.quanlylaptop.ActivityNV_Admin.NVA_DonDat_Activity;
import com.nhom5.quanlylaptop.DAO.DonHangDAO;
import com.nhom5.quanlylaptop.DAO.LaptopDAO;
import com.nhom5.quanlylaptop.DAO.NhanVienDAO;
import com.nhom5.quanlylaptop.Entity.DonHang;
import com.nhom5.quanlylaptop.Entity.Laptop;
import com.nhom5.quanlylaptop.Entity.NhanVien;
import com.nhom5.quanlylaptop.R;
import com.nhom5.quanlylaptop.Support.GetData;
import com.nhom5.quanlylaptop.Support.ChangeType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NVA_Home_Fragment extends Fragment {

    LinearLayout changeTime;
    TextView textView_Date, textView_GiaTien;
    String getDate;
    GetData getData;
    ChangeType changeType = new ChangeType();
    ArrayList<Laptop> listLap = new ArrayList<>();
    ArrayList<DonHang> listDon = new ArrayList<>();
    LaptopDAO laptopDAO;
    DonHangDAO donHangDAO;
    String getMonth;
    NhanVien nhanVien;
    View view;
    String TAG = "NVA_Home_Fragment_____";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_nva_home, container, false);
        changeTime = view.findViewById(R.id.change_Time);
        textView_Date = view.findViewById(R.id.textView_Date);
        textView_GiaTien = view.findViewById(R.id.textView_GiaTien);
        TextView textView = view.findViewById(R.id.textView_TenUser);
        getData = new GetData(getContext());
        laptopDAO = new LaptopDAO(getContext());
        donHangDAO = new DonHangDAO(getContext());
        listLap = laptopDAO.selectLaptop(null, null, null, null);

        getUser();
        setDoanhThuCreate();
        onclickChangeTime();
        sortLaptop(view);
        setSLDonHang(view);
        if (nhanVien == null){
            textView.setText("Xin ch??o, Admin");
        } else {
            textView.setText("Xin ch??o, " + changeType.fullNameNhanVien(nhanVien));
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getUser();
        setDoanhThuCreate();
        onclickChangeTime();
        sortLaptop(view);
        setSLDonHang(view);
    }

    private void getUser() {
        SharedPreferences pref = getContext().getSharedPreferences("Who_Login", MODE_PRIVATE);
        if (pref == null) {
            nhanVien = null;
        } else {
            String user = pref.getString("who", "");
            NhanVienDAO nhanVienDAO = new NhanVienDAO(getContext());
            ArrayList<NhanVien> list = nhanVienDAO.selectNhanVien(null, "maNV=?", new String[]{user}, null);
            if (list.size() > 0) {
                nhanVien = list.get(0);
            }
        }
    }

    private void setDoanhThuCreate() {
        Date currentTime = Calendar.getInstance().getTime();
        int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(currentTime));
        int month = Integer.parseInt(new SimpleDateFormat("MM").format(currentTime));
        if (month < 10) {
            getMonth = "0" + (month);
        } else {
            getMonth = String.valueOf(month);
        }
        textView_Date.setText("Th??ng " + getMonth + "/" + year);
        setDoanhThu(changeType.intDateToStringDate(month, year));
    }

    private void setSLDonHang(View view) {
        TextView slDHText = view.findViewById(R.id.textView_Soluong_DH);
        TextView slDHNum = view.findViewById(R.id.textView_Soluong);
        ImageView goToDonDat = view.findViewById(R.id.imageView_GoTo_DonDat);
        slDHText.setText("0 ????n ?????t");
        slDHNum.setText(String.valueOf(0));
        if (nhanVien == null) {
            listDon = donHangDAO.selectDonHang(null, "trangThai=? and maNV=?", new String[]{"Ch??? x??c nh???n", "No Data"}, null);
            slDHText.setText(listDon.size() + " ????n ?????t");
            slDHNum.setText(String.valueOf(listDon.size()));
        } else {
            if (nhanVien.getRoleNV().equals("B??n h??ng Online")) {
                listDon = donHangDAO.selectDonHang(null, "trangThai=? and maNV=?", new String[]{"Ch??? x??c nh???n", "No Data"}, null);
                slDHText.setText(listDon.size() + " ????n ?????t");
                slDHNum.setText(String.valueOf(listDon.size()));
            }
        }

        goToDonDat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nhanVien != null) {
                    if (!nhanVien.getRoleNV().equals("B??n h??ng Ofline")) {
                        Intent intent = new Intent(getContext(), NVA_DonDat_Activity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), "B???n kh??ng n???m trong b??? ph???n b??n h??ng Online!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Intent intent = new Intent(getContext(), NVA_DonDat_Activity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void setDoanhThu(String[] time) {
        if (time != null) {
            listDon = donHangDAO.selectDonHang(null, "ngayMua>=? and ngayMua<? and trangThai=?", new String[]{time[0], time[1], "Ho??n th??nh"}, null);
            if (listDon != null) {
                Log.d(TAG, "setDoanhThu: hope");
                if (listDon.size() > 0) {
                    int doanhThu = getData.tinhTongKhoanThu(listDon) * 1000;
                    textView_GiaTien.setText(changeType.stringToStringMoney(doanhThu + ""));
                } else {
                    textView_GiaTien.setText(changeType.stringToStringMoney("0"));
                }
            } else {
                textView_GiaTien.setText(changeType.stringToStringMoney("0"));
            }
        }
    }

    private void sortLaptop(View view) {
        TextView titleSort, tenLaptop, giaTien;
        AppCompatButton theoDoanhThu, theoSLAsc, theoSLDesc;
        ImageView imageView;
        LinearLayout linearLayout = view.findViewById(R.id.onlick_Laptop);
        theoDoanhThu = view.findViewById(R.id.button_Theo_DoanhThu);
        theoSLAsc = view.findViewById(R.id.button_Theo_ThapTien);
        theoSLDesc = view.findViewById(R.id.button_Theo_CaoTien);
        titleSort = view.findViewById(R.id.textView_Title);
        tenLaptop = view.findViewById(R.id.textView_TenLaptop);
        giaTien = view.findViewById(R.id.textView_GiaTien_Laptop);
        imageView = view.findViewById(R.id.imageView_Laptop);
        titleSort.setText("S???n ph???m b??n ch???y nh???t theo doanh thu");
        Laptop laptop = getData.getTop1DoanhThu(listLap);
        if (laptop != null) {
            tenLaptop.setText(laptop.getTenLaptop());
            giaTien.setText("Gi?? ti???n: " + laptop.getGiaTien());
            imageView.setImageBitmap(changeType.byteToBitmap(laptop.getAnhLaptop()));
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), Info_Laptop_Activity.class);
                    final Bundle bundle = new Bundle();
                    bundle.putBinder("laptop", laptop);
                    Log.d(TAG, "onBindViewHolder: Laptop: " + laptop.toString());
                    intent.putExtras(bundle);
                    intent.putExtra("openFrom", "other");
                    getContext().startActivity(intent);
                }
            });
        }

        theoDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleSort.setText("S???n ph???m b??n ch???y nh???t theo doanh thu");
                Laptop laptop = getData.getTop1DoanhThu(listLap);
                if (laptop != null) {
                    tenLaptop.setText(laptop.getTenLaptop());
                    giaTien.setText("Gi?? ti???n: " + laptop.getGiaTien());
                    imageView.setImageBitmap(changeType.byteToBitmap(laptop.getAnhLaptop()));
                    linearLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), Info_Laptop_Activity.class);
                            final Bundle bundle = new Bundle();
                            bundle.putBinder("laptop", laptop);
                            Log.d(TAG, "onBindViewHolder: Laptop: " + laptop.toString());
                            intent.putExtras(bundle);
                            intent.putExtra("openFrom", "other");
                            getContext().startActivity(intent);
                        }
                    });
                }
            }
        });

        theoSLAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleSort.setText("S???n ph???m th???p ti???n b??n ch???y nh???t theo s??? l?????ng");
                Laptop laptop = getData.getTop1SoLuong(listLap, "asc");
                if (laptop != null) {
                    tenLaptop.setText(laptop.getTenLaptop());
                    giaTien.setText("Gi?? ti???n: " + laptop.getGiaTien());
                    imageView.setImageBitmap(changeType.byteToBitmap(laptop.getAnhLaptop()));
                    linearLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), Info_Laptop_Activity.class);
                            final Bundle bundle = new Bundle();
                            bundle.putBinder("laptop", laptop);
                            Log.d(TAG, "onBindViewHolder: Laptop: " + laptop.toString());
                            intent.putExtras(bundle);
                            intent.putExtra("openFrom", "other");
                            getContext().startActivity(intent);
                        }
                    });
                }
            }
        });

        theoSLDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleSort.setText("S???n ph???m cao ti???n b??n ch???y nh???t theo s??? l?????ng");
                Laptop laptop = getData.getTop1SoLuong(listLap, "desc");
                if (laptop != null) {
                    tenLaptop.setText(laptop.getTenLaptop());
                    giaTien.setText("Gi?? ti???n: " + laptop.getGiaTien());
                    imageView.setImageBitmap(changeType.byteToBitmap(laptop.getAnhLaptop()));
                    linearLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), Info_Laptop_Activity.class);
                            final Bundle bundle = new Bundle();
                            bundle.putBinder("laptop", laptop);
                            Log.d(TAG, "onBindViewHolder: Laptop: " + laptop.toString());
                            intent.putExtras(bundle);
                            intent.putExtra("openFrom", "other");
                            getContext().startActivity(intent);
                        }
                    });
                }
            }
        });
    }

    private String onclickChangeTime() {
        Calendar calendar = Calendar.getInstance();
        changeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month += 1;
                        if (month < 10) {
                            getMonth = "0" + month;
                        } else {
                            getMonth = String.valueOf(month);
                        }
                        textView_Date.setText("Th??ng " + getMonth + "/" + year);
                        setDoanhThu(changeType.intDateToStringDate(month, year));
                        Log.d(TAG, "onDateSet: hope " + year + "/" + month);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        return getDate;
    }
}