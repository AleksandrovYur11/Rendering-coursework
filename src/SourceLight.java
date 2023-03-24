import java.util.HashMap;

public class SourceLight {

    public HashMap<Double, Double> spectralIntensity;
    public Vector3D position;
    double intensityTotal = 0;

    public SourceLight(Vector3D position, double intensityTotal, HashMap<Double, Double> spectralIntensity) {
        this.position = position;
        this.intensityTotal = intensityTotal;
        this.spectralIntensity = spectralIntensity;
    }

    @Override
    public String toString() {
        return "SourceLight{" +
                "spectralIntensity=" + spectralIntensity +
                ", position=" + position +
                ", intensityTotal=" + intensityTotal +
                '}';
    }
}
