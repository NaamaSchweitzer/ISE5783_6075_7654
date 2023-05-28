package primitives;

/**
 * The Material class holds the attenuation and the shininess factors of a
 * {@link Geometry}
 * These factors are uses for Light-Geometry interaction calculating in the
 * Phong Reflectance Model
 */
public class Material {

    //#region coefficients fields

    /**
     * the diffuse reflection constant, the ratio of reflection of the diffuse term of incoming light
     */
    public Double3 kD = Double3.ZERO;

    /**
     *  the specular reflection constant, the ratio of reflection of the specular term of incoming light
     */
    public Double3 kS = Double3.ZERO;

    /**
     * transparency coefficient (refraction)
     */
    public Double3 kT = Double3.ZERO;
    
    /**
     * reflection coefficient
     */
    public Double3 kR = Double3.ZERO;

    /**
     * the shininess constant for this material, which is larger for surfaces that are smoother and more mirror-like.
     *  When this constant is large the specular highlight is small.
     */
    public int nShininess = 0;

    //#endregion

    /**
     * Setter of the transparency coefficient
     * 
     * @param kt coefficient transparency
     * @return the material
     */
    public Material setKt(Double3 kt) {
        this.kT = kt;
        return this;
    }

    /**
     * Setter of the transparency coefficient
     * 
     * @param kt transparency coefficient
     * @return the material
     */
    public Material setKt(double kt) {
        this.kT = new Double3(kt);
        return this;
    }

    /**
     * Setter of the reflection coefficient
     * 
     * @param kr coefficient of reflection
     * @return the material
     */
    public Material setKr(Double3 kr) {
        this.kR = kr;
        return this;
    }

    /**
     * Setter of the reflection coefficient
     * 
     * @param kr Coefficient of reflection
     * @return the material
     */
    public Material setKr(double kr) {
        this.kR = new Double3(kr);
        return this;
    }

    /**
     * Setter of the kD (diffuse reflection constant) parameter of the material
     * 
     * @param kd, the {@link Double3} value of kD
     * @return the material
     */
    public Material setKd(Double3 kd) {
        this.kD = kd;
        return this;
    }

    /**
     * Setter of the kD (diffuse reflection constant) parameter of the material
     * 
     * @param kd, the {@link double} value of kD
     * @return the material
     */
    public Material setKd(double kd) {
        this.kD = new Double3(kd);
        return this;
    }

    /**
     * Setter of the kS (specular reflection constant) parameter of the material
     * 
     * @param ks, the {@link Double3} value of kS
     * @return the material
     */
    public Material setKs(Double3 ks) {
        this.kS = ks;
        return this;
    }

    /**
     * Setter of the kS (specular reflection constant) parameter of the material
     * 
     * @param ks, the {@link double} value of kS
     * @return the material
     */
    public Material setKs(double ks) {
        this.kS = new Double3(ks);
        return this;
    }

    /**
     * Setter of the nShininess parameter of the material
     * 
     * @param shininess, the value of nShininess
     * @return the material
     */
    public Material setShininess(int shininess) {
        this.nShininess = shininess;
        return this;
    }
}