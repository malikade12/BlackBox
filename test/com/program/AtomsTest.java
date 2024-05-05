package com.program;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AtomsTest {

    private Group root;

    @BeforeEach
    void setUp() {
        root = new Group();
        //Initialize the BoardItems.allAtoms list
        List<Atoms> allAtoms = new ArrayList<>();
        allAtoms.add(new Atoms(root, 100, 100, 1));
        allAtoms.add(new Atoms(root, 200, 200, 2));
        //Set the allAtoms list in the BoardItems class
        BoardItems.allAtoms = allAtoms;
    }


    @Test
    void testConstructor() {
        Atoms atom = new Atoms(root, 100, 100, 1);
        //Verify that the atom is not null
        assertNotNull(atom);
        //Verify that the atom's properties are initialized correctly
        assertEquals(1, atom.hexId);
        assertTrue(atom.isVisible());
        //Verify that the orbit is created and added to the root
        assertNotNull(atom.orbit);
        assertTrue(root.getChildren().contains(atom.orbit));
    }

    @Test
    void testAtomsInvisible() {
        //Make all atoms invisible
        Atoms.makeAllAtomsInvisible();
        //Verify that all atoms and orbits are invisible
        for (Atoms atom : BoardItems.allAtoms) {
            assertFalse(atom.isVisible());
            assertFalse(atom.orbit.isVisible());
        }
    }



    @Test
    void testAtomsVisible() {
        //Make all atoms visible
        Atoms.makeAllAtomsVisible();
        //Verify that all atoms and orbits are visible
        for (Atoms atom : BoardItems.allAtoms) {
            assertTrue(atom.isVisible());
            assertTrue(atom.orbit.isVisible());
        }
    }
}
