import java.util.ArrayList;
import java.util.HashMap;

public class RayOfLight {

    public Vector3D origin;
    public Vector3D direction;
    public HashMap<Double, Double> brightnessCoef = new HashMap<>();
    public HashMap<Double, Double> L = new HashMap<>();

    RayOfLight(Vector3D origin, Vector3D direction) {
        this.origin = origin;
        this.direction = direction;
    }

    RayOfLight(Vector3D origin, Vector3D direction, HashMap<Double, Double> brightnessCoef,
               HashMap<Double, Double> L) {
        this.origin = origin;
        this.direction = direction;
        this.brightnessCoef = brightnessCoef;
        this.L = L;
    }

    void fillWaveLength(ArrayList<SourceLight> sourceLights) {
        for (int j = 0; j < sourceLights.size(); j++) {
            for (Double wave : sourceLights.get(j).spectralIntensity.keySet()) {
                this.brightnessCoef.put(wave, 1D);
                this.L.put(wave, 0D);
            }
        }
    }

    @Override
    public String toString() {
        return "RayOfLight{" +
                "origin=" + origin +
                ", direction=" + direction +
                ", brightnessCoef=" + brightnessCoef +
                ", L=" + L +
                '}';
    }
}


