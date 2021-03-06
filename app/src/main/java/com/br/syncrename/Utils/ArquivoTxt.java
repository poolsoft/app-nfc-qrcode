package com.br.syncrename.Utils;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.br.syncrename.Models.Arquivo;
import com.br.syncrename.Models.Tags;
import com.br.syncrename.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ArquivoTxt {

    // caminho da pasta
    public static String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/sync_rename";

    public static void criarPasta(){
        // criar pasta
        File dir = new File(path);
        dir.mkdirs();
    }

    public static void gravarArquivo (Context context, String texto, String nome)
    {
        //Criar arquivo
        File file = new File (path + "/"+nome+".txt");
        String textoTotal = String.valueOf(texto)+"\n" + lerArquivo(nome) ;
        String [] saveText = textoTotal.split(System.getProperty("line.separator"));

        Toast.makeText(context, context.getResources().getString(R.string.file_saved), Toast.LENGTH_LONG).show();
        Save (file, saveText);
    }

    public static String lerArquivo (String nome)
    {
        File file = new File (path + "/"+nome+".txt");
        String [] loadText = Load(file);
        String finalString = "";

        if(loadText != null)
        for (int i = 0; i < loadText.length; i++)
        {
            finalString += loadText[i] + System.getProperty("line.separator");
        }

        return finalString;
    }

    public static String [] lerArquivoArray (String nome)
    {
        File file = new File (path + "/"+nome+".txt");
        String [] loadText = Load(file);

        return loadText;
    }

    public static List<Arquivo> listaArquivos(String nome){
        String [] todosArquivos = lerArquivoArray(nome);
        List<Arquivo> result = new ArrayList<>();

        if(todosArquivos !=null && todosArquivos.length > 0){
            for (String arquivo: todosArquivos) {
                try {
                    result.add(ServerHandler.getJsonConverter().readValue(arquivo, Arquivo.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;

    }

    public static List<String> listaTags(String nome){
        String [] todosArquivos = lerArquivoArray(nome);
        List<String> result = new ArrayList<>();

        for (String arquivo: todosArquivos) {
            result.add(arquivo);
        }
        return result;

    }

    public static void Save(File file, String[] data)
    {
        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(file);
        }
        catch (FileNotFoundException e) {e.printStackTrace();}
        try
        {
            try
            {
                for (int i = 0; i<data.length; i++)
                {
                    fos.write(data[i].getBytes());
                    if (i < data.length-1)
                    {
                        fos.write("\n".getBytes());
                    }
                }
            }
            catch (IOException e) {e.printStackTrace();}
        }
        finally
        {
            try
            {
                fos.close();
            }
            catch (IOException e) {e.printStackTrace();}
        }
    }

    public static String[] Load(File file)
    {
        FileInputStream fis = null;
        try
        {
            fis = new FileInputStream(file);
        }
        catch (FileNotFoundException e) {e.printStackTrace();}
        if(fis != null){
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String test;
            int anzahl=0;
            try
            {
                while ((test=br.readLine()) != null)
                {
                    anzahl++;
                }
            }
            catch (IOException e) {e.printStackTrace();}

            try
            {
                fis.getChannel().position(0);
            }
            catch (IOException e) {e.printStackTrace();}

            String[] array = new String[anzahl];

            String line;
            int i = 0;
            try
            {
                while((line=br.readLine())!=null)
                {
                    array[i] = line;
                    i++;
                }
            }
            catch (IOException e) {e.printStackTrace();}
            return array;

        }
        return null;
    }

}
