package wzq.com.pindao;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class BlankFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        Bundle bundle = getArguments();
//        String name = bundle.getString("name");
        double random = Math.random();
        textView.setText("xxxxxxxxxxx"+random);
        return textView;
    }

    //静态创建自己实例的方法, 并且传递值过来,返回自己;
    public static BlankFragment getInstances(String name) {
        BlankFragment blankFragment = new BlankFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        blankFragment.setArguments(bundle);
        return blankFragment;

    }

}
