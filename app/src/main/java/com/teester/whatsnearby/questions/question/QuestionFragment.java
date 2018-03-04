package com.teester.whatsnearby.questions.question;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.teester.whatsnearby.BuildConfig;
import com.teester.whatsnearby.R;
import com.teester.whatsnearby.data.Answers;
import com.teester.whatsnearby.data.OsmObject;
import com.teester.whatsnearby.data.OsmObjectType;
import com.teester.whatsnearby.data.source.Preferences;
import com.teester.whatsnearby.questions.QuestionsActivity;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuestionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment implements View.OnClickListener, QuestionFragmentContract.View {

	protected static final String ARG_PARAM1 = "param1";
	private OnFragmentInteractionListener mListener;
	private QuestionFragmentContract.Presenter questionPresenter;

	private TextView questionTextView;
	private ImageView questionImageView;
	private TextView questionPreviousTextView;
	private Button answerYes;
	private Button answerNo;
	private Button answerUnsure;

	private int position;

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @return A new instance of fragment QuestionFragment.
	 */
	public static QuestionFragment newInstance(OsmObject poi, int position, OsmObjectType listOfQuestions) {
		QuestionFragment fragment = new QuestionFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_PARAM1, position);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments() != null) {
			this.position = getArguments().getInt(ARG_PARAM1);
		}

		Preferences preferences = new Preferences(getContext());
		questionPresenter = new QuestionPresenter(this, position, preferences);
		questionPresenter.init();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		return inflater.inflate(R.layout.fragment_question, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		this.questionTextView = view.findViewById(R.id.question_textview);
		this.questionImageView = view.findViewById(R.id.question_imageView);
		this.questionPreviousTextView = view.findViewById(R.id.question_previous_answers);
		this.answerYes = view.findViewById(R.id.answer_yes);
		this.answerNo = view.findViewById(R.id.answer_no);
		this.answerUnsure = view.findViewById(R.id.answer_unsure);

		this.answerYes.setOnClickListener(this);
		this.answerNo.setOnClickListener(this);
		this.answerUnsure.setOnClickListener(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		questionPresenter.getQuestion();
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof OnFragmentInteractionListener) {
			mListener = (OnFragmentInteractionListener) context;
		} else {
			throw new ClassCastException(context.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	@Override
	public void onClick(View v) {
		questionPresenter.onAnswerSelected(v.getId());
		mListener.onQuestionFragmentInteraction();
	}

	/**
	 * View implementation
	 */

	@Override
	public void showQuestion(int question, String name, int color, int drawable) {
		int colorResource = ContextCompat.getColor(getContext(), color);
		questionTextView.setBackgroundColor(colorResource);
		questionImageView.setBackgroundTintList(ContextCompat.getColorStateList(this.getContext(), color));
		questionPreviousTextView.setBackgroundColor(colorResource);

		questionTextView.setText(String.format(getString(question), name));

		// Not every question has a drawable
		if (drawable == 0) {
			this.questionImageView.setImageResource(R.drawable.ic_unsure);
		} else {
			this.questionImageView.setImageResource(drawable);
			this.questionImageView.setColorFilter(ContextCompat.getColor(getContext(), R.color.white));
		}
	}

	@Override
	public void setPreviousAnswer(String answer) {
		if ("".equals(answer)) {
			questionPreviousTextView.setVisibility(View.GONE);
		} else {
			questionPreviousTextView.setText(String.format(getString(R.string.others_have_answered_this_question), answer));
		}
	}

	@Override
	public void makeActivityTextViewInvisible() {
		QuestionsActivity activity = (QuestionsActivity) getActivity();
		activity.makeTextViewInvisible();
	}

	@Override
	public void setBackgroundColor(int yes, int no, int unsure) {
		this.answerYes.setBackgroundColor(ContextCompat.getColor(getContext(), yes));
		this.answerNo.setBackgroundColor(ContextCompat.getColor(getContext(), no));
		this.answerUnsure.setBackgroundColor(ContextCompat.getColor(getContext(), unsure));
	}

	@Override
	public void setPresenter(QuestionFragmentContract.Presenter presenter) {
		questionPresenter = presenter;
	}

	@Override
	public void createChangesetTags() {
		Map<String, String> changesetTags = new HashMap<>();
		changesetTags.put("comment", String.format(getString(R.string.changeset_comment), Answers.getPoiName()));
		changesetTags.put("created_by", getString(R.string.app_name));
		changesetTags.put("version", BuildConfig.VERSION_NAME);
		changesetTags.put("source", getString(R.string.changeset_source));
		Answers.setChangesetTags(changesetTags);
	}

	public interface OnFragmentInteractionListener {
		void onQuestionFragmentInteraction();
	}
}
