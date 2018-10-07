package com.savageking.slide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

public class SlideBottom extends Fragment{

    private View.OnClickListener toggleClick;
    private SlideCallbackBottom callback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        toggleClick = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                callback.toggleTop();
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.slide, container, false);

        final ToggleButton toggleButton = view.findViewById( R.id.toggle);
        toggleButton.setOnClickListener( toggleClick );

        return view;
    }

    protected void setCallback( final SlideCallbackBottom mCallback )
    {
        callback = mCallback;
    }
}
