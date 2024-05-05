package com.program;

import javafx.scene.Group;
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
        //Check that the atom is not null and thus created
        assertNotNull(atom);
    }
    @Test
    void testConstructorID() {
        Atoms atom = new Atoms(root, 100, 100, 3);
        //Verify that the atom's properties are initialized correctly
        //by checking if the atom is made on the correct hexagon
        assertEquals(3, atom.hexId);
    }
    @Test
    void testConstructorVisibility() {
        Atoms atom = new Atoms(root, 100, 100, 1);
        //Make sure the atom is visible on screen
        assertTrue(atom.isVisible());
    }
    @Test
    void testConstructorOrbit() {
        Atoms atom = new Atoms(root, 100, 100, 1);
        //Check if the orbit was created
        assertNotNull(atom.orbit);
    }
    @Test
    void testOrbitAddedToRoot() {
        Atoms atom = new Atoms(root, 100, 100, 1);
        //Verify that the orbit is added to the root
        assertTrue(root.getChildren().contains(atom.orbit));
    }

    @Test
    void testAtomsInvisible() {
        //Make all atoms invisible
        Atoms.makeAllAtomsInvisible();
        //Check that all atoms are invisible
        for (Atoms atom : BoardItems.allAtoms) {
            assertFalse(atom.isVisible());
        }
    }
    @Test
    void testAtomsOrbitInvisible() {
        //Make all atoms invisible
        Atoms.makeAllAtomsInvisible();
        //Check that all orbits are invisible
        for (Atoms atom : BoardItems.allAtoms) {
            assertFalse(atom.orbit.isVisible());
        }
    }

    @Test
    void testAtomsVisible() {
        //Make all atoms visible
        Atoms.makeAllAtomsVisible();
        //Check that all atoms are visible
        for (Atoms atom : BoardItems.allAtoms) {
            assertTrue(atom.isVisible());
        }
    }
    @Test
    void testAtomsOrbitVisible() {
        //Make all atoms visible
        Atoms.makeAllAtomsVisible();
        //Check that all orbits are visible
        for (Atoms atom : BoardItems.allAtoms) {
            assertTrue(atom.orbit.isVisible());
        }
    }
}
