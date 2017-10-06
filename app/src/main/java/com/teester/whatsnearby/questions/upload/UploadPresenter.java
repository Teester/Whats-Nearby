package com.teester.whatsnearby.questions.upload;

public class UploadPresenter implements UploadFragmentContract.Presenter {

	private UploadFragmentContract.View view;

	public void init(UploadFragment view) {
		this.view = view;
	}
}
