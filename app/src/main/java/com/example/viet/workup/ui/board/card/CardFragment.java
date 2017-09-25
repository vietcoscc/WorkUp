package com.example.viet.workup.ui.board.card;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viet.workup.R;
import com.example.viet.workup.base.BaseFragment;
import com.example.viet.workup.event.Listener;
import com.example.viet.workup.model.Card;
import com.example.viet.workup.ui.board.card.menu.CardListOptionMenu;
import com.example.viet.workup.ui.work.WorkActivity;
import com.example.viet.workup.utils.DataUtils;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.viet.workup.utils.FireBaseDatabaseUtils.BOARD;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.CARD_KEY;
import static com.example.viet.workup.utils.FireBaseDatabaseUtils.CARD_LIST;


public class CardFragment extends BaseFragment implements CardMvpView, View.OnClickListener {
    public static final String TAG = "CardFragment";
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.recyclerViewCardList)
    RecyclerView recyclerViewCardList;
    @BindView(R.id.btnAddCard)
    ImageButton btnAddCard;
    @BindView(R.id.edtEdit)
    EditText edtEdit;
    @BindView(R.id.ivCheck)
    ImageView ivCheck;
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.toString().isEmpty()) {
                ivCheck.setVisibility(View.GONE);
            } else {
                ivCheck.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    private String mCardListKey;
    private String mBoardKey;
    private CardRecyclerViewAdapter cardRecyclerViewAdapter;
    private ArrayList<Card> arrCard = new ArrayList<>();
    private ArrayList<String> arrCardKey = new ArrayList<>();

    @Inject
    CardPresenter<CardMvpView> mPresenter;

    public CardFragment() {
        // Required empty public constructor
    }


    public static CardFragment newInstance(String boardKey, String cardListKey) {
        CardFragment fragment = new CardFragment();
        Bundle args = new Bundle();
        args.putString(BOARD, boardKey);
        args.putString(CARD_LIST, cardListKey);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBoardKey = getArguments().getString(BOARD);
            mCardListKey = getArguments().getString(CARD_LIST);
        }
        getmActivityComponent().inject(this);

    }

    private void initViews() {
        tvTitle.setOnClickListener(this);
        ivCheck.setOnClickListener(this);
        edtEdit.addTextChangedListener(textWatcher);
        edtEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                InputMethodManager im = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                im.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        btnAddCard.setOnClickListener(this);
        cardRecyclerViewAdapter = new CardRecyclerViewAdapter(arrCard, arrCardKey);
        cardRecyclerViewAdapter.setOnItemClickListenter(new Listener.OnItemClickListenter() {
            @Override
            public void onClick(View view, int position) {
                mPresenter.onCardClick(mBoardKey, mCardListKey, arrCard.get(position).getKey());
            }
        });
        recyclerViewCardList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewCardList.setAdapter(cardRecyclerViewAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_card, container, false);
        ButterKnife.bind(this, view);
        initViews();
        mPresenter.onAttach(this);
        arrCard.clear();
        mPresenter.onReceiveTitle(mBoardKey, mCardListKey);
        mPresenter.onReceiveData(mBoardKey, mCardListKey);
        return view;
    }


//    @Override
//    public void onDestroy() {
//        mPresenter.onDetach();
//        super.onDestroy();
//    }

    @Override
    public void showListTitle(String listTitle) {
        if (listTitle != null && !listTitle.isEmpty()) {
            tvTitle.setText(listTitle);
        }
        if (listTitle == null) {
            return;
        }

    }

    @Override
    public void showCard(Card card) {
        cardRecyclerViewAdapter.addItem(card);
    }

    @Override
    public void removeCard(Card card) {
        cardRecyclerViewAdapter.removeItem(arrCardKey.indexOf(card.getKey()));
    }

    @Override
    public void changeCard(Card card) {
        cardRecyclerViewAdapter.changeItem(card, arrCardKey.indexOf(card.getKey()));
    }

    @Override
    public void showCreateError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCardDetail(String cardKey) {
        Intent intent = new Intent(getContext(), WorkActivity.class);
        intent.putExtra(CARD_KEY, cardKey);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnAddCard) {
            CardListOptionMenu cardListOptionMenu = new CardListOptionMenu(getContext(), view, mBoardKey, mCardListKey,tvTitle.getText().toString());
            cardListOptionMenu.show();
        } else if (view.getId() == R.id.tvTitle) {
            edtEdit.setText(tvTitle.getText());
            edtEdit.setVisibility(View.VISIBLE);
            tvTitle.setVisibility(View.GONE);
            ivCheck.setVisibility(View.VISIBLE);
            edtEdit.requestFocus();
        } else if (view.getId() == R.id.ivCheck) {

            if (DataUtils.isCardListNameValid(edtEdit.getText().toString())) {
                mPresenter.onChangeTitle(mBoardKey, mCardListKey, edtEdit.getText().toString());
                edtEdit.setVisibility(View.GONE);
                tvTitle.setVisibility(View.VISIBLE);
                ivCheck.setVisibility(View.GONE);
                InputMethodManager im = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(view.getWindowToken(), 0);
            } else {
                Toast.makeText(getContext(), "Title invalid ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }
}
