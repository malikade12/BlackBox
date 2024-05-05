package com.program;

import javafx.scene.Group;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HexagonTest {

    private Group root;

    @BeforeEach
    void setUp() {
        root = new Group();
        //Initialize the BoardItems.allHexagons list
        List<List<Hexagon>> allHexagons = new ArrayList<>();
        List<Hexagon> row1 = new ArrayList<>();
        row1.add(new Hexagon(100, 100, 0, 1));
        row1.add(new Hexagon(200, 100, 0, 2));
        allHexagons.add(row1);
        //Set the allHexagons list in the BoardItems class
        BoardItems.allHexagons = allHexagons;
    }

    @Test
    void testConstructor() {
        Hexagon hexagon = new Hexagon(100, 100, 0, 3);
        //Check that the hexagon is not null and thus created
        assertNotNull(hexagon);
    }
    @Test
    void testConstructorID() {
        Hexagon hexagon = new Hexagon(100, 100, 0, 3);
        //Verify that the hexagon's properties are initialized correctly
        //by checking if the hexagon is given the correct ID
        assertEquals(4, hexagon.Id);
    }
    @Test
    void testConstructorRowID() {
        Hexagon hexagon = new Hexagon(100, 100, 0, 3);
        //Verify that the hexagon's properties are initialized correctly
        //by checking if the hexagon is given the correct row ID
        assertEquals(0, hexagon.rowId);
    }

    @Test
    void testDraw() {
        Hexagon hexagon = new Hexagon(100, 100, 0, 0);
        //Draw the hexagon
        Polygon drawn = hexagon.draw(root, 50);
        //And then check that the drawn hexagon is not null and thus
        //was actually drawn
        assertNotNull(drawn);
    }

    @Test
    void testHexagonAddedToRoot() {
        Hexagon hexagon = new Hexagon(100, 100, 0, 0);
        //Draw the hexagon on the root
        Polygon drawn = hexagon.draw(root, 50);
        //And check that the hexagon shape is added to the root
        assertTrue(root.getChildren().contains(drawn));
    }

    @Test
    void testCreateNumberText() {
        Hexagon hexagon = new Hexagon(100, 100, 0, 0);
        //Draw the hexagon on the root
        Polygon drawn = hexagon.draw(root, 50);
        //And check if the hexagon center text got added
        //This thus checks if the method createNumberText is working correctly
        assertTrue(root.getChildren().stream().anyMatch(node -> node instanceof Text));
    }


    @Test
    void testCalculatePolygonCenterXCoordinate() {
        Hexagon hexagon = new Hexagon(100, 100, 0, 0);
        //Calculate the center of the hexagon
        double[] center = Hexagon.calculatePolygonCenter(hexagon);
        //Verify that the calculated center x-coordinate is correct
        assertEquals(-0.5, center[0]);
    }

    @Test
    void testCalculatePolygonCenterYCoordinate() {
        Hexagon hexagon = new Hexagon(100, 100, 0, 0);
        //Calculate the center of the hexagon
        double[] center = Hexagon.calculatePolygonCenter(hexagon);
        //Verify that the calculated center y-coordinate is correct
        assertEquals(-0.5, center[1]);
    }
}
