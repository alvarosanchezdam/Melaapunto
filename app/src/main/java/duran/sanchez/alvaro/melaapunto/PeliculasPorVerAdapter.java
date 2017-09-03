package duran.sanchez.alvaro.melaapunto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import duran.sanchez.alvaro.melaapunto.model.Pelicula;

/**
 * Created by Álvaro on 08/08/2017.
 */

public class PeliculasPorVerAdapter extends BaseAdapter {
    private Context context;
    private List<Pelicula> peliculas;
    public PeliculasPorVerAdapter(Context context, List<Pelicula> peliculas){
        this.context=context;
        this.peliculas=peliculas;
    }
    @Override
    public int getCount() {
        return peliculas.size();
    }

    @Override
    public Object getItem(int position) {
        return peliculas.get(position);
    }

    @Override
    public long getItemId(int position) {
        int id= (int)peliculas.get(position).getId();
        return id;
    }



    public class ViewHolder{
        public TextView tvNom;
        public LinearLayout lPrioridad;
        public ImageView ivImagen;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View myView =convertView;
        if(myView ==null) {
            //Inflo la lista con el layout que he creado (llista_item)
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            myView = inflater.inflate(R.layout.item_film_to_see, parent, false);
            ViewHolder holder= new ViewHolder();
            holder.tvNom=(TextView) myView.findViewById(R.id.nombre);
            holder.lPrioridad=(LinearLayout) myView.findViewById(R.id.prioridad);
            holder.ivImagen=(ImageView) myView.findViewById(R.id.imagenPelicula);
            myView.setTag(holder);
        }
        ViewHolder holder= (ViewHolder) myView.getTag();

        //Voy asignando los datos
        Pelicula pelicula = peliculas.get(position);
        String nombre=pelicula.getTitulo();
        holder.tvNom.setText(nombre);
        if(pelicula.getTag()==0){
            holder.lPrioridad.setBackgroundColor(Color.RED);
        } else if(pelicula.getTag()==1){
            holder.lPrioridad.setBackgroundColor(Color.YELLOW);
        } else {
            holder.lPrioridad.setBackgroundColor(Color.GREEN);
        }
        File imgFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/melaapunto/imagenes/" + pelicula.getTitulo()+".png");

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.ivImagen.setImageBitmap(myBitmap);

        }
        return myView;
    }
}
