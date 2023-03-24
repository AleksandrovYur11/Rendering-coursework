import java.util.HashMap;

public class Material {

    public HashMap<Double, Double> diffuseReflection  = new HashMap<>();

    Material(){};
    Material(HashMap<Double, Double> diffuseReflection){
        this.diffuseReflection = diffuseReflection;
    }

}
