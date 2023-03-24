import java.util.ArrayList;

public class Camera {

    public Vector3D cameraCord;
    public Vector3D cameraDirection;

    Camera(double x_cord, double y_cord, double z_cord) {
        this.cameraCord = new Vector3D(x_cord, y_cord, z_cord);
    }

    Camera(double x_dir, double y_dir, Vector3D cameraCord) {
        this.cameraCord = cameraCord;
        this.cameraDirection = new Vector3D(x_dir, y_dir, -1);
    }

    public RayOfLight createRayCamera(ArrayList<SourceLight> sourceLights) {
        RayOfLight ray = new RayOfLight(this.cameraCord, this.cameraDirection);
        ray.fillWaveLength(sourceLights);
        return ray;
    }

    public void setCameraDirection(double x_dir, double y_dir) {
        this.cameraDirection= new Vector3D(x_dir, y_dir, -1);
    }

    @Override
    public String toString() {
        return "Camera{" +
                "cameraCord=" + cameraCord +
                ", cameraDirection=" + cameraDirection +
                '}';
    }
}
