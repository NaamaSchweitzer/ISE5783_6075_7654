package unittests.renderer;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 * 
 * @author dzilb
 */
public class ReflectionRefractionTests {
	private Scene scene = new Scene("Test scene");

	/** Produce a picture of a sphere lighted by a spot light */
	@Test
	public void twoSpheres() {
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(150, 150).setVPDistance(1000);

		scene.geometries.add( //
				new Sphere(50d, new Point(0, 0, -50)).setEmission(new Color(BLUE)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(25d, new Point(0, 0, -50)).setEmission(new Color(RED)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
		scene.lights.add( //
				new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
						.setkL(0.0004).setkQ(0.0000006));

		camera.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}

	/** Produce a picture of a sphere lighted by a spot light */
	@Test
	public void twoSpheresOnMirrors() {
		Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(2500, 2500).setVPDistance(10000); //

		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

		scene.geometries.add( //
				new Sphere(400d, new Point(-950, -900, -1000)).setEmission(new Color(0, 50, 100)) //
						.setMaterial(
								new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(new Double3(0.5, 0, 0))),
				new Sphere(200d, new Point(-950, -900, -1000)).setEmission(new Color(100, 50, 20)) //
						.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500), new Point(670, 670, 3000)) //
						.setEmission(new Color(20, 20, 20)) //
						.setMaterial(new Material().setKr(1)),
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
						new Point(-1500, -1500, -2000)) //
								.setEmission(new Color(20, 20, 20)) //
								.setMaterial(new Material().setKr(new Double3(0.5, 0, 0.4))));

		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
				.setkL(0.00001).setkQ(0.000005));

		ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light with a partially
	 * transparent Sphere producing partial shadow
	 */
	@Test
	public void trianglesTransparentSphere() {
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(200, 200).setVPDistance(1000);

		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

		scene.geometries.add( //
				new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Sphere(30d, new Point(60, 50, -50)).setEmission(new Color(BLUE)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
				.setkL(4E-5).setkQ(2E-7));

		ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}

	/**
	 * produce a picture of a teddy bear holding a balloon that has another balloon
	 * in it, sitting on a reflected floor
	 */
	@Test // in the test we made teddy bear that reflected on floor with a balloon in
			// balloon.
	public void TeddyBear() {
		Camera camera = new Camera(new Point(0, 0, 1700), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(150, 150).setVPDistance(1000);

		scene.geometries.add(
				// ears
				new Sphere(20, new Point(-50, 75, -100)) // the center sphere- left ear
						.setEmission(new Color(153, 102, 0))
						.setMaterial(new Material().setKd(0.6).setKs(0.9).setShininess(1000).setKt(0.15).setKr(0)),
				new Sphere(20, new Point(50, 75, -100)) // the center sphere-right ear
						.setEmission(new Color(153, 102, 0))
						.setMaterial(new Material().setKd(0.6).setKs(0.9).setShininess(1000).setKt(0.15).setKr(0)),

				new Sphere(15, new Point(-48, 73, -100)) // the center sphere- left ear inner
						.setEmission(new Color(255, 0, 128))
						.setMaterial(new Material().setKd(0.6).setKs(0.9).setShininess(1000).setKt(0.3).setKr(0)),
				new Sphere(15, new Point(48, 73, -100)) // the center sphere-right ear inner
						.setEmission(new Color(255, 0, 128))
						.setMaterial(new Material().setKd(0.6).setKs(0.9).setShininess(1000).setKt(0.3).setKr(0)),

				// blush
				new Sphere(8, new Point(25, 15, -100)) // the right sphere- blush
						.setEmission(new Color(255, 0, 128))
						.setMaterial(new Material().setKd(0.6).setKs(0.9).setShininess(1000).setKt(0.1).setKr(0)),
				new Sphere(8, new Point(-25, 15, -100)) // the left sphere- blush
						.setEmission(new Color(255, 0, 128))
						.setMaterial(new Material().setKd(0.6).setKs(0.9).setShininess(1000).setKt(0.2).setKr(0)),

				// eyes
				new Sphere(7, new Point(18, 22, -45)) // right eye
						.setEmission(new Color(69, 43, 29)),
				new Sphere(7, new Point(-18, 22, -45)) // left eye
						.setEmission(new Color(69, 43, 29)),
				new Triangle(new Point(-5, 14, 95), new Point(5, 14, 115), new Point(0, 10, 130))
						.setEmission(new Color(69, 43, 29))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)), // triangle of nose

				// head
				new Sphere(60, new Point(0, 40, -100)) // the center sphere-head
						.setEmission(new Color(153, 102, 0))
						.setMaterial(new Material().setKd(0.6).setKs(0.9).setShininess(1000).setKt(0.15).setKr(0)),

				// body
				new Sphere(50, new Point(0, -40, -120)) // the center body
						.setEmission(new Color(153, 102, 0))
						.setMaterial(new Material().setKd(0.6).setKs(0.9).setShininess(1000).setKt(0.15).setKr(0)),

				// hands
				new Sphere(13, new Point(-47, -17, -100)) // the center sphere- left hand
						.setEmission(new Color(153, 102, 0))
						.setMaterial(new Material().setKd(0.6).setKs(0.9).setShininess(1000).setKt(0.15).setKr(0)),
				new Sphere(13, new Point(47, -17, -100)) // the center sphere-right hand
						.setEmission(new Color(153, 102, 0))
						.setMaterial(new Material().setKd(0.6).setKs(0.9).setShininess(1000).setKt(0.15).setKr(0)),

				// legs
				new Sphere(20, new Point(-40, -70, -100)) // the center sphere- left leg
						.setEmission(new Color(153, 102, 0))
						.setMaterial(new Material().setKd(0.6).setKs(0.9).setShininess(1000).setKt(0.15).setKr(0)),
				new Sphere(20, new Point(40, -70, -100)) // the center sphere-right leg
						.setEmission(new Color(153, 102, 0))
						.setMaterial(new Material().setKd(0.6).setKs(0.9).setShininess(1000).setKt(0.15).setKr(0)),

				// balloons
				new Sphere(36, new Point(-95, 70, -120)) // the center sphere- baloon
						.setEmission(new Color(255, 0, 0)) //
						.setMaterial(new Material().setKd(0).setKs(0.2).setShininess(1000).setKt(0.6).setKr(0.2)),
				new Sphere(20, new Point(-95, 70, -120)) // the center sphere- baloon
						.setEmission(new Color(0, 100, 100))
						.setMaterial(new Material().setKd(0.6).setKs(0.9).setShininess(1000).setKt(0).setKr(1)),
				new Triangle(new Point(-70, 42, 95), new Point(-67, 42, 115), new Point(-40, -17, 130))
						.setEmission(new Color(java.awt.Color.BLACK))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)), // line of balloons

				// floor
				new Triangle(new Point(-150, -150, -115), new Point(150, -150, -115), new Point(150, 0, -150)) // floor
						.setEmission(new Color(167, 199, 231)).setMaterial(new Material().setKr(0.2)),
				new Triangle(new Point(-150, -150, -115), new Point(-150, -5, -150), new Point(150, 0, -150)) // floor
						.setEmission(new Color(167, 199, 231)).setMaterial(new Material().setKr(0.2)));

		scene.lights.add( // add spot light
				new SpotLight(new Color(1000, 600, 400), new Point(-250, 400, 1500), new Vector(-40, -1, -2))
						.setkL(0.0004).setkQ(0.0000006));
		scene.lights.add(new DirectionalLight(new Color(50, 100, 0), new Vector(-50, -1, -1))); // add directional light

		scene.setBackground(new Color(167, 199, 231));
		ImageWriter imageWriter = new ImageWriter("TeddyBear", 500, 500);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();

	}


}
