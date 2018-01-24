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
import com.teester.whatsnearby.data.OsmObject;

public class IntroFragment extends Fragment implements View.OnClickListener, IntroFragmentContract.View {

	private TextView intro_name;
	private TextView intro_address;
	private ImageView intro_imageView;
	private Button intro_button;
	private IntroFragmentContract.Presenter introPresenter;
	private OnFragmentInteractionListener mListener;

	@NonNull
	public static IntroFragment newInstance(OsmObject poi) {
		return new IntroFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		introPresenter = new IntroPresenter(this);
		introPresenter.init();
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
		this.intro_name = view.findViewById(R.id.intro_name);
		this.intro_address = view.findViewById(R.id.intro_address);
		this.intro_imageView = view.findViewById(R.id.intro_image);
		this.intro_button = view.findViewById(R.id.intro_button);

		this.intro_button.setOnClickListener(this);
	}

	@Override
	public  void ShowDetails(String name, String address, int drawable) {
		this.intro_name.setText(name);
		this.intro_address.setText(address);
		this.intro_imageView.setImageResource(drawable);
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
			mListener = (IntroFragment.OnFragmentInteractionListener) context;
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
		mListener.onIntroFragmentInteraction();
	}

	@Override
	public void setPresenter(IntroFragmentContract.Presenter presenter) {

	}

	public interface OnFragmentInteractionListener {
		void onIntroFragmentInteraction();
	}
}
