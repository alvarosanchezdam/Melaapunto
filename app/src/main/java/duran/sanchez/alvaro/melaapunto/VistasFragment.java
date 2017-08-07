package duran.sanchez.alvaro.melaapunto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Álvaro on 07/08/2017.
 */

public class VistasFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public VistasFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
        public static VistasFragment newInstance(int sectionNumber) {
        VistasFragment fragment = new VistasFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vistas, container, false);

        return rootView;
    }
}