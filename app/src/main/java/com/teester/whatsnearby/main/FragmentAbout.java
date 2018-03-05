package com.teester.whatsnearby.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teester.whatsnearby.R;

public class FragmentAbout extends Fragment implements MainActivityContract.AboutView, View.OnClickListener {

	private TextView version;
	private MainActivityContract.AboutPresenter aboutPresenter;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		aboutPresenter = new AboutPresenter(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		return inflater.inflate(R.layout.fragment_about, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		this.version = view.findViewById(R.id.about_version_content);

		view.findViewById(R.id.about_licence_title).setOnClickListener(this);
		view.findViewById(R.id.about_licence_content).setOnClickListener(this);
		view.findViewById(R.id.about_licence_icon).setOnClickListener(this);
		view.findViewById(R.id.about_source_title).setOnClickListener(this);
		view.findViewById(R.id.about_source_content).setOnClickListener(this);
		view.findViewById(R.id.about_source_icon).setOnClickListener(this);

		aboutPresenter.findVersion();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.about_licence_content:
			case R.id.about_authors_title:
			case R.id.about_licence_icon:
				aboutPresenter.getLicence();
				break;
			case R.id.about_source_content:
			case R.id.about_source_title:
			case R.id.about_source_icon:
				aboutPresenter.getGitHub();
				break;
			default:
				break;
		}
	}

	@Override
	public void setVersion(String version) {
		this.version.setText(version);
	}

	@Override
	public void visitUri(String uri) {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(uri));
		startActivity(browserIntent);
	}
}
