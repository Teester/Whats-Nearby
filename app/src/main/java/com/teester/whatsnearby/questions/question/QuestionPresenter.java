package com.teester.whatsnearby.questions.question;

import android.content.Context;

import com.teester.whatsnearby.R;
import com.teester.whatsnearby.data.Answer;
import com.teester.whatsnearby.data.Answers;
import com.teester.whatsnearby.data.OsmObject;
import com.teester.whatsnearby.data.OsmObjectType;
import com.teester.whatsnearby.data.PoiList;
import com.teester.whatsnearby.data.PoiTypes;
import com.teester.whatsnearby.data.QuestionObject;
import com.teester.whatsnearby.data.source.UploadToOSM;

import java.util.List;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

public class QuestionPresenter implements QuestionFragmentContract.Presenter {

	private int position;
	private QuestionFragmentContract.View view;
	private OsmObject poi;
	private OsmObjectType listOfQuestions;
	private Context context;

	public QuestionPresenter(QuestionFragmentContract.View view, int position, Context context) {
		this.view = view;
		poi = PoiList.getInstance().getPoiList().get(0);
		listOfQuestions = PoiTypes.getPoiType(poi.getType());
		this.position = position;
		this.context = context;
	}

	@Override
	public void getQuestion() {

		List<QuestionObject> questions = this.listOfQuestions.getQuestionObjects();
		QuestionObject questionObject = questions.get(position);

		int question = questionObject.getQuestion();
		int color = questionObject.getColor();
		int drawable = questionObject.getIcon();

		view.showQuestion(question, poi.getName(), color, drawable);
	}

	@Override
	public void onAnswerSelected(int id) {
		List<QuestionObject> questions = listOfQuestions.getQuestionObjects();
		QuestionObject questionObject = questions.get(position);
		int selectedColor = questionObject.getColor();
		int unselectedColor = R.color.colorPrimary;
		String answer = null;
		switch (id) {
			case R.id.answer_yes:
				view.setBackgroundColor(selectedColor, unselectedColor, unselectedColor);
				answer = "yes";
				break;
			case R.id.answer_no:
				view.setBackgroundColor(unselectedColor, selectedColor, unselectedColor);
				answer = "no";
				break;
			case R.id.answer_unsure:
				view.setBackgroundColor(unselectedColor, unselectedColor, selectedColor);
				break;
			default:
				break;
		}

		Answer answerToQuestion = new Answer(questionObject, answer);
		addAnswer(answerToQuestion);

		if (position == listOfQuestions.getNoOfQuestions() - 1) {
			try {
				new UploadToOSM(context);
			} catch (OAuthNotAuthorizedException e) {
				e.printStackTrace();
			} catch (OAuthExpectationFailedException e) {
				e.printStackTrace();
			} catch (OAuthCommunicationException e) {
				e.printStackTrace();
			} catch (OAuthMessageSignerException e) {
				e.printStackTrace();
			}
		}

	}

	private void addAnswer(Answer answer) {
		if (answer != null) {
			if (Answers.getPoiName() == null) {
				Answers.setPoiDetails(poi);
			}
			Answers.addAnswer(answer);
		}
	}

	@Override
	public void init() {

	}

	@Override
	public void destroy() {

	}
}
