/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imf.json;

import com.google.gson.*;
import imf.entity.MyFile;
import imf.entity.SimulationManual;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import listOrderedUnordered.ArrayOrderedList;

/**
 * Estrutura de Dados - 2020-2021.
 *
 * @author Mariana Ribeiro - 8190573
 * @author André Raro - 8190590
 */
public class Exporter extends MyFile {

    /**
     * Exporta um objeto para o ficheiro, se o ficheiro já existir é lhe
     * adicionado informação caso contrário cria primeiramente o ficheiro.
     * @param obj objeto Json a exportar
     * @param fileName Caminho do ficheiro que se deseja ler
     */
    private static void exporter(Object obj, String fileName) {
        FileWriter file = null;
        try {
            Gson gson = new Gson().newBuilder().setPrettyPrinting().create();
            String filePath = MyFile.getPATH_EXPORT() + fileName + MyFile.getEXTENSION();
            file = new FileWriter(filePath);
            gson.toJson(obj, file);
            file.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     *
     * @param manual iterador para percorrer a simulação manual
     * @param fileName nome do ficheiro
     */
    public static void exportToJson(ArrayOrderedList<SimulationManual> manual, String fileName) {
        Iterator it = manual.iterator();
        SimulationManual[] temp = new SimulationManual[manual.size()];
        int i = 0;
        while (it.hasNext()) {
            SimulationManual simulation = (SimulationManual) it.next();
            temp[i] = new SimulationManual(simulation.getLifePoints(), simulation.getPath(), simulation.isGetTarget());
            i++;
        }
        Exporter.exporter(temp, fileName);
    }

}
