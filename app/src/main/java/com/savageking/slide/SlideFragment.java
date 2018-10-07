package com.savageking.slide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SlideFragment extends Fragment
{
    private final static String TAG = "SlideFragment";

    private View.OnClickListener toolBarClick;
    private View.OnClickListener floatingActionButtonClick;

    public static SlideFragment getInstance() {
        return new SlideFragment();
    }
    public static String getInstanceTag() {
        return TAG;
    }

    private Fragment top;
    private Fragment bottom;

    private SlideCallbackBottom bottomCallback;
    private SlideCallbackTop topCallback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolBarClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        };

        floatingActionButtonClick = new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final boolean visible = top.isVisible() && bottom.isVisible();

                if( visible )
                {
                    hideFragments();
                }

                else
                {
                    showFragments();
                }
            }
        };

        bottomCallback = new SlideCallbackBottom() {
            @Override
            public void toggleTop()
            {
                if( top.isVisible() )
                {
                    //hide top
                    hideFragment( top );
                    hideFab();
                }

                else
                {
                    //show top
                    showFragment( top );
                    showFab();
                }
            }
        };


        topCallback = new SlideCallbackTop() {
            @Override
            public void toggleBottom()
            {
                if( bottom.isVisible() )
                {
                    hideFragment( bottom );
                    hideFab();
                }

                else
                {
                    showFragment( bottom );
                    showFab();
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.slide_fragment, container, false);

        final Toolbar toolBar = view.findViewById(R.id.toolbar);
        toolBar.setTitle(R.string.toolbar_title);
        toolBar.setSubtitle(R.string.toolbar_subtitle);
        toolBar.setNavigationIcon(R.drawable.ic_clear_mtrl_alpha);
        toolBar.setNavigationOnClickListener(toolBarClick);

        final FloatingActionButton floatingActionButton = view.findViewById( R.id.floating_action_button );
        floatingActionButton.setOnClickListener( floatingActionButtonClick );

        //create fragments
        final FragmentManager manager = getChildFragmentManager();
        top =    manager.findFragmentById(R.id.top);
        bottom =      manager.findFragmentById(R.id.bottom);

        final SlideTop slideTop = ( SlideTop ) top;
        final SlideBottom slideBottom = (SlideBottom ) bottom;

        slideTop.setCallback( topCallback );
        slideBottom.setCallback( bottomCallback );

        return view;
    }


    @Override
    public void onDestroyView()
    {
        super.onDestroyView();

        //destroy fragments
        final FragmentManager manager = getChildFragmentManager();
        manager.beginTransaction().remove(top).commitAllowingStateLoss();
        manager.beginTransaction().remove(bottom).commitAllowingStateLoss();
    }

    private void hideFragments()
    {
        getChildFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.abc_slide_in_top, R.anim.abc_slide_out_top)
                .hide(top)
                .commitAllowingStateLoss();

        getChildFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom)
                .hide(bottom)
                .commitAllowingStateLoss();
    }

    private void showFragments()
    {
        getChildFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.abc_slide_in_top, R.anim.abc_slide_out_top)
                .show(top)
                .commit();

        getChildFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom)
                .show(bottom)
                .commit();
    }


    private void hideFragment( final Fragment fragment )
    {
        final boolean isTop = fragment instanceof SlideTop;
        final int inAnimation = isTop ? R.anim.abc_slide_in_top : R.anim.abc_slide_in_bottom;
        final int outAnimation = isTop ? R.anim.abc_slide_out_top : R.anim.abc_slide_out_bottom;

        getChildFragmentManager()
                .beginTransaction()
                .setCustomAnimations( inAnimation, outAnimation)
                .hide( fragment )
                .commitAllowingStateLoss();
    }

    private void showFragment( final Fragment fragment )
    {
        final boolean isTop = fragment instanceof SlideTop;
        final int inAnimation = isTop ? R.anim.abc_slide_in_top : R.anim.abc_slide_in_bottom;
        final int outAnimation = isTop ? R.anim.abc_slide_out_top : R.anim.abc_slide_out_bottom;

        getChildFragmentManager()
                .beginTransaction()
                .setCustomAnimations( inAnimation, outAnimation)
                .show(fragment)
                .commit();
    }

    private void hideFab( )
    {
        final View view = getView();
        final FloatingActionButton floatingActionButton = view.findViewById( R.id.floating_action_button );
        floatingActionButton.hide();
    }

    private void showFab()
    {
        final View view = getView();
        final FloatingActionButton floatingActionButton = view.findViewById( R.id.floating_action_button );
        floatingActionButton.show();
    }
}
