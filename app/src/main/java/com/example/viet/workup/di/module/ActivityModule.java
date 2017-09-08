package com.example.viet.workup.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.example.viet.workup.ui.board.BoardMvpPresenter;
import com.example.viet.workup.ui.board.BoardMvpView;
import com.example.viet.workup.ui.board.BoardPresenter;
import com.example.viet.workup.ui.board.adding.AddingMvpPresenter;
import com.example.viet.workup.ui.board.adding.AddingMvpView;
import com.example.viet.workup.ui.board.adding.AddingPresenter;
import com.example.viet.workup.ui.board.card.CardMvpPresenter;
import com.example.viet.workup.ui.board.card.CardMvpView;
import com.example.viet.workup.ui.board.card.CardPresenter;
import com.example.viet.workup.ui.introduced.IntroducedMvpPresenter;
import com.example.viet.workup.ui.introduced.IntroducedMvpView;
import com.example.viet.workup.ui.introduced.IntroducedPresenter;
import com.example.viet.workup.ui.introduced.content.ContentMvpPresenter;
import com.example.viet.workup.ui.introduced.content.ContentMvpView;
import com.example.viet.workup.ui.introduced.content.ContentPresenter;
import com.example.viet.workup.ui.login.LoginMvpPresenter;
import com.example.viet.workup.ui.login.LoginMvpView;
import com.example.viet.workup.ui.login.LoginPresenter;
import com.example.viet.workup.ui.main.MainMvpPresenter;
import com.example.viet.workup.ui.main.MainMvpView;
import com.example.viet.workup.ui.main.MainPresenter;
import com.example.viet.workup.ui.main.creating.BoardCreatingMvpPresenter;
import com.example.viet.workup.ui.main.creating.BoardCreatingMvpView;
import com.example.viet.workup.ui.main.creating.BoardCreatingPresenter;
import com.example.viet.workup.ui.main.menu.BoardOptionMvpPresenter;
import com.example.viet.workup.ui.main.menu.BoardOptionMvpView;
import com.example.viet.workup.ui.main.menu.BoardOptionPresenter;
import com.example.viet.workup.ui.profile.ProfileMvpPresenter;
import com.example.viet.workup.ui.profile.ProfileMvpView;
import com.example.viet.workup.ui.profile.ProfilePresenter;
import com.example.viet.workup.ui.register.RegisterMvpPresenter;
import com.example.viet.workup.ui.register.RegisterMvpView;
import com.example.viet.workup.ui.register.RegisterPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by viet on 04/09/2017.
 */
@Module
public class ActivityModule {
    @Provides
    Context provideContext(AppCompatActivity activity) {
        return activity;
    }

    @Provides
    IntroducedMvpPresenter<IntroducedMvpView> provideIntroducedPresenter(IntroducedPresenter<IntroducedMvpView> introducedPresenter) {
        return introducedPresenter;
    }

    @Provides
    BoardMvpPresenter<BoardMvpView> provideBoardPresenter(BoardPresenter<BoardMvpView> boardPresenter) {
        return boardPresenter;
    }

    @Provides
    MainMvpPresenter<MainMvpView> provideMainPresenter(MainPresenter<MainMvpView> mainPresenter) {
        return mainPresenter;
    }

    @Provides
    LoginMvpPresenter<LoginMvpView> provideLoginPresenter(LoginPresenter<LoginMvpView> loginPresenter) {
        return loginPresenter;
    }

    @Provides
    RegisterMvpPresenter<RegisterMvpView> provideRegisterPresenter(RegisterPresenter<RegisterMvpView> registerPresenter) {
        return registerPresenter;
    }

    @Provides
    ProfileMvpPresenter<ProfileMvpView> provideProfilePresenter(ProfilePresenter<ProfileMvpView> profilePresenter) {
        return profilePresenter;
    }

    @Provides
    CardMvpPresenter<CardMvpView> provideCardPresenter(CardPresenter<CardMvpView> cardPresenter) {
        return cardPresenter;
    }

    @Provides
    AddingMvpPresenter<AddingMvpView> provideAddingPresenter(AddingPresenter<AddingMvpView> addingPresenter) {
        return addingPresenter;
    }

    @Provides
    ContentMvpPresenter<ContentMvpView> provideContentPresenter(ContentPresenter<ContentMvpView> contentPresenter) {
        return contentPresenter;
    }

    @Provides
    BoardCreatingMvpPresenter<BoardCreatingMvpView> provideBoardCreatinPresenter(BoardCreatingPresenter<BoardCreatingMvpView> boardCreatingPresenter) {
        return boardCreatingPresenter;
    }

    @Provides
    BoardOptionMvpPresenter<BoardOptionMvpView> provideBoardOptionPresenter(BoardOptionPresenter<BoardOptionMvpView> boardOptionPresenter) {
        return boardOptionPresenter;
    }
}
