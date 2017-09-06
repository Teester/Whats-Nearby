package com.teester.mapquestions;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuestionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment {
	private static final String TAG = QuestionFragment.class.getSimpleName();

	protected static final String ARG_PARAM1 = "param1";
	protected static final String ARG_PARAM2 = "param2";
	protected static final String ARG_PARAM3 = "param3";

	private OnFragmentInteractionListener mListener;

	private TextView question_textView;
	private ImageView question_imageView;
	private RelativeLayout question_relative_layout;
	private Button answer_yes;
	private Button answer_no;
	private Button answer_unsure;

	private OsmObject poi;
	private int position;
	private OsmObjectType listOfQuestions;

	private int color;

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
		args.putParcelable(ARG_PARAM1, poi);
		args.putInt(ARG_PARAM2, position);
		args.putParcelable(ARG_PARAM3, listOfQuestions);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			this.poi = getArguments().getParcelable(ARG_PARAM1);
			this.position = getArguments().getInt(ARG_PARAM2);
			this.listOfQuestions = getArguments().getParcelable(ARG_PARAM3);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View questionFragmentView = inflater.inflate(R.layout.fragment_question, container, false);
		this.question_textView = questionFragmentView.findViewById(R.id.question_textview);
		this.question_imageView = questionFragmentView.findViewById(R.id.question_imageView);
		this.question_relative_layout = questionFragmentView.findViewById(R.id.question_relative_layout);
		this.answer_yes = questionFragmentView.findViewById(R.id.answer_yes);
		this.answer_no = questionFragmentView.findViewById(R.id.answer_no);
		this.answer_unsure = questionFragmentView.findViewById(R.id.answer_unsure);
		return questionFragmentView;
	}

//	private void setOnClick(final Button btn, final int color, final int position, final String answer){
//		btn.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				v.setBackgroundColor(ContextCompat.getColor(getContext(), color));
//			}
//		});
//	}

	public void onButtonPressed(ArrayList<OsmObject> uri) {
		if (mListener != null) {
			mListener.onQuestionFragmentInteraction(uri);
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		// Get the list of questions associated with the object type
		ArrayList<QuestionObject> questions = this.listOfQuestions.getQuestionObjects();
		//Get the question Object containing details of the question at the viewpager page position
		QuestionObject questionObject = questions.get(position);
		QuestionsActivity.question = questionObject;
		//Get the integer of the question String
		int question = questionObject.getQuestion();
		this.color = questionObject.getColor();

		this.question_relative_layout.setBackgroundColor(ContextCompat.getColor(getContext(), color));
		this.question_textView.setText(String.format(getResources().getString(question), this.poi.getName()));

		int drawableIdentifier = questionObject.getIcon();
		if (drawableIdentifier == 0) {
			this.question_imageView.setImageResource(R.drawable.ic_unsure);
		} else {
			this.question_imageView.setImageResource(drawableIdentifier);
			this.question_imageView.setColorFilter(ContextCompat.getColor(getContext(), R.color.white));
		}

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

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated
	 * to the activity and potentially other fragments contained in that
	 * activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFragmentInteractionListener {
		void onQuestionFragmentInteraction(ArrayList<OsmObject> poiList);
	}
}
