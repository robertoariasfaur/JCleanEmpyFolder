/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcleanempyfolder;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author Roberto Arias Faur
 * @see "robertoariasfaur@gmail.com"
 * @version 1.0
 */
public class JCleanEmpyFolder {

    /**
     * @param args the command line arguments
     */
    static long flag = 0;
    static long flagDelete = 0;

    public static void main(String[] args) {
        // TODO code application logic here
        
        
        
        

         long tini = System.currentTimeMillis();

         File homFile;
         JFileChooser flChooser = new JFileChooser();
         flChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
         flChooser.setMultiSelectionEnabled(false);

         if (flChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
         homFile = flChooser.getSelectedFile();

         RePrintEmpyFolder1(homFile);

         System.err.println(flag + "  Directorios Eliminados");
         System.err.println(flagDelete + "  Archivos .MAIV Eliminados");
         flag = flagDelete = 0;
         }
         long tfin = System.currentTimeMillis();
         System.err.println((tfin - tini) + "  Tiempo en milisegundos");

         long milliSeconds = (tfin - tini),//1551798059000L;
         seconds = ((milliSeconds/1000)%60),
         minutes = (seconds/60),//(milliSeconds/1000)
         hours = minutes/60; 
        
         DateFormat simple = new SimpleDateFormat("HH:mm:ss:SSS Z");

         Date toDate = Calendar.getInstance().getTime();
         Date date = new Date(toDate.getYear(), toDate.getMonth(), toDate.getDay(),
         (int)hours,(int) minutes,(int) seconds);
         //date.setTime(milliSecond);
         System.out.println(simple.format(date));
         System.out.println();

    }

    public static void RePrintEmpyFolder1(File args1) {
        try {
            LinkedList<File> list;
            list = LinkedList.class.newInstance();
            list.add(args1);

            while (!list.isEmpty()) {
                File args = list.remove();
                String parent = args.getParent();

                if (args.canRead()) {
                    if (isEmpy(args)) {
                        String fileDelete = args.getAbsolutePath();
                        if (args.delete()) {
                            System.err.println("Se Elimino el directorio : " + fileDelete);
                            flag++;
                            list.add(new File(parent));
                        } else {
                            System.out.println("No se pudo elimiar el directorio : " + args.getAbsolutePath());
                        }
                    } else {
                        File[] list2 = args.listFiles();
                        for (File file : list2) {
                            if (file.isDirectory()) {
                                list.add(file);
                            } else { //asumo is a file if(file.isFile())
                                //eliminar de ser .maiv
                                String result = DeleteMaiv(file);
                                if (result != null) {
                                    System.err.println(result);
                                    list.add(new File(parent));
                                }
                            }
                        }
                    }
                }
            }
        } catch (IllegalAccessException | InstantiationException ex) {
            Logger.getLogger(JCleanEmpyFolder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String DeleteMaiv(File args) {
        //args is file not folder
        //and canread this file
        if (args.canRead()) {
            String math = args.getName();
            String path = args.getAbsolutePath();

            if (math.endsWith(".maiv")) {
                if (args.delete()) {
                    flagDelete++;
                    return "Se Elimino el Archivo: " + path;
                } else {
                    return "No se Elimino el Archivo: " + path;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static void RePrintEmpyFolder(File args) {

        if (args.canRead()) {

            if (isEmpy(args)) {
                String parent = args.getParent();
                String fileDelete = (args.getAbsolutePath());
                if (args.delete()) {

                    System.err.println("Se Elimino el directorio : " + fileDelete);

                    flag++;
                    RePrintEmpyFolder(new File(parent));

                } else {
                    System.out.println("No se pudo elimiar el directorio : " + args.getAbsolutePath());
                }

            } else {

                File[] list = args.listFiles();
                for (File file : list) {
                    if (file.isDirectory()) {
                        RePrintEmpyFolder(file);
                    }
                }

            }

        }
    }

    public static boolean isEmpy(File args) {

        if (args.isDirectory()) {
            String[] listFiles = args.list();
            return listFiles == null ? true
                    : (listFiles.length == 0);
        }
        return false;
    }
}
