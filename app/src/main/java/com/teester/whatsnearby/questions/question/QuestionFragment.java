package com.teester.whatsnearby.questions.question;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.teester.whatsnearby.R;
import com.teester.whatsnearby.data.OsmObject;
import com.teester.whatsnearby.data.OsmObjectType;
import com.teester.whatsnearby.data.source.Preferences;
import com.teester.whatsnearby.questions.QuestionsPresenter;


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
	private static final String TAG = QuestionFragment.class.getSimpleName();
	QuestionsPresenter presenter;
	private OnFragmentInteractionListener mListener;
	private QuestionFragmentContract.Presenter questionPresenter;

	private TextView question_textView;
	private ImageView question_imageView;
	//	private RelativeLayout question_relative_layout;
	private Button answer_yes;
	private Button answer_no;
	private Button answer_unsure;

	private int position;

	public QuestionFragment() {
		// Required empty public constructor
	}

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
		return inflater.inflate(R.layout.fragment_question, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		this.question_textView = view.findViewById(R.id.question_textview);
		this.question_imageView = view.findViewById(R.id.question_imageView);
		//this.question_relative_layout = view.findViewById(R.id.question_relative_layout);
		this.answer_yes = view.findViewById(R.id.answer_yes);
		this.answer_no = view.findViewById(R.id.answer_no);
		this.answer_unsure = view.findViewById(R.id.answer_unsure);

		this.answer_yes.setOnClickListener(this);
		this.answer_no.setOnClickListener(this);
		this.answer_unsure.setOnClickListener(this);
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
			throw new RuntimeException(context.toString()
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

		Log.w(TAG, "in Show Question");
		Log.w(TAG, name);
		question_textView.setBackgroundColor(ContextCompat.getColor(getContext(), color));
		question_textView.setText(String.format(getString(question), name));

		// Not every question has a drawable
		if (drawable == 0) {
			this.question_imageView.setImageResource(R.drawable.ic_unsure);
		} else {
			this.question_imageView.setImageResource(drawable);
			this.question_imageView.setColorFilter(ContextCompat.getColor(getContext(), R.color.white));
		}
	}

	@Override
	public void setBackgroundColor(int yes, int no, int unsure) {
		Log.w(TAG, "inSetBackgroundColor");
		this.answer_yes.setBackgroundColor(ContextCompat.getColor(getContext(), yes));
		this.answer_no.setBackgroundColor(ContextCompat.getColor(getContext(), no));
		this.answer_unsure.setBackgroundColor(ContextCompat.getColor(getContext(), unsure));
	}

	@Override
	public void setPresenter(QuestionFragmentContract.Presenter presenter) {
		questionPresenter = presenter;
	}

	public interface OnFragmentInteractionListener {
		void onQuestionFragmentInteraction();
	}
}
