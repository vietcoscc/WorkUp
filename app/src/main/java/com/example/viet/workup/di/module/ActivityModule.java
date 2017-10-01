package com.example.viet.workup.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.example.viet.workup.di.PerActivity;
import com.example.viet.workup.ui.board.BoardMvpPresenter;
import com.example.viet.workup.ui.board.BoardMvpView;
import com.example.viet.workup.ui.board.BoardPresenter;
import com.example.viet.workup.ui.board.option.add_member.MemberAddingMvpPresenter;
import com.example.viet.workup.ui.board.option.add_member.MemberAddingMvpView;
import com.example.viet.workup.ui.board.option.add_member.MemberAddingPresenter;
import com.example.viet.workup.ui.board.option.background.BackgroundMvpPresenter;
import com.example.viet.workup.ui.board.option.background.BackgroundMvpView;
import com.example.viet.workup.ui.board.option.background.BackgroundPresenter;
import com.example.viet.workup.ui.board.card.CardMvpPresenter;
import com.example.viet.workup.ui.board.card.CardMvpView;
import com.example.viet.workup.ui.board.card.CardPresenter;
import com.example.viet.workup.ui.board.card.menu.CardListMvpPresenter;
import com.example.viet.workup.ui.board.card.menu.CardListMvpView;
import com.example.viet.workup.ui.board.card.menu.CardListPresenter;
import com.example.viet.workup.ui.board.card.menu.create.CardCreatingMvpPresenter;
import com.example.viet.workup.ui.board.card.menu.create.CardCreatingMvpView;
import com.example.viet.workup.ui.board.card.menu.create.CardCreatingPresenter;
import com.example.viet.workup.ui.board.card.menu.delete.CardDeletingMvpPresenter;
import com.example.viet.workup.ui.board.card.menu.delete.CardDeletingMvpView;
import com.example.viet.workup.ui.board.card.menu.delete.CardDeletingPresenter;
import com.example.viet.workup.ui.board.card.move.CardMovingMvpPresenter;
import com.example.viet.workup.ui.board.card.move.CardMovingMvpView;
import com.example.viet.workup.ui.board.card.move.CardMovingPresenter;
import com.example.viet.workup.ui.board.option.list_card.ListAddingMvpPresenter;
import com.example.viet.workup.ui.board.option.list_card.ListAddingMvpView;
import com.example.viet.workup.ui.board.option.list_card.ListAddingPresenter;
import com.example.viet.workup.ui.board.option.member.CardMemberMvpPresenter;
import com.example.viet.workup.ui.board.option.member.CardMemberMvpView;
import com.example.viet.workup.ui.board.option.member.CardMemberPresenter;
import com.example.viet.workup.ui.image.item.ItemMvpPresenter;
import com.example.viet.workup.ui.image.item.ItemMvpView;
import com.example.viet.workup.ui.image.item.ItemPresenter;
import com.example.viet.workup.ui.image.item.menu.ItemImageOptionMvpPresenter;
import com.example.viet.workup.ui.image.item.menu.ItemImageOptionMvpView;
import com.example.viet.workup.ui.image.item.menu.ItemImageOptionPrsenter;
import com.example.viet.workup.ui.image.main.ImageMvpPresenter;
import com.example.viet.workup.ui.image.main.ImageMvpView;
import com.example.viet.workup.ui.image.main.ImagePresenter;
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
import com.example.viet.workup.ui.work.due_date.DueDateMvpPresenter;
import com.example.viet.workup.ui.work.due_date.DueDateMvpView;
import com.example.viet.workup.ui.work.due_date.DueDatePresenter;
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
    ListAddingMvpPresenter<ListAddingMvpView> provideAddingPresenter(ListAddingPresenter<ListAddingMvpView> addingPresenter) {
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

    @Provides
    DueDateMvpPresenter<DueDateMvpView> provideDueDatePresenter(DueDatePresenter<DueDateMvpView> duaDatePresenter) {
        return duaDatePresenter;
    }

    @Provides
    MemberAddingMvpPresenter<MemberAddingMvpView> provideMemberAddingPresenter(MemberAddingPresenter<MemberAddingMvpView> memberAddingPresenter) {
        return memberAddingPresenter;
    }

    @Provides
    CardMovingMvpPresenter<CardMovingMvpView> provideCardMoingPresenter(CardMovingPresenter<CardMovingMvpView> cardMovingPresenter) {
        return cardMovingPresenter;
    }

    @Provides
    CardDeletingMvpPresenter<CardDeletingMvpView> provideCardDeletingPresenter(CardDeletingPresenter<CardDeletingMvpView> cardDeletingPresenter) {
        return cardDeletingPresenter;
    }

    @Provides
    CardMemberMvpPresenter<CardMemberMvpView> provideCardMemberPresenter(CardMemberPresenter<CardMemberMvpView> cardMemberPresenter) {
        return cardMemberPresenter;
    }

    @Provides
    @PerActivity
    ImageMvpPresenter<ImageMvpView> provideImagePresenter(ImagePresenter<ImageMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ItemMvpPresenter<ItemMvpView> provideItemPresenter(ItemPresenter<ItemMvpView> presenter) {
        return presenter;
    }

    @Provides
    ItemImageOptionMvpPresenter<ItemImageOptionMvpView> provideItemImagePresenter(ItemImageOptionPrsenter<ItemImageOptionMvpView> itemOptionPrsenter) {
        return itemOptionPrsenter;
    }

    @Provides
    BackgroundMvpPresenter<BackgroundMvpView> provideBackgroundPresenter(BackgroundPresenter<BackgroundMvpView> backgroundPresenter) {
        return backgroundPresenter;
    }
}
