package com.jcalm;

import java.awt.*;

public interface CollisionDetector {
    Rectangle getBounds();
    boolean collide (CollisionDetector cd);
}
