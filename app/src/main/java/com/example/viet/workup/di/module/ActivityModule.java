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
import com.example.viet.workup.ui.board.card.create_dialog.CardCreatingMvpPresenter;
import com.example.viet.workup.ui.board.card.create_dialog.CardCreatingMvpView;
import com.example.viet.workup.ui.board.card.create_dialog.CardCreatingPresenter;
import com.example.viet.workup.ui.board.card.menu.CardListMvpPresenter;
import com.example.viet.workup.ui.board.card.menu.CardListMvpView;
import com.example.viet.workup.ui.board.card.menu.CardListPresenter;
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
import com.example.viet.workup.ui.main.board.BoardCreatingMvpPresenter;
import com.example.viet.workup.ui.main.board.BoardCreatingMvpView;
import com.example.viet.workup.ui.main.board.BoardCreatingPresenter;
import com.example.viet.workup.ui.main.menu.BoardOptionMvpPresenter;
import com.example.viet.workup.ui.main.menu.BoardOptionMvpView;
import com.example.viet.workup.ui.main.menu.BoardOptionPresenter;
import com.example.viet.workup.ui.profile.ProfileMvpPresenter;
import com.example.viet.workup.ui.profile.ProfileMvpView;
import com.example.viet.workup.ui.profile.ProfilePresenter;
import com.example.viet.workup.ui.register.RegisterMvpPresenter;
import com.example.viet.workup.ui.register.RegisterMvpView;
import com.example.viet.workup.ui.register.RegisterPresenter;
import com.example.viet.workup.ui.work.WorkMvpPresenter;
import com.example.viet.workup.ui.work.WorkMvpView;
import com.example.viet.workup.ui.work.WorkPresenter;
import com.example.viet.workup.ui.work.label.LabelMvpPresenter;
import com.example.viet.workup.ui.work.label.LabelMvpView;
import com.example.viet.workup.ui.work.label.LabelPresenter;
import com.example.viet.workup.ui.work.work_list.WorkListMvpPresenter;
import com.example.viet.workup.ui.work.work_list.WorkListMvpView;
import com.example.viet.workup.ui.work.work_list.WorkListPresenter;

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

    @Provides
    CardCreatingMvpPresenter<CardCreatingMvpView> provideCardCeatingPresenter(CardCreatingPresenter<CardCreatingMvpView> cardCreatingPresenter) {
        return cardCreatingPresenter;
    }

    @Provides
    CardListMvpPresenter<CardListMvpView> provideCardListPresenter(CardListPresenter<CardListMvpView> cardListPresenter) {
        return cardListPresenter;
    }

    @Provides
    WorkMvpPresenter<WorkMvpView> provideWorkPresenter(WorkPresenter<WorkMvpView> workPresenter) {
        return workPresenter;
    }

    @Provides
    LabelMvpPresenter<LabelMvpView> provideLabelPresenter(LabelPresenter<LabelMvpView> labelPresenter) {
        return labelPresenter;
    }

    @Provides
    WorkListMvpPresenter<WorkListMvpView> provideWorkListPresenter(WorkListPresenter<WorkListMvpView> workListPresenter) {
        return workListPresenter;
    }
}
