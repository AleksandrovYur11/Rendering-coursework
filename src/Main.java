import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        Scene scene = new Scene();

        ArrayList<Vector3D> points = new ArrayList<>();
        ArrayList<SourceLight> sourceLights = new ArrayList<>();
        ArrayList<Integer> objId = new ArrayList<>();
        ArrayList<Polygon3D> polygons = new ArrayList<>();

        HashMap<Double, Double> specKD_brs0 = new HashMap<>();
        specKD_brs0.put(400.0, 0.364);
        specKD_brs0.put(500.0, 0.532);
        specKD_brs0.put(600.0, 0.52);
        specKD_brs0.put(700.0, 0.67);
        Material brs0 = new Material(specKD_brs0);

        HashMap<Double, Double> specKD_brs1 = new HashMap<>();
        specKD_brs1.put(400.0, 0.012);
        specKD_brs1.put(500.0, 0.3);
        specKD_brs1.put(600.0, 0.2);
        specKD_brs1.put(700.0, 0.5);
        Material brs1 = new Material(specKD_brs1);

        HashMap<Double, Double> specKD_brs2 = new HashMap<>();
        specKD_brs2.put(400.0, 0.35);
        specKD_brs2.put(500.0, 0.56);
        specKD_brs2.put(600.0, 0.3);
        specKD_brs2.put(700.0, 0.7);
        Material brs2 = new Material(specKD_brs2);

        HashMap<Double, Double> specKD_brs3 = new HashMap<>();
        specKD_brs3.put(400.0, 0.562);
        specKD_brs3.put(500.0, 0.643);
        specKD_brs3.put(600.0, 0.562);
        specKD_brs3.put(700.0, 0.71);
        Material brs3 = new Material(specKD_brs3);

        HashMap<Double, Double> specKD_brs4 = new HashMap<>();
        specKD_brs4.put(400.0, 0.767);
        specKD_brs4.put(500.0, 0.747);
        specKD_brs4.put(600.0, 0.74);
        specKD_brs4.put(700.0, 0.737);
        Material brs4 = new Material(specKD_brs4);

        ArrayList<Material> materials = new ArrayList<>();
        materials.add(brs0);
        materials.add(brs1);
        materials.add(brs2);
        materials.add(brs3);
        materials.add(brs4);

        System.out.println("Start program!!!");

        int flag = 0;
        double pointSize = 0;

        File file = new File("cornel_box0.shp");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.contains("Number of parts")) {
                    flag = 0;
                    continue;
                }

                if (line.contains("Number of triangles")) {
                    flag = 3;
                    continue;
                }

                if (flag == 3) {
                    List<Integer> numberTriangles = Arrays.stream(line.split("[ ]+")).map(Integer::parseInt).
                            collect(Collectors.toList());
                    polygons.add(new Polygon3D((points.get((int) (numberTriangles.get(0) + pointSize))),
                            points.get((int) (numberTriangles.get(1) + pointSize)),
                            points.get((int) (numberTriangles.get(2) + pointSize)),
                            materials.get(objId.get(objId.size() - 1))));
                    System.out.println(materials.get(objId.get(objId.size() - 1)).toString());
                    int a = 0;
                    continue;
                }

                if (line.startsWith("define breps brs_")) {
                    Integer x = Integer.parseInt(line.substring(line.length() - 1));
                    System.out.println(x);
                    objId.add(x);
                    flag = 1;
                    continue;
                }

                if (line.endsWith("Number of vertices")) {
                    pointSize = points.size();
                    flag = 2;
                    System.out.println(pointSize);
                    continue;
                }

                if (flag == 2) {
                    List<Double> point = Arrays.stream(line.split("[ ]+")).map(Double::parseDouble).
                            collect(Collectors.toList());
                    points.add(new Vector3D(point.get(0), point.get(1), point.get(2)));
                    //System.out.println(point.get(0) + " " + point.get(1) + " " + point.get(2));
                }
            }
            System.out.println(polygons.size());
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        System.out.println("Data file loaded!!!");
        double intensityTotal = 650.0;
        double ITotal = 2100.0;
        double Isim_400 = 0;
        double Isim_500 = 400.0;
        double Isim_600 = 780.0;
        double Isim_700 = 920.0;

        HashMap<Double, Double> specIntensity = new HashMap<>();
        specIntensity.put(400.0, intensityTotal * (Isim_400 / ITotal));
        specIntensity.put(500.0, intensityTotal * (Isim_500 / ITotal));
        specIntensity.put(600.0, intensityTotal * (Isim_600 / ITotal));
        specIntensity.put(700.0, intensityTotal * (Isim_700 / ITotal));

        sourceLights.add(new SourceLight(new Vector3D(250.0, 548.7, -279.5), intensityTotal, specIntensity));
        System.out.println("Rendering scene....");
        scene.renderScene(polygons, sourceLights);
        System.out.println("Successful render");

         int array[] = new int[];
        array = {1, 3}
    }
}
