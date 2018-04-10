package com.teester.whatsnearby.questions.intro;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.teester.whatsnearby.R;

public class IntroFragment extends Fragment implements View.OnClickListener, IntroFragmentContract.View {

	private TextView introName;
	private TextView introAddress;
	private ImageView introImageView;
	private IntroFragmentContract.Presenter introPresenter;
	private OnFragmentInteractionListener listener;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		introPresenter = new IntroPresenter(this);
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		return inflater.inflate(R.layout.fragment_intro, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		this.introName = view.findViewById(R.id.intro_name);
		this.introAddress = view.findViewById(R.id.intro_address);
		this.introImageView = view.findViewById(R.id.intro_image);
		Button introButton = view.findViewById(R.id.intro_button);

		introButton.setOnClickListener(this);
	}

	@Override
	public void showDetails(String name, String address, int drawable) {
		this.introName.setText(name);
		this.introAddress.setText(address);
		this.introImageView.setImageResource(drawable);
	}

	@Override
	public void onResume() {
		super.onResume();
		introPresenter.getDetails();
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof IntroFragment.OnFragmentInteractionListener) {
			listener = (IntroFragment.OnFragmentInteractionListener) context;
		} else {
			throw new ClassCastException(context.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		listener = null;
	}
	@Override
	public void onClick(View v) {
		listener.onIntroFragmentInteraction();
	}

	public interface OnFragmentInteractionListener {
		void onIntroFragmentInteraction();
	}
}
