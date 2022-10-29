package com.example.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.Fragments.Status;
import com.example.Fragments.calls;
import com.example.Fragments.chats;

public class fregmentadapter extends FragmentPagerAdapter {
    public fregmentadapter(@NonNull FragmentManager fm) {
        super(fm);
    }



    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:return  new chats();
            case 1:return  new Status();
            case 2:return  new calls();
            default:return new chats();

        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title=null;
        if(position==0){
            title="CHATS";

        }
        if(position==1){
            title="STATUS";

        }if(position==2){
            title="CALL";

        }
        return title;
    }
}
