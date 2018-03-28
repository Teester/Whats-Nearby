package com.teester.whatsnearby.questions.question;

public interface QuestionFragmentContract {

	interface Presenter {

		void getQuestion();

		void onAnswerSelected(int id);
	}

	interface View {

		void showQuestion(int question, String name, int color, int drawable);

		void setPreviousAnswer(String answer);

		void setBackgroundColor(int yes, int no, int unsure);

		void createChangesetTags();

	}
}
