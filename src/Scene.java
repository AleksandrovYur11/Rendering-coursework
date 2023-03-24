import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class Scene {

    Quad<Boolean, Material, Vector3D, Vector3D> intersectedScene(RayOfLight ray, ArrayList<Polygon3D> polygons){
        double distTriangle = Double.MAX_VALUE;
        double t = 0;
        Material material = null;
        Vector3D hit = null;
        Vector3D n = null;
        for (Polygon3D polygon : polygons) {
            var p = polygon.polygonIntersected(ray, t);
            t = p.second;
            if (polygon.polygonIntersected(ray, t).first && t < distTriangle) {
                distTriangle = t;
                material = polygon.material;
                hit = ray.origin.sum(ray.direction.mult(t));
                n = polygon.getNormalObserver(ray.origin.sub(hit));
            }
        }
        return new Quad<>(distTriangle < Double.MAX_VALUE, material, hit, n);
    }

    RayOfLight rayCast(RayOfLight ray, ArrayList<Polygon3D> polygons,
                       ArrayList<SourceLight> sourceLights, int depth) {
        var quad = intersectedScene(ray, polygons);
        Vector3D n = quad.fourth;
        Vector3D hit = quad.third;
        Material material = quad.second;

        if (depth > 3 || !quad.first) {
            return ray;
        }

        for (Double item : material.diffuseReflection.keySet()) {
           material.diffuseReflection.replace(item, ray.brightnessCoef.get(item)
                   * material.diffuseReflection.get(item));
        }

        for (SourceLight raySourceLights: sourceLights) {
            Vector3D lightDir = (raySourceLights.position.sub(hit)).normal();
            Vector3D lightDirMinus = (hit.sub(raySourceLights.position)).normal();
            double dist = (raySourceLights.position.sub(hit)).length();
            boolean includeKd = true;
            double cosTheta = Vector3D.dot(lightDir, n);
            if (cosTheta <= 0){
                includeKd = false;
            }
            double BRDF = 0;

            Vector3D shadowOrigin = hit.sum(n.mult(1e-3));
            RayOfLight shadowRay = new RayOfLight(shadowOrigin, lightDir);
            var quadShadow = intersectedScene(shadowRay, polygons);
            Vector3D shadowHit = quadShadow.third;
            Vector3D shadowN = quadShadow.fourth;
            Material shadowMaterial = quadShadow.second;

            if(intersectedScene(shadowRay, polygons).first
            && (shadowHit.sub(shadowOrigin)).length() < dist){
                includeKd = false;
            }


            for (Double item : ray.L.keySet()) {
                double E = (raySourceLights.spectralIntensity.get(item) * cosTheta) / (dist * dist);
                if (includeKd) {
                    BRDF = ray.brightnessCoef.get(item);
                }
                double t  = (E * BRDF) / Math.PI;
                ray.L.replace(item, (E * BRDF) / Math.PI);
            }
        }
        return ray;
    }

    void renderScene(ArrayList<Polygon3D> polygons, ArrayList<SourceLight> sourceLights) throws IOException {
        int height = 600;
        int width = 600;
        double angle = Math.PI / 3.;
        ArrayList<RayOfLight> frameBuffer = new ArrayList<RayOfLight>(height * width);
        ArrayList<HashMap<Double, Double>> LBuffer = new ArrayList<>(height * width);

        ArrayList<Double> waveLength = new ArrayList<>();
        waveLength.add(400D);
        waveLength.add(500D);
        waveLength.add(600D);
        waveLength.add(700D);
        Camera camera = new Camera (250, 300, 500);

        for(int k = 0; k < height; k++){
            for(int r = 0; r < width; r++){
                double yCenter = -(2 * (k + 0.5) / (double)height - 1) * Math.tan(angle / 2.);
                double xCenter = -(2 * (r + 0.5) / (double)width - 1) * Math.tan(angle / 2.) * width / (double)height;
                camera.setCameraDirection(xCenter, yCenter);
                RayOfLight centerRay = camera.createRayCamera(sourceLights);
                frameBuffer.add(rayCast(centerRay, polygons, sourceLights, 2));
                LBuffer.add(frameBuffer.get(r + k * width).L);
            }
        }

        try (FileWriter writer = new FileWriter("render.txt", false)){
            for(int k = 0; k < waveLength.size(); k++){
                writer.write("wavelength " + waveLength.get(k) + "\n");
                for (int i = 0; i < height; i++){
                    for(int j = 0; j < width; j++){
                        if (j == 0){
                            writer.write(String.valueOf(LBuffer.get(j + i * width).get(waveLength.get(k))));
                        } else {
                            writer.write(" " + LBuffer.get(j + i * width).get(waveLength.get(k)));
                        }
                    }
                    writer.write("\n");
                }
                writer.write("\n");
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
