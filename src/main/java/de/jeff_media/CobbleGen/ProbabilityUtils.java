package de.jeff_media.CobbleGen;

import org.bukkit.Material;

import java.io.*;
import java.util.*;

public class ProbabilityUtils {

    private Random random;
    private HashMap<Material, Double> items;
    private double totalSum;
    private final Main main;

    public ProbabilityUtils(Main main) {
        this.main = main;
        this.items = new HashMap<>();
        this.random = new Random();

        for(String entry : readLines(main, main.getDataFolder()+ File.separator+"blocks.txt")) {
            String prob = entry.split("%")[0];
            String mat = entry.split("%")[1];
            Material mat2 = Material.valueOf(mat.toUpperCase());
            if(mat2 == null) {
                main.getLogger().severe("Unknown material: "+mat.toUpperCase());
                continue;
            }
            totalSum += Double.valueOf(prob);
            items.put(Material.valueOf(mat.toUpperCase()),Double.valueOf(prob));
            main.getLogger().info("Added material "+mat2.name()+" with "+prob+" probability.");
        }
    }

    public Material getMaterial() {
        double r = 0 + (totalSum - 0) * random.nextDouble();
        double currentSum = 0;
        for(Map.Entry<Material,Double> entry : items.entrySet()) {
            currentSum += entry.getValue();
            if(r <= currentSum) {
                return entry.getKey();
            }
        }
        return Material.BEDROCK;
    }

    public static String[] readLines(Main main, String filename) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            bufferedReader.close();
            return lines.toArray(new String[lines.size()]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }



}
