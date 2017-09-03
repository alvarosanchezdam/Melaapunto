package duran.sanchez.alvaro.melaapunto;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import duran.sanchez.alvaro.melaapunto.model.Pelicula;

public class AddFilmActivity extends AppCompatActivity {

    private EditText nuevoNombre;
    private EditText nuevoRecomendado;
    private EditText nuevaDescripcion;

    private ImageView imagenPelicula;

    private Button escogerImagen;
    private Button crearPelicula;
    private Uri outputFileUri;
    private List<Pelicula> peliculas = new ArrayList<>();
    private Pelicula pelicula = new Pelicula();
    private String cadenaTotal = "";
    private Uri uriImagen;
    File f;
    final private int PERMISSION_CODE = 123;
    private static final int MAX_USER_IMAGE_HEIGHT = 200000;
    private static final int MAX_USER_IMAGE_WIDTH = 200000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_film);
        nuevoNombre = (EditText) findViewById(R.id.nuevoTituloInput);
        nuevoRecomendado = (EditText) findViewById(R.id.nuevoRecomendadoInput);
        nuevaDescripcion = (EditText) findViewById(R.id.nuevaDescripcionInput);
        imagenPelicula = (ImageView) findViewById(R.id.imagenPelicula);
        escogerImagen = (Button) findViewById(R.id.escogerImagen);
        crearPelicula = (Button) findViewById(R.id.crearPelicula);
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
        crearPelicula.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                pelicula.setTitulo(nuevoNombre.getText()+"");
                pelicula.setTag(2);
                peliculas.add(pelicula);
                File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/melaapunto/");
                f = new File(dir, "peliculas.txt");
                if(f.exists())f.delete();
                    try {
                        f.createNewFile();
                        Gson gson = new Gson();
                        String pelis = gson.toJson(peliculas);
                        montarFile(pelis);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                final File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/melaapunto/imagenes/" + nuevoNombre.getText()+".png");
                try {
                    if(!f.exists()) {
                        f.createNewFile();
                    }
                    File file = new File(getRealPathFromURI(uriImagen));
                    copyFile(file, f);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                onBackPressed();
                }
            }
        );
        escogerImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkPermission();
                } else {
                    setImageChooser();
                }
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPermission() {
        int permission = this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            this.requestPermissions(
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_CODE
            );
            return;
        } else {
            //method that requires permissions
            setImageChooser();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    int permission = this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (permission == PackageManager.PERMISSION_GRANTED) {
                        //method that requires permissions
                        setImageChooser();
                    } else {
                        //permission denied
                        Toast.makeText(this, "Write external storage denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    private void montarFile(String peliculas) {
        //Me aseguro que la descarga no llega vacia, si es asi no la guardo en el fichero
        if (!peliculas.equals("")) {
            PrintWriter wr = null;
            try {
                //Escribo la descarga en el fichero creado
                wr = new PrintWriter(f);
                wr.println(peliculas);
                wr.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    void setImageChooser() {
        final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "melaapunto" + File.separator + "imagenes" + File.separator);
        root.mkdirs();
        final String fname = nuevoNombre.getText() + ".jpg";
        final File sdImageMainDirectory = new File(root, fname);
        outputFileUri = Uri.fromFile(sdImageMainDirectory);
        Intent galleryintent = new Intent(Intent.ACTION_GET_CONTENT, null);
        galleryintent.setType("image/*");
        Intent chooser = new Intent(Intent.ACTION_CHOOSER);
        chooser.putExtra(Intent.EXTRA_INTENT, galleryintent);
        chooser.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        Intent[] intentArray = {cameraIntent};
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
        startActivityForResult(chooser, 1);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        if (resultCode == Activity.RESULT_OK) {
            boolean isCamera;
            try {
                if (imageReturnedIntent.getData() == null) {
                    isCamera = true;
                } else {
                    final String action = imageReturnedIntent.getAction();
                    isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                isCamera = true;
            }
            uriImagen = imageReturnedIntent.getData();
            imagenPelicula.setImageURI(uriImagen);
            File aux = new File(this.getCacheDir() + File.separator + "melaapunto" + File.separator + "imagenes"+ File.separator + nuevoNombre.getText()+".jpg");
            final String fname = nuevoNombre.getText()+".jpg";


        }

}
    private void copyFile(File sourceFile, File destFile) throws IOException {
        if (!sourceFile.exists()) {
            return;
        }

        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }


    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String getRealPathFromURI(Uri contentURI) {
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(contentURI);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = { MediaStore.Images.Media.DATA };

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{ id }, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        startManagingCursor(cursor);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    private void saveFile(Uri sourceUri, File destination){
    try {
        File source = new File(sourceUri.getPath());
        FileChannel src = new FileInputStream(source).getChannel();
        FileChannel dst = new FileOutputStream(destination).getChannel();
        dst.transferFrom(src, 0, src.size());
        src.close();
        dst.close();
    } catch (IOException ex) {
        ex.printStackTrace();
    }
}
    public static void copyUriToFile(Uri selectedImageUri, File sdImage) {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            outputStream = new FileOutputStream(sdImage);

            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (outputStream != null) outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
