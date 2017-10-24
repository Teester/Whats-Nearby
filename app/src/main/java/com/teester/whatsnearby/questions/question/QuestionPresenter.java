package com.teester.whatsnearby.questions.question;

import com.teester.whatsnearby.R;
import com.teester.whatsnearby.data.Answers;
import com.teester.whatsnearby.data.OsmObject;
import com.teester.whatsnearby.data.OsmObjectType;
import com.teester.whatsnearby.data.PoiList;
import com.teester.whatsnearby.data.PoiTypes;
import com.teester.whatsnearby.data.QuestionObject;
import com.teester.whatsnearby.data.source.SourceContract;
import com.teester.whatsnearby.data.source.UploadToOSM;

import java.util.List;

public class QuestionPresenter implements QuestionFragmentContract.Presenter {

	private int position;
	private QuestionFragmentContract.View view;
	private OsmObject poi;
	private OsmObjectType listOfQuestions;
	private SourceContract.Preferences preferences;

	public QuestionPresenter(QuestionFragmentContract.View view, int position, SourceContract.Preferences preferences) {
		this.view = view;
		poi = PoiList.getInstance().getPoiList().get(0);
		listOfQuestions = PoiTypes.getPoiType(poi.getType());
		this.position = position;
		this.preferences = preferences;
	}

	@Override
	public void getQuestion() {
		List<QuestionObject> questions = this.listOfQuestions.getQuestionObjects();
		QuestionObject questionObject = questions.get(position);

		int question = questionObject.getQuestion();
		int color = questionObject.getColor();
		int drawable = questionObject.getIcon();

		view.showQuestion(question, poi.getName(), color, drawable);

		String key = questionObject.getTag();
		String answer = poi.getTag(key);
		if (answer != null) {
			view.setPreviousAnswer(answer);
		} else {
			view.setPreviousAnswer("");
		}
	}

	@Override
	public void getPreviousAnswer(String key) {
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
				answer = "unsure";
				break;
			default:
				break;
		}

		String answerTag = questionObject.getAnswer(answer);
		String questionTag = questionObject.getTag();
		addAnswer(questionTag, answerTag);

		if (position == listOfQuestions.getNoOfQuestions() - 1) {
			view.createChangesetTags();
			new Thread(new Runnable() {
				@Override
				public void run() {
					SourceContract.Upload upload = new UploadToOSM(preferences);
					upload.Upload();
				}
			}).start();
		}
	}

	private void addAnswer(String questionTag, String answerTag) {
		if (Answers.getPoiName() != poi.getName()) {
			Answers.setPoiDetails(poi);
		}
		Answers.addAnswer(questionTag, answerTag);
	}

	@Override
	public void init() {

	}

	@Override
	public void destroy() {

	}
}
