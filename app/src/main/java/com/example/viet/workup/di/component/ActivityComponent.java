package com.example.viet.workup.di.component;

import com.example.viet.workup.di.PerActivity;
import com.example.viet.workup.di.module.ActivityModule;
import com.example.viet.workup.ui.board.BoardActivity;
import com.example.viet.workup.ui.board.adding.AddingFragment;
import com.example.viet.workup.ui.board.card.CardFragment;
import com.example.viet.workup.ui.introduced.IntroducedActivity;
import com.example.viet.workup.ui.introduced.content.ContentFragment;
import com.example.viet.workup.ui.login.LoginActivity;
import com.example.viet.workup.ui.main.MainActivity;
import com.example.viet.workup.ui.main.creating.BoardCreatingDialog;
import com.example.viet.workup.ui.main.menu.BoardOptionMenu;
import com.example.viet.workup.ui.profile.ProfileActivity;
import com.example.viet.workup.ui.register.RegisterActivity;
import com.example.viet.workup.ui.work.WorkActivity;

import dagger.Component;

/**
 * Created by viet on 04/09/2017.
 */
@Component(modules = ActivityModule.class)
@PerActivity
public interface ActivityComponent {
    void inject(IntroducedActivity activity);

    void inject(BoardActivity activity);

    void inject(AddingFragment activity);

    void inject(CardFragment activity);

    void inject(ContentFragment activity);

    void inject(LoginActivity activity);

    void inject(MainActivity activity);

    void inject(ProfileActivity activity);

    void inject(RegisterActivity activity);

    void inject(WorkActivity activity);

    void inject(BoardCreatingDialog dialog);

    void inject(BoardOptionMenu menu);
}
