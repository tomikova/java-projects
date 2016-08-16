package hr.fer.zemris.java.student0036461026.hw08;

import hr.fer.zemris.java.raytracer.model.*;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for Sphere class.
 * @author Tomislav
 *
 */

public class SphereTest {

    @Test
    public void findClosestRayIntersectionTest() {
            Ray ray = Ray.fromPoints(new Point3D(), new Point3D(6, 5, 4));
            
            Sphere sphere = new Sphere(new Point3D(3.5, 3.5, 3.5), 2, 1, 1, 1, 0.5, 0.5, 0.5, 10);
            
            RayIntersection intersection = sphere.findClosestRayIntersection(ray);
            
            Assert.assertEquals(1, intersection.getKdr(), 1e-6);
            Assert.assertEquals(1, intersection.getKdg(), 1e-6);  
            Assert.assertEquals(1, intersection.getKdb(), 1e-6);
            Assert.assertEquals(0.5, intersection.getKrr(), 1e-6);        
            Assert.assertEquals(0.5, intersection.getKrg(), 1e-6);
            Assert.assertEquals(0.5, intersection.getKrb(), 1e-6);
            Assert.assertEquals(10, intersection.getKrn(), 1e-6);
            
            Assert.assertEquals(intersection.getPoint().x, 2.8976577014716707, 1e-15);
            Assert.assertEquals(intersection.getPoint().y, 2.4147147512263927, 1e-15);
            Assert.assertEquals(intersection.getPoint().z, 1.9317718009811140, 1e-15);
    }

    @Test
    public void findClosestRayIntersectionZeroPointTest() {
            Ray ray = Ray.fromPoints(new Point3D(), new Point3D(1, 0, 0));
            
            Sphere sphere = new Sphere(new Point3D(0, 1, 0), 1, 1, 1, 1, 0.5, 0.5, 0.5, 10);
            RayIntersection intersection = sphere.findClosestRayIntersection(ray);

            Assert.assertEquals(1, intersection.getKdr(), 1e-6);
            Assert.assertEquals(1, intersection.getKdg(), 1e-6);  
            Assert.assertEquals(1, intersection.getKdb(), 1e-6);
            Assert.assertEquals(0.5, intersection.getKrr(), 1e-6);        
            Assert.assertEquals(0.5, intersection.getKrg(), 1e-6);
            Assert.assertEquals(0.5, intersection.getKrb(), 1e-6);
            Assert.assertEquals(10, intersection.getKrn(), 1e-6);
            
            Assert.assertEquals(intersection.getPoint().x, 0, 1e-15);
            Assert.assertEquals(intersection.getPoint().y, 0, 1e-15);
            Assert.assertEquals(intersection.getPoint().z, 0, 1e-15);
    }

    @Test
    public void findClosestRayIntersectionNoIntersectionTest() {
            Ray ray = Ray.fromPoints(new Point3D(), new Point3D(7, 8, 6));
            
            Sphere sphere = new Sphere(new Point3D(10, 3.5, 3.5), 1, 1, 1, 1, 0.5, 0.5, 0.5, 10);
            
            RayIntersection intersection = sphere.findClosestRayIntersection(ray);
            Assert.assertEquals(intersection,null);
    }

}