package com.nhom5.quanlylaptop.ActivityNV_Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.nhom5.quanlylaptop.Activity.PickRole_Activity;
import com.nhom5.quanlylaptop.DAO.DonHangDAO;
import com.nhom5.quanlylaptop.PagerAdapter.Admin_PagerAdapter_Bottom;
import com.nhom5.quanlylaptop.PagerAdapter.Admin_PagerAdapter_Drawer;
import com.nhom5.quanlylaptop.PagerAdapter.KH_PagerAdapter_Drawer;
import com.nhom5.quanlylaptop.R;

public class Main_Admin_Navi_Activity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ViewPager viewPager;
    NavigationView naviView;
    String TAG = "Main_Admin_Navi_Activity_____";
    DrawerLayout drawerLayout;
    int itemNaviDr, count;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin_navi);
        bottomNavigationView = findViewById(R.id.bottomNavi);
        viewPager = findViewById(R.id.viewP);
        naviView = findViewById(R.id.naviView);
        drawerLayout = findViewById(R.id.drawerLayout);
        setViewNaviBottom();
        setViewNaviDrawer();
        useToolbar("", 0);

        SharedPreferences pref = getSharedPreferences("Info_Click", MODE_PRIVATE);
        if (pref != null) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("what", "none");
            editor.commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences pref = getSharedPreferences("Info_Click", MODE_PRIVATE);
        if (pref != null) {
            String infoWhat = pref.getString("what", "none");
            if (infoWhat.equals("home")) {
                setOnResumeNavi(0);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("what", "none");
                editor.commit();
            }
            if (infoWhat.equals("noti")) {
                setOnResumeNavi(1);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("what", "none");
                editor.commit();
            }
        }
    }

    private void useToolbar(String title, int type) {
        setSupportActionBar(findViewById(R.id.toolbar_Account));
        if (type == 0) {
            layoutAccount(title);
        } else {
            layoutSearch(title);
        }
        ImageButton open = findViewById(R.id.imageButton_Open_Drawer);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }

    private void layoutAccount(String title) {
        LinearLayout layoutAcc = findViewById(R.id.layout_Account);
        LinearLayout layoutSearch = findViewById(R.id.layout_Search);
        TextView titleView = findViewById(R.id.textView_Title_Toolbar_Acc);
        ImageView imageView = findViewById(R.id.imageView_Avatar);

        layoutAcc.setVisibility(View.VISIBLE);
        layoutSearch.setVisibility(View.GONE);
        titleView.setText(title);
        imageView.setImageResource(R.drawable.admin_avatar);
    }

    private void layoutSearch(String title) {
        LinearLayout layoutAcc = findViewById(R.id.layout_Account);
        LinearLayout layoutSearch = findViewById(R.id.layout_Search);
        TextView titleView = findViewById(R.id.textView_Title_Toolbar_Search);

        layoutAcc.setVisibility(View.GONE);
        layoutSearch.setVisibility(View.VISIBLE);
        titleView.setText(title);
    }

    private void setViewNaviDrawer() {
        naviView.getMenu().getItem(0).setChecked(true);
        naviView.getMenu().getItem(0).setCheckable(true);
        naviView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.item_navi_drawer_admin_trangChu) {
                    item.setCheckable(true);
                    Log.d(TAG, "onNavigationItemSelected: 0 - home");
                    Admin_PagerAdapter_Drawer adapter = new Admin_PagerAdapter_Drawer(getSupportFragmentManager());
                    viewPager.setAdapter(adapter);
                    viewPager.setCurrentItem(0);
                    itemNaviDr = 0;
                    useToolbar("", 0);
                    bottomNavigationView.setVisibility(View.VISIBLE);
                    bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_admin_home).setCheckable(true);
                    bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_admin_home).setChecked(true);
                }
                if (id == R.id.item_navi_drawer_admin_noti) {
                    item.setCheckable(true);
                    Log.d(TAG, "onNavigationItemSelected: 1 - fptshop");
                    Admin_PagerAdapter_Drawer adapter = new Admin_PagerAdapter_Drawer(getSupportFragmentManager());
                    viewPager.setAdapter(adapter);
                    viewPager.setCurrentItem(1);
                    itemNaviDr = 1;
                    useToolbar("FPT Shop", 0);
                    bottomNavigationView.setVisibility(View.VISIBLE);
                    bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_admin_noti).setCheckable(true);
                    bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_admin_noti).setChecked(true);
                }
                if (id == R.id.item_navi_drawer_admin_Laptop) {
                    item.setCheckable(true);
                    Log.d(TAG, "onNavigationItemSelected: 2 - laptop");
                            Admin_PagerAdapter_Drawer adapter = new Admin_PagerAdapter_Drawer(getSupportFragmentManager());
                            viewPager.setAdapter(adapter);
                            viewPager.setCurrentItem(2);
                            itemNaviDr = 3;
                            useToolbar("QLý Laptop", 1);
                            bottomNavigationView.setVisibility(View.GONE);
                }
                if (id == R.id.item_navi_drawer_admin_DonHang) {
                    item.setCheckable(true);
                    Log.d(TAG, "onNavigationItemSelected: 3 - đơn hàng");
                            Admin_PagerAdapter_Drawer adapter = new Admin_PagerAdapter_Drawer(getSupportFragmentManager());
                            viewPager.setAdapter(adapter);
                            viewPager.setCurrentItem(3);
                            itemNaviDr = 4;
                            useToolbar("QLý Đơn Hàng", 0);
                            bottomNavigationView.setVisibility(View.GONE);
                }
                if (id == R.id.item_navi_drawer_admin_KhachHang) {
                    item.setCheckable(true);
                    Log.d(TAG, "onNavigationItemSelected: 4 - khách hàng");
                            Admin_PagerAdapter_Drawer adapter = new Admin_PagerAdapter_Drawer(getSupportFragmentManager());
                            viewPager.setAdapter(adapter);
                            viewPager.setCurrentItem(4);
                            itemNaviDr = 5;
                            useToolbar("QLý Khách Hàng", 1);
                            bottomNavigationView.setVisibility(View.GONE);
                }
                if (id == R.id.item_navi_drawer_admin_Voucher) {
                    item.setCheckable(true);
                    Log.d(TAG, "onNavigationItemSelected: 5 - voucher");
                            Admin_PagerAdapter_Drawer adapter = new Admin_PagerAdapter_Drawer(getSupportFragmentManager());
                            viewPager.setAdapter(adapter);
                            viewPager.setCurrentItem(5);
                            itemNaviDr = 6;
                            useToolbar("QLý Voucher", 0);
                            bottomNavigationView.setVisibility(View.GONE);
                }
                if (id == R.id.item_navi_drawer_admin_NhanVien) {
                    item.setCheckable(true);
                    Log.d(TAG, "onNavigationItemSelected: 6 - nhân viên");
                            Admin_PagerAdapter_Drawer adapter = new Admin_PagerAdapter_Drawer(getSupportFragmentManager());
                            viewPager.setAdapter(adapter);
                            viewPager.setCurrentItem(6);
                            itemNaviDr = 7;
                            useToolbar("QLý Nhân Viên", 1);
                            bottomNavigationView.setVisibility(View.GONE);
                }
                if (id == R.id.item_navi_drawer_admin_ThongKe) {
                    item.setCheckable(true);
                    Log.d(TAG, "onNavigationItemSelected: 7 - thống kê");
                            Admin_PagerAdapter_Drawer adapter = new Admin_PagerAdapter_Drawer(getSupportFragmentManager());
                            viewPager.setAdapter(adapter);
                            viewPager.setCurrentItem(7);
                            itemNaviDr = 8;
                            useToolbar("Doanh Thu\nThống Kê", 0);
                            bottomNavigationView.setVisibility(View.VISIBLE);
                            bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_admin_thongKe).setCheckable(true);
                            bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_admin_thongKe).setChecked(true);
                }
                if (id == R.id.item_navi_drawer_admin_Them_NV) {
                            Admin_PagerAdapter_Bottom adapter = new Admin_PagerAdapter_Bottom(getSupportFragmentManager());
                            viewPager.setAdapter(adapter);
                            viewPager.setCurrentItem(3);
                            Log.d(TAG, "onNavigationItemSelected: itemNavi: " + itemNaviDr);
                            naviView.getMenu().getItem(itemNaviDr).setChecked(false);
                            naviView.getMenu().getItem(itemNaviDr).setCheckable(false);
                            useToolbar("Thêm Nhân Viên", 0);
                            bottomNavigationView.setVisibility(View.VISIBLE);
                            bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_add_staff).setCheckable(true);
                            bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_add_staff).setChecked(true);
                }

                if (id == R.id.item_navi_drawer_admin_Logout) {
                    itemNaviDr = 9;
                    item.setCheckable(true);
                    Log.d(TAG, "onNavigationItemSelected: 8 - log out");
                    bottomNavigationView.setVisibility(View.VISIBLE);
                    finish();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        Log.d(TAG, "onNavigationItemSelected: itemNavi: " + itemNaviDr);
    }

    private void setViewNaviBottom() {
        Admin_PagerAdapter_Bottom adapter = new Admin_PagerAdapter_Bottom(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int i = item.getItemId();
                if (i == R.id.item_navi_bottom_admin_home) {
                            Admin_PagerAdapter_Bottom adapter = new Admin_PagerAdapter_Bottom(getSupportFragmentManager());
                            viewPager.setAdapter(adapter);
                            viewPager.setCurrentItem(0);
                            naviView.getMenu().getItem(0).setChecked(true);
                            naviView.getMenu().getItem(0).setCheckable(true);
                            itemNaviDr = 0;
                            useToolbar("", 0);
                }
                if (i == R.id.item_navi_bottom_admin_noti) {
                            Admin_PagerAdapter_Bottom adapter = new Admin_PagerAdapter_Bottom(getSupportFragmentManager());
                            viewPager.setAdapter(adapter);
                            viewPager.setCurrentItem(1);
                            naviView.getMenu().getItem(1).setChecked(true);
                            naviView.getMenu().getItem(1).setCheckable(true);
                            itemNaviDr = 1;
                            useToolbar("FPT Shop", 0);
                }
                if (i == R.id.item_navi_bottom_admin_thongKe) {
                            Admin_PagerAdapter_Bottom adapter = new Admin_PagerAdapter_Bottom(getSupportFragmentManager());
                            viewPager.setAdapter(adapter);
                            viewPager.setCurrentItem(2);
                            naviView.getMenu().getItem(8).setChecked(true);
                            naviView.getMenu().getItem(8).setCheckable(true);
                            itemNaviDr = 8;
                            useToolbar("Doanh Thu\nThống Kê", 0);
                }
                if (i == R.id.item_navi_bottom_add_staff) {
                            Admin_PagerAdapter_Bottom adapter = new Admin_PagerAdapter_Bottom(getSupportFragmentManager());
                            viewPager.setAdapter(adapter);
                            viewPager.setCurrentItem(3);
                            Log.d(TAG, "onNavigationItemSelected: itemNavi: " + itemNaviDr);
                            naviView.getMenu().getItem(itemNaviDr).setChecked(false);
                            naviView.getMenu().getItem(itemNaviDr).setCheckable(false);
                            useToolbar("Thêm Nhân Viên", 0);
                }
                return true;
            }
        });
    }

    private void setOnResumeNavi(int frag) {
        Admin_PagerAdapter_Drawer adapter = new Admin_PagerAdapter_Drawer(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(frag);
        itemNaviDr = frag;
        if (frag == 0) {
            useToolbar("", 0);
            bottomNavigationView.setVisibility(View.VISIBLE);
            bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_admin_home).setCheckable(true);
            bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_admin_home).setChecked(true);
            naviView.getMenu().getItem(0).setChecked(true);
            naviView.getMenu().getItem(0).setCheckable(true);
            getSupportActionBar().show();
        }
        if (frag == 1) {
            useToolbar("Thông Báo", 0);
            bottomNavigationView.setVisibility(View.VISIBLE);
            bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_admin_noti).setCheckable(true);
            bottomNavigationView.getMenu().findItem(R.id.item_navi_bottom_admin_noti).setChecked(true);
            naviView.getMenu().getItem(1).setChecked(true);
            naviView.getMenu().getItem(1).setCheckable(true);
            getSupportActionBar().show();
        }
    }
}