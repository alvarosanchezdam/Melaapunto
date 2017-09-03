package duran.sanchez.alvaro.melaapunto;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import duran.sanchez.alvaro.melaapunto.model.Pelicula;

/**
 * Created by Álvaro on 07/08/2017.
 */

public class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    String cadenaTotal="";
    private ListView listView;
    private List<Pelicula> peliculas = new ArrayList<>();
    PeliculasPorVerAdapter peliculasPorVerAdapter;
    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        listView = (ListView) rootView.findViewById(R.id.peliculas_por_ver);
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/melaapunto/peliculas.txt");

        if(dir.exists()){

            String cadena;
            FileReader f = null;
            try {
                f = new FileReader(dir);
                BufferedReader b = new BufferedReader(f);
                while((cadena = b.readLine())!=null) {
                    cadenaTotal += cadena;
                }
                b.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Gson gson = new Gson();

            peliculas = (List<Pelicula>) gson.fromJson(cadenaTotal, new TypeToken<ArrayList<Pelicula>>(){}.getType());

        }
        peliculasPorVerAdapter = new PeliculasPorVerAdapter(getContext(), peliculas);
        listView.setAdapter(peliculasPorVerAdapter);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        peliculasPorVerAdapter.notifyDataSetChanged();
    }
}
