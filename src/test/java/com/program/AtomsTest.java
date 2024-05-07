package com.program;

import javafx.scene.Group;
import org.junit.*;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AtomsTest {
    private Group root;

    @Before
    public void setUp() {
        root = new Group();
        //Initialize the BoardItems.allAtoms list
        List<Atoms> allAtoms = new ArrayList<>();
        allAtoms.add(new Atoms(root, 100, 100, 1));
        allAtoms.add(new Atoms(root, 200, 200, 2));
        //Set the allAtoms list in the BoardItems class
        BoardItems.AllAtoms = allAtoms;
    }


    @Test
    public void testConstructor() {
        Atoms atom = new Atoms(root, 100, 100, 1);
        //Check that the atom is not null and thus created
        assertNotNull(atom);
    }
    @Test
    public void testConstructorID() {
        Atoms atom = new Atoms(root, 100, 100, 3);
        //Verify that the atom's properties are initialized correctly
        //by checking if the atom is made on the correct hexagon
        assertEquals(3, atom.HexId);
    }
    @Test
    public void testConstructorVisibility() {
        Atoms atom = new Atoms(root, 100, 100, 1);
        //Make sure the atom is visible on screen
        assertTrue(atom.isVisible());
    }
    @Test
    public void testConstructorOrbit() {
        Atoms atom = new Atoms(root, 100, 100, 1);
        //Check if the orbit was created
        assertNotNull(atom.Orbit);
    }
    @Test
    public void testOrbitAddedToRoot() {
        Atoms atom = new Atoms(root, 100, 100, 1);
        //Verify that the orbit is added to the root
        assertTrue(root.getChildren().contains(atom.Orbit));
    }

    @Test
    public void testAtomsInvisible() {
        //Make all atoms invisible
        Atoms.MakeAllAtomsInvisible();
        //Check that all atoms are invisible
        for (Atoms atom : BoardItems.AllAtoms) {
            assertFalse(atom.isVisible());
        }
    }
    @Test
    public void testAtomsOrbitInvisible() {
        //Make all atoms invisible
        Atoms.MakeAllAtomsInvisible();
        //Check that all orbits are invisible
        for (Atoms atom : BoardItems.AllAtoms) {
            assertFalse(atom.Orbit.isVisible());
        }
    }

    @Test
    public void testAtomsVisible() {
        //Make all atoms visible
        Atoms.MakeAllAtomsVisible();
        //Check that all atoms are visible
        for (Atoms atom : BoardItems.AllAtoms) {
            assertTrue(atom.isVisible());
        }
    }
    @Test
    public void testAtomsOrbitVisible() {
        //Make all atoms visible
        Atoms.MakeAllAtomsVisible();
        //Check that all orbits are visible
        for (Atoms atom : BoardItems.AllAtoms) {
            assertTrue(atom.Orbit.isVisible());
        }
    }}
