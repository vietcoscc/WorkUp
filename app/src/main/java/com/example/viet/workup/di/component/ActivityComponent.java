package com.example.viet.workup.di.component;

import com.example.viet.workup.di.PerActivity;
import com.example.viet.workup.di.module.ActivityModule;
import com.example.viet.workup.ui.board.BoardActivity;
import com.example.viet.workup.ui.board.card.move.CardMovingDialog;
import com.example.viet.workup.ui.board.option.add_member.MemberAddingDialogFragment;
import com.example.viet.workup.ui.board.option.background.BackgroundDialogFragment;
import com.example.viet.workup.ui.board.card.CardFragment;
import com.example.viet.workup.ui.board.card.menu.CardListOptionMenu;
import com.example.viet.workup.ui.board.card.menu.create.CardCreatingDialog;
import com.example.viet.workup.ui.board.card.menu.delete.CardDeletingDialog;
import com.example.viet.workup.ui.board.option.list_card.ListAddingDialogFragment;
import com.example.viet.workup.ui.board.option.member.CardMemberDialogFragment;
import com.example.viet.workup.ui.image.item.ItemActivity;
import com.example.viet.workup.ui.image.item.menu.ItemImageImageOptionMenu;
import com.example.viet.workup.ui.image.main.ImageActivity;
import com.example.viet.workup.ui.introduced.IntroducedActivity;
import com.example.viet.workup.ui.introduced.content.ContentFragment;
import com.example.viet.workup.ui.login.LoginActivity;
import com.example.viet.workup.ui.main.MainActivity;
import com.example.viet.workup.ui.main.board.BoardCreatingDialog;
import com.example.viet.workup.ui.main.menu.BoardOptionMenu;
import com.example.viet.workup.ui.profile.ProfileActivity;
import com.example.viet.workup.ui.register.RegisterActivity;
import com.example.viet.workup.ui.work.WorkActivity;
import com.example.viet.workup.ui.work.due_date.DueDateDialog;
import com.example.viet.workup.ui.work.label.LabelDialogFragment;
import com.example.viet.workup.ui.work.work_list.WorkListDialogFragment;

import dagger.Component;

/**
 * Created by viet on 04/09/2017.
 */
@Component(modules = ActivityModule.class, dependencies = NetComponent.class)
@PerActivity
public interface ActivityComponent {
    void inject(ImageActivity activity);

    void inject(ItemActivity activity);

    void inject(IntroducedActivity activity);

    void inject(BoardActivity activity);

    void inject(ListAddingDialogFragment activity);

    void inject(CardFragment activity);

    void inject(ContentFragment activity);

    void inject(LoginActivity activity);

    void inject(MainActivity activity);

    void inject(ProfileActivity activity);

    void inject(RegisterActivity activity);

    void inject(WorkActivity activity);

    void inject(BoardCreatingDialog dialog);

    void inject(BoardOptionMenu menu);

    void inject(CardCreatingDialog dialog);

    void inject(CardListOptionMenu menu);

    void inject(LabelDialogFragment dialog);

    void inject(WorkListDialogFragment dialog);

    void inject(DueDateDialog dialog);

    void inject(MemberAddingDialogFragment dialog);

    void inject(CardMovingDialog dialog);

    void inject(CardDeletingDialog dialog);

    void inject(CardMemberDialogFragment dialog);

    void inject(ItemImageImageOptionMenu menu);

    void inject(BackgroundDialogFragment dialog);
}
