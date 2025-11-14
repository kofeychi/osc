package dev.kofeychi.osc;

import net.minecraft.util.math.Vec3d;
import org.joml.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class Maths {
    public static List<Vec3d> circle(Vec3d origin, float radius, int segments) {
        List<Vector2d> points = new ArrayList<>();
        double angleIncrement = 2 * Math.PI / segments;

        for (int i = 0; i < segments; i++) {
            double angle = i * angleIncrement;
            double x = origin.x + radius * Math.cos(angle);
            double y = origin.z + radius * Math.sin(angle);
            points.add(new Vector2d(x, y));
        }
        return points.stream().map(p -> new Vec3d(p.x,origin.y,p.y)).toList();
    }
}
